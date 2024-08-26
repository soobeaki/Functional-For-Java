package com.func.functional.filter.wrapper;

import java.nio.charset.StandardCharsets;

import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseWrapper extends ContentCachingResponseWrapper {

    public ResponseWrapper(HttpServletResponse response) {
	super(response);
    }
    
    public String getResponseBody() {
        return new String(getContentAsByteArray(), StandardCharsets.UTF_8);
    }

}
