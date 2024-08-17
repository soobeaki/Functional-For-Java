package com.func.functional.configs.properties;

import java.util.Iterator;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API 서버에 대한 구성 속성을 로드하는 클래스입니다.
 * 
 * <p>
 * 이 클래스는 애플리케이션의 설정 파일에서 'api-server' 접두어를 가진 속성을 로드하고,
 *  API 서버 정보를 처리하는 메소드를 제공합니다.
 * </p>
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api-server")
public class ApiServerConfigProperties {

    /** API 서버 목록 */
    private List<ApiServer> servers;

    /**
     * API 서버 정보 클래스
     * 
     * <p>
     * 각 API 서버의 이름, 도메인, API 키를 나타냅니다.
     * </p>
     */
    @Getter
    @Setter
    @ToString
    public static class ApiServer {
	private String name;
	private String domain;
	private String key;
    }

    /**
     * 주어진 이름에 해당하는 API 서버 정보를 반환합니다.
     * 
     * <p>
     * API 서버 목록에서 이름이 일치하는 서버 정보를 찾고, 도메인 끝에 '/'가 없으면 추가합니다.
     * </p>
     * 
     * @param name API 서버 이름
     * @return 일치하는 API 서버 정보 또는 null
     */
    public ApiServer getApiProperties(String name) {
	if (servers == null) {
	    return null;
	}

	for (Iterator<ApiServer> iterator = servers.iterator(); iterator.hasNext();) {
	    ApiServer props = iterator.next();
	    if (props.getName().equalsIgnoreCase(name)) {

		// 도메인 끝에 '/'가 없으면 추가
		if (!props.getDomain().endsWith("/")) {
		    props.setDomain(props.getDomain() + "/");
		}

		return props;
	    }
	}

	return null;
    }

    /**
     * 주어진 이름에 해당하는 API 서버의 API 키를 반환합니다.
     * 
     * <p>
     * API 서버 목록에서 이름이 일치하는 서버의 API 키를 반환합니다.
     * </p>
     * 
     * @param name API 서버 이름
     * @return API 키 또는 null
     */
    public String getApiKey(String name) {
	if (servers == null) {
	    return null;
	}

	for (Iterator<ApiServer> iterator = servers.iterator(); iterator.hasNext();) {
	    ApiServer props = iterator.next();

	    if (props.getName().equalsIgnoreCase(name)) {
		return props.getKey();
	    }

	}

	return null;
    }

}
