package com.example.redistest.test.lettecode.question6;

public class Solution {

//    /**
//     * 最长回文串
//     * @param s
//     * @return
//     */
//    public String longestPalindrome(String s) {
//
//    }

    public int reverse(int x) {
        int r = 0;
        while (x != 0){
            int k = x%10;
            if (r > Integer.MAX_VALUE / 10 || (r == Integer.MAX_VALUE / 10 && k > 7))
                return 0;
            if (r < Integer.MIN_VALUE / 10 || (r == Integer.MIN_VALUE / 10 && k < -8))
                return 0;

            r = r * 10 + k;
            x /= 10;
        }
        return r;
    }

    public String convert(String s, int n) {
        StringBuilder sb = new StringBuilder();
        if (s.length() == 1 || n == 1){
            return s;
        }
        int length = s.length();
        for (int i = 0; i < n; i++){
            for (int j = 0, index; (index = caculateIndex(i,j, n)) < length; j++){
                sb.append(s.charAt(index));
            }
        }
        return sb.toString();
    }

    private int caculateIndex(int i, int j, int n) {
        if (i == 0 || i == n - 1){
            return i + 2*j*(n - 1);
        }else{
            if (j%2 == 0){
                return i + (n-1)*j;
            }else{
                return i + 2*(n-i-1) + (n-1)*(j-1);
            }
        }
    }

    public static void main(String[] args){
        System.out.println();
        System.out.println(new Solution().reverse(-123));
//        System.out.println(new Solution().convert("PAYPALISHIRING", 3));
    }


}
