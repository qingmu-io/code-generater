package com.code.core;

import static com.code.core.util.FM.FM;

import java.util.HashMap;
import java.util.List;

import com.code.core.file.MyBatisXmlFileUtils;
import com.code.core.jdbc.SQLRunner;
import com.code.core.meta.mysql.MysqlTableMeta;
import com.code.core.parse.MyBatisMapperParser;
import com.code.core.parse.MybatisXmlParser;
import com.code.core.parse.MysqlScriptParser;
import com.code.core.util.UnderlineToCameUtils;

public class Generater {
	public static void main(String[] args) {
		new MybatisXmlParser().mybatisXmlMetaParse().forEach((mxm)->{
			String runTemplate = FM.runTemplate("mybatis", new HashMap(){{
			this.put("mxm", mxm);
			}});
			System.out.println(runTemplate);
			String model = mxm.getModel();
			new MyBatisXmlFileUtils().saveXml(model.substring(model.lastIndexOf(".")+1)+"Mapper.xml", runTemplate);
		});
		
		MyBatisMapperParser myBatisMapperParser = new MyBatisMapperParser();
myBatisMapperParser.mybatisMapperMetas().stream().forEach((mmm)->{
			String runTemplate = FM.runTemplate("mapper", new HashMap(){{
				this.put("basePackage", myBatisMapperParser.basePackage);
				this.put("mmm", mmm);
				}});
			new MyBatisXmlFileUtils().saveMapper(mmm.getSimpleName()+"Mapper.java", runTemplate);
		});

			List<MysqlTableMeta> parseMysqlScriptMeta = new MysqlScriptParser()
			.parseMysqlScriptMeta();
			SQLRunner runner = new SQLRunner();
			parseMysqlScriptMeta.forEach((mtm) -> {
				runner.execute(mtm);
			});
	
	}
}
