package org.haijun.study.config;

import org.haijun.study.tools.SpringContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DatabasePropertyConfig extends PropertyPlaceholderConfigurer {

    private DataSource ds;

    private String sql = "select property_key,property_value from t_property_config where deleted = ? ";

    public DatabasePropertyConfig() {
    }
    public DatabasePropertyConfig(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        //获取父类配置文件
        Properties location_props = null;

        ds = SpringContext.getBean(DataSource.class);

        try {
            //获取父类配置文件
            location_props = super.mergeProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (location_props == null) {
            location_props = new Properties();
        }

        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1,0);// 非删除状态
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String key = rs.getString("property_key");
                    if(key.startsWith(" ")||key.endsWith(" ")){
                        key=key.trim();
                    }

                    if(StringUtils.isEmpty(key))
                    { continue; }
                    String value = rs.getString("property_value");
                    if(null != value)
                    {
                        if(key.startsWith("system.")){
                            System.setProperty(key.substring(7), value);
                        } else {
                            location_props.put(key,value);
                        }
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.processProperties(beanFactoryToProcess, location_props);
        //super.processProperties(beanFactoryToProcess, props);
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
