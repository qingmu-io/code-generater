package com.code.core;

import static com.code.core.util.FM.FM;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.code.core.config.Config;
import com.code.core.file.MyBatisXmlFileUtils;
import com.code.core.jdbc.SQLRunner;
import com.code.core.meta.mybatis.mapper.MybatisMapperMeta;
import com.code.core.meta.mybatis.xml.MybatisXmlMeta;
import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.parse.MyBatisMapperParser;
import com.code.core.parse.MybatisXmlParser;
import com.code.core.parse.MysqlScriptParser;
import com.code.core.util.KlassHelper;

public class Generater {

	public static void run(Config config, File log) {
		if (log == null)
			throw new NullPointerException("log file is null");
		Logger.log = log;
		Set<Class<?>> classes = KlassHelper.getClasses(config
				.getEntityPackage());
		Map<String, Class<?>> querysClasses = KlassHelper
				.getQuerysClasses(config.getQueryModelPackage());

		List<MybatisXmlMeta> mybatisXmlMetaParse = new MybatisXmlParser(config,
				classes, querysClasses).mybatisXmlMetaParse();
		MyBatisMapperParser myBatisMapperParser = new MyBatisMapperParser(
				config, classes, querysClasses);
		MyBatisXmlFileUtils utils = new MyBatisXmlFileUtils(config, classes,
				querysClasses);
		List<MybatisMapperMeta> mybatisMapperMetas = myBatisMapperParser
				.mybatisMapperMetas();
		MysqlScriptParser mysqlScriptParser = new MysqlScriptParser(config,
				classes, querysClasses);
		List<MysqlTableMeta> parseMysqlScriptMeta = mysqlScriptParser
				.parseMysqlScriptMeta();
		SQLRunner runner = new SQLRunner();

		mybatisXmlMetaParse.parallelStream().forEach(
				(mxm) -> {
					String runTemplate = getMxmString(mxm);
					String model = mxm.getModel();
					utils.saveXml(model.substring(model.lastIndexOf(".") + 1)
							+ "Mapper.xml", runTemplate);
				});
		Logger.info("完成 xml写入");

		mybatisMapperMetas.parallelStream().forEach((mmm) -> {
			String runTemplate = getTepl(config, mmm);
			utils.saveMapper(mmm.getSimpleName() + "Mapper.java", runTemplate);
		});
		Logger.info("完成 mapper写入");


		parseMysqlScriptMeta.parallelStream().forEach((mtm) -> runner.execute(mtm, config));
		Logger.info("同步数据库表完成");
		parseMysqlScriptMeta.forEach(mtm -> runner.checkEntity2DB(mtm.getTable(), mtm.getKlass(), config));

		parseMysqlScriptMeta.forEach(mtm -> runner.checkUnique(mtm.getTable(), mtm.getUniques(), config));
		Logger.info("检查完成");

		Logger.flush();

	}

	private static String getMxmString(MybatisXmlMeta mxm) {
		return FM.runTemplate("mybatis", new HashMap<String, Object>() {
			private static final long serialVersionUID = 1;
			{
				this.put("mxm", mxm);
			}
		});
	}

	private static String getTepl(Config config, MybatisMapperMeta mmm) {
		return FM.runTemplate("mapper", new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("basePackage", config.getMapperPackage());
				this.put("mmm", mmm);
			}
		});
	}

}
