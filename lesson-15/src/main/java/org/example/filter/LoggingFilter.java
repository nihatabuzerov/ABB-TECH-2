package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("LoggingFilter → Request gəldi");
        chain.doFilter(request, response); // növbəti filter və ya servlet-ə göndər
        System.out.println("LoggingFilter → Response göndərildi");
    }

    @Override
    public void destroy() {
        System.out.println("LoggingFilter destroyed");
    }
}
