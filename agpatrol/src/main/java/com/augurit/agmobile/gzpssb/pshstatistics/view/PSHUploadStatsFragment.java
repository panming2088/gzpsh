package com.augurit.agmobile.gzpssb.pshstatistics.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.MyGridView;
import com.augurit.agmobile.gzps.statistic.model.EchartsDataBean;
import com.augurit.agmobile.gzps.statistic.model.StatisticBean;
import com.augurit.agmobile.gzps.statistic.model.UploadYTStatisticBean;
import com.augurit.agmobile.gzps.statistic.view.StatisticsFragment2;
import com.augurit.agmobile.gzpssb.pshstatistics.adapter.MyGridViewAdapter;
import com.augurit.agmobile.gzpssb.pshstatistics.adapter.MyGridViewAdapter1;
import com.augurit.agmobile.gzpssb.pshstatistics.event.LoadStatisticsEvent;
import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadStatisticBean;
import com.augurit.agmobile.gzpssb.pshstatistics.model.PSHUploadYTStatisticBean;
import com.augurit.agmobile.gzpssb.pshstatistics.service.PSHUploadStatisticService;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sdb on 2018/4/11.
 */

public class PSHUploadStatsFragment extends Fragment {
    private MyGridView mGridView;
    private MyGridView mTypeGridView;
    private MyGridView mCertificateGridView;
    private MyGridView mTodayGridView;
    private LinearLayout mDistrcContain;
    private List<String> areas = new ArrayList<>();
    //private String[] areas = {"????????????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????","??????", "??????", "????????????", "??????"};
    private String[] lead_yAxle = {"??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????"};
    private String[] patrol_yAxle = {"??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????"};
    private List<String> patrol_type = Arrays.asList("?????????", "?????????", "?????????", "?????????", "???????????????", "???????????????", "?????????????????????", "???????????????", "?????????", "?????????????????????", "???????????????", "?????????", "??????", "??????");
    private List<String> certificate_type = Arrays.asList("??????????????????", "???????????????", "???????????????", "???????????????");
    private ArrayList<Float> datas = new ArrayList<>();
    private MyGridViewAdapter myGridViewAdapter;
    private MyGridViewAdapter1 certificateAdapter;
    private MyGridViewAdapter patrolypeAdapter;
    private MyGridViewAdapter todayTypeAdapter;
    private TextView all_count, install_count, no_install_count;
    //    private WebView mWebView;
    private ProgressDialog progressDialog;
    private String echartsPieJson;
    private String currentOrgName = "??????";
    private ArrayList<StatisticBean.ChildOrg> childOrgs;
    private boolean currentRoleType = false;
    private boolean isInit = true;
    private PSHUploadStatisticService uploadStatisticService;
    private String currentType = "??????";
    private String currentTodayType = "";
    //????????????
    private String certificateType;
    private Context mContext;
    private Calendar cal;
    private Long startDate = null;
    private Long endDate = null;
    private Long TempEndDate = null;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;
    private Button btn_start_date;
    private Button btn_end_date;
    private WebView mWebView;
    private PSHUploadStatiscAdapter mUploadStatiscAdapter;
    private RecyclerView mRecyclerView;
    private Button btnOkTime;
    private Button btnRefresh;
    private TextView newadded_check_count;
    private TextView correct_check_count;
    private TextView all_check_count;
    private TextView newadded_question_count;
    private TextView correct_question_count;
    private TextView all_question_count;
    //???????????????
    private TextView all_question_count_no;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psh_uploadstats, null);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGridView = (MyGridView) view.findViewById(R.id.gv_area);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_all_upload);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mTypeGridView = (MyGridView) view.findViewById(R.id.gv_type);
        mCertificateGridView = (MyGridView) view.findViewById(R.id.certificate_type);
        mTodayGridView = (MyGridView) view.findViewById(R.id.gv_toyester_type);
        all_count = (TextView) view.findViewById(R.id.all_install_count);
        install_count = (TextView) view.findViewById(R.id.install_count);
        no_install_count = (TextView) view.findViewById(R.id.no_install_count);
        /**
         * ????????????
         */
        newadded_check_count = (TextView) view.findViewById(R.id.newadded_check_count);
        correct_check_count = (TextView) view.findViewById(R.id.correct_check_count);

        all_question_count_no = (TextView) view.findViewById(R.id.all_question_count_no);
        all_check_count = (TextView) view.findViewById(R.id.all_check_count);

        newadded_question_count = (TextView) view.findViewById(R.id.newadded_question_count);
        correct_question_count = (TextView) view.findViewById(R.id.correct_question_count);
        all_question_count = (TextView) view.findViewById(R.id.all_question_count);

        mDistrcContain = (LinearLayout) view.findViewById(R.id.upload_statisc_distrc);
        /***
         * ????????????
         */
        btn_start_date = (Button) view.findViewById(R.id.btn_start_date);
        cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);       //????????????????????????
//        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
//        int day = cal.get(Calendar.DAY_OF_MONTH);
        //       startDate = new Date(year - 1900, month-3, day).getTime();
//        if (month == 1 || month == 2){
//            btn_start_date.setText((year - 1) + "-" + (month + 10) + "-" + day);
//        }else {
//            btn_start_date.setText(year + "-" + (month-2) + "-" + day);
//        }

        btn_end_date = (Button) view.findViewById(R.id.btn_end_date);
//        btn_end_date.setText(year + "-" + month + "-" + day);
//        endDate = new Date(year - 1900, month - 1, day+1).getTime();
//        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
        setStateDate();
        setCurrentDate();
        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate == null) {
                    showDatePickerDialog(btn_start_date, cal, START_DATE);
                } else {
                    cal.setTimeInMillis(startDate);
                    showDatePickerDialog(btn_start_date, cal, START_DATE);
                }
            }
        });

        btn_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TempEndDate == null) {
                    showDatePickerDialog(btn_end_date, cal, END_DATE);
                } else {
                    cal.setTimeInMillis(TempEndDate);
                    showDatePickerDialog(btn_end_date, cal, END_DATE);
                }
            }
        });

        btnOkTime = (Button) view.findViewById(R.id.stats_time_ok);
        btnRefresh = (Button) view.findViewById(R.id.stats_refresh);
        RxView.clicks(btnRefresh)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loadYTData(false);
                    }
                });
        RxView.clicks(btnOkTime)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (startDate > TempEndDate) {
                            ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                            return;
                        }
                        loadDatas(false, currentOrgName, currentType, startDate, endDate, certificateType);
                    }
                });

        mWebView = (WebView) view.findViewById(R.id.chart_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(getActivity()), "android");
        mWebView.loadUrl("file:///android_asset/uploadecharts/myechart.html");
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
                //?????????????????????js?????? ???????????????????????????
//                getOrgAppInstallInfo(currentOrgName, currentRoleType,true);
                //mWebView.loadUrl("javascript:createChart('line'," + echartsPieJson + ");");

//                certificateType  = "0000";
//                loadDatas(false, currentOrgName, currentType, startDate, endDate,certificateType);

            }
        });
        if (uploadStatisticService == null) {
            uploadStatisticService = new PSHUploadStatisticService(mContext);
        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("?????????...");
        progressDialog.setCancelable(true);
        view.findViewById(R.id.install_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
//                intent.putExtra("org_name", currentOrgName);
//                intent.putExtra("roleType", currentRoleType);
//                intent.putExtra("inInstall", "true");
//                startActivity(intent);
            }
        });
        view.findViewById(R.id.no_install_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
//                intent.putExtra("org_name", currentOrgName);
//                intent.putExtra("roleType", currentRoleType);
//                intent.putExtra("inInstall", "false");
//                startActivity(intent);T
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadStatisticsEvent(LoadStatisticsEvent event) {
        certificateType = "0000";
        if (event.getType() == StatisticsFragment2.LOAD_UPLOAD_PSH) {
            loadDatas(false, currentOrgName, currentType, startDate, endDate, certificateType);
        }
    }

    public void setStateDate() {
        cal = Calendar.getInstance();
//        cal.add(Calendar.MONDAY,   -2);   //??????????????????
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);
        startDate = new Date(2018 - 1900, 0, 1).getTime();
        btn_start_date.setText(2018 + "-" + 1 + "-" + 1);
    }

    public void setCurrentDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);
        btn_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context c) {
            context = c;
        }

        /**
         * ???js??????????????????????????????js??????????????????
         */
        @JavascriptInterface
        public void toDetailPage(String org_name) {
            //????????????
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
//        if (!ListUtil.isEmpty(areas)) {
//            String distrc = areas.get(areas.size() - 1);
//            if (distrc.equals("??????")) {
//                loadDatas(false, currentOrgName, startDate, endDate);
//            } else {
//                currentOrgName = distrc;
//                loadDatas(false, distrc, startDate, endDate);
//            }
//
//        }
        loadYTData(false);

//        getOrgAppInstallInfo(currentOrgName, currentRoleType, isInit);
    }

    /**
     * ????????????????????????????????????
     */
    private void loadYTData(boolean ifShowPb) {
        if (ifShowPb) {
            progressDialog.show();
        }

        uploadStatisticService.getPSHUploadNearTimeData(currentTodayType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHUploadYTStatisticBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        e.printStackTrace();
                        ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "??????????????????");
                    }

                    @Override
                    public void onNext(PSHUploadYTStatisticBean uploadStatisticBean) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (uploadStatisticBean == null || uploadStatisticBean.getCode() != 200) {
                            ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "??????????????????");
                            return;
                        }
                        creatListData(uploadStatisticBean);
                    }
                });
    }

    private void creatListData(PSHUploadYTStatisticBean uploadStatisticBean) {
        List<PSHUploadYTStatisticBean.ToDayEntity> toDay = uploadStatisticBean.getToDay();
        List<PSHUploadYTStatisticBean.YestDayEntity> yestDay = uploadStatisticBean.getYestDay();

        List<UploadYTStatisticBean.ToDayEntity> newToday = new ArrayList<>();
        List<UploadYTStatisticBean.YestDayEntity> newyestDay = new ArrayList<>();
        int sumYCorrect = 0;
        int sumYLack = 0;

        int sumTCorrect = 0;
        int sumTLack = 0;
        UploadYTStatisticBean.ToDayEntity tempToday;
        UploadYTStatisticBean.YestDayEntity tempYestDay;
        int length = patrol_yAxle.length;
        String orgName;
        for (int i = 0; i < length; i++) {
            tempToday = new UploadYTStatisticBean.ToDayEntity();
            tempYestDay = new UploadYTStatisticBean.YestDayEntity();
            orgName = patrol_yAxle[i];
            tempToday.setName(orgName);
            tempYestDay.setName(orgName);
            if (ListUtil.isEmpty(toDay)) {
                tempToday.setCorrCount(0);
                tempToday.setLackCount(0);
            } else {
                for (PSHUploadYTStatisticBean.ToDayEntity childOrg : toDay) {
                    if (childOrg.getName().contains(orgName) || (orgName.equals("????????????") && childOrg.getName().contains("??????"))) {
//                        tempToday.setCorrCount(childOrg.getAddCount());
                        tempToday.setLackCount(childOrg.getAddCount());
//                        sumTCorrect +=childOrg.getCorrCount();
                        sumTLack += childOrg.getAddCount();
                        break;
                    } else {
                        tempToday.setCorrCount(0);
                        tempToday.setLackCount(0);
                    }
                }
            }

            if (ListUtil.isEmpty(yestDay)) {
                tempYestDay.setCorrCount(0);
                tempYestDay.setLackCount(0);
            } else {
                for (PSHUploadYTStatisticBean.YestDayEntity childOrg : yestDay) {
                    if (childOrg.getName().contains(orgName) || (orgName.equals("????????????") && childOrg.getName().contains("??????"))) {
//                        tempYestDay.setCorrCount(childOrg.getAddCount());
                        tempYestDay.setLackCount(childOrg.getAddCount());
//                        sumYCorrect +=childOrg.getCorrCount();
                        sumYLack += childOrg.getAddCount();
                        break;
                    } else {
                        tempYestDay.setCorrCount(0);
                        tempYestDay.setLackCount(0);
                    }
                }
            }
            if (orgName.equals("??????")) {
                tempToday.setLackCount(sumTLack);
                tempToday.setCorrCount(sumTCorrect);
                tempYestDay.setLackCount(sumYLack);
                tempYestDay.setCorrCount(sumYCorrect);
            }
            newToday.add(tempToday);
            newyestDay.add(tempYestDay);
        }
        mUploadStatiscAdapter.refresh(newyestDay, newToday);
    }

    public void loadDatas(boolean ifShowPb, String distrct, String reportType, long startTime, long endTime, String certificateType) {
        if (ifShowPb) {
            progressDialog.show();
        }

        uploadStatisticService.getPSHUploadStatisticForDistric(distrct, reportType, startTime, endTime, certificateAdapter.getSelectedPositions())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHUploadStatisticBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        e.printStackTrace();
                        ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "??????????????????");
                    }

                    @Override
                    public void onNext(PSHUploadStatisticBean uploadStatisticBean) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (uploadStatisticBean == null) {
                            return;
                        }
                        all_count.setText(uploadStatisticBean.getTotalStr());
                        no_install_count.setText(uploadStatisticBean.getCorrCountStr());
                        install_count.setText(uploadStatisticBean.getLackCountStr());
                        all_check_count.setText(uploadStatisticBean.getPassTotalStr());
                        correct_check_count.setText(uploadStatisticBean.getPassCorrCountStr());
                        //????????????????????????
                        all_question_count_no.setText(uploadStatisticBean.getUnCheckStr());
                        newadded_check_count.setText(uploadStatisticBean.getPassLackCountStr());
                        all_question_count.setText(uploadStatisticBean.getDoubtTotalStr());
                        correct_question_count.setText(uploadStatisticBean.getDoubtCorrCountStr());
                        newadded_question_count.setText(uploadStatisticBean.getDoubtLackCountStr());
                        creatBarChart(uploadStatisticBean);

                    }
                });
    }

    private void creatBarChart(PSHUploadStatisticBean uploadStatisticBean) {
        List<PSHUploadStatisticBean.ChartsEntity> charts = uploadStatisticBean.getCharts();
        float[] floats = new float[lead_yAxle.length];
        for (int i = 0; i < lead_yAxle.length; i++) {
            String orgName = lead_yAxle[i];
            if (ListUtil.isEmpty(charts)) {
                floats[i] = 0;
                break;
            }
            for (PSHUploadStatisticBean.ChartsEntity childOrg : charts) {
                if (childOrg.getName().contains(orgName) || (orgName.equals("????????????") && childOrg.getName().contains("??????"))) {
                    floats[i] = Float.parseFloat(String.valueOf(childOrg.getTotal()));
                    break;
                } else {
                    floats[i] = 0;
                }
            }
        }
        String echartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(lead_yAxle, null, floats);
        mWebView.loadUrl("javascript:createBarChart('bar'," + echartsBarJson + ");");
    }

    private void setViewData(StatisticBean bean) {
        all_count.setText(bean.getData().getTotal() + "");
        install_count.setText(bean.getData().getInstall() + "");
        no_install_count.setText((bean.getData().getTotal()) - (bean.getData().getInstall()) + "");

    }

    private void initData() {
        mUploadStatiscAdapter = new PSHUploadStatiscAdapter(mContext);
        mRecyclerView.setAdapter(mUploadStatiscAdapter);
        mDistrcContain.setVisibility(View.VISIBLE);
        areas.clear();
        mGridView.setVisibility(View.VISIBLE);
        areas.clear();
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
        areas.add("??????");
//        areas.add("????????????");
        areas.add("??????");

        //??????
        myGridViewAdapter = new MyGridViewAdapter(getActivity());
        myGridViewAdapter.setPosition(areas.size() - 1);
        myGridViewAdapter.setArrayList(areas);
        myGridViewAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onClick(int position) {
                currentOrgName = areas.get(position);
                if (startDate > TempEndDate) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                    return;
                }
                loadDatas(false, currentOrgName, currentType, startDate, endDate, certificateType);
            }
        });
        mGridView.setAdapter(myGridViewAdapter);

        //????????????
        patrolypeAdapter = new MyGridViewAdapter(getActivity());
        patrolypeAdapter.setArrayList(patrol_type);
        patrolypeAdapter.setPosition(patrol_type.size() - 1);
        patrolypeAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onClick(int position) {
                currentType = patrol_type.get(position);
                if (startDate > TempEndDate) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                    return;
                }
                loadDatas(false, currentOrgName, currentType, startDate, endDate, certificateType);
            }
        });
        mTypeGridView.setAdapter(patrolypeAdapter);

        //???????????????
        certificateAdapter = new MyGridViewAdapter1(getActivity());
        certificateAdapter.setArrayList(certificate_type);
        certificateAdapter.setSelectedPositions(new HashSet<Integer>());
        certificateAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onClick(int position) {
                if (startDate > TempEndDate) {
                    ToastUtil.iconLongToast(mContext, R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                    return;
                }
                certificateAdapter.addOrDeleleItem(position);
//                certificateType = certificateAdapter.getSelectedPositions();

                loadDatas(false, currentOrgName, currentType, startDate, endDate, certificateType);
            }
        });
        mCertificateGridView.setAdapter(certificateAdapter);


        //????????????????????????
        todayTypeAdapter = new MyGridViewAdapter(getActivity());
        todayTypeAdapter.setArrayList(patrol_type);
        todayTypeAdapter.setPosition(patrol_type.size() - 1);
        todayTypeAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onClick(int position) {
                currentTodayType = patrol_type.get(position);
                if (currentTodayType.equals("??????")) {
                    currentTodayType = "";
                }
                loadYTData(false);
            }
        });
        mTodayGridView.setAdapter(todayTypeAdapter);
    }


    public void setGridViewHeight(GridView gridview) {
        // ??????gridview???adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // ???????????????????????????
        int numColumns = gridview.getNumColumns(); //5
        int totalHeight = 0;
        // ??????????????????????????????
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // ??????gridview????????????item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // ??????item????????????
            totalHeight += listItem.getMeasuredHeight();
        }

        // ??????gridview???????????????
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        // ????????????
        params.height = totalHeight;
        // ????????????
        gridview.setLayoutParams(params);
    }

    public void showDatePickerDialog(final Button btn, Calendar calendar, final int type) {
        // Calendar ?????????????????????
        // Calendar calendar = Calendar.getInstance();
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(mContext,
                // ???????????????(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        display(btn, year, monthOfYear, dayOfMonth);
                        if (type == START_DATE) {
                            startDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                        } else {
                            endDate = new Date(year - 1900, monthOfYear, dayOfMonth + 1).getTime();
                            TempEndDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                        }
                    }
                }
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    /**
     * ????????????
     */
    public void display(Button dateDisplay, int year,
                        int monthOfYear, int dayOfMonth) {
        dateDisplay.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
    }

    public interface OnItemListener {
        void onClick(int position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
