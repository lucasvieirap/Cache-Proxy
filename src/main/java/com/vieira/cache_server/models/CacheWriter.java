package com.vieira.cache_server.models;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.vieira.cache_server.domain.Cache;
import com.vieira.cache_server.domain.CacheObject;

public class CacheWriter {
	static public void updateCache(File file, Cache cache) throws ParseException, IOException {
		JSONObject jsonObject = new JSONObject();
		for (CacheObject cacheObj : cache.getData().values()) {
			jsonObject.put(cacheObj.getData().get("url"), cacheObj.getUrlJsonObject());
		}

		FileWriter fileWriter = new FileWriter(file);

		fileWriter.write(jsonObject.toJSONString());
		fileWriter.flush();
		fileWriter.close();
	}

	static public void cache(File file, CacheObject cache) throws ParseException, IOException {
		JSONObject jsonObject = CacheReader.readCache(file);

		if (cache.getData().get("response").length() == 0) {
			System.out.println("CACHEOBJ CACHE IS EMPTY");	
			return;
		}

		jsonObject.put(cache.getData().get("url"), cache.getUrlJsonObject());

		FileWriter fileWriter = new FileWriter(file);

		fileWriter.write(jsonObject.toJSONString());
		fileWriter.flush();
		fileWriter.close();
	}
}
