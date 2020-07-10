package com.example.redistest.netty.mytalk.client;


import mytalk.domain.Message;

interface TalkClientListener{
        void onRegisterResponse(boolean isSuccess);
        void onReceiveMessage(Message message);
        void onRegisterDisconnect(boolean isConnected);
    }
