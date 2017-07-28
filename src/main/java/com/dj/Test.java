package com.dj;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author d.c
 * @since 2017/4/23
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String str =sf.format(new Date());
        str=str+"aaaaddd";
        System.out.println(str.substring(15,str.length()));
    }
}
