package com.code.core.parse;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.code.core.config.Config;
import com.code.core.meta.mysql.MysqlColumnMeta;
import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.util.StringUtils;
import com.code.core.util.UnderlineToCameUtils;

public final class MysqlScriptParser extends AbstractParser {


	public MysqlScriptParser(Config config, Set<Class<?>> klasses,
			Map<String, Class<?>> querys) {
		super(config, klasses, querys);
	}

	public List<MysqlTableMeta> parseMysqlScriptMeta() {
		List<MysqlTableMeta> mtms = new ArrayList<MysqlTableMeta>();
		klasses.stream().forEach(
				(klass) -> {
					Table table = klass.getAnnotation(Table.class);
					if (table != null) {
						MysqlTableMeta mtm = MysqlTableMeta.newMysqlTableMeta()
								.setTable(table.name()).setKlass(klass);
						UniqueConstraint[] uniqueConstraints = table.uniqueConstraints();
						Arrays.asList(uniqueConstraints).forEach((un)->{
							String[] columnNames = un.columnNames();
							if(columnNames != null  &&  columnNames.length >0){
								String string = Arrays.asList(columnNames).stream().reduce((p,n)-> p + "," + n).get();
								mtm.getUniques().add(string);
							}
						});
						Arrays.asList(klass.getDeclaredFields()).stream().filter(this::filterSerialVersionUID).forEach(
								(field) -> {
									mtm.getMysqlColumnMetas().add(parseColumn(field));
									Column column = field.getDeclaredAnnotation(Column.class);
									if(column != null)
										if(column.unique())
									mtm.getUniques().add(field.getName());
								});
						mtms.add(mtm);
					}
				});
		return mtms;
	}


	private MysqlColumnMeta parseColumn(Field field) {
		String type = getMysqlType(field);
		Column column = field.getDeclaredAnnotation(Column.class);
		String name;
		if (column != null)
			name = column.name();
		else
			name = field.getName();
		if(!StringUtils.isNotBank(name))
			name = field.getName();
		
		return MysqlColumnMeta.newMysqlColumnMeta()
				.setName(UnderlineToCameUtils.camelToUnderline(name))
				.setType(type);
	}

	private String getMysqlType(Field field) {
		Type genericType = field.getType();
		String typeName = genericType.getTypeName();
		Lob lob = field.getDeclaredAnnotation(Lob.class);
		if(lob != null){
			if(field.getType().isAssignableFrom(String.class))
				return "longtext";
				return "blob";
		}
		
		

		if (typeName.toUpperCase().contains("boolean".toUpperCase())) 
			return "bit(1)";
		
		if (typeName.toUpperCase().contains("Date".toUpperCase())) {
			Temporal dateType = field.getDeclaredAnnotation(Temporal.class);
			if(dateType != null){
				TemporalType value = dateType.value();
				if (value != null) 
					if(value.equals(TemporalType.DATE))
						return "date";
					else if(value.equals(TemporalType.TIMESTAMP))
						return "datetime";
					else if(value.equals(TemporalType.TIME))
						return "time";
			}
			return "datetime";
		}
		if (typeName.toUpperCase().contains("long".toUpperCase())) {
			Id id = field.getDeclaredAnnotation(Id.class);
			if (id == null)
				return "int(20)";
			return "int(20) not null AUTO_INCREMENT PRIMARY KEY";
		}
		if (typeName.toUpperCase().contains("int".toUpperCase())) {
			Id id = field.getDeclaredAnnotation(Id.class);
			if (id == null)
				return "int(11)";
			return "int(11) not null AUTO_INCREMENT PRIMARY KEY";
		}

		if (typeName.toUpperCase().contains("string".toUpperCase())) {
			Column column = field.getDeclaredAnnotation(Column.class);
			if (column == null) 
				return "varchar(255)";
			int length = column.length();
			return "varchar(" + length + ")";
		}

		if (field.getType().isEnum()) {
			Enumerated enumd = field.getDeclaredAnnotation(Enumerated.class);
			if(enumd !=null ){
				EnumType value = enumd.value();
				if (value.equals(EnumType.ORDINAL)) 
					return "int(2)";
					return "varchar(2)";
			}
			return "int(2)";
		}
		if(field.getType().isAssignableFrom(BigDecimal.class)){
			return "decimal(19,2)";
		}
		if(field.getType().isAssignableFrom(Double.class)){
			return "double(19,2)";
		}

		throw new RuntimeException(typeName + " 无法解析。请检查getMysqlType解析方法");
	}

}
