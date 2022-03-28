package com.qulix.yurkevichvv.trainingtask.util;

public class Util {

    public static String htmlSpecialChars(String s){
        return s.replaceAll("&","&amp;").replaceAll("<","&lt;").
                replaceAll(">","&gt;").replaceAll(  "\"", "&quot;");
    }

}
