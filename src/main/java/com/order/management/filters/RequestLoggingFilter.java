package com.order.management.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    var request = (HttpServletRequest) req;
    var response = (HttpServletResponse) res;

    long start = System.currentTimeMillis();

    try {
      chain.doFilter(request, response);
    } finally {
      long duration = System.currentTimeMillis() - start;
      int status = response.getStatus();

      // Log level based on status code: errors stand out in Loki/Grafana
      if (status >= 500) {
        log.error("{} {} → {} in {}ms",
            request.getMethod(), request.getRequestURI(), status, duration);
      } else if (status >= 400) {
        log.warn("{} {} → {} in {}ms",
            request.getMethod(), request.getRequestURI(), status, duration);
      } else {
        log.info("{} {} → {} in {}ms",
            request.getMethod(), request.getRequestURI(), status, duration);
      }
    }
  }
}
