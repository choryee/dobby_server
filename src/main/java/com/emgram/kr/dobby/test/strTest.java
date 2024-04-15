package com.emgram.kr.dobby.test;

public class strTest {
    public static void main(String[] args) {
        String str="김태수";
        StringBuilder rv=new StringBuilder(str);
        int sl=str.length();
        System.out.println(sl);
        rv.setCharAt(sl - 1, '*');
        System.out.println(rv.toString());
        //masking();
    }

//    public static String masking(){
//        String str="김태수";
//        StringBuilder rv=new StringBuilder(str);
//        int sl=str.length();
//        rv.setCharAt(sl-1, '*');
//        return
//    }
}
