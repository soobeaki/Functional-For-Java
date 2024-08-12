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
@ConfigurationProperties(prefix = "secret-key")
public class ApiKeyConfigProperties {

	private List<ApiKey> keys;

	@Getter
	@Setter
	@ToString
	private static class ApiKey {
		String name;
		String key;
	}

	public String getApiKey(String name) {
		if (keys == null) {
			return null;
		}

		for (Iterator<ApiKey> iterator = keys.iterator(); iterator.hasNext();) {
			ApiKey props = iterator.next();

			if (props.getName().equalsIgnoreCase(name)) {
				return props.getKey();
			}

		}
		
		return null;
	}

}
