package com.taobao.patch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodReplacement;

/**
 * Created by renxuan on 15/8/25.
 */
public class FragmentPatch implements IPatch {
    private static final String TAG="FragmentPatch";
    @Override
    public void handlePatch(final PatchParam patchParam) throws Throwable {
        {
            Class<?> cls = null;
            try {
                cls= patchParam.context.getClass().getClassLoader().loadClass("com.taobao.dexposed.TestFragment");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Log.e(TAG, "cls:" + cls);
            DexposedBridge.findAndHookMethod(cls, "getText", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.e(TAG, "methodHookParam:" + methodHookParam.method.getName());
                    return "from patch";
                }
            });

            DexposedBridge.findAndHookMethod(cls, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    ViewGroup view = (ViewGroup) methodHookParam.args[1];
                    TextView tv = new TextView(view.getContext());
                    tv.setText("replace onCreateView");
                    return tv;
                }
            });
        }
    }
}
