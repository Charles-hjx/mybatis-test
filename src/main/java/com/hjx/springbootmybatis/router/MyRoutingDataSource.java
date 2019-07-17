package com.hjx.springbootmybatis.router;

import com.hjx.springbootmybatis.context.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * 数据库路由
 *
 * @Author: hjx
 * @Date: 2019/7/12
 * @Version 1.0
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getContextHolder();
    }
}
