package com.laky.edu.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * Created by 95 on 2017/11/14.
 */
@Configuration
@MapperScan("com.laky.edu.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${jdbc.datasource.url}")
    private String dataUrl;
    @Value("${jdbc.datasource.username}")
    private String userName;
    @Value("${jdbc.datasource.password}")
    private String password;

    @Bean("dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(dataUrl);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(60);
        dataSource.setMinPoolSize(10);
        //数据库链接关闭后不自动提交
        dataSource.setAutoCommitOnClose(false);
        //数据库链接超时时间
        dataSource.setCheckoutTimeout(3000);
        //数据库链接失败重连次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }

}
