package com.code.core.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.code.core.parse.AbstractParser;

public class MyBatisXmlFileUtils extends AbstractParser{
	String path = (this.projectPath+this.mappingPath + File.separator+(this.mappingPackage.replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);
	String mapper = (this.projectPath+this.mapperPath + File.separator+(this.mapperPackage.replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);

	{
		System.out.println(path);
		File file = new File(path);
		if(!file.isDirectory())file.mkdirs();
		System.out.println(mapper);
		 file = new File(mapper);
		if(!file.isDirectory())file.mkdirs();
	}
	
	public void saveXml(String xmlName,String xml){
		String string = this.path+File.separator+xmlName;
		File file = new File(string);
		if(file.isFile())file.delete();
		createFile(file);
		try(FileOutputStream os = new FileOutputStream(file)){
			os.write(xml.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveMapper(String mapperName	,String mapper){
		String string = this.mapper+File.separator+mapperName;
		File file = new File(string);
		if(file.isFile())file.delete();
		createFile(file);
		try(FileOutputStream os = new FileOutputStream(file)){
			os.write(mapper.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void createFile(File file)  {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
