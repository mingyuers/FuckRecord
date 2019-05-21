package com.fangm.fuckrecord;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

import java.sql.Blob;

import cn.bmob.v3.Bmob;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, "appid=5ce366ae");
        Bmob.initialize(this,"42f9610b542701d172d24fb837ac6bd8");
    }
}
