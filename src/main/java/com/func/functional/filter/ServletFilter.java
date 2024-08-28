package com.func.functional.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.func.functional.filter.wrapper.RequestWrapper;
import com.func.functional.filter.wrapper.ResponseWrapper;
import com.func.functional.http.model.BaseHeader;

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

    /** 현재 활성화된 Spring 프로파일 */
    @Value("${spring.profiles.active}")
    String activatedProfile;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestWrapper  requestWrapper  = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        /**
         * CORS 헤더 설정
         */
        response.setHeader("Server-Phase", activatedProfile);
        response.setHeader(BaseHeader.CRYPTO_APPLY, request.getHeader(BaseHeader.CRYPTO_APPLY));

        // OPTIONS 요청에 대한 빠른 응답 처리
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 필터 체인 실행
        filterChain.doFilter(requestWrapper, responseWrapper);

        // 요청 쿼리 문자열
        log.info("Query String: {}", requestWrapper.getQueryString());

        // 요청 쿼리 본문
        log.info("Response Body: {}", new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));

        // 응답 본문을 원래 응답에 복사
        responseWrapper.copyBodyToResponse();

    }
}
