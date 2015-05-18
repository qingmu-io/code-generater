package com.code.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.code.core.config.Config;

public class Connections {
	static Config config = new Config();
	public static  Connection getConnection() {
		try {
			return DriverManager.getConnection(config.getStringValue("url"), config.getStringValue("username"), config.getStringValue("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

