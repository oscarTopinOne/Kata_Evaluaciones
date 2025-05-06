package com.kata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsFilterConfig {

    @Bean
    public Filter corsFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {

                HttpServletResponse response = (HttpServletResponse) res;

                response.setHeader("Access-Control-Allow-Origin", "https://kata-evaluaciones-fd57.vercel.app");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Headers", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");

                chain.doFilter(req, res);
            }

            @Override
            public void init(FilterConfig filterConfig) {}

            @Override
            public void destroy() {}
        };
    }
}

