package com.hjx.springbootmybatis.hadoop.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT> :
 * KEYIN , VALUEIN 对应mapper输出的KEYOUT, VALUEOUT类型
 *
 *  KEYOUT，VALUEOUT 对应自定义reduce逻辑处理结果的输出数据类型 KEYOUT是单词 VALUEOUT是总次数
 *
 * @Author: hjx
 * @Date: 2019/7/22
 * @Version 1.0
 */
public class WordcountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        // 1 汇总各个key的个数
        for(IntWritable value : values){
            count += value.get();
        }
        context.write(key,new IntWritable(count));
    }
}
