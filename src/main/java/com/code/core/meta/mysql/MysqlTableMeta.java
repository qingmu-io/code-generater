package com.code.core.meta.mysql;

import java.util.ArrayList;
import java.util.List;

public class MysqlTableMeta {

	private String table;

	private List<MysqlColumnMeta> mysqlColumnMetas = new ArrayList<MysqlColumnMeta>();

	public String getTable() {
		return table;
	}

	public List<MysqlColumnMeta> getMysqlColumnMetas() {
		return mysqlColumnMetas;
	}

	public MysqlTableMeta setTable(String table) {
		this.table = table;
		return this;
	}

	public MysqlTableMeta setMysqlColumnMetas(List<MysqlColumnMeta> columnMetas) {
		this.mysqlColumnMetas = columnMetas;
		return this;
	}

	public static MysqlTableMeta newMysqlTableMeta() {
		return new MysqlTableMeta();
	}

	@Override
	public String toString() {
		return  mysqlColumnMetas.toString().replace("]", "").replace("[", "");
	}

}
