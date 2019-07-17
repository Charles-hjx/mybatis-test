package com.hjx.springbootmybatis.aop;

import com.hjx.springbootmybatis.context.DBContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: hjx
 * @Date: 2019/7/12
 * @Version 1.0
 */
@Aspect
@Component
public class DataSourceAop {


    @Pointcut("!@annotation(com.hjx.springbootmybatis.annotation.Master)"+
            "&&(execution(* com.hjx.springbootmybatis.service..*.select*(..))"+
            "|| execution(* com.hjx.springbootmybatis.service..*.get*(..))" +
            "|| execution(* com.hjx.springbootmybatis.service..*.find*(..)))")
    public void readPointcut(){
    }

    @Pointcut("@annotation(com.hjx.springbootmybatis.annotation.Master) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.insert*(..)) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.add*(..)) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.update*(..)) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.edit*(..)) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.delete*(..)) " +
            "|| execution(* com.hjx.springbootmybatis.service..*.remove*(..))"+
            "|| execution(* com.hjx.springbootmybatis.service..*.save*(..))")
    public void writePointcut(){
    }

    @Before("readPointcut()")
    public void read(){
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write(){
        DBContextHolder.master();
    }

    /**
     * 另一种写法 判断哪些需要读从数据库，其余的走主数据库
     */
//    @@Before("execution(* com.hjx.springbootmybatis.service.*.*(..))")
//    public void before(JoinPoint jp){
//        String methodName = jp.getSignature().getName()
//        if(StringUtils.startsWithAny(methodName,"get","select","find")){
//            DBContextHolder.slave();
//        }else{
//            DBContextHolder.master();
//        }
//    }
}
