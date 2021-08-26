package com.augurit.agmobile.gzpssb.pshstatistics.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.common.webview.WebAppInterface;
import com.augurit.agmobile.gzps.common.webview.WebViewActivity;
import com.augurit.agmobile.gzps.common.webview.WebViewConstant;
import com.augurit.agmobile.gzps.statistic.model.EchartsDataBean;
import com.augurit.agmobile.gzps.statistic.model.StatisticBean;
import com.augurit.agmobile.gzps.statistic.view.StatisticsFragment2;
import com.augurit.agmobile.gzpssb.pshstatistics.event.LoadStatisticsEvent;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.fw.utils.DensityUtil;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.augurit.agmobile.gzps.common.constant.LoginConstant.GZPSH_AGSUPPORT;

/**
 * Created by sdb on 2018/4-11
 */

public class TaskQueryFragment extends Fragment {
    private WebView mWebView;
    private ProgressBar pb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taskquery, null);
        view.findViewById(R.id.rl).setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        mWebView = (CustomWebView) view.findViewById(R.id.web_view);
        pb = (ProgressBar) view.findViewById(R.id.progressBar1);
        mWebView.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        WebSettings seting = mWebView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        seting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //        seting.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pb.setProgress(newProgress);//设置进度值
                }

            }

        });
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键时的操作

                        // mWebView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        // 用JavaScript调用Android函数：
        // 先建立桥梁类，将要调用的Android代码写入桥梁类的public函数
        // 绑定桥梁类和WebView中运行的JavaScript代码
        // 将一个对象起一个别名传入，在JS代码中用这个别名00这个对象，可以调用这个对象的一些方法

//        String urlPath = "http://"+GZPSH_AGSUPPORT+"/event/app_pshKjTbStatistics.html";
//        if (urlPath.contains("http")) {
//            mWebView.loadUrl(urlPath);
//        } else {
//            String url = BaseInfoManager.getBaseServerUrlWithoutRestSystem(getActivity()) + urlPath;
//            mWebView.loadUrl(url);
//        }
//        findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//        tv_title.setText(StringUtil.getNotNullString(title, ""));
        view.findViewById(R.id.ll_back).setVisibility(View.GONE);
        view.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mWebView.canGoBack()) {
//                    mWebView.goBack();   //后退
//                } else {
//                    finish();
//                }
            }
        });
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadStatisticsEvent(LoadStatisticsEvent event){
        if(event.getType() == StatisticsFragment2.LOAD_TASK_PSH){
            String urlPath = "http://"+GZPSH_AGSUPPORT+"/event/app_pshKjTbStatistics.html";
            if (urlPath.contains("http")) {
                mWebView.loadUrl(urlPath);
            } else {
                String url = BaseInfoManager.getBaseServerUrlWithoutRestSystem(getActivity()) + urlPath;
                mWebView.loadUrl(url);
            }
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
