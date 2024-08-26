package com.func.functional.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.func.functional.filter.wrapper.RequestWrapper;
import com.func.functional.filter.wrapper.ResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServletFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        // Proceed with the filter chain
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Log the query string from the request
        log.info("Query String: {}", requestWrapper.getQueryString());

        // Log the response body
        log.info("Response Body: {}", new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));

        // Copy the content to the original response
        responseWrapper.copyBodyToResponse();
        
        
    }
}
