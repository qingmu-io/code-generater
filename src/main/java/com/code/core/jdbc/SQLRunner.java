package com.code.core.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.code.core.Logger;
import com.code.core.config.Config;
import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.util.FM;
import com.code.core.util.UnderlineToCameUtils;

public class SQLRunner {
	
	
	

	public void execute(MysqlTableMeta mtm,Config config) {
		
		try (Connection connection = Connections.getConnection(config);
				Statement st = connection.createStatement();
				ResultSet executeQuery = st.executeQuery("show tables like '"+mtm.getTable()+"'");
				) {
			if(!executeQuery.next()){
				@SuppressWarnings("serial")
				String sql = FM.FM.runTemplate("mysql", new HashMap<String, Object>(){{
					this.put("mtm",mtm);
				}});
				
				Logger.info(sql);
				st.executeUpdate(sql);
				this.execute(mtm,config);
			}else{
				DatabaseMetaData metaData = connection.getMetaData();
				String string = metaData.getURL().toString();
				String db = string.substring(string.lastIndexOf("/")+1);
				mtm.getMysqlColumnMetas().forEach((cm)->{
					String sql = "SELECT * FROM information_schema.columns WHERE table_schema='"+db+"' and table_name = '"+mtm.getTable()+"' AND column_name = '"+cm.getName()+"'";
					try {
						ResultSet rs = st.executeQuery(sql);
						if(!rs.next()){
							sql = "ALTER TABLE `"+mtm.getTable()+"` ADD COLUMN `"+cm.getName()+"` "+cm.getType();
							Logger.info(sql);
							st.executeUpdate(sql);
							this.execute(mtm,config);
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

	public void checkEntity2DB(String table, Class<?> klass,Config config) {
		try(Connection connection = Connections.getConnection(config)){
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getColumns(null, "%", table, "%");
			while(rs.next()){
				String string = rs.getString(4);
				String field = UnderlineToCameUtils.underlineToCamel(string);
				try {
					klass.getDeclaredField(field);
				} catch (NoSuchFieldException e) {
					String sql = "warn : 数据库中的列  ["+string+" --> "+field+" ]" +   "在实体类 "+klass.getSimpleName()+" 不存在";
					Logger.warn(sql);
					System.out.println(sql);
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				}
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void checkUnique(String table, List<String> uniques, Config config) {
		try(Connection conn = Connections.getConnection(config);
				Statement createStatement = conn.createStatement();
				){
			uniques.forEach((un)->{
				try {
					if(!un.contains(",")){
						String sql = String.format("ALTER TABLE %s ADD   UNIQUE unique_%s(%s);", table, UnderlineToCameUtils.camelToUnderline(un), UnderlineToCameUtils.camelToUnderline(un));
						createStatement.executeUpdate(sql);
						Logger.info(sql);
						System.out.println(sql);
					}else {
						String uniqueName = Arrays.asList(un.split(",")).stream().reduce((p,n)->UnderlineToCameUtils.camelToUnderline(p) +"_" + UnderlineToCameUtils.camelToUnderline(n)).get();
						String unique = Arrays.asList(un.split(",")).stream().reduce((p,n)->UnderlineToCameUtils.camelToUnderline(p) +"," + UnderlineToCameUtils.camelToUnderline(n)).get();
						String sql = String.format("ALTER TABLE %s ADD   UNIQUE unique_%s(%s);", table, uniqueName, unique);
						createStatement.executeUpdate(sql);
						Logger.info(sql);
						System.out.println(sql);
					}
				} catch (Exception e) {
					String message = e.getMessage();
					if(!message.startsWith("Duplicate"))
					throw new RuntimeException(e);
				}
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}



}
