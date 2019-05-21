package com.fangm.fuckrecord.component;

import android.util.Log;

public class RecordUploadImpl implements IRecordUpload {

    private static final String TAG = RecordUploadImpl.class.getName();

    @Override
    public void upload(String str) {
        Log.e(TAG, "upload: " + str);
        if(str.length() < 5){
            return;
        }
        RecordBean recordBean = new RecordBean("q8",str);
        recordBean.saveSync();
    }
}
