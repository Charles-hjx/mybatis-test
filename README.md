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
一、使用Java客户端管理ES
1、创建索引库
步骤：
	1）创建一个Java工程
	2）添加jar包，添加maven的坐标
	3）编写测试方法实现创建索引库
		1、创建一个Settings对象，相当于是一个配置信息。主要配置集群的名称。
		2、创建一个客户端Client对象
		3、使用client对象创建一个索引库
		4、关闭client对象
2、使用Java客户端设置Mappings
	步骤：
	1）创建一个Settings对象
	2）创建一个Client对象
	3）创建一个mapping信息，应该是一个json数据，可以是字符串，也可以是XContextBuilder对象
	4）使用client向es服务器发送mapping信息
	5）关闭client对象
3、添加文档
	步骤：
	1）创建一个Settings对象
	2）创建一个Client对象
	3）创建一个文档对象，创建一个json格式的字符串，或者使用XContentBuilder
	4）使用Client对象吧文档添加到索引库中
	5）关闭client
4、添加文档第二种方式
	创建一个pojo类
	使用工具类把pojo转换成json字符串
	把文档写入索引库

二、使用es客户端实现搜索
1、根据id搜索
	QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1", "2");
2、根据Term查询（关键词）
	QueryBuilder queryBuilder = QueryBuilders.termQuery("title", "北方");
3、QueryString查询方式（带分析的查询）
	QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("速度与激情")
                .defaultField("title");
查询步骤：
	1）创建一个Client对象
	2）创建一个查询对象，可以使用QueryBuilders工具类创建QueryBuilder对象。
	3）使用client执行查询
	4）得到查询的结果。
	5）取查询结果的总记录数
	6）取查询结果列表
	7）关闭client
4、分页的处理
	在client对象执行查询之前，设置分页信息。
	然后再执行查询
	 //执行查询
        SearchResponse searchResponse = client.prepareSearch("index_hello")
                .setTypes("article")
                .setQuery(queryBuilder)
                //设置分页信息
                .setFrom(0)
                //每页显示的行数
                .setSize(5)
                .get();
	分页需要设置两个值，一个from、size
	from：起始的行号，从0开始。
	size：每页显示的记录数
5、查询结果高亮显示
（1）高亮的配置
	1）设置高亮显示的字段
	2）设置高亮显示的前缀
	3）设置高亮显示的后缀
（2）在client对象执行查询之前，设置高亮显示的信息。
（3）遍历结果列表时可以从结果中取高亮结果