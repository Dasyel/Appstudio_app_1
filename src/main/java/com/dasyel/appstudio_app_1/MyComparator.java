package com.dasyel.appstudio_app_1;

public class MyComparator implements java.util.Comparator<String> {

    public int compare(String s1, String s2) {
        return - s1.length() + s2.length();
    }
}