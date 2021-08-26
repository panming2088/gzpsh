package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.drainage_unit_monitor.Event.RefreshUserInfoView;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import rx.Observer;

public class MonitorInfoEditActivity extends BaseActivity {
    private Context context;
    private TextView tvAn;
    private TextView tvHandlingSum;
    private TextView tvJbj;
    private TextView tvJbjYjg;
    private TextView tvJhj;
    private TextView tvJhjYjg;
    private LinearLayout llUnitName;
    private LinearLayout llAddress;
    private LinearLayout llType;
    private LinearLayout llUnitCode;
    private LinearLayout llUnitSize;
    private LinearLayout llUnitArea;
    private LinearLayout llUnitTown;
    private LinearLayout llOwner;
    private LinearLayout llYwfl;
    private LinearLayout llDbcj;
    private LinearLayout llDate;
    private LinearLayout llWf;
    private LinearLayout llManager;
    private LinearLayout llMonitor;
    private Button btnReset;
    private Button btnSave;

    private boolean hasChange = false;
    private Component component;
    private PsdyJg psdyJg;
    private MonitorService service;
    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_info_edit);
        context = this;
        service = new MonitorService(context);
        component = EventBus.getDefault().removeStickyEvent(Component.class);
        psdyJg = EventBus.getDefault().removeStickyEvent(PsdyJg.class);
        initView();
        initData();
    }
    
    private void initView(){
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.tv_title)).setText("排水单元信息编辑");

        tvAn = (TextView) findViewById(R.id.tv_an);
        tvHandlingSum = (TextView) findViewById(R.id.tv_handling_sum);
        tvJbj = (TextView) findViewById(R.id.tv_jbj);
        tvJbjYjg = (TextView) findViewById(R.id.tv_jbj_yjg);
        tvJhj = (TextView) findViewById(R.id.tv_jhj);
        tvJhjYjg = (TextView) findViewById(R.id.tv_jhj_yjg);
        llUnitName = (LinearLayout) findViewById(R.id.ll_unit_name);
        llAddress = (LinearLayout) findViewById(R.id.ll_address);
        llType = (LinearLayout) findViewById(R.id.ll_type);
        llUnitCode = (LinearLayout) findViewById(R.id.ll_unit_code);
        llUnitSize = (LinearLayout) findViewById(R.id.ll_unit_size);
        llUnitArea = (LinearLayout) findViewById(R.id.ll_unit_area);
        llUnitTown = (LinearLayout) findViewById(R.id.ll_unit_town);
        llOwner = (LinearLayout) findViewById(R.id.ll_owner);
        llYwfl = (LinearLayout) findViewById(R.id.ll_ywfl);
        llDbcj = (LinearLayout) findViewById(R.id.ll_dbcj);
        llDate = (LinearLayout) findViewById(R.id.ll_date);
        llWf = (LinearLayout) findViewById(R.id.ll_wf);
        llManager = (LinearLayout) findViewById(R.id.ll_manager);
        llMonitor = (LinearLayout) findViewById(R.id.ll_monitor);
        btnReset = (Button) findViewById(R.id.btn_reset);
        btnSave = (Button) findViewById(R.id.btn_save);

        llYwfl.findViewById(R.id.tv_right).setVisibility(View.GONE);
        llYwfl.findViewById(R.id.rg).setVisibility(View.VISIBLE);
        llDbcj.findViewById(R.id.tv_right).setVisibility(View.GONE);
        llDbcj.findViewById(R.id.rg).setVisibility(View.VISIBLE);

        ((TextView)llDate.findViewById(R.id.tv_right)).setCompoundDrawablesWithIntrinsicBounds(
                null, null, getResources().getDrawable(R.mipmap.calendar), null);

        ((TextView)llDate.findViewById(R.id.tv_left)).setText("完成达标创建时间");
        ((TextView)llDate.findViewById(R.id.tv_right)).setHint("请选择创建时间");
        ((TextView)llDate.findViewById(R.id.tv_right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        ((RadioGroup)llDbcj.findViewById(R.id.rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_left){
                    llDate.setVisibility(View.VISIBLE);
                } else {
                    llDate.setVisibility(View.GONE);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        llOwner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llOwner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = llOwner.findViewById(R.id.ll_invisible).getWidth();
                llOwner.findViewById(R.id.ll_visible).getLayoutParams().width = width;
                llOwner.findViewById(R.id.ll_visible).requestLayout();
                llWf.findViewById(R.id.ll_visible).getLayoutParams().width = width;
                llWf.findViewById(R.id.ll_visible).requestLayout();
                llManager.findViewById(R.id.ll_visible).getLayoutParams().width = width;
                llManager.findViewById(R.id.ll_visible).requestLayout();
                llMonitor.findViewById(R.id.ll_visible).getLayoutParams().width = width;
                llMonitor.findViewById(R.id.ll_visible).requestLayout();
            }
        });
    }

    private void save(){
        String psdyId;
        String sfywfl = "";
        String sfwcdb = "";
        String wcdbsj = "";
        String qsr = "";
        String qsr_lxfs = "";
        String whr = "";
        String whr_lxfs = "";
        String glr = "";
        String glr_lxfs = "";
        String jgr = "";
        String jgr_lxfs = "";
        psdyId = component.getObjectId() + "";
        if(((RadioButton)llYwfl.findViewById(R.id.rb_left)).isChecked()){
            sfywfl = "1";
        } else if(((RadioButton)llYwfl.findViewById(R.id.rb_right)).isChecked()){
            sfywfl = "0";
        }
        if(((RadioButton)llDbcj.findViewById(R.id.rb_left)).isChecked()){
            sfwcdb = "1";
            Object tag = ((TextView)llDate.findViewById(R.id.tv_right)).getTag();
            if(tag == null){
                wcdbsj = "";
            } else {
                wcdbsj = (String)tag;
            }

            if(TextUtils.isEmpty(wcdbsj)){
                ToastUtil.shortToast(context, "请选择完成达标时间");
                return;
            }
        } else if(((RadioButton)llDbcj.findViewById(R.id.rb_right)).isChecked()){
            sfwcdb = "0";
            wcdbsj = "";
        }
        qsr = ((EditText)llOwner.findViewById(R.id.et_input_left)).getText().toString().trim();
        qsr_lxfs = ((EditText)llOwner.findViewById(R.id.et_input_right)).getText().toString().trim();
        whr = ((EditText)llWf.findViewById(R.id.et_input_left)).getText().toString().trim();
        whr_lxfs = ((EditText)llWf.findViewById(R.id.et_input_right)).getText().toString().trim();
        glr = ((EditText)llManager.findViewById(R.id.et_input_left)).getText().toString().trim();
        glr_lxfs = ((EditText)llManager.findViewById(R.id.et_input_right)).getText().toString().trim();
        jgr = ((EditText)llMonitor.findViewById(R.id.et_input_left)).getText().toString().trim();
        jgr_lxfs = ((EditText)llMonitor.findViewById(R.id.et_input_right)).getText().toString().trim();

        String a = sfywfl + sfwcdb + wcdbsj + qsr + qsr_lxfs + whr + whr_lxfs + glr + glr_lxfs + jgr + jgr_lxfs;
        if(TextUtils.isEmpty(a)){
            ToastUtil.shortToast(context, "您未修改任何内容");
            return;
        }

        pd = new ProgressDialog(context);
        pd.setMessage("正在提交...");
        pd.show();
        service.updatePsdySde(psdyId, sfywfl, sfwcdb, wcdbsj, qsr, qsr_lxfs, whr, whr_lxfs, glr, glr_lxfs, jgr, jgr_lxfs)
                .subscribe(new Observer<Result2>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pd.dismiss();
                        ToastUtil.shortToast(context, "保存失败");
                    }

                    @Override
                    public void onNext(Result2 result2) {
                        pd.dismiss();
                        if(result2.getCode() == 200){
                            hasChange = true;
                            ToastUtil.shortToast(context, "保存成功");
                        } else {
                            ToastUtil.shortToast(context, "保存失败");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(hasChange) EventBus.getDefault().post(new RefreshUserInfoView(component));
    }

    private void initData(){
        Map<String, Object> attrMap = component.getGraphic().getAttributes();
        setInfo(llUnitName, "排水单元名称", getAttrValue(attrMap, "单元名称"));
        setInfo(llAddress, "单元地址", getAttrValue(attrMap, "地址DZ"));
        setInfo(llType, "单元类型", getAttrValue(attrMap, "类型LX"));
        setInfo(llUnitCode, "排水单元编号", getAttrValue(attrMap, "编号"));
        String size = getAttrDouble(attrMap, "面积");
        String[] tmp = size.split("\\.");
        if(tmp.length == 2 && tmp[1].length() > 2){
            size = tmp[0] + "." + tmp[1].substring(0, 2);
        }
        size = size + "m2";
        Spannable sp = new SpannableString(size);
        sp.setSpan(new SuperscriptSpan(), size.length() - 1, size.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(sp2px(context, 8)), size.length() - 1, size.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        setInfo(llUnitSize, "排水单元面积", sp);
        setInfo(llUnitArea, "所在行政区", getAttrValue(attrMap, "所属区"));
        setInfo(llUnitTown, "所在街道", getAttrValue(attrMap, "所属镇街"));

        String value = getAttrValue(attrMap, "SFYWFL");
        if("1".equals(value)){
            ((RadioButton)llYwfl.findViewById(R.id.rb_left)).setChecked(true);
        } else if("0".equals(value)){
            ((RadioButton)llYwfl.findViewById(R.id.rb_right)).setChecked(true);
        }else {
            ((RadioButton)llYwfl.findViewById(R.id.rb_left)).setChecked(false);
            ((RadioButton)llYwfl.findViewById(R.id.rb_right)).setChecked(false);
        }
        ((TextView)llYwfl.findViewById(R.id.tv_left)).setText("是否完成雨污分流");

        value = getAttrValue(attrMap, "SFWCDB");
        if("1".equals(value)){
            ((RadioButton)llDbcj.findViewById(R.id.rb_left)).setChecked(true);
        } else if("0".equals(value)){
            ((RadioButton)llDbcj.findViewById(R.id.rb_right)).setChecked(true);
        }else {
            ((RadioButton)llDbcj.findViewById(R.id.rb_left)).setChecked(false);
            ((RadioButton)llDbcj.findViewById(R.id.rb_right)).setChecked(false);
        }
        ((TextView)llDbcj.findViewById(R.id.tv_left)).setText("是否完成达标创建");

        long time = getAttrLong(attrMap, "WCDBSJ");
        if(time == 0 || !"1".equals(value)){
            llDate.setVisibility(View.GONE);
            setInfo(llDate, "完成达标创建时间", "");
            llDate.findViewById(R.id.tv_right).setTag("");
        } else {
            llDate.setVisibility(View.VISIBLE);
            String date;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
            } catch (NumberFormatException e){
                date = "";
            }
            setInfo(llDate, "完成达标创建时间", date);
            llDate.findViewById(R.id.tv_right).setTag(time + "");
        }
        setInfo(llOwner, "权属人", getAttrValue(attrMap, "QSR"), "联系电话", getAttrValue(attrMap, "QSR_LXFS"));
        setInfo(llWf, "维护人", getAttrValue(attrMap, "WHR"), "联系电话", getAttrValue(attrMap, "WHR_LXFS"));
        setInfo(llManager, "管理人", getAttrValue(attrMap, "GLR"), "联系电话", getAttrValue(attrMap, "GLR_LXFS"));
        setInfo(llMonitor, "监管人", getAttrValue(attrMap, "JGR"), "联系电话", getAttrValue(attrMap, "JGR_LXFS"));

        tvAn.setText(psdyJg.getPsdyjqpj() + "");
        tvHandlingSum.setText(psdyJg.getClzNum() + "");
        tvJbj.setText(psdyJg.getJbjNum() + "");
        tvJbjYjg.setText(psdyJg.getJbjJgNum() + "");
        tvJhj.setText(psdyJg.getJhjNum() + "");
        tvJhjYjg.setText(psdyJg.getJhjJgNum() + "");
    }

    private String getAttrValue(Map<String, Object> attrMap, String key){
        Object value = attrMap.get(key);
        if(value == null){
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    private String getAttrDouble(Map<String, Object> attrMap, String key){
        Object value = attrMap.get(key);
        if(value == null){
            return "0";
        } else {
            return (Double) value + "";
        }
    }

    private long getAttrLong(Map<String, Object> attrMap, String key){
        Object value = attrMap.get(key);
        if(value == null){
            return 0;
        } else {
            return (Long) value;
        }
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void setInfo(LinearLayout parent, String left, String right){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(left);
        ((TextView)parent.findViewById(R.id.tv_right)).setText(right);
    }

    private void setInfo(LinearLayout parent, String left, Spannable right){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(left);
        ((TextView)parent.findViewById(R.id.tv_right)).setText(right);
    }

    private void setInfo(LinearLayout parent, String leftleft, String leftright, String rightleft, String rightright){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(leftleft);
        ((EditText)parent.findViewById(R.id.et_input_left)).setText(leftright);
        ((EditText)parent.findViewById(R.id.et_input_left)).setHint("请输入姓名");
        ((TextView)parent.findViewById(R.id.tv_right)).setText(rightleft);
        ((EditText)parent.findViewById(R.id.et_input_right)).setText(rightright);
        ((EditText)parent.findViewById(R.id.et_input_right)).setHint("请输入联系电话");
        ((EditText)parent.findViewById(R.id.et_input_right)).setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    Calendar calendar = Calendar.getInstance();
    private void chooseDate(){
        new DatePickerDialog(this,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String date = new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).toString();
                        ((TextView)llDate.findViewById(R.id.tv_right)).setText(date);
                        try {
                            Date time = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                            ((TextView)llDate.findViewById(R.id.tv_right)).setTag(time.getTime() + "");
                        } catch (ParseException e) {
                            e.printStackTrace();
                            ((TextView)llDate.findViewById(R.id.tv_right)).setTag("");
                        }
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
