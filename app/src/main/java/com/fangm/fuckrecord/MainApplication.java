package com.fangm.fuckrecord;

import android.app.Application;

import cn.bmob.v3.Bmob;


/***
 * Created by fangm on 2020-04-08 17:53
 *
 * Copyright (c) 2001-2019 Primeton Technologies, Ltd.
 **/
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"42f9610b542701d172d24fb837ac6bd8");
    }
}
