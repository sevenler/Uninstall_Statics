package com.example.untitled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        Intent intent = new Intent(this, SDCardListenSer.class);
        startService(intent);


        init();

    }


    private native void init();

    static {
        Log.d("onEvent", "load jni lib");
        System.loadLibrary("hello-jni");
    }
}