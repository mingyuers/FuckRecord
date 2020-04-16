package com.fangm.fuckrecord.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class XFRecordActionImpl implements RecordAction {
    private static String TAG = XFRecordActionImpl.class.getSimpleName();

    // 语音听写对象
    private SpeechRecognizer mIat;
    private StringBuffer buffer = new StringBuffer();

    private RecordResultListener recordResultListener;

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        SpeechUtility.createUtility(context, "appid=5ce366ae");

        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
        setParam();
    }

    @Override
    public void startAction(RecordResultListener recordResultListener) {
        this.recordResultListener = recordResultListener;
        startListening();
    }

    private void startListening() {
        int ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            recordResultListener.onError("听写失败,错误码：" + ret);
            showTip("听写失败,错误码：" + ret + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
        } else {
            showTip("startListening : 请开始说话");
        }
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    private void showTip(final String str) {
        Log.e(TAG, str);
    }


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("onBeginOfSpeech : 开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            showTip("onError : " + error.getPlainDescription(true));
            if (error.getErrorCode() == 10118) {
            }
            startListening();
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("onEndOfSpeech : 结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            buffer.append(results.getResultString());
            showTip("onResult: " + results.getResultString() + " ------- isLast : " + isLast);
            if (isLast) {
                onceDone();
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    private void onceDone() {
        if (!"".equals(buffer.toString())) {
            recordResultListener.onResult(buffer.toString());
        }
        buffer.setLength(0);
        startListening();
    }
}
