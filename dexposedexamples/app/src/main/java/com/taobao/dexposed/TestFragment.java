package com.taobao.dexposed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taobao.model.Test;

/**
 * Created by renxuan on 15/8/25.
 */
public class TestFragment extends Fragment {
    private Test mTest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTest=new Test();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv=new TextView(container.getContext());
        tv.setText(getText());
        return tv;
    }

    public String getText(){
        //如果 没有patch成功，那就得到hello
        return mTest.getHello(-1);
    }
}
