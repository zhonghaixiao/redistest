package com.example.redistest;

import com.example.redistest.netty.testproto.ProtoDemo;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

public class ProtoBufTest {

    @Test
    public void protobufTest() {
        ProtoDemo.Student.Builder builder = ProtoDemo.Student.newBuilder();
        builder.setEmail("280208673@qq.com");
        builder.setName("zhou");
        builder.setId(100);

        ProtoDemo.Student student = builder.build();
        System.out.println(student);
        System.out.println("===============  to byte");

        for (byte b : student.toByteArray()) {
            System.out.println(b);
        }
        byte[] bytes = student.toByteArray();
        try {
            ProtoDemo.Student student1 = ProtoDemo.Student.parseFrom(bytes);
            System.out.println("email:" + student1.getEmail());
            System.out.println("name:" + student1.getName());
            System.out.println("id:" + student1.getId());
        } catch (InvalidProtocolBufferException e) {
        }
    }
}
