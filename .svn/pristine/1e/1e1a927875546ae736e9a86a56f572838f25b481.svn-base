package com.augurit.agmobile.gzps.common.webview;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.fw.utils.StringUtil;

/**
 * Created by xcl on 2017/10/23.
 */

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.web_view);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
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

                        finish();
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
        mWebView.addJavascriptInterface(new WebAppInterface(this),
                "root");
        mWebView.setDownloadListener(new MyDownloadStart());
        String title = getIntent().getStringExtra(WebViewConstant.WEBVIEW_TITLE);
        String urlPath = getIntent().getStringExtra(WebViewConstant.WEBVIEW_URL_PATH)
                + "?userId=" + BaseInfoManager.getUserId(WebViewActivity.this);
        if (urlPath.contains("http")) {
            mWebView.loadUrl(urlPath);
        } else {
            String url = BaseInfoManager.getBaseServerUrlWithoutRestSystem(this) + urlPath;
            mWebView.loadUrl(url);
        }
//        findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(StringUtil.getNotNullString(title, ""));
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mWebView.canGoBack()) {
//                    mWebView.goBack();   //后退
//                } else {
//                    finish();
//                }
                finish();
            }
        });
    }

    class MyDownloadStart implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            //调用自己的下载方式
//          new HttpThread(url).start();
            //调用系统浏览器下载
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
