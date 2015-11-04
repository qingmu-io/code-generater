package com.code.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.code.core.config.Config;
import com.code.core.parse.AbstractParser;

public class MyBatisXmlFileUtils extends AbstractParser {


    public MyBatisXmlFileUtils(Config config, Set<Class<?>> klasses,
                               Map<String, Class<?>> querys) {
        super(config, klasses, querys);
    }

    String path = (this.projectPath + this.config.getResources() + File.separator + (this.config.getXMLPackage().replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);
    String mapper = (this.projectPath + this.config.getJavaSrc() + File.separator + (this.config.getMapperPackage().replace(".", File.separator))).replace("/", File.separator).replace("\\", File.separator);

    {
        File file = new File(path);
        if (!file.isDirectory()) file.mkdirs();
        file = new File(mapper);
        if (!file.isDirectory()) file.mkdirs();
    }

    public void saveXml(String xmlName, String xml) {
        String string = this.path + File.separator + xmlName;
        File file = new File(string);
        StringBuilder append = new StringBuilder();
        if (file.isFile()) {
            Document doc = this.getDoc(file);
            if (doc != null) {
                @SuppressWarnings("unchecked")
                List<Element> elements = doc.getRootElement().elements();
                elements.forEach((element) -> {
                    String id = element.attribute("id").getStringValue().intern();

                    if (!(id == "delete".intern()
                            || id == "insert".intern()
                            || id == "update".intern()
                            || id == "findOne".intern()
                            || id == "findAll".intern()
                            || id == "baseResultMap".intern()
                            || id == "merge".intern()
                            || id == "count".intern()
                    )) {
                        append.append("\t").append(element.asXML()).append("\r\n");
                    }
                });
            }
            file.delete();
        }
        if (!StringUtils.isBlank(append.toString()))
            xml = xml.split("</mapper>")[0] + append.toString() + "\r\n</mapper>";

        createFile(file);
        try (FileOutputStream os = new FileOutputStream(file)) {
            System.out.println("写入" + xmlName);
            os.write(xml.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFileToString(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder lines = new StringBuilder();
            bufferedReader.lines().forEach((line) -> {
                lines.append(line).append("\r\n");
            });
            return lines.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class MyEntityResolver implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) {
            return new InputSource(new StringReader(""));
        }
    }

    private Document getDoc(File file) {
        SAXReader reader = new SAXReader();

//			reader.setFeature("http://mybatis.org/dtd/mybatis-3-mapper.dtd", false);?]
        try {
            reader.setEntityResolver(new MyEntityResolver());
            return reader.read(file);
        } catch (DocumentException e) {
//				throw new RuntimeException(e);
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public void saveMapper(String mapperName, String mapper) {
        String string = this.mapper + File.separator + mapperName;
        File file = new File(string);
        StringBuilder b = new StringBuilder();
        String imp = "";
        if (file.isFile()) {
            String fileToString = this.getFileToString(file);
            if (StringUtils.isNotBlank(fileToString)) {
                String[] split2 = fileToString.split("\\{");
                imp = split2[0];
                String[] split = split2[1].split("\\}")[0].split(";");
                Arrays.asList(split).forEach((m) -> {
                    if (!(m.contains("insert(")
                            || m.contains("update(")
                            || m.contains("findOne(")
                            || m.contains("findAll(")
                            || m.contains("merge(")
                            || m.contains("count(")
                            || m.contains("delete(")
                    )) {
                        if (StringUtils.isNotBlank(m.trim())) {
                            if (!";".equals(m.trim()) && !"\\t".equals(m.trim())) {
                                if (mapperName.contains("AdminMapper")) {
                                    System.out.println();
                                }
                                b.append("\r\n\t").append((m + ";\r\n").trim());
                            }
                        }
                    }
                });
            }
            file.delete();
        }
        createFile(file);
        if (StringUtils.isNotBlank(b.toString())) {
            mapper = mapper.split("\\}")[0] + b.toString() + "\r\n }";
        }
        if (StringUtils.isNotBlank(imp))
            mapper = imp + "{" + mapper.split("\\{")[1];
        try (FileOutputStream os = new FileOutputStream(file)) {
            System.out.println("写入" + mapperName);
            os.write(mapper.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        URL resource = MyBatisXmlFileUtils.class.getClassLoader().getResource("template");
        System.err.println(resource.getPath());

    }


}
