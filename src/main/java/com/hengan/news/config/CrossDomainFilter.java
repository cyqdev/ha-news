package com.hengan.news.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("crossDomainFilter")
public class CrossDomainFilter implements Filter {
  
    private static Logger errorLogger = LoggerFactory.getLogger(CrossDomainFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }  

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
  
            // 跨域  
            String origin = httpRequest.getHeader("Origin");  
            if (origin == null) {  
                httpResponse.addHeader("Access-Control-Allow-Origin", "*");  
            } else {  
                httpResponse.addHeader("Access-Control-Allow-Origin", origin);  
            }  
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,X-Cookie,cache-control,authKey,sessionId");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");  
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");  
            if ("OPTIONS".equals(httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;  
            }  
            chain.doFilter(request, response);  
        } catch (Exception e) {  
            errorLogger.error("Exception in crossDomainFilter.doFilter", e);
        }  
    }  

    @Override
    public void destroy() {  
    }  
}  