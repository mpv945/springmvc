package org.haijun.study;


import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成mybatis文档；官网http://www.mybatis.org/mybatis-3/zh/index.html；http://www.mybatis.org/spring/zh/factorybean.html
 * mysql 8  docker打开方式 https://blog.csdn.net/xinpengfei521/article/details/80403965
 */
public class TkMybatisGenerator {

    public static void main(String[] args) {
        args =  new String[] { "-configfile", "src\\main\\resources\\tk-generatorConfig.xml", "-overwrite" };
        ShellRunner.main(args);
    }

    @Test
    public void mysqlTk(){

        String[] args =  new String[] { "-configfile", "src\\test\\resources\\tk-generatorConfig.xml", "-overwrite" };
        ShellRunner.main(args);
        /*try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config =
                    cp.parseConfiguration(getResourceAsStream("generatorConfig.xml"));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            for (String warning : warnings) {
                System.out.println(warning);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    public static InputStream getResourceAsStream(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
