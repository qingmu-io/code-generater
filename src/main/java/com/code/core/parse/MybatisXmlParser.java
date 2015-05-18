package com.code.core.parse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.code.core.meta.mybatis.xml.MybatisMappingMeta;
import com.code.core.meta.mybatis.xml.MybatisXmlMeta;
import com.code.core.util.UnderlineToCameUtils;


public class MybatisXmlParser extends AbstractParser {


	
	public List<MybatisXmlMeta> mybatisXmlMetaParse(){
		List<MybatisXmlMeta> result = new ArrayList<>();
		klasses.parallelStream().forEach((klass)->{
			Table table = klass.getAnnotation(Table.class);
			if(table != null){
				String mapperName = parseMapperName(klass);
				MybatisXmlMeta meta = MybatisXmlMeta.newMybatisXmlMeta()
						.setMapperName(mapperName).setModel(klass.getName())
						.setTable(UnderlineToCameUtils.camelToUnderline(klass.getSimpleName()).replaceFirst("\\_", " "));
				parseBasic(klass, meta);
				String simpleName = klass.getSimpleName() + "QueryModel";
				Class<?> queryKlass = querys.get(simpleName);
				
				if(queryKlass!=null){
					meta.setQuery(this.queryModelPackage+"."+simpleName);
					Arrays.asList(queryKlass.getDeclaredFields())
					.stream().filter(this::filterSerialVersionUID)
					.forEach((field)->{
						parseQueryModel(meta,field);
					});
				}else
					System.out.println("query model :" + simpleName + " is not fond。Plase check ");
				result.add(meta);
			}
		});
		return result;
		
	}

	/**
	 * <if test="registerTimeLT != null">
			and register_time &lt; #{registerTimeLT}
		</if>
		nicknameLK = key 
		
		<if test="nicknameLK != null">
			<bind name="nicknameLK" value="'%' + _parameter.getNicknameLK() + '%'"/>
			and nickname like #{nicknameLK}
		</if>

	 * @param meta
	 * @param field
	 */
	private void parseQueryModel(MybatisXmlMeta meta, Field field) {
		String name = field.getName();
		String value = null;
		if(name.endsWith("EQ")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("EQ", ""));
			value = " and " + camelToUnderline + " = #{"+name+"}";
		}else if(name.endsWith("GT")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("GT", ""));
			value = " and " + camelToUnderline + " &gt; #{"+name+"}";
		}else if(name.endsWith("GTE")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("GTE", ""));
			value = " and " + camelToUnderline + " &gt;= #{"+name+"}";
		}else if(name.endsWith("LT")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LT", ""));
			value = " and " + camelToUnderline + " &lt; #{"+name+"}";
		}else if(name.endsWith("LTE")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LTE", ""));
			value = " and " + camelToUnderline + " &lt;= #{"+name+"}";
		}else if(name.endsWith("NL")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("NL", ""));
			value = " and " + camelToUnderline + " is null #{"+name+"}";
		}else if(name.endsWith("NN")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("NN", ""));
			value = " and " + camelToUnderline + " is not null #{"+name+"}";
		}else if(name.endsWith("LK")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LK", ""));
			String bind = "<bind name=\""+name+"\" value=\"'%' + " + name + " + '%'\"/>";
			value = bind  + " and " + camelToUnderline + " like #{"+name+"}";
		}else if(name.endsWith("SW")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("SW", ""));
			String bind = "<bind name=\""+name+"\" value=\"" + name + " + '%'\"/>";
			value = bind  + " and " + camelToUnderline + " like #{"+name+"}";
		}else if(name.endsWith("SW")){
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("SW", ""));
			String bind = "<bind name=\""+name+"\" value=\"'%' + " + name + "\"/>";
			value = bind  + " and " + camelToUnderline + " like #{"+name+"}";
		}
		meta.getQuerys().put(name, value);
	}


	private void parseBasic(Class<?> klass, MybatisXmlMeta meta) {
		Arrays.asList(klass.getDeclaredFields()).stream().filter(this::filterSerialVersionUID).forEach((field)->{
			String name = field.getName();
			MybatisMappingMeta setProperty = MybatisMappingMeta.newMybatisMappingMeta().
					setColumn(UnderlineToCameUtils.camelToUnderline(name)).setProperty(name);
			setProperty.setEnumHander("");
			if(field.getType().isEnum()){
				setProperty.setEnumHander("org.apache.ibatis.type.EnumOrdinalTypeHandler");
				Enumerated enumerated = field.getDeclaredAnnotation(Enumerated.class);
				if(enumerated != null){
					EnumType value = enumerated.value();
					if(EnumType.STRING.equals(value)){
						setProperty.setEnumHander("org.apache.ibatis.type.EnumStringTypeHandler");
					}
				}
				
			}
			meta.getMappingMetas().
			add(setProperty);
		});
	}

	private String parseMapperName(Class<?> klass) {
		return this.mapperPackage+"."+klass.getSimpleName()+"Mapper";
	}
	
}
