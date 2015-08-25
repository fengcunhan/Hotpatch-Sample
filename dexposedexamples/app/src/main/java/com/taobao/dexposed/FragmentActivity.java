package com.taobao.dexposed;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by renxuan on 15/8/25.
 */
public class FragmentActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.content, new TestFragment());
        //transaction.add(getFragmentByTitle(title),title);
        transaction.commit();

    }
}
