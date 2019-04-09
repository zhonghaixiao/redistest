package com.example.redistest.test.lettecode;

import java.util.ArrayDeque;

public class Solution {
    public int evalRPN(String[] tokens) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < tokens.length; i++){
            if (tokens[i].equals("+")){
                int a = Integer.parseInt(deque.pop());
                int b = Integer.parseInt(deque.pop());
                deque.push(String.valueOf(b + a));
            }else if (tokens[i].equals("-")){
                int a = Integer.parseInt(deque.pop());
                int b = Integer.parseInt(deque.pop());
                deque.push(String.valueOf(b - a));
            }else if (tokens[i].equals("*")){
                int a = Integer.parseInt(deque.pop());
                int b = Integer.parseInt(deque.pop());
                deque.push(String.valueOf(b * a));
            }else if (tokens[i].equals("/")){
                int a = Integer.parseInt(deque.pop());
                int b = Integer.parseInt(deque.pop());
                deque.push(String.valueOf(b / a));
            }else{
                deque.push(tokens[i]);
            }
        }
        return Integer.parseInt(deque.pop());
    }
}