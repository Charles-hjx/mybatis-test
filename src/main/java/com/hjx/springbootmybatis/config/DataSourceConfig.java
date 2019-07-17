package com.hjx.springbootmybatis.config;

import com.hjx.springbootmybatis.enums.DBTypeEnum;
import com.hjx.springbootmybatis.router.MyRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 数据源配置 配置 主库 masterDataSource 和两个从库 slave01DataSource slave01DataSource
 *
 * @Author: hjx
 * @Date: 2019/7/12
 * @Version 1.0
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.slave01")
    public DataSource slave01DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave02")
    public DataSource slave02DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource")DataSource masterDataSource,
                                         @Qualifier("slave01DataSource")DataSource slave01DataSource,
                                         @Qualifier("slave02DataSource")DataSource slave02DataSource){

        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER,masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE01,slave01DataSource());
        targetDataSources.put(DBTypeEnum.SLAVE02,slave02DataSource());

        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);

        return myRoutingDataSource;
    }


}
