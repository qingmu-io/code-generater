package com.code.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.code.core.parse.AbstractParser;

public class KlassHelper {
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					Class<?> forName = Class.forName(packageName + '.' + className);
					classes.add(forName);
					AbstractParser.querys.put(className, forName);
					System.out.println(className+":"+forName);
				} catch (ClassNotFoundException e) {
					System.out.println("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
	
    public static Set<Class<?>> getClasses(String pack){ 
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>(); 
        boolean recursive = true; 
        String packageName = pack;//pack.getName(); 
        String packageDirName = packageName.replace('.', '/'); 
        Enumeration<URL> dirs; 
        try { 
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName); 
            while (dirs.hasMoreElements()){ 
                URL url = dirs.nextElement(); 
                String protocol = url.getProtocol(); 
                if ("file".equals(protocol)) { 
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8"); 
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes); 
                } else if ("jar".equals(protocol)){ 
                    JarFile jar; 
                    try { 
                        jar = ((JarURLConnection) url.openConnection()).getJarFile(); 
                        Enumeration<JarEntry> entries = jar.entries(); 
                        while (entries.hasMoreElements()) { 
                            JarEntry entry = entries.nextElement(); 
                            String name = entry.getName(); 
                            if (name.charAt(0) == '/') { 
                                name = name.substring(1); 
                            } 
                            if (name.startsWith(packageDirName)) { 
                                int idx = name.lastIndexOf('/'); 
                                if (idx != -1) { 
                                    packageName = name.substring(0, idx).replace('/', '.'); 
                                } 
                                if ((idx != -1) || recursive){ 
                                    if (name.endsWith(".class") && !entry.isDirectory()) { 
                                        String className = name.substring(packageName.length() + 1, name.length() - 6); 
                                        try { 
                                            classes.add(Class.forName(packageName + '.' + className)); 
                                            System.out.println(packageName + '.' + className);
                                        } catch (ClassNotFoundException e) { 
                                            System.out.println("添加用户自定义视图类错误 找不到此类的.class文件"); 
                                            e.printStackTrace(); 
                                        } 
                                      } 
                                } 
                            } 
                        } 
                    } catch (IOException e) { 
                        System.out.println("在扫描用户定义视图时从jar包获取文件出错"); 
                        e.printStackTrace(); 
                    }  
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
        return classes; 
    } 
}
