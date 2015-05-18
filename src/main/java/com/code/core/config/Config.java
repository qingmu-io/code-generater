package com.code.core.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Config {

	public static final Map<String, String> config = new HashMap<String, String>();
	static {
			config.put("basePackage", "com.ke");
			config.put("queryModelPackage", "com.ke.model.query");
			
			config.put("mapperPath", "/src/main/java");
			config.put("mapperPackage", "com.ke.mapper");
			
			config.put("mappingPath", "/src/main/resources");
			config.put("mappingPackage", "com.ke.mapping");
			
			
			
			config.put("fmTemp", projectPath()+"/src/main/resources"+File.separator+"template");
			
			config.put("url", "jdbc:mysql://192.168.1.66/la-admin");
			config.put("username", "root");
			config.put("password", "123456");
			
	}

	public String getStringValue(String key) {
			return config.get(key);
	}
	
	public static String projectPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/target/classes/", "");
	}
}
