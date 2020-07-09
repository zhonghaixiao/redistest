package com.example.redistest.filemanage;

import sun.management.Agent;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        RandomAccessFile file = new RandomAccessFile("G:/test", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
//            for (int i = 0; i < 100; i++){
//                User user = new User("name" + i, i);
//                buffer.put()
//            }
            byte[] strBytes = "haixiao".getBytes("utf-8");
            buffer.putInt(strBytes.length);
            buffer.put(strBytes);
            buffer.flip();
            channel.write(buffer);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static class User implements Serializable{
        private String userName;
        private int userAge;
        public User(String userName, int userAge){
            this.userName = userName;
            this.userAge = userAge;
        }
    }

}
