package com.fangm.fuckrecord.component;

import android.util.Log;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RecordUploadImpl implements RecordUpload {

    private static final String TAG = RecordUploadImpl.class.getName();

    @Override
    public void upload(String str) {
        Log.e(TAG, "upload: " + str);
        if (str.length() < 5) {
            return;
        }
        RecordBean recordBean = new RecordBean("xiaomi 8", str);
        recordBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Log.d(TAG, "done: " + s);
            }
        });
    }
}
