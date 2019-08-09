package com.example.redistest.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerTest {

    public static void main(String[] args){
        String topicName = "topic1";
        String groupId = "group1";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupId);//消费者组，同属一个消费者组的消费者均衡消费一个主题的所有分区
        props.put("enable.auto.commit", true);//自动提交
        props.put("auto.commit.interval.ms", "1000");//自动提交的时间间隔
        props.put("auto.offset.reset", "earliest");//当前消费者没有位移信息，消费者从哪里开始消费
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicName));//订阅topic
        try{
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(1000);
                for (ConsumerRecord<String,String> record: records){
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
                            record.key(), record.value());
                }
            }
        }finally {
            consumer.close();
        }
    }

}













