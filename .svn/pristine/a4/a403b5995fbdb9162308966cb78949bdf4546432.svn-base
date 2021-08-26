package com.augurit.agmobile.gzpssb.pshstatistics.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.statistic.model.EchartsDataBean;
import com.augurit.agmobile.gzps.statistic.model.StatisticBean;
import com.augurit.agmobile.gzps.statistic.view.StatisticFragment;
import com.augurit.agmobile.gzps.statistic.view.StatisticsFragment2;
import com.augurit.agmobile.gzpssb.pshstatistics.event.LoadAppInstallEvent;
import com.augurit.agmobile.gzpssb.pshstatistics.event.LoadStatisticsEvent;
import com.augurit.am.fw.utils.DensityUtil;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
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

/**
 * Created by sdb on 2018/4-11
 */

public class AppInstalledStatsFragment extends Fragment {

    private GridView mGridView;
    private ArrayList<String> areas = new ArrayList<>();
    private String[] lead_yAxle;
    private String[] patrol_yAxle;
    private String[] nwLead_yAxle = {"白云", "黄埔", "花都", "番禺", "南沙", "从化", "增城", "林场"};
    private String[] nwPatrol_yAxle = {"白云", "黄埔", "花都", "番禺", "南沙", "从化", "增城", "林场"};
    //    private String[] psLead_yAxle = {"市水务局", "天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化", "净水公司"};
    private String[] psLead_yAxle = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};
    private String[] pshLead_yAxle = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};
    //    private String[] psPatrol_yAxle = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化", "净水公司"};
    private String[] psPatrol_yAxle = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};
    private String[] pshPatrol_yAxle = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};
    private ArrayList<Float> datas = new ArrayList<>();
    private MyGridViewAdapter myGridViewAdapter;
    private TextView all_count, install_count, no_install_count, all_count_title;
    private WebView mWebView;
    private ProgressDialog progressDialog;
    private String echartsPieJson;
    private String currentOrgName = "";
    private ArrayList<StatisticBean.ChildOrg> childOrgs;
    private boolean currentRoleType = false;
    private boolean isInit = true;
    private String currentUserOrg;
    private boolean allPower = false;
    private ScrollView mScrollView;
    public int curPos = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appinstalledstats, null);

        curPos = getArguments().getInt("curPos", 0);

        lead_yAxle = psLead_yAxle;
        patrol_yAxle = psPatrol_yAxle;

        EventBus.getDefault().register(this);

        //currentUserOrg = BaseInfoManager.getUserOrg(getActivity());
        currentUserOrg = "市水务局";
        //市水务局角色可以看全市安装情况 其他只看看自己本区
        if (currentUserOrg.equals("市水务局")) {
            allPower = true;
            currentOrgName = "全市";
        } else {
            currentOrgName = currentUserOrg;
        }
        initView(view);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Integer position) {
        curPos = position;
        switch (curPos) {
            case 0:
                lead_yAxle = psLead_yAxle;
                patrol_yAxle = psPatrol_yAxle;
                break;
            case 1:
                lead_yAxle = psLead_yAxle;
                patrol_yAxle = psPatrol_yAxle;
                break;
            case 2:
                lead_yAxle = nwLead_yAxle;
                patrol_yAxle = nwPatrol_yAxle;
                break;
            case 3:
                lead_yAxle = pshLead_yAxle;
                patrol_yAxle = pshPatrol_yAxle;
                break;
            default:
                break;
        }

        getAppInstallInfo();//根据当前选择0全部 1污水 2农污 3排水户四个类型获取数据
    }

    private void getAppInstallInfo() {
        initData();

        if (currentRoleType) {
            if (curPos != 2 && curPos != 3) {
                if (areas.size() != 14) {
                    areas.add(0, "市水务局");
                }
            } else {
//                if (areas.size() != 10) {
//                    areas.add(0, "市水务局");
//                }
            }
        } else {
            if (curPos != 2) {
                if (areas.size() == 14) {
                    areas.remove(0);
                }
            } else {
//                if (areas.size() == 10) {
//                    areas.remove(0);
//                }
            }
        }

        if (allPower) {
            myGridViewAdapter.setPosition(areas.size() - 1);
            myGridViewAdapter.notifyDataSetChanged();
            currentOrgName = "全市";
            getOrgAppInstallInfo("全市", currentRoleType, false);
        } else {
            getOrgAppInstallInfo(currentUserOrg, currentRoleType, false);
        }
    }


    private void initView(View view) {
        mGridView = (GridView) view.findViewById(R.id.gv_area);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        if (currentUserOrg.equals("市水务局")) {
            mGridView.setVisibility(View.VISIBLE);
        } else {
            mGridView.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mScrollView.getLayoutParams();
            params.height = DensityUtil.dp2px(getActivity(), 850);
            mScrollView.setLayoutParams(params);
        }
        all_count = (TextView) view.findViewById(R.id.all_install_count);
        all_count_title = (TextView) view.findViewById(R.id.area_all_count_title);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        mWebView = (WebView) view.findViewById(R.id.chart_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(getActivity()), "android");
        mWebView.loadUrl("file:///android_asset/echarts/installchart.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //最好在这里调用js代码 以免网页未加载完成
//                getOrgAppInstallInfo(currentOrgName, currentRoleType, true);
                //mWebView.loadUrl("javascript:createChart('line'," + echartsPieJson + ");");
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("加载中...");
        progressDialog.setCancelable(true);
        view.findViewById(R.id.install_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
                if (allPower) {
                    intent.putExtra("org_name", currentOrgName);
                } else {
                    intent.putExtra("org_name", currentUserOrg);
                }
                intent.putExtra("roleType", currentRoleType);
                intent.putExtra("inInstall", "true");
                intent.putExtra("curPos", curPos);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.no_install_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
                if (allPower) {
                    intent.putExtra("org_name", currentOrgName);
                } else {
                    intent.putExtra("org_name", currentUserOrg);
                }
                intent.putExtra("roleType", currentRoleType);
                intent.putExtra("inInstall", "false");
                intent.putExtra("curPos", curPos);
                startActivity(intent);
            }
        });

        final TextView leadTv = (TextView) view.findViewById(R.id.lead_tv);
        final TextView patrolTv = (TextView) view.findViewById(R.id.patrol_tv);
        leadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leadTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                leadTv.setTextColor(Color.WHITE);
                patrolTv.setBackgroundColor(Color.WHITE);
                patrolTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                currentRoleType = true;

                if (curPos != 2 && curPos != 3) {
                    if (areas.size() != 14) {
                        areas.add(0, "市水务局");
                    }
                } else {
//                    if (areas.size() != 10) {
//                        areas.add(0, "市水务局");
//                    }
                }


                if (allPower) {
                    myGridViewAdapter.setPosition(areas.size() - 1);
                    myGridViewAdapter.notifyDataSetChanged();
                    currentOrgName = "全市";
                    getOrgAppInstallInfo("全市", currentRoleType, false);
                    //          int index = areas.indexOf(currentOrgName);
                    //                    if (index == -1) {
                    //                        myGridViewAdapter.setPosition(areas.size() - 1);
                    //                        myGridViewAdapter.notifyDataSetChanged();
                    //                        getOrgAppInstallInfo("全市", currentRoleType, false);
                    //                    } else {
                    //                        myGridViewAdapter.setPosition(index);
                    //                        myGridViewAdapter.notifyDataSetChanged();
                    //                        getOrgAppInstallInfo(currentOrgName, currentRoleType, false);
                    //                    }
                } else {
                    getOrgAppInstallInfo(currentUserOrg, currentRoleType, false);
                }

            }
        });

        patrolTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patrolTv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                patrolTv.setTextColor(Color.WHITE);
                leadTv.setBackgroundColor(Color.WHITE);
                leadTv.setTextColor(getResources().getColor(R.color.colorPrimary));

                currentRoleType = false;

                if (curPos != 2) {
                    if (areas.size() == 14) {
                        areas.remove(0);
                    }
                } else {
//                    if (areas.size() == 10) {
//                        areas.remove(0);
//                    }
                }

                //每次切换一线人员和管理人员将按钮重置为全市便于柱状图刷新
                if (allPower) {
                    myGridViewAdapter.setPosition(areas.size() - 1);
                    myGridViewAdapter.notifyDataSetChanged();
                    currentOrgName = "全市";
                    getOrgAppInstallInfo("全市", currentRoleType, false);
                    //                    int index = areas.indexOf(currentOrgName);
                    //                    if (index == -1) {
                    //                        myGridViewAdapter.setPosition(areas.size() - 1);
                    //                        myGridViewAdapter.notifyDataSetChanged();
                    //                        getOrgAppInstallInfo("全市", currentRoleType, false);
                    //                    } else {
                    //                        myGridViewAdapter.setPosition(index);
                    //                        myGridViewAdapter.notifyDataSetChanged();
                    //                        getOrgAppInstallInfo(currentOrgName, currentRoleType, false);
                    //                    }

                } else {
                    getOrgAppInstallInfo(currentUserOrg, currentRoleType, false);
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadStatisticsEvent(LoadStatisticsEvent event){
        if(event.getType() == StatisticsFragment2.LOAD_APP_INSTALL_PSH
                || event.getType() == StatisticsFragment2.LOAD_APP_INSTALL_PS
                || event.getType() == StatisticsFragment2.FIRST_LOAD_APP_INSTALL_PSH){
            if (allPower) {
                initData();
                getOrgAppInstallInfo("全市", currentRoleType, isInit);
            } else {
                getOrgAppInstallInfo(currentUserOrg, currentRoleType, isInit);
            }

//            getOrgAppInstallInfo(currentOrgName, currentRoleType, true);
        }
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * 点击柱状图柱子进入人员安装详细页面
         */
        @JavascriptInterface
        public void toDetailPage(String org_name) {
            Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
            intent.putExtra("org_name", org_name);
            intent.putExtra("roleType", currentRoleType);
            intent.putExtra("curPos", curPos);
            startActivity(intent);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (allPower) {
            initData();
//            getOrgAppInstallInfo("全市", currentRoleType, isInit);
        } else {
//            getOrgAppInstallInfo(currentUserOrg, currentRoleType, isInit);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getOrgAppInstallInfo(final String org_name, final boolean roleType, final boolean isFirst) {
        EventBus.getDefault().post(new LoadAppInstallEvent(true));

        final String temp_org_name;
        //净水这个字段特殊处理 移动设备写死的净水公司 但后台只能接收净水
        if (org_name.contains("净水")) {
            temp_org_name = "净水";
        } else {
            if (org_name.equals("增城")) {
                temp_org_name = "增城区";
            } else {
                temp_org_name = org_name;
            }

        }
        //        //Log.d("aaaa", roleType + "---" + temp_org_name);
        //        if (!isFirst) {
        //            progressDialog.show();
        //            isFirst = false;
        //        }

        Observable.create(new Observable.OnSubscribe<StatisticBean>() {
            @Override
            public void call(final Subscriber<? super StatisticBean> subscriber) {
                String url;
                if (curPos == 1) {//排水
                    url = "http://" + LoginConstant.GZPS_AGSUPPORT +
                            "/rest/installRecord/StatisticalApp?org_name=" + temp_org_name + "&roleType=" + roleType;
                } else if (curPos == 2) {//农污
                    url = "http://" + LoginConstant.GZWS_AGSUPPORT +
                            "/rest/installRecord/StatisticalNwApp?org_name=" + temp_org_name + "&roleType=" + roleType;
//                    url = "http://192.168.43.136:8080/agsupport_swj/rest/installRecord/StatisticalNwApp?org_name=" + temp_org_name + "&roleType=" + roleType;
                } else if (curPos == 3) {//排水户
                    url = "http://" + LoginConstant.GZPSH_AGSUPPORT +
                            "/rest/installPsh/StatisticalApp?org_name=" + temp_org_name + "&roleType=" + roleType;
                } else {//排水
                    url = "http://" + LoginConstant.GZPS_AGSUPPORT +
                            "/rest/installRecord/StatisticalApp?org_name=" + temp_org_name + "&roleType=" + roleType;
                }
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder().url(url).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String string = response.body().string();
                        try {
                            JSONObject object = new JSONObject(string);
                            int code = object.getInt("code");
                            if (object.has("data")) {
                                String data = object.getString("data");
                                if (code == 200) {
                                    if (!TextUtils.isEmpty(data)) {
                                        StatisticBean bean = JsonUtil.getObject(string, StatisticBean.class);
                                        subscriber.onNext(bean);

                                    }
                                } else {
                                    ToastUtil.shortToast(getActivity(), "数据加载失败");
                                    new Exception("数据加载失败").printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<StatisticBean>() {
            @Override
            public void call(StatisticBean bean) {

                setViewData(bean);
                int install = bean.getData().getInstall();
                int total = bean.getData().getTotal();
                creatPieChart(install, total, temp_org_name);
                if (temp_org_name.equals("全市")) {
                    childOrgs = bean.getData().getChild_orgs();
                    //Log.d("bbb", childOrgs.toString());
                }
//                if(curPos==2){
//                    childOrgs = bean.getData().getChild_orgs();
//                }

                if (allPower) {
                    creatBarChart(temp_org_name, childOrgs);
                }
            }
        });
    }

    private void creatPieChart(int install, int total, String title) {

        float percent;
        if (total == 0) {
            percent = 0;
        } else {
            float num = (float) (install * 100.0 / total);
            DecimalFormat df = new DecimalFormat("#.0");
            String format = df.format(num);
            percent = Float.parseFloat(format);
        }
        echartsPieJson = EchartsDataBean.getInstance().getEchartsPieJson(percent, title);
        mWebView.loadUrl("javascript:createPieChart('pie'," + echartsPieJson + ");");

        //        else {
        //            float v = Float.parseFloat(child_orgs.get(0).getInstall_percent());
        //
        //            String echartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(new
        //                    String[]{title}, title, new float[]{v});
        //            mWebView.loadUrl("javascript:createBarChart('bar'," + echartsBarJson + ");");
        //        }

    }

    private void creatBarChart(String org_name, ArrayList<StatisticBean.ChildOrg> child_orgs) {
        if (datas.size() > 0) {
            datas.clear();
        }
        if (currentRoleType) {
            float[] floats = new float[lead_yAxle.length];
            for (int i = 0; i < lead_yAxle.length; i++) {
                //解决腾讯bugly上的空指针bug
                if(ListUtil.isEmpty(child_orgs)){continue;}
                for (StatisticBean.ChildOrg childOrg : child_orgs) {
                    if ((lead_yAxle[i].equals("净水公司") && childOrg.getOrg_name().indexOf("净水") !=
                            -1) || (lead_yAxle[i].equals("林场") && childOrg.getOrg_name().contains("林业"))) {
                        floats[i] = Float.parseFloat(childOrg.getInstall_percent());
                        break;
                    } else {
                        if (childOrg.getOrg_name().indexOf(lead_yAxle[i]) != -1) {
                            floats[i] = Float.parseFloat(childOrg.getInstall_percent());
                            break;
                        } else {
                            floats[i] = 0.0f;
                        }
                    }
                }
            }
            String echartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(lead_yAxle, org_name, floats);
            mWebView.loadUrl("javascript:createBarChart('bar'," + echartsBarJson + ");");
        } else {
            float[] floats = new float[patrol_yAxle.length];
            for (int i = 0; i < patrol_yAxle.length; i++) {
                for (StatisticBean.ChildOrg childOrg : child_orgs) {
                    if ((patrol_yAxle[i].equals("净水公司") && childOrg.getOrg_name().indexOf("净水") !=
                            -1) || (patrol_yAxle[i].equals("林场") && childOrg.getOrg_name().contains("林业"))) {
                        floats[i] = Float.parseFloat(childOrg.getInstall_percent());
                        break;
                    } else {
                        if (childOrg.getOrg_name().indexOf(patrol_yAxle[i]) != -1) {
                            if (!TextUtils.isEmpty(childOrg.getInstall_percent())) {
                                floats[i] = Float.parseFloat(childOrg.getInstall_percent());
                            }
                            break;
                        } else {
                            floats[i] = 0.0f;
                        }
                    }

                }
            }
            String echartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(patrol_yAxle, org_name, floats);
            mWebView.loadUrl("javascript:createBarChart('bar'," + echartsBarJson + ");");
        }

    }

    private void setViewData(StatisticBean bean) {
        all_count.setText(bean.getData().getTotal() + "");
        install_count.setText(bean.getData().getInstall() + "");
        no_install_count.setText((bean.getData().getTotal()) - (bean.getData().getInstall()) + "");

    }

    private void initData() {
        areas.clear();
        if (curPos == 0 || curPos == 1) {
            areas.add("天河");
            areas.add("番禺");
            areas.add("黄埔");
            areas.add("白云");
            areas.add("南沙");
            areas.add("海珠");
            areas.add("荔湾");
            areas.add("花都");
            areas.add("越秀");
            areas.add("增城");
            areas.add("从化");
            areas.add("净水公司");
            areas.add("全市");
        } else if (curPos == 3) {
            areas.add("天河");
            areas.add("番禺");
            areas.add("黄埔");
            areas.add("白云");
            areas.add("南沙");
            areas.add("海珠");
            areas.add("荔湾");
            areas.add("花都");
            areas.add("越秀");
            areas.add("增城");
            areas.add("从化");
            areas.add("全市");
        } else {
            areas.add("白云");
            areas.add("黄埔");
            areas.add("花都");
            areas.add("番禺");
            areas.add("南沙");
            areas.add("从化");
            areas.add("增城");
            areas.add("林场");
            areas.add("全市");

        }
        myGridViewAdapter = new MyGridViewAdapter();
        mGridView.setAdapter(myGridViewAdapter);
        //        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //                myGridViewAdapter.setSelectedPosition(position);
        //                myGridViewAdapter.notifyDataSetChanged();
        //                getOrgAppInstallInfo(org_ids[position]);
        //            }
        //        });
    }

    class MyGridViewAdapter extends BaseAdapter {
        private int selectedPosition = areas.size() - 1;//默认选中的位置

        public void setPosition(int pos) {
            this.selectedPosition = pos;
        }

        @Override
        public int getCount() {
            return areas.size() == 0 ? 0 : areas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.gridview_item_layout, null);
            TextView area_name = (TextView) view.findViewById(R.id.tv_area_name);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_selected);
            area_name.setText(areas.get(position));
            if (selectedPosition == position) {
                area_name.setBackground(getResources().getDrawable(R.drawable.corner_color_primary3));
                area_name.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                iv.setVisibility(View.VISIBLE);
            } else {
                area_name.setBackground(getResources().getDrawable(R.drawable.corner_color_write));
                area_name.setTextColor(Color.BLACK);
                iv.setVisibility(View.GONE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedPosition == position) {//点击同个按钮就不作处理
                        return;
                    }
                    selectedPosition = position;
                    currentOrgName = areas.get(position);
                    if (areas.get(position).equals("净水公司")) {
                        getOrgAppInstallInfo("净水有限公司", currentRoleType, false);
                    } else {
                        getOrgAppInstallInfo(areas.get(position), currentRoleType, false);
                    }

                    notifyDataSetChanged();
                }
            });
            return view;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
