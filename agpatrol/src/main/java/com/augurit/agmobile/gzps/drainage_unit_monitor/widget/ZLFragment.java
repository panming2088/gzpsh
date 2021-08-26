package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.Data;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.view.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscriber;

public class ZLFragment extends Fragment {
    private Button btnSave;
    private LinearLayout llGw;
    private LinearLayout llCheck;
    private LinearLayout llTrain;
    private LinearLayout llMaintain;
    private LinearLayout ll_container;
    private LinearLayout llReporter;
    private TextView tvReporter;

    private MonitorService service;
    private long objectId;
    private String name;
    private ProgressDialog pd;
    private int height;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drainage_monitor_detail_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        service = new MonitorService(getContext());

        llGw = (LinearLayout) view.findViewById(R.id.ll_gw);
        llCheck = (LinearLayout) view.findViewById(R.id.ll_check);
        llTrain = (LinearLayout) view.findViewById(R.id.ll_train);
        llMaintain = (LinearLayout) view.findViewById(R.id.ll_maintain);
        ll_container = (LinearLayout) view.findViewById(R.id.ll_container);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        llReporter = (LinearLayout) view.findViewById(R.id.ll_reporter);
        tvReporter = (TextView) view.findViewById(R.id.tv_reporter);
        ((TextView)llGw.findViewById(R.id.tv_left)).setText("管网清疏养护记录");
        ((TextView)llCheck.findViewById(R.id.tv_left)).setText("安全隐患检查记录");
        ((TextView)llTrain.findViewById(R.id.tv_left)).setText("开展安全培训记录");
        ((TextView)llMaintain.findViewById(R.id.tv_left)).setText("预处理设施运维记录");
        setRadioButton(llGw);
        setRadioButton(llCheck);
        setRadioButton(llTrain);
        setRadioButton(llMaintain);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!((RadioButton) llGw.findViewById(R.id.rb_yes)).isChecked() && !((RadioButton) llGw.findViewById(R.id.rb_no)).isChecked())
                || (!((RadioButton) llCheck.findViewById(R.id.rb_yes)).isChecked() && !((RadioButton) llCheck.findViewById(R.id.rb_no)).isChecked())
                || (!((RadioButton) llTrain.findViewById(R.id.rb_yes)).isChecked() && !((RadioButton) llTrain.findViewById(R.id.rb_no)).isChecked())
                || (!((RadioButton) llMaintain.findViewById(R.id.rb_yes)).isChecked() && !((RadioButton) llMaintain.findViewById(R.id.rb_no)).isChecked())){
                    ToastUtil.shortToast(getContext(), "请先完成记录选择");
                    return;
                }
                pd = new ProgressDialog(getContext());
                pd.setMessage("正在保存...");
                pd.show();
                User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
                service.addPsdyZljc(user.getLoginName(), objectId + "", name, getSelect(llGw), getSelect(llCheck), getSelect(llTrain), getSelect(llMaintain))
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                pd.dismiss();
                                ToastUtil.shortToast(getContext(), "保存失败！");
                            }

                            @Override
                            public void onNext(String msg) {
                                pd.dismiss();
                                if(!TextUtils.isEmpty(msg)){
                                    ToastUtil.shortToast(getContext(), msg);
                                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                                    User user = new LoginRouter(getContext(), AMDatabase.getInstance()).getUser();
                                    tvReporter.setText("上报人：" + user.getUserName() + " " + date);
                                } else {
                                    ToastUtil.shortToast(getContext(), "保存失败！");
                                }
                            }
                        });
            }
        });
        initData();
    }

    private void initData(){
        service.getPshZljc(objectId)
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Data data) {
                        if("1".equals(data.getGyqsyhjl())) ((RadioButton) llGw.findViewById(R.id.rb_yes)).setChecked(true);
                        if("0".equals(data.getGyqsyhjl())) ((RadioButton) llGw.findViewById(R.id.rb_no)).setChecked(true);
                        if("1".equals(data.getAqyhjcjl())) ((RadioButton) llCheck.findViewById(R.id.rb_yes)).setChecked(true);
                        if("0".equals(data.getAqyhjcjl())) ((RadioButton) llCheck.findViewById(R.id.rb_no)).setChecked(true);
                        if("1".equals(data.getKzaqpxjl())) ((RadioButton) llTrain.findViewById(R.id.rb_yes)).setChecked(true);
                        if("0".equals(data.getKzaqpxjl())) ((RadioButton) llTrain.findViewById(R.id.rb_no)).setChecked(true);
                        if("1".equals(data.getYclssywjl())) ((RadioButton) llMaintain.findViewById(R.id.rb_yes)).setChecked(true);
                        if("0".equals(data.getYclssywjl())) ((RadioButton) llMaintain.findViewById(R.id.rb_no)).setChecked(true);
                        if(!TextUtils.isEmpty(data.getMarkPerson())) {
                            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(data.getMarkTime().getTime()));
                            tvReporter.setText("上报人：" + data.getMarkPerson() + " " + date);
                        }
                    }
                });
    }

    private String getSelect(LinearLayout parent){
        if(((RadioButton)parent.findViewById(R.id.rb_yes)).isChecked()){
            return "1";
        } else if(((RadioButton)parent.findViewById(R.id.rb_no)).isChecked()){
            return "0";
        } else {
            return "";
        }
    }

    private void setRadioButton(final ViewGroup parent){
        ((RadioButton) parent.findViewById(R.id.rb_yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((RadioButton) parent.findViewById(R.id.rb_yes)).setChecked(true);
                ((RadioButton) parent.findViewById(R.id.rb_no)).setChecked(false);
            }
        });
        ((RadioButton) parent.findViewById(R.id.rb_no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((RadioButton) parent.findViewById(R.id.rb_yes)).setChecked(true);
                ((RadioButton) parent.findViewById(R.id.rb_yes)).setChecked(false);
            }
        });
    }

    public void setId(long objectId){
        this.objectId = objectId;
        tvReporter.setText("");
        if(llGw != null) {
            ((RadioButton) llGw.findViewById(R.id.rb_yes)).setChecked(false);
            ((RadioButton) llGw.findViewById(R.id.rb_no)).setChecked(false);
            ((RadioButton) llCheck.findViewById(R.id.rb_yes)).setChecked(false);
            ((RadioButton) llCheck.findViewById(R.id.rb_no)).setChecked(false);
            ((RadioButton) llTrain.findViewById(R.id.rb_yes)).setChecked(false);
            ((RadioButton) llTrain.findViewById(R.id.rb_no)).setChecked(false);
            ((RadioButton) llMaintain.findViewById(R.id.rb_yes)).setChecked(false);
            ((RadioButton) llMaintain.findViewById(R.id.rb_no)).setChecked(false);
            initData();
        }
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public void onResume() {
        super.onResume();
        setHeight(height);
    }

    public void setHeight(int height){
        this.height = height;
        if(isVisible()) {
            llReporter.setTranslationY(height - llReporter.getHeight() - DensityUtils.dp2px(getContext(), 10));
        }
    }
}