package com.dudu.wechat.services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WechatSyncService extends Service{
    public WechatSyncService(){
        
    }
    @Override
    public IBinder onBind(Intent intent) {
    	throw new UnsupportedOperationException("not did it");
    }
    @Override
    public void onCreate() {
    	super.onCreate();
    
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
    	return(super.onStartCommand(intent,flags,startId));
    }
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
