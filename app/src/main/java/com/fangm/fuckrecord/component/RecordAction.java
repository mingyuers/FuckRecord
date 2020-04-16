package com.fangm.fuckrecord.component;

import android.content.Context;

public interface RecordAction {
    void init(Context context);
    void startAction(RecordResultListener recordResultListener );
}
