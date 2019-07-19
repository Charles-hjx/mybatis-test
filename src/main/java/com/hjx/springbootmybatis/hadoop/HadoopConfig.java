package com.hjx.springbootmybatis.hadoop;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FsShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: hjx
 * @Date: 2019/7/19
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(name = "hadoop.name-node")
@Slf4j
public class HadoopConfig {


}
