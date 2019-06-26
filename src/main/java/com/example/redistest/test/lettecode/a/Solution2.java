package com.example.redistest.test.lettecode.a;

import java.util.ArrayList;

public class Solution2 {
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> t = new ArrayList<>();
        if (root != null){
            add(t, root);
        }
        return t;
    }

    private void add(ArrayList<Integer> t, TreeNode r){
        if (r != null){
            t.add(r.val);
        }
        if (r.left != null){
            add(t, r.left);
        }
        if (r.left != null){
            add(t, r.right);
        }
    }

    public static void main(String[] args){
        Solution2 solution2 = new Solution2();
        TreeNode node = new TreeNode(1, null, null);
        System.out.println(solution2.postorderTraversal(node));
    }



    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        TreeNode(int x, TreeNode l, TreeNode r) { val = x; left = l; right = r;}
    }
}
