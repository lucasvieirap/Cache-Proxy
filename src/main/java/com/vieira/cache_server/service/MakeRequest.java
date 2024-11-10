package com.vieira.cache_server.service;

import org.springframework.web.client.RestClient;

public class MakeRequest {
	static public String makeRequest(String url) {
		RestClient client = RestClient.create();
		String result = client.get() // FORWARDING REQUEST
						.uri(url)
						.retrieve()
						.body(String.class);
		return result;
	}
}
