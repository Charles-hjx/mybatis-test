package com.hjx.springbootmybatis;

import com.hjx.springbootmybatis.entity.Item;
import com.hjx.springbootmybatis.es.repository.ItemRepository;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testCreateIndex(){
        elasticsearchTemplate.createIndex(Item.class);
    }


    //创建索引 添加映射关系
    @Test
    public void testElasticSearch(){
        boolean index = elasticsearchTemplate.createIndex(Item.class);
        boolean mapping = elasticsearchTemplate.putMapping(Item.class);
        System.out.println("创建索引:"+index + "添加映射关系:"+mapping);
//        log.debug("创建索引:{} 添加映射关系:{}",index,mapping);
    }

    // 删除索引
    @Test
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex("item");
    }

    //新增
    @Test
    public void insert(){
        Item item = new Item(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
        itemRepository.save(item);
    }

    /**
     * 批量save
     */
    @Test
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }
    /**
     * 修改：修改和新增是同一个接口，区分的依据就是id，这一点跟我们在页面发起PUT请求是类似的。
     */

    /**
     * 删除所有
     */
    @Test
    public void delete() {
        //删除所有数据 ，不删索引
        itemRepository.deleteAll();
    }

    /**
     * 基本查询
     */
    @Test
    public void query() {
        // 查询全部，并按照价格降序排序
        Iterable<Item> items = itemRepository.findAll(Sort.by("price").descending());
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义方法
     */
    @Test
    public void queryByPriceBetween() {
        // 根据价格区间查询
        List<Item> list = itemRepository.findByPriceBetween(5000.00, 6000.00);
        list.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义查询
     */
    @Test
    public void search(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的 分词查询 类型于模糊搜索，不精确的匹配
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机"));
        //搜索，获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());

        long totalElements = items.getTotalElements();
        System.out.println("total Elements = "+totalElements);
        items.forEach(item -> System.out.println("item="+item));
    }

    /**
     * 分页查询
     */
    @Test
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询 termQuery 精确匹配
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 搜索，获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        System.out.println("总页数 = " + items.getTotalPages());
        System.out.println("当前页：" + items.getNumber());
        System.out.println("每页大小：" + items.getSize());
        items.forEach(item -> System.out.println("item = " + item));

    }

    /**
     * 排序
     */
    @Test
    public void searchAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 聚合为桶 （感觉相当于 分组，后利用聚合函数等 来计算）
     */
    @Test
    public void testAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) itemRepository.search(queryBuilder.build());

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }

    /**
     * 嵌套聚合，求平均值
     */
    @Test
    public void testSubAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }

    @Test
    public void testCommon(){
        System.out.println(Math.round(2.4));
    }

    //测试文件的上传
    @Test
    public void testHadoop() throws IOException {
        //zhelde的设置没起到作用 靠的是 系统环境变量的设置
//        System.setProperty("HADOOP_USER_NAME","hjx");
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://ubuntu01:9000");
        FileSystem fileSystem = FileSystem.get(configuration);

        // 2 把本地文件上传到文件系统中
        fileSystem.copyFromLocalFile(new Path("f:/hello.txt"), new Path("/hello1.copy.txt"));

        // 3 关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    //测试 // 参数优先级： 1、客户端代码中设置的值  2、classpath下的用户自定义配置文件 3、然后是服务器的默认配置
    @Test
    public void testHadoopGet() throws IOException {
        // new Configuration();的时候，它就会去加载jar包中的hdfs-default.xml
        // 然后再加载classpath下的hdfs-site.xml
        Configuration configuration = new Configuration();
//        configuration.set("fs.defaultFS","hdfs://ubuntu01:9000");
        //设置副本数
        configuration.set("dfs.replication",",");
        FileSystem fileSystem = FileSystem.get(configuration);

        System.out.println(fileSystem.toString());
    }

    //文件上传
    @Test
    public void testUploadFile() throws URISyntaxException, IOException, InterruptedException {

        Configuration configuration = new Configuration();
        String srcUrl = "f:/question.txt";
        String dstUrl = "hdfs://ubuntu01:9000/user/hjx/que.txt";
        //这里对用户的设置 在服务器中 起作用的 但“hjx” 会被拒绝 “root”则不会
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://ubuntu01:9000"), configuration, "hjx");
        fileSystem.copyFromLocalFile(new Path(srcUrl),new Path(dstUrl));

        fileSystem.close();
    }


    //获取 block 块位置

    @Test
    public void testGetBlockLocation() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs:/ubuntu01:9000"),configuration,"hjx");

        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (int i = 0; i < blockLocations.length; i++) {
                BlockLocation blockLocation = blockLocations[i];
                String[] hosts = blockLocation.getHosts();
                for (int j = 0; j < hosts.length; j++) {
                    String host = hosts[j];
                    System.out.println("block location:====>>>:"+host);
                }

                System.out.println("======================");
            }
        }
    }



}
