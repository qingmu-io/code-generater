package com.code.core.parse;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.code.core.config.Config;

public class AbstractParser {
protected Config config;
	protected Set<Class<?>> klasses;
	protected   Map<String, Class<?>> querys; 
	
	
	public AbstractParser(Config config, Set<Class<?>> klasses,
			Map<String, Class<?>> querys) {
		super();
		this.config = config;
		this.klasses = klasses;
		this.querys = querys;
	}


	public String projectPath = Config.projectPath();
	

	 
	 public boolean filterSerialVersionUID(Field field){
		 return !(field.getName().equals("serialVersionUID"));
	 }
}
