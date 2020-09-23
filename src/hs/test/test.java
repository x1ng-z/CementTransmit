package hs.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/4/21 18:31
 */
public class test {
    static Pattern pattern=Pattern.compile("([a-zA-Z]*)([0-9|.]*)");
    public static void main(String[] args) {

        List<Integer> unuserpinscope=new ArrayList<>();
        unuserpinscope.add(0,1);
        unuserpinscope.add(0,2);
         Pattern pvpattern = Pattern.compile("(^pv(\\d+)$)");

         Matcher matcher=pvpattern.matcher("pv132");

         if(matcher.find()){
             System.out.println("find is");
             System.out.println(matcher.group());
             System.out.println(matcher.group(1));
             System.out.println(matcher.group(2));

         }
        try {
            String gb = "喷码机通讯异常";
            byte[] bytes = gb.getBytes("gb2312");// 先把字符串按gb2312转成byte数组
            StringBuilder gbString = new StringBuilder();
            for (byte b : bytes)// 循环数组
            {
                String temp = Integer.toHexString(b);// 再用Integer中的方法，把每个byte转换成16进制输出
                temp = temp.substring(6, 8); // 截取
                gbString.append("%" + temp);
            }
            System.out.println(gbString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        if(1==0){
            System.out.println("1");
        }else if(2==0){
            System.out.println("2");
        }else if(3==0){
            System.out.println("3");
        }else {
            System.out.println("4");
        }





//        Matcher matcher=pattern.matcher("opc192.168.10.212");
        Matcher matcher2=pattern.matcher("constant");

        while (matcher.find()){
            String a=matcher.group(2);
            System.out.println(a);
        }

//                ="88 18 40 10 12 06 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 50 43 34 32 2E 35 00 00 00 00 00 00 00 00 00 00 32 30 00 00 00 42 31 33 30 34 32 30 30 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A"
//        String a="88 18 40 10 12 05 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 50 43 34 32 2E 35 00 00 00 00 00 00 00 00 00 00 32 30 00 00 00 42 31 33 30 34 32 30 30 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";
//        String d="88 18 40 10 12 06 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 4D 20 33 32 2E 35 B0 FC D7 B0 00 00 00 00 00 00 32 36 30 00 00 42 31 33 30 34 32 30 30 33 31 48 4D 30 33 30 30 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";
//        String b="88 18 40 10 12 06 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 4D 20 33 32 2E 35 B0 FC D7 B0 00 00 00 00 00 00 32 36 30 00 00 42 31 33 30 34 32 30 30 33 31 48 4D 30 33 30 30 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";
//        String c="88 18 40 10 12 05 A5 00 00 01 31 00 00 00 00 00 00 00 00 00 BA EC CA A8 50 43 34 32 2E 35 00 00 00 00 00 00 00 00 00 00 32 30 00 00 00 31 30 34 32 31 30 31 31 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";
//                ="88 18 40 10 12 05 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 50 43 34 32 2E 35 00 00 00 00 00 00 00 00 00 00 32 30 00 00 00 42 31 33 30 34 32 30 30 33 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A"
//
//
//        String e="88 18 40 10 12 06 A5 00 00 01 31 00 00 00 00 00 00 00 00 00 BA EC CA A8 50 43 34 32 2E 35 00 00 00 00 00 00 00 00 00 00 32 30 00 00 00 31 30 34 32 32 30 31 31 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";
//        String f="88 18 40 10 12 06 A5 00 00 01 B8 D3 43 30 36 57 39 35 00 00 BA EC CA A8 4D 20 33 32 2E 35 B0 FC D7 B0 00 00 00 00 00 00 32 36 30 00 00 42 31 33 30 34 32 30 30 33 31 48 4D 30 33 30 30 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 5A";



    }
}
