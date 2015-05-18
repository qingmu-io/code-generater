package com.code.test;

import java.util.List;

import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.parse.MysqlScriptParser;

public class Tests {

	public static void main(String[] args) {
		List<MysqlTableMeta> parseMysqlScriptMeta = new MysqlScriptParser().parseMysqlScriptMeta();
		System.out.println(parseMysqlScriptMeta);
	}
}
