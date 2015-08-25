package com.taobao.dexposed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by renxuan on 15/8/25.
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv=new TextView(container.getContext());
        tv.setText(getText());
        return tv;
    }

    public String getText(){
        return "Fragment test";
    }
}
