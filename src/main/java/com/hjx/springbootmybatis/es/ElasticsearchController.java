package com.hjx.springbootmybatis.es;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author: hjx
 * @Date: 2019/7/16
 * @Version 1.0
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class ElasticsearchController {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /*@Autowired
    ItemRepository itemRepository;

    //创建索引 添加映射关系
    @GetMapping("/es")
    public void testElasticSearch(){
        boolean index = elasticsearchTemplate.createIndex(Item.class);
        boolean mapping = elasticsearchTemplate.putMapping(Item.class);
        log.debug("创建索引:{} 添加映射关系:{}",index,mapping);
    }

    // 删除索引
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex("item");
    }

    //新增
    public void insert(){
        Item item = new Item(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
        itemRepository.save(item);
    }

    *//**
     * 批量save
     *//*
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }
    *//**
     * 修改：修改和新增是同一个接口，区分的依据就是id，这一点跟我们在页面发起PUT请求是类似的。
     *//*

    *//**
     * 删除所有
     *//*
    public void delete() {
        itemRepository.deleteAll();
    }

    *//**
     * 基本查询
     *//*
    public void query() {
        // 查询全部，并按照价格降序排序
        Iterable<Item> items = itemRepository.findAll(Sort.by("price").descending());
        items.forEach(item -> System.out.println("item = " + item));
    }

    *//**
     * 自定义方法
     *//*
    public void queryByPriceBetween() {
        // 根据价格区间查询
        List<Item> list = itemRepository.findByPriceBetween(5000.00, 6000.00);
        list.forEach(item -> System.out.println("item = " + item));
    }

    *//**
     * 自定义查询
     *//*

    public void search(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的 分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米手机"));
        //搜索，获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());

        long totalElements = items.getTotalElements();
        System.out.println("total Elements"+totalElements);
        items.forEach(item -> System.out.println("item="+item));
    }

    *//**
     * 分页查询
     *//*
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
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

    *//**
     * 排序
     *//*
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

    *//**
     * 聚合为桶
     *//*
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

        *//**
         * 嵌套聚合，求平均值
         *//*
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

        }*/
}
