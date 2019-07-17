package com.hjx.springbootmybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * Mybatatis 配置
 *
 * @Author: hjx
 * @Date: 2019/7/12
 * @Version 1.0
 */
@EnableTransactionManagement
@MapperScan("com.hjx.springbootmybatis.dao")
@Configuration
public class MyBatisConfig {

    @Qualifier("myRoutingDataSource")
    @Autowired
    private DataSource myRoutingDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/hjx/springbootmybatis/mapper/**"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.hjx.springbootmybatis.entity");
        return sqlSessionFactoryBean.getObject();
    }

    // 事务处理 使用自定义的数据源
    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new DataSourceTransactionManager(myRoutingDataSource);
    }
}



