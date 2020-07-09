package com.example.redistest.test.lettecode.a;

import java.util.ArrayList;
import java.util.HashSet;

public class Solution2 {

    public static void main(String[] args){
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.lengthOfLongestSubstring("au"));
    }


    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        int maxLength = 0;
        for(; right < s.length();){
            Character c = s.charAt(right);
            int i;
            for(i = left; i < right; i++){
                if(c.equals(s.charAt(i))){
                    maxLength = Math.max(maxLength, right - left);
                    left = i + 1;
                    break;
                }
            }
            if(i == right){
                right++;
            }
            if(right == s.length()){
                maxLength = Math.max(maxLength, right - left);
            }
        }
        return maxLength;
    }

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





    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        TreeNode(int x, TreeNode l, TreeNode r) { val = x; left = l; right = r;}
    }
}
