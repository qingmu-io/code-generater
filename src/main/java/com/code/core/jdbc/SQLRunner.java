package com.code.core.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.util.FM;

public class SQLRunner {
	

	public void execute(MysqlTableMeta mtm) {
		
		try (Connection connection = Connections.getConnection();
				Statement st = connection.createStatement();
				ResultSet executeQuery = st.executeQuery("show tables like '"+mtm.getTable()+"'");
				) {
			if(!executeQuery.next()){
				@SuppressWarnings("serial")
				String sql = FM.FM.runTemplate("mysql", new HashMap<String, Object>(){{
					this.put("mtm",mtm);
				}});
				
				System.out.println(sql);
				st.executeUpdate(sql);
				this.execute(mtm);
			}else{
				DatabaseMetaData metaData = connection.getMetaData();
				String string = metaData.getURL().toString();
				String db = string.substring(string.lastIndexOf("/")+1);
				mtm.getMysqlColumnMetas().forEach((cm)->{
					String sql = "SELECT * FROM information_schema.columns WHERE table_schema='"+db+"' and table_name = '"+mtm.getTable()+"' AND column_name = '"+cm.getName()+"'";
					try {
						ResultSet rs = st.executeQuery(sql);
						if(!rs.next()){
							sql = "ALTER TABLE "+mtm.getTable()+" ADD COLUMN `"+cm.getName()+"` "+cm.getType();
							st.executeUpdate(sql);
							this.execute(mtm);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
