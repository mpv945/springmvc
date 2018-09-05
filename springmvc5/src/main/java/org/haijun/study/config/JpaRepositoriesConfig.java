package org.haijun.study.config;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * hibernate 配置使用 ：https://www.boraji.com/spring-mvc-5-spring-security-5-hibernate-5-example
 * https://www.baeldung.com/hibernate-5-spring
 * 需要引入spring-data-jpa和hibernate-core;hibernate 5参考https://howtodoinjava.com/spring5/webmvc/spring5-mvc-hibernate5-example/
 * @author zhenx
 *
 */
@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan({ "org.haijun.study.service" })
//@EnableJpaRepositories(includeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Repository.class)})
//excludeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Service.class),排除
@EnableJpaRepositories(basePackages= {"org.haijun.study.repository"})
@EnableTransactionManagement
//@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")//开启审计功能，类似要注释监听
@EnableBatchProcessing//开启spring batch
@Log4j2
public class JpaRepositoriesConfig {

	@Autowired
	private Environment env;

	// @Value("#{systemProperties['os.name']}") // 注入操作系统属性
    // @Value("#{ T(java.lang.Math).random() * 100.0 }") //注入表达式结果
    // @Value("#{beanInject.another}")// // 注入其他Bean属性：注入beanInject对象的属性another，
	// @Value("http://www.baidu.com")注入URL资源,下面为注入文件资源
    @Value("classpath:import.sql")
    private Resource importTables;

    @Value("classpath:data.sql")
    private Resource addData;

    // spring batch 创建表，每个数据库类型都有对应的sql
    @Value("classpath:org/springframework/batch/core/schema-drop-mysql.sql")
    private Resource dropReopsitoryTables;
    @Value("classpath:org/springframework/batch/core/schema-mysql.sql")
    private Resource dataReopsitorySchema;
	
	/**
	 * 这是一种快速配置，可以创建HSQL嵌入式数据库实例并使用简单的SQL脚本预填充该实例；依赖hsqldb
	 * @return
	 */
/*	@Bean
	public DataSource dataSource() {
	    return new EmbeddedDatabaseBuilder()
	        .setType(EmbeddedDatabaseType.HSQL)
	        .addScript("classpath:jdbc/schema.sql")
	        .addScript("classpath:jdbc/test-data.sql").build();
	}*/
	
	/**
	 * mysql 配置
	 * @return
	 */
    @Profile("product")//环境变量，可以通过在类上注释，或者搞两套初始方法
	@Bean(name = "dataSource")
    public DataSource dataSource_product() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mysql.driver"));
        dataSource.setUrl(env.getProperty("mysql.jdbcUrl"));
        dataSource.setUsername(env.getProperty("mysql.username"));
        dataSource.setPassword(env.getProperty("mysql.password"));
        return dataSource;
    }

    //各种数据连接池配置和JNDI配置https://www.jianshu.com/p/20c1e5d4cd2e  -Dspring.profiles.active=dev -P product
/*    @Profile("product")
    @Bean
    public DataSource dataSource_product() {
        final org.apache.tomcat.jdbc.pool.DataSource  dataSource = new org.apache.tomcat.jdbc.pool.DataSource ();
        dataSource.setDriverClassName(env.getProperty("mysql.driver"));
        dataSource.setUrl(env.getProperty("mysql.jdbcUrl"));
        dataSource.setUsername(env.getProperty("mysql.username"));
        dataSource.setPassword(env.getProperty("mysql.password"));
        dataSource.setMaxActive(100);// 表示并发情况下最大可从连接池中获取的连接数。
        // 如果在并发时达到了maxActive=100，那么连接池就必须从数据库中获取100个连接来供应用程序使用，当应用程序关闭连接后，
        // 由于maxIdle=30,因此并不是所有的连接都会归还给数据库，将会有30个连接保持在连接池种中，状态为空闲。
        dataSource.setMaxIdle(30);
        // 最小默认情况下并不生效，它的含义是当连接池中的连接少有minIdle，系统监控线程将启动补充功能，一般情况下我们并不启动补充线程。
        dataSource.setMinIdle(2);
        // 一般会是几种情况出现需要removeAbandoned：遇到数据库死锁。以前遇到过后端存储过程做了锁表操作，导致前台集群中连接池全都被block住，后续的业务处理因为拿不到链接所有都处理失败了。
        dataSource.setRemoveAbandoned(true);//超过时间限制是否回收
        dataSource.setRemoveAbandonedTimeout(60); //超时时间；单位为秒
        dataSource.setLogAbandoned(true);//关闭abanded连接时输出错误日志
        dataSource.setInitialSize(1);//连接池启动时创建的初始化连接数量（默认值为0）
        dataSource.setMaxWait(60000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);

        // 这里建议配置为TRUE，防止取到的连接不可用
        dataSource.setTestOnBorrow(false);// 建议配置关闭
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);//生产关闭
        log.info("**************************************************************************dataSource_product 执行");
        return dataSource;
    }*/

    // https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98
    // 数据库配置https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_DruidDataSource%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE，右边有其他更多配置
    @Profile("dev")//环境变量，可以通过在类上注释，或者搞两套初始方法,在集成测试类上，使用@ActiveProfiles注解设置
    @Bean(name = "dataSource")
    // 读配置和加密数据库 https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
    public DataSource dataSource_dev() {
        final com.alibaba.druid.pool.DruidDataSource dataSource = new com.alibaba.druid.pool.DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("mysql.driver"));
        dataSource.setUrl(env.getProperty("mysql.jdbcUrl"));
        dataSource.setUsername(env.getProperty("mysql.username"));
        dataSource.setPassword(env.getProperty("mysql.password"));
        // 配置初始化大小、最小、最大，通常来说，只需要修改initialSize、minIdle、maxActive。
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        // 配置获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);

        // 这里建议配置为TRUE，防止取到的连接不可用
        dataSource.setTestOnBorrow(false);// 建议配置关闭
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);//生产关闭
        // 验证连接有效与否的SQL，不同的数据配置不同
        dataSource.setValidationQuery("SELECT 'x'"); // oracle :select 1 from dual
        // 打开PSCache，并且指定每个连接上PSCache的大小
        //如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false。
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        // 这里配置提交方式，默认就是TRUE，可以不用配置
        //<property name="defaultAutoCommit" value="true" />
        try {
            dataSource.setFilters("stat,wall,log4j2");
            // 如果配置stat慢SQL记录，可以指定多久执行完的sql才作为慢sql显示
            dataSource.setConnectionProperties("druid.stat.slowSqlMillis=5000"); //时间单位为毫秒
        } catch (SQLException e) {
            log.info(e);
        }
        com.alibaba.druid.filter.logging.Log4j2Filter log4j2Filter = new com.alibaba.druid.filter.logging.Log4j2Filter();
        log4j2Filter.setStatementExecutableSqlLogEnable(false);

        // 防御sql注入过滤配置 https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
        WallFilter wallFilter = new WallFilter();
        WallConfig wallConfig = new WallConfig();
        wallConfig.setDir("META-INF/druid/wall");
        wallFilter.setConfig(wallConfig);
        wallFilter.setDbType("mysql"); // mysql/oracle/sqlserver/postgres

        dataSource.setProxyFilters(Lists.newArrayList(log4j2Filter,wallFilter));
        log.info("**************************************************************************dataSource_dev 执行");
        return dataSource;
    }

    // 数据库初始化脚本
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource){
        String createSql = "DROP TABLE IF EXISTS `t_property_config`;";
        createSql+="CREATE TABLE `t_property_config`  (";
        createSql+="`property_key` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'key值',";
        createSql+="`property_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT 'vlaue值',";
        createSql+="`deleted` int(1) NULL DEFAULT NULL COMMENT '0:表示删除状态为false，1:表示删除状态为true',";
        createSql+="PRIMARY KEY (`property_key`) USING BTREE";
        createSql+=") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;";
        createSql+="DELETE FROM `t_property_config`;";
        Resource scripts = new ByteArrayResource(createSql.getBytes());
        //ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(new ClassPathResource("classpath:data.sql"));
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();//new FileSystemResource()
        //databasePopulator.addScript(scripts);importTables
        databasePopulator.addScript(importTables);
        databasePopulator.addScript(addData);
        // spring batch 创建表，每个数据库类型都有对应的sql
        databasePopulator.addScript(dropReopsitoryTables);
        databasePopulator.addScript(dataReopsitorySchema);

        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }

	  @Bean
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);// 自动生成ddl
	    vendorAdapter.setShowSql(true); // 打印日志
	    vendorAdapter.setDatabase(Database.MYSQL);
	    // 方言参考https://blog.csdn.net/qq_28048579/article/details/79529634
	    vendorAdapter.setDatabasePlatform("org.haijun.study.tools.CustomMySQLDialect");
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("org.haijun.study.model.entity");//扫描实体,通配符是允许的
          log.info("ActiveProfiles={}",env.getActiveProfiles());
          log.info("DefaultProfiles={}",env.getDefaultProfiles());
          log.info("DefaultProfiles={}",env.getProperty("spring.profiles.default"));
          log.info("ActiveProfiles={}",env.getProperty("spring.profiles.active"));
	    factory.setDataSource(dataSource);
          //factory.setJpaProperties(hibernateProperties()); //指定其他特殊配置
	    return factory;
	  }

    /**
     * 通过配置设置连接数据库方言和ddl生成策略......
     * @return
     */
    protected Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        return hibernateProperties;
    }

	  @Bean("platformTransactionManager")
	  public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory.getObject());
	    return txManager;
	  }

	  // 初始数据库到配置
/*	  @Bean
    @DependsOn("dataSource")
    public DatabasePropertyConfig extPropertyConfig(){
        return new DatabasePropertyConfig();
    }*/


    // spring batch
    @Bean
    @DependsOn("platformTransactionManager")
    public JobRepository getJobRepository(DataSource dataSource, PlatformTransactionManager platformTransactionManager) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(platformTransactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
    @Bean
    public JobLauncher getJobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
