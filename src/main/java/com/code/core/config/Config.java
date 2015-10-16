package com.code.core.config;

import java.io.File;

public final class Config {
	
	private String entityPackage;
	private String queryModelPackage;
	private String mapperPackage;
	private String XMLPackage;
	private String url = "jdbc:mysql://127.0.0.1/test";
	private String username = "root";
	private String password;
	
	private String javaSrc = "/src/main/java";
	private String resources = "/src/main/resources";
	
	private String templatePath = Config.projectPath()+"/src/main/resources"+File.separator+"template";
	
	public static String projectPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/target/classes/", "");
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getQueryModelPackage() {
		return queryModelPackage;
	}

	public void setQueryModelPackage(String queryModelPackage) {
		this.queryModelPackage = queryModelPackage;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public String getXMLPackage() {
		return XMLPackage;
	}

	public void setXMLPackage(String xMLPackage) {
		XMLPackage = xMLPackage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJavaSrc() {
		return javaSrc;
	}

	public void setJavaSrc(String javaSrc) {
		this.javaSrc = javaSrc;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
	
}
