package com.code.core.parse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;

import com.code.core.config.Config;
import com.code.core.meta.mybatis.xml.MybatisMappingMeta;
import com.code.core.meta.mybatis.xml.MybatisXmlMeta;
import com.code.core.util.UnderlineToCameUtils;

public class MybatisXmlParser extends AbstractParser {


	public MybatisXmlParser(Config config, Set<Class<?>> klasses,
			Map<String, Class<?>> querys) {
		super(config, klasses, querys);
	}

	public List<MybatisXmlMeta> mybatisXmlMetaParse() {
		List<MybatisXmlMeta> result = new ArrayList<>();
		System.out.println(klasses.size());
		klasses.parallelStream().forEach(
				(klass) -> {
					Table table = klass.getAnnotation(Table.class);
					if (table != null) {
						String mapperName = parseMapperName(klass);
						MybatisXmlMeta meta = MybatisXmlMeta.newMybatisXmlMeta().setMapperName(mapperName).setModel(klass.getName())
								.setTable(UnderlineToCameUtils.camelToUnderline(klass.getSimpleName()).replaceFirst("\\_", " "))
								.setSimpleName(klass.getSimpleName());
						parseBasic(klass, meta);
						String simpleName = klass.getSimpleName() + "QueryModel";
						Class<?> queryKlass = querys.get(simpleName);

						if (queryKlass != null) {
							meta.setQuery(String.format("%s.%s", config.getQueryModelPackage(), simpleName));
							Arrays.asList(queryKlass.getDeclaredFields()).stream().filter(this::filterSerialVersionUID).forEach((field) -> parseQueryModel(meta, field));
						}
						result.add(meta);
					} else {
						System.out.println("skip class " + klass);
					}
				});
		return result;

	}

	/**
	 * <if test="registerTimeLT != null"> and register_time &lt;
	 * #{registerTimeLT} </if> nicknameLK = key
	 * 
	 * <if test="nicknameLK != null"> <bind name="nicknameLK"
	 * value="'%' + _parameter.getNicknameLK() + '%'"/> and nickname like
	 * #{nicknameLK} </if>
	 * 
	 * @param meta
	 * @param field
	 */
	private void parseQueryModel(MybatisXmlMeta meta, Field field) {
		String name = field.getName();
		String value;
		if (name.endsWith("NEQ")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("NEQ", ""));
			if (field.getType().isEnum())
				value = " and " + camelToUnderline + " != #{" + name + ",typeHandler=" + this.parseEnum(field) + "}";
			else
				value = " and " + camelToUnderline + " != #{" + name + "}";
		} else if (name.endsWith("EQ")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("EQ", ""));
			if (field.getType().isEnum())
				value = " and " + camelToUnderline + " = #{" + name + ",typeHandler=" + this.parseEnum(field) + "}";
			else
				value = " and " + camelToUnderline + " = #{" + name + "}";
		} else if (name.endsWith("GT")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("GT", ""));
			value = " and " + camelToUnderline + " &gt; #{" + name + "}";
		} else if (name.endsWith("GTE")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("GTE", ""));
			value = " and " + camelToUnderline + " &gt;= #{" + name + "}";
		} else if (name.endsWith("LT")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LT", ""));
			value = " and " + camelToUnderline + " &lt; #{" + name + "}";
		} else if (name.endsWith("LTE")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LTE", ""));
			value = " and " + camelToUnderline + " &lt;= #{" + name + "}";
		} else if (name.endsWith("NL")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("NL", ""));
			value = " and " + camelToUnderline + " is null #{" + name + "}";
		} else if (name.endsWith("NN")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("NN", ""));
			value = " and " + camelToUnderline + " is not null #{" + name + "}";
		} else if (name.endsWith("LK")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("LK", ""));
			String bind = "<bind name=\"" + name + "\" value=\"'%' + " + name + " + '%'\"/>";
			value = bind + " and " + camelToUnderline + " like #{" + name + "}";
		} else if (name.endsWith("SW")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("SW", ""));
			String bind = "<bind name=\"" + name + "\" value=\"" + name + " + '%'\"/>";
			value = bind + " and " + camelToUnderline + " like #{" + name + "}";
		} else if (name.endsWith("EW")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("EW", ""));
			String bind = "<bind name=\"" + name + "\" value=\"'%' + " + name + "\"/>";
			value = bind + " and " + camelToUnderline + " like #{" + name + "}";
		} else if (name.endsWith("IN")) {
			String camelToUnderline = UnderlineToCameUtils.camelToUnderline(name.replace("IN", ""));
			value = " and " + camelToUnderline + " in\r\n" + "\t\t\t\t<foreach collection=\"" + name
					+ "\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\r\n\t\t\t\t#{item}\r\n\t\t\t\t</foreach>";
		} else
			return;

		meta.getQuerys().put(name, value);
	}

	private void parseBasic(Class<?> klass, MybatisXmlMeta meta) {
		Arrays.asList(klass.getDeclaredFields())
				.stream()
				.filter(this::filterSerialVersionUID)
				.forEach(
						(field) -> {
							String name = field.getName();
							Version version = field.getDeclaredAnnotation(Version.class);
							if(version != null){
								if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(Integer.class)){
									meta.setVersion(name);
								}else
									throw new RuntimeException("version field type please use int or integer");
							}else{
								MybatisMappingMeta setProperty = MybatisMappingMeta.newMybatisMappingMeta().setColumn(UnderlineToCameUtils.camelToUnderline(name))
										.setProperty(name);
								setProperty.setEnumHander("");
								if (field.getType().isEnum()) {
									setProperty.setEnumHander(this.parseEnum(field));
									setProperty.setJavaType(field.getType().getName());
								}
						
								meta.getMappingMetas().add(setProperty);
							}
						
						});
	}

	private String parseEnum(Field field) {
		Enumerated enumerated = field.getDeclaredAnnotation(Enumerated.class);
		if (enumerated != null) {
			EnumType value = enumerated.value();
			if (EnumType.STRING.equals(value)) 
				return "org.apache.ibatis.type.EnumStringTypeHandler";
		}
		return "org.apache.ibatis.type.EnumOrdinalTypeHandler";

	}

	private String parseMapperName(Class<?> klass) {
		return this.config.getMapperPackage() + "." + klass.getSimpleName() + "Mapper";
	}

}

