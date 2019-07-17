# mybatis-test

learning mybatis source code

## 读写分离
1.枚举类型（3种），
2，数据源配置（3个，包括事务方面）
3.aop（定义哪些方法使用哪个数据源）
4.contextHolder（给每个线程设置 不同的数据源），
5，MyRoutingDataSource：设置数据源路由，在mybatisConfig中调用。框架中是使用动态代理，
    根据不同的key（determineCurrentLookupKey()中的数据） 返回不同的数据源。



## 异常处理系统
    通常情况下 异常处理需要在特定的 业务环境下。即：不同环境，不同的异常类，不同的返回码，不同的
异常信息。怎么利用断言 Assert 来优雅的实现 异常的处理？


## spring data elasticsearch


