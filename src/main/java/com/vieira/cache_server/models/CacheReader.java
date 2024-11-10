package com.vieira.cache_server.models;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CacheReader {
	static public JSONObject readCache(File file) throws ParseException, IOException {
		if (file.length() == 0) {
			return new JSONObject();
		}

		JSONParser parser = new JSONParser();
		JSONObject o = (JSONObject) parser.parse(new FileReader(file.getPath()));

		return o;
	}

	static public String getFieldCache(File file, String url, String field) throws ParseException, IOException {
		JSONObject jsonObject = CacheReader.readCache(file);
		JSONObject urlDataJsonObject = (JSONObject) jsonObject.get(url);

		if (urlDataJsonObject != null) {
			String urlData = urlDataJsonObject.get(field).toString();
			return urlData;
		}

		return "";
	}

	static public JSONObject getCache(File file, String url) throws ParseException, IOException {
		JSONObject jsonObject = CacheReader.readCache(file);
		JSONObject urlDataJsonObject = (JSONObject) jsonObject.get(url);

		return urlDataJsonObject;
	}
}
