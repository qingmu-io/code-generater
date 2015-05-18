package com.code.core.parse;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.code.core.config.Config;
import com.code.core.util.KlassHelper;

public class AbstractParser {
	public static Config config = new Config();
	public static String basePackage = config.getStringValue("basePackage");
	public String mapperPath = config.getStringValue("mapperPath");
	public String mapperPackage = config.getStringValue("mapperPackage");
	public String mappingPath = config.getStringValue("mappingPath");
	public String mappingPackage =config.getStringValue("mappingPackage");
	public String queryModelPackage =config.getStringValue("queryModelPackage");
	public String projectPath = Config.projectPath();
	
	public static final Map<String, Class<?>> querys = new HashMap<String, Class<?>>(); 

	 static final Set<Class<?>> klasses = KlassHelper.getClasses(basePackage);
	 
	 public boolean filterSerialVersionUID(Field field){
		 return !(field.getName().equals("serialVersionUID"));
	 }
}
