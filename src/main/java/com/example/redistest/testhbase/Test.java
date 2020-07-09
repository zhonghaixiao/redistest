//package com.example.redistest.testhbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.util.Bytes;
//
//public class Test {
//
//    public static void main(String[] args) throws Exception {
//        Configuration config = HBaseConfiguration.create();
////        config.set("hbase.zookeeper.quorum", "VM_0_11_centos");
//        config.set("hbase.zookeeper.quorum", "122.152.207.136");
//        config.set("hbase.zookeeper.property.clientPort", "2181");
//
//        // instantiate HTable class
//        HTable hTable = new HTable(config, "testtable");
//        Put put = new Put(Bytes.toBytes("row1"));
//        put.add(Bytes.toBytes("colfaml"), Bytes.toBytes("quall"), Bytes.toBytes("val1"));
//
//
//
//        // save the put Instance to the HTable.
//        hTable.put(put);
//        System.out.println("data inserted successfully");
//
//        // close HTable instance
//        hTable.close();
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
