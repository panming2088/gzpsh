package com.augurit.agmobile.gzpssb.okhttp;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public abstract class DataCallBack<T> {
    Type type;

    public DataCallBack(Type type) {
        this.type = type;
    }

    public void sendMess(int code, String data) {
        if (code == OKhttptUtils.HTTPOK) {
            T o = new Gson().fromJson(data, type);
            try {
                onSuccess(o);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Log.d("TAG", "sendMess" + o.toString());
        } else if (code == OKhttptUtils.GSON_ER) {
            //字符串异常
            onFailure(code);
        } else if (code == OKhttptUtils.HTTPER) {
            //请求异常
            onFailure(code);
        } else {
            //Http 其他状态码
            onFailure(code);
        }

    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(int code);

}
