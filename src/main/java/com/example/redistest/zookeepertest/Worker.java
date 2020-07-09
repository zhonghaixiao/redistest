package com.example.redistest.zookeepertest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import scala.reflect.io.Path;

import java.io.IOException;
import java.util.Random;

public class Worker implements Watcher {

    ZooKeeper zk;
    String port;
    String serverId = Integer.toHexString(new Random().nextInt());
    String status;
    String name;

    public Worker(String hostPort){
        this.port = hostPort;
    }

    void startZk() throws IOException {
        zk = new ZooKeeper(port, 15000, this);
    }

    void register(){
        zk.create("/worker/worker-" + serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int i, String path, Object o, String s1) {
                        switch (KeeperException.Code.get(i)){
                            case CONNECTIONLOSS:
                                register();
                                break;
                            case OK:
                                System.out.println("Registered successfully: " + serverId);
                                break;
                            case NODEEXISTS:
                                System.out.println("already registered: " + serverId);
                                break;
                                default:
                                    System.out.println("something wen wrong: "
                                            + KeeperException.create(KeeperException.Code.get(i), path));
                        }
                    }
                }, null);
    }


    synchronized private void updateStatus(String status){
        if (status == this.status){
            zk.setData("/workers/" + name, status.getBytes(), -1,
                    new AsyncCallback.StatCallback(){
                @Override
                public void processResult(int rc, String path, Object ctx, Stat stat) {
                    switch (KeeperException.Code.get(rc)){
                        case CONNECTIONLOSS:
                            updateStatus((String) ctx);
                            return;
                    }
                }
            }, status);
        }
    }

    public void setStatus(String status){
        this.status = status;
        updateStatus(status);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString() + ", " + port);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Worker worker = new Worker("127.0.0.1:2181");
        worker.startZk();
        worker.register();
        Thread.sleep(30000);
    }

}
