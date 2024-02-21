package com.dudu.wechat.utils;

public class HeaderParser {
    public static String get(String data, String key) {
        String value = "";
        if (data.contains(key)) {
            data = data.substring(data.indexOf(key)).replaceAll(" +", "");
            value = data.substring(data.indexOf("=") + 1, data.indexOf(";"));
        }
        return value;
    }
}
