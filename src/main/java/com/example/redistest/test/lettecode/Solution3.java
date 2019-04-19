package com.example.redistest.test.lettecode;

import java.util.ArrayList;
import java.util.Collections;

public class Solution3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

//    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
//
//    }

    public class ListNode {
        int val;
        ListNode next = null;
        ListNode(int val) { this.val = val; }
   }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> r = new ArrayList<>();
        if (listNode == null){
            return r;
        }
        while (listNode != null){
            r.add(listNode.val);
            listNode = listNode.next;
        }
        Collections.reverse(r);
        return r;
    }

    public String replaceSpace(StringBuffer str) {
        StringBuffer copy = new StringBuffer();
        for (int i = 0 ; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                copy.append("%20");
            }else{
                copy.append(str.charAt(i));
            }
        }
        return copy.toString();
    }

    public boolean Find(int target, int [][] array) {
        int len = array.length;
        int i = 0;
        while (i < len && len > 0){
            if (array[i][len - 1] < target){
                i++;
            }else if(array[i][len - 1] > target){
                len--;
            }else{
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}