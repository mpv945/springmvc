package org.haijun.study.tools;

import org.apache.commons.configuration2.DatabaseConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Properties;

/**
 * 把数据库的数据加载到配置java.util.Properties文件对象返回。
 */
// bean 生命周期  https://www.cnblogs.com/xrq730/p/5721366.html
public class CommonsConfigurationFactory implements FactoryBean  { //去掉 InitializingBean,

    private DatabaseConfiguration configuration;

    public CommonsConfigurationFactory(DatabaseConfiguration configuration){

        // https://commons.apache.org/proper/commons-configuration/apidocs/org/apache/commons/configuration2/DatabaseConfiguration.html
        // https://blog.csdn.net/10km/article/details/78594229
        //FileBasedConfigurationBuilder<XMLConfiguration> builder1 = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class);
/*
        builder = new BasicConfigurationBuilder<DatabaseConfiguration>(DatabaseConfiguration.class);
        BasicBuilderParameters builderParameters = new BasicBuilderParameters();
        builder.configure()
        builder.setParameters()*/
        //Assert.notNull(configuration);
        this.configuration = configuration;
    }

    /**
     * bean 获取该对象的实例
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return (configuration != null) ? getProperties(configuration) : null;
    }

    /**
     * 工厂获取该实例返回的Class对象
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return java.util.Properties.class;
    }

/*    @Override
    public void afterPropertiesSet() throws Exception {

    }*/

    public static Properties getProperties(DatabaseConfiguration config) {
        Properties props = new Properties();
        Iterator<String> keys = config.getKeys();
        while(keys.hasNext()) {
            String key = keys.next();
            props.setProperty(key, config.getString(key));
        }
        return props;
    }
}
