### 简介
        1.基于实体类为主导的代码生成器，可基于实体类快速的生成mybatis的相关配置。
        如生成接口mapper,生成xml配置文件。
        2.可通过实体类在生成自动建表，自动加索引,自动更新数据列。
        3.可检测出数据库与实体类之间的差异并在日志中打印出对饮警告或者修复用的sql语句。
        如 warn : 数据库中的列  [mysql_ame --> mysqlNname] 在实体类 Admin 不存在;
        4.支持大部分JPA注解解析，可通过此代码生成器快速从hibernate转换到mybatis。
        5.抽取查询对象，简化查询。QuerModel
        6.自动驼峰装换
### 实体类demo
         //JPA注解 需要解析的类必须加此注解
        @Entity
        //JPA注解 name表示数据表的名称 uniqueConstraints表示需要进行唯一约束的列,可自动追加到数据库
        @Table(name = "admin",uniqueConstraints=@UniqueConstraint(columnNames={"userId,age"}))
        public class Admin implements Serializable {
            private static final long serialVersionUID = 1L;
            //JPA 注解 此注解会自动同步数据为主键
            @Id
            private Long id;
            private Long userId;
            //唯一约束
            @Column(unique=true)//
            private String roleNames;
            private int age;
        
            //getter setter
        }
### 查询对象DEMO
        //注意查询对象是由于Admin+QueryModel来进行命名
        public class AdminQueryModel  {
            private static final long serialVersionUID = -8493398486786898485L;
            //字段命名规则为需要进行的操作的属性名+操作符号 操作符会在代码解析的时候进行对应解析
            //支持几乎大部分的操作符解析 集体解析请看src\main\java\com\code\core\parse\MybatisXmlParser.java
            private String nicknameLK;
            private String phoneEQ;
        
         //getter setter
        }

### 快速使用
            public static void main(String[] S) {
            //全局配置对象
                Config config = new Config();
                //配置需要进行扫面的包
                config.setEntityPackage("test.com.entity");
                //配置需要扫描的queryModel包
                config.setQueryModelPackage("test.com.model.query");
                
                //配置mysql
                config.setUrl("jdbc:mysql://192.168.1.66/test");
                config.setUsername("root");
                config.setPassword("123456");
                
                //配置生成的xml的路径 默认生成在resource包下
                onfig.setXMLPackage("test.com.mapping");
                //配置生成mapper的包
                config.setMapperPackage("test.com.mapper");
                //配置log的存储路径 此log很重要。将会保存每次数据库和实体的检测结果以及修复的sql建议
                Generater.run(config,new File("D:/LOGS3.LOG"));
            }
### 生成的结果            