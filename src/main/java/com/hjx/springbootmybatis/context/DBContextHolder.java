package com.hjx.springbootmybatis.context;

import com.hjx.springbootmybatis.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 通过ThreadLocal 将数据源设置到 每个线程上下文中
 *
 * @Author: hjx
 * @Date: 2019/7/12
 * @Version 1.0
 */
@Slf4j
public class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void setContextHolder(DBTypeEnum dbType){
        contextHolder.set(dbType);
    }
    public static DBTypeEnum getContextHolder(){
        return contextHolder.get();
    }

    public static void master(){
        setContextHolder(DBTypeEnum.MASTER);
        log.debug("=========>>>>>切换到master");
    }

    public static void slave(){
        //轮询
        int index = counter.getAndIncrement() % 2;
        if(counter.get() > 9999){
            counter.set(-1);
        }
        if(index == 0){
            setContextHolder(DBTypeEnum.SLAVE01);
            log.debug("=========>>>>>切换到slave01");
        }else {
            setContextHolder(DBTypeEnum.SLAVE02);
            log.debug("=========>>>>>切换到slave02");
        }
    }


}
