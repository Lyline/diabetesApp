package com.medicscreen.diabetesmainapp.configuration;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsConfig extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Origin","*");
    response.setHeader("Access-Control-Allow-Methods","GET,PUT,POST,DELETE");
    response.setHeader("Access-Control-Allow-Headers","Authorization,Cache-Control,Content-Type");
    response.setHeader("Access-Control-Expose-Headers","*");
    response.setHeader("Access-Control-Max-Age","3600");

    if(request.getMethod().equalsIgnoreCase("options")){
      response.setStatus(HttpServletResponse.SC_OK);
    }else{
      filterChain.doFilter(request,response);
    }
  }
}
