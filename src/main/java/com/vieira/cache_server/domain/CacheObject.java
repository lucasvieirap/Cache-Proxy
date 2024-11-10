package com.vieira.cache_server.domain;

import java.time.LocalDateTime;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class CacheObject {
	private HashMap<String, String> urlData;
	private HashMap<String, JSONObject> urlObject;
	private JSONObject urlJsonObject;
	private LocalDateTime lastUsed;

	public CacheObject(String url, String method, String response) {
		this.lastUsed = LocalDateTime.now();
		this.urlData = new HashMap<String, String>();
		urlData.put("method", method);
		urlData.put("url", url);
		urlData.put("response", response);
		urlData.put("lastUsed", this.lastUsed.toString());

		urlJsonObject = new JSONObject(urlData);

		this.urlObject = new HashMap<String, JSONObject>();
		urlObject.put(this.getData().get("url"), urlJsonObject);
	}

	public HashMap<String, String> getData() {
		return this.urlData;
	}

	public JSONObject getUrlJsonObject() {
		return this.urlJsonObject;
	}

	public void update(CacheObject cacheObj) {
		this.lastUsed = LocalDateTime.now();
		this.urlData = new HashMap<String, String>();
		urlData.put("method", cacheObj.getData().get("method"));
		urlData.put("response", cacheObj.getData().get("response"));
		urlData.put("lastUsed", this.lastUsed.toString());
		urlData.put("url", this.getData().get("url"));

		urlJsonObject = new JSONObject(urlData);

		this.urlObject = new HashMap<String, JSONObject>();
		urlObject.put(this.getData().get("url"), urlJsonObject);
	}
}

