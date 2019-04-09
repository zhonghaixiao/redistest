package com.example.redistest.test.lettecode;

import java.util.Currency;
import java.util.LinkedList;

public class Solution1 {


    public int run(TreeNode root) {
        if (root == null){
            return 0;
        }
        LinkedList<TreeNode> treeNodes = new LinkedList<>();
        TreeNode now;
        int level = 0;
        treeNodes.push(root);
        while (!treeNodes.isEmpty()){
            int length = treeNodes.size();
            level++;
            for (int i = 0; i < length; i++){
                TreeNode t = treeNodes.pop();
                if (t.left == null && t.right == null){
                    return level;
                }
                if (t.left != null){
                    treeNodes.push(t.left);
                }
                if (t.right != null){
                    treeNodes.push(t.right);
                }
            }
        }
        return 0;
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

}
