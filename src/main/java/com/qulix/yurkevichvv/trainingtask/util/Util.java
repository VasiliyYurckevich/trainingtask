package com.qulix.yurkevichvv.trainingtask.util;

public class Util {

    public static String htmlSpecialChars(String s){
        return s.replace("&","&amp").replace("<","&lt").
                replace(">","&gt").replace("\"", "&quot").replace("\"","&quot");
    }

}
