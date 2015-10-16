package com.code.core.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Table;

import com.code.core.config.Config;
import com.code.core.meta.mybatis.mapper.MybatisMapperMeta;

public class MyBatisMapperParser extends AbstractParser {
	

	public MyBatisMapperParser(Config config, Set<Class<?>> klasses,
			Map<String, Class<?>> querys) {
		super(config, klasses, querys);
	}

	public List<MybatisMapperMeta> mybatisMapperMetas(){
		 List<MybatisMapperMeta> result = new ArrayList<MybatisMapperMeta>();
		klasses.stream().forEach((klass)->{
		if(klass.getDeclaredAnnotation(Table.class) != null){
			String name = klass.getName();
			String simpleName = klass.getSimpleName();
			Class<?> class1 = querys.get(simpleName+"QueryModel");
			MybatisMapperMeta meta = new MybatisMapperMeta();
			if(class1 != null){
				String name2 = class1.getName();
				String simpleName2 = class1.getSimpleName();
				meta.setQueryModel(name2);
				meta.setSimpleQuery(simpleName2);
			}else{
				meta.setQueryModel("");
				meta.setSimpleQuery("");
			}
			meta.setModelName(name);
			meta.setSimpleName(simpleName);
			result.add(meta);
		
		}
		});
		return result;
	}

}
