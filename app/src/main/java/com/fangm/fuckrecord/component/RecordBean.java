package com.fangm.fuckrecord.component;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

public class RecordBean extends BmobObject {
    private String name;
    private String record;

    public RecordBean(String name, String record) {
        this.name = name;
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
