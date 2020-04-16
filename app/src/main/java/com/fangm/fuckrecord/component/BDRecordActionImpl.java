package com.fangm.fuckrecord.component;

import android.content.Context;
import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class BDRecordActionImpl implements RecordAction {
    private static String TAG = BDRecordActionImpl.class.getSimpleName();

    private RecordResultListener recordResultListener;
    private EventManager asr;
    private String res = "";

    @Override
    public void init(Context context) {
        asr = EventManagerFactory.create(context, "asr");
        asr.registerListener(eventListener);
    }

    @Override
    public void startAction(RecordResultListener recordResultListener) {
        this.recordResultListener = recordResultListener;
        start();
    }

    private void start() {
        res = "";
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event  = SpeechConstant.ASR_START;
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
         params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号

        String json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
    }

    EventListener eventListener = new EventListener() {
        @Override
        public void onEvent(String name, String params, byte[] data, int offset, int length) {
            Log.d(TAG, "onEvent name : " + name);
            Log.d(TAG, "onEvent params : " + params);
//            Log.e(TAG, "onEvent params : " + params);
            if (SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL.equals(name) && params != null) {
                try {
                    JSONObject jsonObject = new JSONObject(params);
                    String result_type = jsonObject.getString("result_type");
                    if ("final_result".equals(result_type)) {
                        String best_result = jsonObject.getString("best_result");
                        res = best_result;
                        Log.d(TAG, "onEvent, partial_result : " + best_result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (SpeechConstant.CALLBACK_EVENT_ASR_EXIT.equals(name)) {
                recordResultListener.onResult(res);
                start();
            }
        }
    };

}
