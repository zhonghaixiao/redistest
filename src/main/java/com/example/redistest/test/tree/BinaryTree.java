package com.example.redistest.test.tree;

import java.util.Comparator;

//public class BinaryTree<T extends Comparable<? super T>> {
public class BinaryTree<T> {

    private BinaryNode<T> root;

    private Comparator<? super T> cmp;

    public BinaryTree(){
       this(null);
    }

    public BinaryTree(Comparator<? super T> cmp){
        this.cmp = cmp;
    }

    private int myCompare(T t1, T t2){
        if (cmp != null){
            return cmp.compare(t1, t2);
        }else{
            return ((Comparable)t1).compareTo((t2));
        }
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(T x, BinaryNode<T> t){
        if (t == null){
            return false;
        }
        int c = myCompare(x, t.elem);
        if (c < 0){
            return contains(x, t.left);
        }else if(c > 0){
            return contains(x, t.right);
        }else{
            return true;
        }
    }

    public BinaryNode<T> findMin(BinaryNode<T> t){
        if (t != null){
            while (t.left != null){
                t = t.left;
            }
        }
        return t;
    }

    public BinaryNode<T> findMax(BinaryNode<T> t){
        if (t == null){
            return null;
        }
        if (t.right == null){
            return t;
        }
        return findMax(t.right);
    }

    public BinaryNode<T> insert(T x, BinaryNode<T> t){

        if (t == null){
            return new BinaryNode<>(x, null, null);
        }
        int c = myCompare(x, t.elem);
        if (c < 0){
            t.left = insert(x, t.left);
        }else if (c > 0){
            t.right = insert(x, t.right);
        }
        return t;
    }

    static class BinaryNode<D>{
        public BinaryNode(D elem, BinaryNode left, BinaryNode right){
            this.elem = elem;
            this.left = left;
            this.right = right;

        }
        D  elem;
        BinaryNode left;
        BinaryNode right;
    }

}
