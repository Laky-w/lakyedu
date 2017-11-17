package com.laky.edu.config.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 95 on 2017/11/14.
 */
@Configuration
public class SessionFactoryConfiguration {
    @Autowired
    private DataSource dataSource;
    private static String  configLocation;
    private static String  mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private  String typeAliasesPackage;

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException ,ClassNotFoundException{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setConfigLocation( new ClassPathResource(configLocation));
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperLocations));
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        /*Properties properties = new Properties();
        sessionFactoryBean.setConfigurationProperties(properties);*/
       /*
        这里可以通过代码的方式去注入mybatis配置 注意 setConfigLocation 如果给了路径，这里再次使用就会运行时报错
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        Class onwClass = Class.forName("org.apache.ibatis.logging.stdout.StdOutImpl");
        configuration.setLogImpl(onwClass);
        configuration.setUseGeneratedKeys(true);
        configuration.setUseColumnLabel(true);
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);*/
        return  sessionFactoryBean;
    }

    @Value("${mybatis.config-location}")
    public  void setConfigLocation(String configLocation) {
        SessionFactoryConfiguration.configLocation = configLocation;
    }

    @Value("${mybatis.mapper-locations}")
    public  void setMapperLocations(String mapperLocations) {
        SessionFactoryConfiguration.mapperLocations = mapperLocations;
    }


}
