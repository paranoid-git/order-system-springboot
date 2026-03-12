package com.order.management.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import com.order.management.util.JWTUtil;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.order.management.util.ResponseUtil;

@Component
public class JWTFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;
  private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
  private static final ResponseUtil responseUtil = new ResponseUtil();

  public JWTFilter(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  private static final List<String> EXCLUDED_PATHS = List.of(
      "/api/auth/login",
      "/api/auth/register");

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String authHeader = request.getHeader("Authorization");
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);

        String username = jwtUtil.extractUsername(token);
        var auth = new UsernamePasswordAuthenticationToken(
            username, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else {
        throw new Exception("No token provided");
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      try {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(responseUtil
            .makeResponse("You are not authorized, please log in.", HttpStatus.FORBIDDEN).getBody()
            .toString());
        return;
      } catch (java.io.IOException e2) {
        logger.info(e2.toString());
      }

    }
  }
}
