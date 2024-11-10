package com.vieira.cache_server.domain;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;

import java.io.File;
import java.io.IOException;

import com.vieira.cache_server.models.CacheWriter;
import com.vieira.cache_server.models.CacheReader;

public class Cache {

	private File file;
	private HashMap<String, CacheObject> data;
	static final private int Capacity = 3;

	public Cache(File file) throws IOException, ParseException {
		this.file = file;
		if (!this.file.exists()) {
			System.out.println("FILE DOESN'T EXISTS");
			System.out.println("Creating File: cache.json...");
			this.file.createNewFile();
		}

		this.data = new HashMap<String, CacheObject>();

		// Load Cache on data ---------------
		JSONObject cacheFileData = (JSONObject) CacheReader.readCache(this.file);
		for (Object o : cacheFileData.values()) {
			JSONObject jsonO = (JSONObject) o;
			CacheObject cacheObj = new CacheObject(jsonO.get("url").toString(), jsonO.get("method").toString(), jsonO.get("response").toString());
			this.data.put(jsonO.get("url").toString(), cacheObj);
		}
	}

	public void add(CacheObject cacheObj) throws IOException, ParseException {
		if (data.size() < Cache.Capacity) {
			data.put(cacheObj.getData().get("url"), cacheObj);	
			return;
		}

		System.out.println("CACHE CAPACITY REACHED");

		// LRU Algorithm
		LocalDateTime latestCache = this.getData().values().stream().map(value -> LocalDateTime.parse(value.getData().get("lastUsed"))).min(LocalDateTime::compareTo).get();
		CacheObject latestCacheObj = cacheObj;

		System.out.println("Last Date Cache: " + latestCache);
		for (CacheObject cacheObject : this.getData().values()) {
			System.out.println(latestCache.toString() + " : " + cacheObject.getData().get("lastUsed") + "( " + cacheObject.getData().get("url") + " )");
			if (LocalDateTime.parse(latestCacheObj.getData().get("lastUsed")).isAfter(LocalDateTime.parse(cacheObject.getData().get("lastUsed")))) {
				latestCacheObj = cacheObject;
			}
		}

		this.getData().remove(latestCacheObj.getData().get("url"));
	}

	public String getCachePayload(String url) throws IOException, ParseException {
		return this.data.get(url).getData().get("response");
	}

	public void cacheAll() throws IOException, ParseException {
		CacheWriter.updateCache(this.file, this);
	}

	public void cache() throws IOException, ParseException {
		for (CacheObject cache : this.data.values()) {
			CacheWriter.cache(this.file, cache);
		}
	}

	public JSONObject getCache(String url) throws IOException, ParseException {
		if (this.file.length() == 0 || this.data.get(url) == null) {
			return null;
		}
		return this.data.get(url).getUrlJsonObject();
	}

	public void updateCache(String key, CacheObject cacheObj) throws IOException, ParseException {
		this.data.get(key).update(cacheObj);
		CacheWriter.cache(this.file, cacheObj);
	}

	public File getFile() {
		return this.file;
	}

	public HashMap<String, CacheObject> getData() {
		return this.data;
	}

	public int getCapacity() {
		return Cache.Capacity;
	}
}
