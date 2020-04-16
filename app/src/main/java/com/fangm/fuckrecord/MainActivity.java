package com.fangm.fuckrecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fangm.fuckrecord.component.BDRecordActionImpl;
import com.fangm.fuckrecord.component.RecordAction;
import com.fangm.fuckrecord.component.XFRecordActionImpl;
import com.fangm.fuckrecord.component.RecordResultListener;
import com.fangm.fuckrecord.component.RecordUpload;
import com.fangm.fuckrecord.component.RecordUploadImpl;
import com.mingyuers.permission.PermissionAnywhere;
import com.mingyuers.permission.PermissionCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();


    private RecordUpload recordUpload;
    private RecordAction recordAction;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        PermissionAnywhere.requestPermission(this, new String[]{Manifest.permission.RECORD_AUDIO}, new PermissionCallback() {
            @Override
            public void onComplete(List<String> grantedPermissions, List<String> deniedPermissions, List<String> alwaysDeniedPermissions) {
                if (grantedPermissions != null && grantedPermissions.size() == 1) {
                    start();
                }
            }
        });
    }

    public void init() {
        recordUpload = new RecordUploadImpl();
        recordAction = new BDRecordActionImpl();
        recordAction.init(this);
    }

    public void start() {
        recordAction.startAction(new RecordResultListener() {
            @Override
            public void onResult(String result) {
                if (result.length() >= 3) {
                    recordUpload.upload(result);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


}
