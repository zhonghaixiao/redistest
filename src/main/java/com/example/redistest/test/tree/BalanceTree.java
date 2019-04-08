package com.example.redistest.test.tree;

import java.util.Comparator;

public class BalanceTree<T extends Comparable<? super T>> {

    private BinaryNode<T> root;

    private Comparator<? super T> cmp;

    public BalanceTree(){
        this(null);
    }

    public BalanceTree(Comparator<? super T> cmp){
        this.cmp = cmp;
    }

    private int myCompare(T t1, T t2){
        if (cmp != null){
            return cmp.compare(t1, t2);
        }else{
            return ((Comparable)t1).compareTo((t2));
        }
    }

    private int getHeight(BinaryNode<T> t){
        if (t == null){
            return -1;
        }else{
            return t.height;
        }
    }

    private BinaryNode<T> LL(BinaryNode<T> t){
        BinaryNode<T> q = t.left;
        t.left = q.right;
        q.right = t;
        t = q;
        t.height = Math.max(getHeight(t.left), )

    }

    private BinaryNode<T> LR(BinaryNode<T> t){

    }

    private BinaryNode<T> RR(BinaryNode<T> t){

    }

    private BinaryNode<T> RL(BinaryNode<T> t){

    }

    public void insert(BinaryNode<T> tree, T value){

    }

    static class BinaryNode<D>{
        public BinaryNode(D elem, BinaryNode<D> left, BinaryNode<D> right){
            this.elem = elem;
            this.left = left;
            this.right = right;

        }
        D elem;
        int height;
        BinaryNode<D> left;
        BinaryNode<D> right;
    }

}
