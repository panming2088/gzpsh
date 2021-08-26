package com.augurit.agmobile.gzps.drainage_unit_monitor.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.model.Result3;
import com.augurit.agmobile.gzps.drainage_unit_monitor.bottomsheet.AnchorSheetBehavior;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJg;
import com.augurit.agmobile.gzps.drainage_unit_monitor.model.PsdyJgjl;
import com.augurit.agmobile.gzps.drainage_unit_monitor.service.MonitorService;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.DrainageUnitMonitorFragment;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.MonitorInfoEditActivity;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.bottomsheet.IBottomSheetBehavior;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.FuncN;

public class UnitInfoView {
    private Context context;
    private View view;
    private TextView tvAn;
    private NestedScrollView svContent;
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
    private LinearLayout llBottom;
    private TextView tvUploadPerson;
    private Button btnMonitor;
    private Button btnEditInfo;
    private Button btnSubmit;

    private View.OnClickListener onClickListener;
    private MonitorService service;
    private ViewGroup parent;
    private AnchorSheetBehavior behavior;

    private ProgressDialog pd;
    private DrainageUnitMonitorFragment fragment;
    private PsdyJg psdyJg;

    public UnitInfoView(DrainageUnitMonitorFragment fragment, ViewGroup parent, View.OnClickListener onClickListener){
        context = parent.getContext();
        this.fragment = fragment;
        this.parent = parent;
        this.onClickListener = onClickListener;
        service = new MonitorService(context);
        behavior = AnchorSheetBehavior.from(parent);
        behavior.setState(IBottomSheetBehavior.STATE_HIDDEN);
        initView(parent);
    }

    private void initView(ViewGroup parent){
        view = LayoutInflater.from(context).inflate(R.layout.layout_drainage_monitor, parent, false);
        parent.addView(view);
        svContent = (NestedScrollView) view.findViewById(R.id.sv_content);
        tvAn = (TextView) view.findViewById(R.id.tv_an);
        tvHandlingSum = (TextView) view.findViewById(R.id.tv_handling_sum);
        tvJbj = (TextView) view.findViewById(R.id.tv_jbj);
        tvJbjYjg = (TextView) view.findViewById(R.id.tv_jbj_yjg);
        tvJhj = (TextView) view.findViewById(R.id.tv_jhj);
        tvJhjYjg = (TextView) view.findViewById(R.id.tv_jhj_yjg);
        tvUploadPerson = (TextView) view.findViewById(R.id.tv_upload_person);
        btnMonitor = (Button) view.findViewById(R.id.btn_monitor);
        btnMonitor.setOnClickListener(onClickListener);
        llBottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        llUnitName = (LinearLayout) view.findViewById(R.id.ll_unit_name);
        llAddress = (LinearLayout) view.findViewById(R.id.ll_address);
        llType = (LinearLayout) view.findViewById(R.id.ll_type);
        llUnitCode = (LinearLayout) view.findViewById(R.id.ll_unit_code);
        llUnitSize = (LinearLayout) view.findViewById(R.id.ll_unit_size);
        llUnitArea = (LinearLayout) view.findViewById(R.id.ll_unit_area);
        llUnitTown = (LinearLayout) view.findViewById(R.id.ll_unit_town);
        llOwner = (LinearLayout) view.findViewById(R.id.ll_owner);
        llYwfl = (LinearLayout) view.findViewById(R.id.ll_ywfl);
        llDbcj = (LinearLayout) view.findViewById(R.id.ll_dbcj);
        llDate = (LinearLayout) view.findViewById(R.id.ll_date);
        llWf = (LinearLayout) view.findViewById(R.id.ll_wf);
        llManager = (LinearLayout) view.findViewById(R.id.ll_manager);
        llMonitor = (LinearLayout) view.findViewById(R.id.ll_monitor);
        btnEditInfo = (Button) view.findViewById(R.id.btn_edit_info);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        resetView();

        view.findViewById(R.id.fl_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(behavior.getState() == IBottomSheetBehavior.STATE_ANCHOR){
                    behavior.setState(IBottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
                }
            }
        });

        behavior.setBottomSheetCallback(new AnchorSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int left = ((ViewGroup)bottomSheet.getParent()).getHeight() - bottomSheet.getTop() -
                        view.findViewById(R.id.fl_top).getHeight() - llBottom.getHeight();
                llBottom.setTranslationY(left);
            }
        });

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(component);
                EventBus.getDefault().postSticky(psdyJg);
                Intent intent = new Intent(context, MonitorInfoEditActivity.class);
                context.startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(submitListener);

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

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Map<String, Object> attrMap = component.getGraphic().getAttributes();
            service.getPsdyJg((long)component.getObjectId())
                    .subscribe(new Subscriber<Result3<PsdyJg, PsdyJgjl>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(Result3<PsdyJg, PsdyJgjl> result) {
                            if(result.getCode() != 200){
                                ToastUtil.shortToast(context, "服务器繁忙，请稍后重试");
                                return;
                            }
                            int left = result.getData().getJbjNum() + result.getData().getJhjNum() - result.getData().getJbjJgNum() - result.getData().getJhjJgNum();
                            String title = null;
                            if(left > 0){
                                title = "请继续监管！";
                            } else {
                                title = "已完成监管，是否提交？";
                            }
                            new ShowMsgPopupwindow(context, title, left + "", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    List<Observable<Result2>> observableList = new ArrayList<>();
                                    User user = new LoginRouter(context, AMDatabase.getInstance()).getUser();
                                    String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                                    Observable observable = service.addPsdyJg(user.getLoginName(), user.getId(), user.getUserName(),
                                            date, (Integer)attrMap.get("OBJECTID") + "", (String)attrMap.get("单元名称"));
                                    observableList.add(observable);
                                    if(TextUtils.isEmpty(fragment.checkView.getDMType())){
                                        ToastUtil.shortToast(context, "请进行地面检查");
                                        return;
                                    } else if(!"-1".equals(fragment.checkView.getDMType())) {
                                        observable = service.addPsdyWtjc(user.getLoginName(), attrMap.get("OBJECTID") + "", (String) attrMap.get("单元名称"), "0", "0");
                                        observableList.add(observable);
                                    }
                                    if(TextUtils.isEmpty(fragment.checkView.getKGType())){
                                        ToastUtil.shortToast(context, "请进行开盖检查");
                                        return;
                                    } else if(!"-1".equals(fragment.checkView.getKGType())) {
                                        observable = service.addPsdyWtjc(user.getLoginName(), attrMap.get("OBJECTID") + "", (String) attrMap.get("单元名称"), "1", "0");
                                        observableList.add(observable);
                                    }
                                    if(TextUtils.isEmpty(fragment.checkView.getLGType())){
                                        ToastUtil.shortToast(context, "请进行立管检查");
                                        return;
                                    } else if(!"-1".equals(fragment.checkView.getLGType())) {
                                        observable = service.addPsdyWtjc(user.getLoginName(), attrMap.get("OBJECTID") + "", (String) attrMap.get("单元名称"), "2", "0");
                                        observableList.add(observable);
                                    }
                                    pd = new ProgressDialog(context);
                                    pd.setMessage("正在提交...");
                                    pd.show();
                                    Observable.zip(observableList, new FuncN<Result2>() {
                                        @Override
                                        public Result2 call(Object... args) {
                                            Result2 result2 = new Result2();
                                            result2.setCode(200);
                                            for (Object arg : args) {
                                                if(((Result2)arg).getCode() != 200){
                                                    result2.setCode(((Result2)arg).getCode());
                                                    return result2;
                                                }
                                            }
                                            return result2;
                                        }
                                    }).subscribe(new Subscriber<Result2>() {
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
                                                        ToastUtil.shortToast(context, "完成监管");
                                                        loadPsdyJg(component.getObjectId());
                                                    } else {
                                                        ToastUtil.shortToast(context, "保存失败");
                                                    }
                                                }
                                            });
                                }
                            }).showAtLocation(((Activity)context).findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
                        }
                    });
        }
    };

    private void resetView(){
        setInfo(llUnitName, "排水单元名称", "");
        setInfo(llAddress, "单元地址", "");
        setInfo(llType, "单元类型", "");
        setInfo(llUnitCode, "排水单元编号", "");
        setInfo(llUnitSize, "排水单元面积", "");
        setInfo(llUnitArea, "所在行政区", "");
        setInfo(llUnitTown, "所在街道", "");
        setInfo(llYwfl, "是否完成雨污分流", "");
        llYwfl.findViewById(R.id.tv_right).setVisibility(View.VISIBLE);
        llYwfl.findViewById(R.id.rg).setVisibility(View.GONE);
        setInfo(llDbcj, "是否完成达标创建", "");
        setInfo(llDate, "完成达标创建时间", "");
        llDate.setVisibility(View.GONE);

        ((TextView)llDate.findViewById(R.id.tv_right)).setEnabled(false);
        setInfo(llOwner, "权属人", "", "联系电话", "");
        setInfo(llWf, "维护人", "", "联系电话", "");
        setInfo(llManager, "管理人", "", "联系电话", "");
        setInfo(llMonitor, "监管人", "", "联系电话", "");
        tvAn.setText("");
        tvHandlingSum.setText("");
        tvJbj.setText("");
        tvJbjYjg.setText("");
        tvJhj.setText("");
        tvJhjYjg.setText("");
        tvUploadPerson.setText("");
        tvUploadPerson.setVisibility(View.INVISIBLE);
    }

    private void setInfo(LinearLayout parent, String left, String right){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(left);
        ((TextView)parent.findViewById(R.id.tv_right)).setText(right);
    }

    private void setInfo(LinearLayout parent, String leftleft, String leftright, String rightleft, String rightright){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(leftleft);
        ((EditText)parent.findViewById(R.id.et_input_left)).setText(leftright);
        ((EditText)parent.findViewById(R.id.et_input_left)).setEnabled(false);
        ((EditText)parent.findViewById(R.id.et_input_left)).setBackgroundColor(Color.TRANSPARENT);
        ((TextView)parent.findViewById(R.id.tv_right)).setText(rightleft);
        ((EditText)parent.findViewById(R.id.et_input_right)).setText(rightright);
        ((EditText)parent.findViewById(R.id.et_input_right)).setEnabled(false);
        ((EditText)parent.findViewById(R.id.et_input_right)).setBackgroundColor(Color.TRANSPARENT);
    }

    private void setInfo(LinearLayout parent, String left, Spannable right){
        ((TextView)parent.findViewById(R.id.tv_left)).setText(left);
        ((TextView)parent.findViewById(R.id.tv_right)).setText(right);
    }

    private Component component;
    public void reshow(){
        if(component == null){
            return;
        }
        show(component);
        btnSubmit.setVisibility(View.VISIBLE);
    }

    public void show(Component component){
        this.component = component;
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
            setInfo(llYwfl, "是否完成雨污分流", "是");
        } else if("0".equals(value)){
            setInfo(llYwfl, "是否完成雨污分流", "否");
        } else {
            setInfo(llYwfl, "是否完成雨污分流", "");
        }

        llDate.setVisibility(View.GONE);
        value = getAttrValue(attrMap, "SFWCDB");
        if("1".equals(value)){
            setInfo(llDbcj, "是否完成达标创建", "是");
            long time = getAttrLong(attrMap, "WCDBSJ");
            if(time != 0){
                llDate.setVisibility(View.VISIBLE);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
                setInfo(llDate, "完成达标创建时间", date);
            }
        } else if("0".equals(value)){
            setInfo(llDbcj, "是否完成达标创建", "否");
        } else {
            setInfo(llDbcj, "是否完成达标创建", "");
        }

        setInfo(llOwner, "权属人", getAttrValue(attrMap, "QSR"), "联系电话", getAttrValue(attrMap, "QSR_LXFS"));
        setInfo(llWf, "维护人", getAttrValue(attrMap, "WHR"), "联系电话", getAttrValue(attrMap, "WHR_LXFS"));
        setInfo(llManager, "管理人", getAttrValue(attrMap, "GLR"), "联系电话", getAttrValue(attrMap, "GLR_LXFS"));
        setInfo(llMonitor, "监管人", getAttrValue(attrMap, "JGR"), "联系电话", getAttrValue(attrMap, "JGR_LXFS"));
        tvAn.setText("");
        tvHandlingSum.setText("");
        tvJbj.setText("");
        tvJbjYjg.setText("");
        tvJhj.setText("");
        tvJhjYjg.setText("");
        tvUploadPerson.setText("");
        tvUploadPerson.setVisibility(View.INVISIBLE);
        show(component, (Integer)attrMap.get("OBJECTID"), (String)attrMap.get("单元名称"));
        btnSubmit.setVisibility(View.GONE);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void hide(){
        behavior.setState(IBottomSheetBehavior.STATE_HIDDEN);
        svContent.scrollTo(0, 0);
    }

    public boolean isVisible(){
        return behavior.getState() != IBottomSheetBehavior.STATE_HIDDEN;
    }

    private void show(Component component, long objectId, String name) {
        btnMonitor.setTag(R.id.tag_1, objectId);
        btnMonitor.setTag(R.id.tag_2, name);
        btnMonitor.setTag(R.id.tag_3, component);
        behavior.setAnchorHeight(DensityUtils.dp2px(context, 36) + llType.getBottom() + llBottom.getBottom());
        behavior.setPeekHeight(DensityUtils.dp2px(context, 36));
        if(behavior.getState() == IBottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(IBottomSheetBehavior.STATE_ANCHOR);
        }

        loadPsdyJg(objectId);
    }

    private void loadPsdyJg(long objectId){
        service.getPsdyJg(objectId)
                .subscribe(new Subscriber<Result3<PsdyJg, PsdyJgjl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result3<PsdyJg, PsdyJgjl> result) {
                        psdyJg = result.getData();
                        tvAn.setText(result.getData().getPsdyjqpj() + "");
                        tvHandlingSum.setText(result.getData().getClzNum() + "");
                        tvJbj.setText(result.getData().getJbjNum() + "");
                        tvJbjYjg.setText(result.getData().getJbjJgNum() + "");
                        tvJhj.setText(result.getData().getJhjNum() + "");
                        tvJhjYjg.setText(result.getData().getJhjJgNum() + "");

                        if(result.getData2() == null || result.getData2().getMarkPerson() == null) {
                            tvUploadPerson.setText("");
                            tvUploadPerson.setVisibility(View.INVISIBLE);
                        } else {
                            String date = new SimpleDateFormat("yyyyMMdd").format(new Date(result.getData2().getMarkTime()));
                            String text = String.format("%s于%s监管过该排水单元", result.getData2().getMarkPerson(), date);
                            tvUploadPerson.setText(text);
                            tvUploadPerson.setVisibility(View.VISIBLE);
                        }
                    }
                });
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
}