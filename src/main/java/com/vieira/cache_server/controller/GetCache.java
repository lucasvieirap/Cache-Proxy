package com.vieira.cache_server.controller;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

import com.vieira.cache_server.domain.CacheObject;
import com.vieira.cache_server.domain.Cache;

import com.vieira.cache_server.service.MakeRequest;

public class GetCache{

	public static String handleRequest(String body) {
		String method = "GET";
		String request = method + " " + body + "/";

		try {
			Cache cache = new Cache(new File("cache.json"));

			if (cache.getCache(body) == null) {
				System.out.println("MISS - " + request);
				System.out.println("Forwarding to: " + body + '\n');

				String result = MakeRequest.makeRequest(body);
				if (result == null || result.length() == 0) {
					return "Empty Response";
				}

				cache.add(new CacheObject(body, method, result));
				cache.cacheAll();

				return result;
			}

			System.out.println("HIT - " + request + '\n');
			CacheObject cacheObj = new CacheObject(body, method, cache.getCachePayload(body));
			cache.updateCache(cacheObj.getData().get("url"), cacheObj);

			return cacheObj.getData().get("response");

		} catch(IOException e) {
			e.printStackTrace();
			return "Error on IO";
		} catch(ParseException e) {
			e.printStackTrace();
			return "Error on Parse";
		}
	}
}
