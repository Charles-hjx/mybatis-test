package com.hjx.springbootmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * indexName：对应索引库名称
 * type：对应在索引库中的类型
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 *
 * @Author: hjx
 * @Date: 2019/7/16
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "item",type = "docs",shards = 1,replicas = 0)
public class Item {

    @Id
    private Long id;
    /**
     * type：字段类型，是枚举：FieldType，
     * 可以是text、long、short、date、integer、object等
     *
     *text：存储数据时候，会自动分词，并生成索引
     * analyzer：分词器名称，这里的ik_max_word即使用ik分词器
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title; //标题

    /**
     * keyword：存储数据时候，不会分词建立索引
     */
    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    /**
     * index：是否索引，布尔类型，默认是true
     */
    @Field(index = false ,type = FieldType.Keyword)
    private String images; // 图片地址

}
