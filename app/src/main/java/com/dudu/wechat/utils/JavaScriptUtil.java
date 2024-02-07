package com.dudu.wechat.utils;
import java.io.IOException;

public class JavaScriptUtil {
    public static String getString(String data,String key){
        String stringData = "error";
    	if(data.contains(key)){
            data = data.substring(data.indexOf(key)).replaceAll(" +","").replaceAll("[\"']", "");
            stringData = data.substring(data.indexOf("=")+1,data.indexOf(";"));
        }
        return stringData;
    }
    public static String getBase64Data(String data,String key){
        String base64Data = "error";
    	if(data.contains(key)){
            data = data.substring(data.indexOf(key)).replaceAll(" +","");
            base64Data = data.substring(data.indexOf("='")+2,data.indexOf("';"));
        }
        return base64Data;
    }
    public static int getInt(String data,String key){
    	int intData = -1;
        if(data.contains(key)) {
        	data = data.substring(data.indexOf(key)).replaceAll(" +","");
            intData = Integer.valueOf(data.substring(data.indexOf("=")+1,data.indexOf(";")));
        }
        return intData;
    }
}
