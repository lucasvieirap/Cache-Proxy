package com.vieira.cache_server.routers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.vieira.cache_server.controller.GetCache;

@RestController
@RequestMapping("/")
public class MainRouter {

	@GetMapping // GET /
	public String getRoot(@RequestBody String body) {
		return GetCache.handleRequest(body);
	}
}
