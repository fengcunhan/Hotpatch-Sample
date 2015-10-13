package com.taobao.patch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;
import com.taobao.android.dexposed.XC_MethodReplacement;

/**
 * Created by renxuan on 15/9/8.
 */
public class TestPatch implements IPatch {
    private static final String TAG="TestPatch";
    @Override
    public void handlePatch(final PatchParam patchParam) throws Throwable {
        {
            Class<?> cls = null;
            try {
                cls= patchParam.context.getClass().getClassLoader().loadClass("com.taobao.model.Test");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            Log.e(TAG, "cls:" + cls);
            /**
             * 修改逻辑，原先是==0的时候返回Patch success，现在改成<=0都是返回Patch Success
             * */
            DexposedBridge.findAndHookMethod(cls, "getHello",int.class,new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.e(TAG, "methodHookParam:" + methodHookParam.method.getName());
                    int methodArgsLength=methodHookParam.args.length;
                    if(methodArgsLength>0){
                        int a=(int)methodHookParam.args[0];
                        if(a<=0){
                            return "Patch Success";
                        }
                    }
                    return "hello";
                }
            });

        }
    }
}
