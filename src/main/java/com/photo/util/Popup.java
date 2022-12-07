package com.photo.util;

public class Popup {

    public static String historyBack(String msg){
        StringBuffer sb = new StringBuffer();
        sb.append("<script>alert('"+msg+"'); history.back();</script>");
        return sb.toString();
    }
}
