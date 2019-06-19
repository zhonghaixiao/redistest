package com.example.redistest.rpctest.rpc2;

import lombok.Data;

@Data
public class RpcResponse {

    private String requestId;
    private Throwable error;
    private Object result;

    // getter/setter...
}
