package com.code.core.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.code.core.config.Config;

public class Connections {
	public static  Connection getConnection(Config config) {
		try {
			return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

