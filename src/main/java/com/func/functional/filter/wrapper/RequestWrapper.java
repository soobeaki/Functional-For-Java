package com.func.functional.filter.wrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
	super(request);
	body = request.getInputStream().readAllBytes();
    }

    public String getBody() {
	return new String(body, StandardCharsets.UTF_8);
    }

    public String getQueryString() {
	return super.getQueryString();
    }
}
