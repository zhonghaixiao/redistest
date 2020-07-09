package com.example.redistest.undertowtest;

import com.alibaba.fastjson.JSON;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.protocol.ajp.AjpOpenListener;
import io.undertow.util.Headers;
import org.xnio.*;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Undertowtest {

    public static void main(String[] args) throws IOException {

        Undertow server = Undertow.builder().addHttpListener(8000, "localhost")
                .setHandler(exchange -> {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                    exchange.getResponseSender().send(JSON.toJSONString(new UserTest("haixiao", 28)));
                })
                .build();
        server.start();

//        Xnio xnio = Xnio.getInstance();
//
//        XnioWorker worker = xnio.createWorker(OptionMap.builder()
//                .set(Options.WORKER_IO_THREADS, 1)
//                .set(Options.WORKER_TASK_CORE_THREADS, 2)
//                .set(Options.WORKER_TASK_MAX_THREADS, 2)
//                .set(Options.TCP_NODELAY, true)
//                .getMap());
//
//        OptionMap socketOptions = OptionMap.builder()
//                .set(Options.WORKER_IO_THREADS, 1)
//                .set(Options.TCP_NODELAY, true)
//                .set(Options.REUSE_ADDRESSES, true)
//                .getMap();
//
//        Pool<ByteBuffer> buffers = new ByteBufferSlicePool(BufferAllocator.DIRECT_BYTE_BUFFER_ALLOCATOR, bufferSize, bufferSize * buffersPerRegion);
//
//
//        if (listener.type == Undertow.ListenerType.AJP) {
//            AjpOpenListener openListener = new AjpOpenListener(buffers, serverOptions, bufferSize);
//            openListener.setRootHandler(rootHandler);
//            ChannelListener<AcceptingChannel<StreamConnection>> acceptListener = ChannelListeners.openListenerAdapter(openListener);
//            AcceptingChannel<? extends StreamConnection> server = worker.createStreamConnectionServer(new InetSocketAddress(Inet4Address.getByName(listener.host), listener.port), acceptListener, socketOptions);
//            server.resumeAccepts();
//        } else if (listener.type == ListenerType.HTTP) {
//            HttpOpenListener openListener = new HttpOpenListener(buffers, OptionMap.builder().set(UndertowOptions.BUFFER_PIPELINED_DATA, true).addAll(serverOptions).getMap(), bufferSize);
//            openListener.setRootHandler(rootHandler);
//            ChannelListener<AcceptingChannel<StreamConnection>> acceptListener = ChannelListeners.openListenerAdapter(openListener);
//            AcceptingChannel<? extends StreamConnection> server = worker.createStreamConnectionServer(new InetSocketAddress(Inet4Address.getByName(listener.host), listener.port), acceptListener, socketOptions);
//            server.resumeAccepts();
//        } else if (listener.type == ListenerType.HTTPS){
//            HttpOpenListener openListener = new HttpOpenListener(buffers, OptionMap.builder().set(UndertowOptions.BUFFER_PIPELINED_DATA, true).addAll(serverOptions).getMap(), bufferSize);
//            openListener.setRootHandler(rootHandler);
//            ChannelListener<AcceptingChannel<StreamConnection>> acceptListener = ChannelListeners.openListenerAdapter(openListener);
//            XnioSsl xnioSsl;
//            if(listener.sslContext != null) {
//                xnioSsl = new JsseXnioSsl(xnio, OptionMap.create(Options.USE_DIRECT_BUFFERS, true), listener.sslContext);
//            } else {
//                xnioSsl = xnio.getSslProvider(listener.keyManagers, listener.trustManagers, OptionMap.create(Options.USE_DIRECT_BUFFERS, true));
//            }
//            AcceptingChannel <SslConnection> sslServer = xnioSsl.createSslConnectionServer(worker, new InetSocketAddress(Inet4Address.getByName(listener.host), listener.port), (ChannelListener) acceptListener, socketOptions);
//            sslServer.resumeAccepts();
//        }


    }

}
