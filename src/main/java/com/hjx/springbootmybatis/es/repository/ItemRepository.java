package com.hjx.springbootmybatis.es.repository;

import com.hjx.springbootmybatis.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: hjx
 * @Date: 2019/7/17
 * @Version 1.0
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item>  findByPriceBetween(double price1, double price2);
}
