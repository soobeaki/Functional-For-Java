package com.func.functional.configs.properties;

import java.util.Iterator;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api-server")
public class ApiServerConfigProperties {

	private List<ApiServer> servers;

	@Getter
	@Setter
	@ToString
	public static class ApiServer {
		private String name;
		private String domain;
	}

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

}
