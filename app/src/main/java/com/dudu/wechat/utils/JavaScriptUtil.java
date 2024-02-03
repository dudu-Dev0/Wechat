package com.dudu.wechat.utils;
import java.io.IOException;

public class JavaScriptUtil {
    public static String getString(String data,String key){
        String stringData = "error";
    	if(data.contains(key)){
            data = data.substring(data.indexOf(key));
            stringData = data.substring(data.indexOf("\"")+1,data.indexOf("\"",data.indexOf("\"")+1));
        }
        return stringData;
    }
    public int getInt(String data,String key) throws IOException {
    	
        return 0;
    }
}
