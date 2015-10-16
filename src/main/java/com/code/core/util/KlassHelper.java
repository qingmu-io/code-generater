package com.code.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.code.core.Generater;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class KlassHelper {
	static ClassPath classpath;
	static {
		try {
			classpath = ClassPath.from(Generater.class.getClassLoader());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static Set<Class<?>> getClasses(String pack){ 
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>(); 
		ImmutableSet<ClassInfo> topLevelClassesRecursive = classpath.getTopLevelClassesRecursive(pack);
		topLevelClassesRecursive
		.forEach(k->{
			try {
				classes.add(Class.forName(k.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return classes;
    
    }

	public static Map<String, Class<?>> getQuerysClasses(
			String queryModelPackage) {
			Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
			classpath.getTopLevelClassesRecursive(queryModelPackage)
			.forEach(k->{
				try {
					classes.put(k.getSimpleName(), Class.forName(k.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return classes;
	} 
}
