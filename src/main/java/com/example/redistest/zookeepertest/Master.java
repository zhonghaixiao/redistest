package com.example.redistest.zookeepertest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

public class Master implements Watcher {
    ZooKeeper zk;
    String hostPort;
    String serverId = Integer.toHexString(new Random().nextInt());
    static boolean isLeader = false;


    public void checkMaster(){
        zk.getData("/master", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String s, Object o, byte[] bytes, Stat stat) {
                switch (KeeperException.Code.get(rc)){
                    case CONNECTIONLOSS:
                        checkMaster();
                        return;
                    case NONODE:
                        runForMaster();
                        return;
                }
            }
        }, null);
    }

    void runForMaster() {
        zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String s, Object o, String s1) {
                        switch (KeeperException.Code.get(rc)){
                            case CONNECTIONLOSS:
                                checkMaster();
                                return;
                            case OK:
                                isLeader = true;
                                break;
                            default:
                                isLeader = false;
                        }
                        System.out.println("i am " + (isLeader ? "":"not") + "the leader");
                    }
                }, null);
    }

    public Master(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    @Override
    public void process(WatchedEvent e) {
        System.out.println(e);
    }

    void stopZK() throws InterruptedException {zk.close();}

    public void bootstrap(){
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    void createParent(String path, byte[] data){
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int i, String s, Object ctx, String s1) {
                switch (KeeperException.Code.get(i)){
                    case CONNECTIONLOSS:
                        createParent(path, (byte[]) ctx);
                        break;
                    case OK:
                        System.out.println("Parent created");
                        break;
                    case NODEEXISTS:
                        System.out.println("parent already registered: " + path);
                        break;
                        default:
                            System.out.println("something went wrong: " + KeeperException.create(KeeperException.Code.get(i), path));
                }
            }
        }, data);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Master master = new Master("127.0.0.1:2181");
        master.startZK();

        master.runForMaster();
        if (isLeader){
            System.out.println("i am leader");
            Thread.sleep(60000);
        }else{
            System.out.println("someone else is leader");
        }

        master.stopZK();
    }

}
