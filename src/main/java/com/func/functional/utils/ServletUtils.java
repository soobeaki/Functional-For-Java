package com.func.functional.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.func.functional.http.model.BaseHeader;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

/**
 * 현재 요청의 컨텍스트를 관리하는 유틸리티 클래스입니다.
 * 
 * <p>
 * 이 클래스는 현재 스레드의 {@link HttpServletRequest} 및 {@link HttpServletResponse} 객체를
 * 가져오는 메서드와, 요청 헤더에서 정보를 추출하는 메서드, 요청 URI에 대한 조건을 검사하는 메서드를 제공합니다.
 * </p>
 */
@UtilityClass
public class ServletUtils {

    /**
     * 현재 스레드의 {@link ServletRequestAttributes}를 반환합니다.
     * 
     * @return 현재 스레드의 {@link ServletRequestAttributes} 객체 또는 {@code null}
     */
    public ServletRequestAttributes getAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 현재 스레드의 {@link HttpServletRequest} 객체를 반환합니다.
     * 
     * @return 현재 스레드의 {@link HttpServletRequest} 객체 또는 {@code null}
     */
    public HttpServletRequest getRequest() {
        return getAttributes() != null ? getAttributes().getRequest() : null;
    }

    /**
     * 현재 스레드의 {@link HttpServletResponse} 객체를 반환합니다.
     * 
     * @return 현재 스레드의 {@link HttpServletResponse} 객체 또는 {@code null}
     */
    public HttpServletResponse getResponse() {
        return getAttributes() != null ? getAttributes().getResponse() : null;
    }

    /**
     * 현재 요청의 헤더에서 지정된 이름의 값을 반환합니다.
     * 
     * @param attributeName 헤더 이름
     * @return 요청 헤더의 값 또는 빈 문자열
     */
    public String getRequestHeader(String attributeName) {
        return getAttributes() != null ? getAttributes().getRequest().getHeader(attributeName) : "";
    }

    /**
     * 주어진 HttpServletRequest에서 전체 요청 헤더 정보를 반환합니다.
     * 
     * @param request HttpServletRequest 객체
     * @return 요청 헤더 정보를 담고 있는 Map
     */
    public Map<String, String> getRequestHeader() {
        Map<String, String> headerMap = new HashMap<>();

        if (getRequest() != null) {
            Enumeration<String> headerNames = getRequest().getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = getAttributes().getRequest().getHeader(headerName);
                headerMap.put(headerName, headerValue);
            }
        }
        return headerMap;
    }

    /**
     * 요청 헤더에서 요청 ID를 가져옵니다.
     * 
     * <p>
     * 헤더에서 요청 ID를 가져오며, 값이 비어 있거나 {@code null}인 경우 랜덤 UUID를 결합하여 반환합니다.
     * </p>
     * 
     * @return 요청 ID 또는 랜덤 UUID
     */
    public String getRequestId() {
        String requestId = getRequestHeader(BaseHeader.REQUEST_ID);
        return StringUtils.isBlank(requestId) ? UUID.randomUUID().toString() : requestId;
    }

    /**
     * 요청 헤더에서 사용자 ID를 가져옵니다.
     * 
     * <p>
     * 헤더에서 사용자 ID를 가져오며, 값이 비어 있거나 {@code null}인 경우 "anonymous"를 반환합니다.
     * </p>
     * 
     * @return 사용자 ID 또는 "anonymous"
     */
    public String getUserId() {
        String userId = getRequestHeader(BaseHeader.USER_ID);
        return StringUtils.isBlank(userId) ? "anonymous" : userId;
    }

    /**
     * 현재 요청이 사이퍼 요청인지 판단합니다.
     * 
     * <p>
     * 요청 URI가 빈 문자열이거나 {@code null}인 경우, 또는 요청 URI가 지정된 정규 표현식 패턴과 일치하지 않는 경우, 사이퍼
     * 요청이 아닌 것으로 간주합니다. 또한, 사이퍼 적용 여부를 결정하는 헤더 값이 "4885"가 아닌 경우 사이퍼 요청으로 판단합니다.
     * </p>
     * 
     * @return 사이퍼 요청이면 {@code true}, 그렇지 않으면 {@code false}
     */
    public Boolean isCipher() {
        if (getRequest() == null || StringUtils.isBlank(getRequest().getRequestURI())) {
            return false;
        }

        String url = getRequest().getRequestURI();
        if (url.startsWith("^\\/v\\d+.*")) {
            return !"4885".equalsIgnoreCase(getRequestHeader(BaseHeader.CRYPTO_APPLY));
        }
        return false;
    }
}
