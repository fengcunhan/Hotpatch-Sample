package com.taobao.dexposed;

import android.app.Application;
import android.os.StrictMode;

import com.hotpatch.HotpatchManager;


public class DexposedSampleApplication extends Application {

    static {
        // load xposed lib for hook.
        try {
            if (android.os.Build.VERSION.SDK_INT == 22){
                System.loadLibrary("dexposed_l51");
            } else if (android.os.Build.VERSION.SDK_INT > 19 && android.os.Build.VERSION.SDK_INT <= 21){
                System.loadLibrary("dexposed_l");
            } else if (android.os.Build.VERSION.SDK_INT > 14){
                System.loadLibrary("dexposed");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override  
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        HotpatchManager manager=HotpatchManager.getInstance(new DefaultPatchInfoRequest(this));
        manager.init(this);
    }  
}  
