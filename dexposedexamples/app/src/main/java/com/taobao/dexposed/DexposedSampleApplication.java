package com.taobao.dexposed;

import android.app.Application;
import android.os.StrictMode;

import com.hotpatch.HotpatchManager;


public class DexposedSampleApplication extends Application {  

    @Override  
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        HotpatchManager manager=HotpatchManager.getInstance(new DefaultPatchInfoRequest(this));
        manager.init(this);
    }  
}  
