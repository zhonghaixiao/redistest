package com.example.redistest.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerTest {

    public static void main(String[] args){
        String topicName = "test";
        String groupId = "group1";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupId);
        props.put("enable.auto.commit", true);
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);
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













