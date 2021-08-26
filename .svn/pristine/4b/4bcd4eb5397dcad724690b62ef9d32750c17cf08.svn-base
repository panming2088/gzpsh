package com.augurit.agmobile.gzpssb.uploadfacility.view.condiction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by liangsh on 2017/11/20.
 */

public class UploadEventConditionView implements View.OnClickListener, Animation.AnimationListener {

    private View mContainer;
    private Context mContext;

    private FlexRadioGroup frgArea;
    private FlexRadioGroup frgComponentType;
    private View ll_eventtype, ll_event_other;
    private FlexRadioGroup frgEventType;

    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    private Animation animCollapse, animExpand;

    private SparseBooleanArray isCollapses; //是否收缩

    private Drawable dropUp, dropDown;

    private int currentAnimId;//当前正在执行动画的ID

    private boolean mProtectFromCheckedChange = false;


    /**
     * 筛选的区域
     */
    private String distrct = null;
    /**
     * 设施类型：窨井，雨水口等
     */
    private String componentType = null;
    /**
     * 问题类型
     */
    private String eventType = null;

    private Long startDate = null;

    private Long endDate = null;
    private String[] allDistricts;
    private String[] allComponentTypeArr;
    private String[] allEventTypeArr;
    private int curNwChildPos;
    private EditText auto_psh_type_name;
    private Button btn_start_date, btn_end_date;
    private Calendar cal;
    private Long TempEndDate = null;
    private EditText et_org_name, et_address, et_uploadId;
    private boolean isIndustry1;
    public UploadEventConditionView(View container, int curNwChiidPos, Context context) {
        this.mContainer = container;
        this.mContext = context;
        this.curNwChildPos = curNwChiidPos;
        initView();
    }

    public UploadEventConditionView(View container, Context context, boolean isIndustry) {
        this.mContainer = container;
        this.mContext = context;
        this.isIndustry1 = isIndustry;
        initView();
    }

    private void initView() {


        frgArea = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_event_distrct);
        frgComponentType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_component_type);
        ll_eventtype = mContainer.findViewById(R.id.ll_psh_eventtype);
        ll_event_other = mContainer.findViewById(R.id.ll_event_other);
        auto_psh_type_name = (EditText) mContainer.findViewById(R.id.auto_psh_type_name);
        et_org_name = (EditText) mContainer.findViewById(R.id.auto_org_name);
        et_address = (EditText) mContainer.findViewById(R.id.et_address);
        et_uploadId = (EditText) mContainer.findViewById(R.id.et_mark_id);
        frgEventType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_event_type);
        btn_start_date = (Button) mContainer.findViewById(R.id.btn_psh_start_date);
        btn_end_date = (Button) mContainer.findViewById(R.id.btn_psh_psh_end_date);
        // text = (TextView) findViewById(R.id.text);

        mContainer.findViewById(R.id.btn_psh_event_clear).setOnClickListener(this);
        mContainer.findViewById(R.id.btn_psh_event_submit).setOnClickListener(this);

        isCollapses = new SparseBooleanArray();

        String[] all = {"全部"};
        String[] dist = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};
        FacilityAffairService facilityAffairService = new FacilityAffairService(mContext.getApplicationContext());
        boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
        if (b || isIndustry1) {
            allDistricts = mergeArray(all, dist);
            createRadioButton(allDistricts, frgArea);
        } else {
            String[] district = new String[]{facilityAffairService.getParentOrgOfCurrentUser()};
            createRadioButton(district, frgArea);
        }

//        List<DictionaryItem> component_type_list = new TableDBService().getDictionaryByTypecodeInDB("A179");
//        sortDictionaryList(component_type_list);
//        String[] componentTypeNameArr = new String[component_type_list.size()];
//        String[] componentTypeCodeArr = new String[component_type_list.size()];
//        for (int i = 0; i < component_type_list.size(); i++) {
//            DictionaryItem dictionaryItem = component_type_list.get(i);
//            componentTypeNameArr[i] = dictionaryItem.getName();
//            componentTypeCodeArr[i] = dictionaryItem.getCode();
//        }
        String[] componentTypeNameArr = new String[]{"工业类", "餐饮类", "建筑类", "医疗类","农贸市场类","畜禽养殖类"
                ,"机关事业单位类","汽修机洗类","洗涤类","垃圾收集处理类"
                ,"综合商业类","居民类","其他"};
        allComponentTypeArr = mergeArray(all, componentTypeNameArr);
        createComponentRadioButton(allComponentTypeArr, frgComponentType);

        mContainer.findViewById(R.id.tv_distrct).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_component_type).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_event_type).setOnClickListener(this);

        animExpand = AnimationUtils.loadAnimation(mContext, R.anim.expand);
        animExpand.setAnimationListener(this);
        animExpand.setFillAfter(true);

        animCollapse = AnimationUtils.loadAnimation(mContext, R.anim.collapse);
        animCollapse.setAnimationListener(this);
        animCollapse.setFillAfter(true);

        ll_eventtype.setVisibility(View.GONE);
        ll_event_other.setVisibility(View.GONE);

      //  resetDate();
        initStartDate();
        initEndDate();

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
    }

    private void createRadioButton(String[] keys, final FlexRadioGroup group) {
        group.removeAllViews();
        /**
         *  64dp菜单的边距{@link DrawerLayout#MIN_DRAWER_MARGIN}+10dp*2为菜单内部的padding=84dp
         */
        float margin = DensityUtils.dp2px(mContext, 60);
        float width = DensityUtils.getWidth(mContext);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            RadioButton rb = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.item_radiobutton, null);
            rb.setText(key);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lp);
            group.addView(rb);
            if (key.equals("全部") || keys.length == 1) {
                rb.setChecked(true);
            }

            /**
             * 下面两个监听器用于点击两次可以清除当前RadioButton的选中
             * 点击RadioButton后，{@link FlexRadioGroup#OnCheckedChangeListener}先回调，然后再回调{@link View#OnClickListener}
             * 如果当前的RadioButton已经被选中时，不会回调OnCheckedChangeListener方法，故判断没有回调该方法且当前RadioButton确实被选中时清除掉选中
             */
            group.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@IdRes int checkedId) {
                    mProtectFromCheckedChange = true;
                }
            });
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mProtectFromCheckedChange && ((RadioButton) v).isChecked()) {
                        group.clearCheck();
                    } else {
                        RadioButton radioButton = (RadioButton) v;
                        String code = radioButton.getText().toString();
                        if ("其他".equals(code)) {
                            ll_event_other.setVisibility(View.VISIBLE);
                        } else {
                            ll_event_other.setVisibility(View.GONE);
                        }

                        mProtectFromCheckedChange = false;
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    private void createComponentRadioButton(String[] keys, final FlexRadioGroup group) {
        group.removeAllViews();

        /**
         *  64dp菜单的边距{@link DrawerLayout#MIN_DRAWER_MARGIN}+10dp*2为菜单内部的padding=84dp
         */
        float margin = DensityUtils.dp2px(mContext, 60);
        float width = DensityUtils.getWidth(mContext);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            RadioButton rb = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.item_radiobutton, null);
            rb.setText(key);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lp);
            group.addView(rb);
            if (key.equals("全部")) {
                rb.setChecked(true);
            }

            /**
             * 下面两个监听器用于点击两次可以清除当前RadioButton的选中
             * 点击RadioButton后，{@link FlexRadioGroup#OnCheckedChangeListener}先回调，然后再回调{@link View#OnClickListener}
             * 如果当前的RadioButton已经被选中时，不会回调OnCheckedChangeListener方法，故判断没有回调该方法且当前RadioButton确实被选中时清除掉选中
             */
            group.setOnCheckedChangeListener(new FlexRadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@IdRes int checkedId) {
                    mProtectFromCheckedChange = true;
                }
            });
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mProtectFromCheckedChange && ((RadioButton) v).isChecked()) {
                        group.clearCheck();
                    } else {
                        RadioButton radioButton = (RadioButton) v;
                        String code = radioButton.getText().toString();
//                        initEventType(code);
                        mProtectFromCheckedChange = false;
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    private void initEventType(String code) {
        frgEventType.removeAllViews();
        if ("全部".equals(code)) {
            ll_eventtype.setVisibility(View.GONE);
            ll_event_other.setVisibility(View.GONE);
            return;
        }
        ll_event_other.setVisibility(View.GONE);
        ll_eventtype.setVisibility(View.VISIBLE);
        allEventTypeArr = new String[0];
//        String[] all = {"全部"};
//        List<DictionaryItem> dictionaryItemList = new TableDBService().getChildDictionaryByPCodeInDB(code);
//        sortDictionaryList(dictionaryItemList);
//        String[] eventTypeNameArr = new String[dictionaryItemList.size()];
//        String[] eventTypeCodeArr = new String[dictionaryItemList.size()];
//        for (int i = 0; i < dictionaryItemList.size(); i++) {
//            DictionaryItem dictionaryItem = dictionaryItemList.get(i);
//            eventTypeNameArr[i] = dictionaryItem.getName();
//            eventTypeCodeArr[i] = dictionaryItem.getCode();
//        }
        if (code.equals("生活")) {
            allEventTypeArr = new String[]{"全部", "机关企事业单位", "学校", "商场", "居民住宅", "其他"};
        } else if (code.equals("餐饮")) {
            allEventTypeArr = new String[]{"全部", "餐饮店", "农家乐", "酒店", "大型食堂", "其他"};
        } else if (code.equals("沉淀物")) {
            allEventTypeArr = new String[]{"全部", "洗车、修车档", "沙场", "建筑工地", "养殖场", "农贸市场","垃圾(收集)转运站", "其他"};
        } else if (code.equals("有毒有害")) {
            allEventTypeArr = new String[]{"全部", "化工", "印染", "电镀", "医疗", "其他"};
        }

//        String[] allEventTypeCodeArr = mergeArray(all, eventTypeCodeArr);
        createRadioButton(allEventTypeArr, frgEventType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_psh_event_clear:

                if (allDistricts != null && allDistricts.length > 0) {
                    frgArea.check(allDistricts[0]);
                }

                if (allComponentTypeArr != null && allComponentTypeArr.length > 0) {
                    frgComponentType.check(allComponentTypeArr[0]);
                }

                if (allEventTypeArr != null && allEventTypeArr.length > 0) {
                    frgEventType.check(allEventTypeArr[0]);
                }

                resetDate();
                auto_psh_type_name.setText("");
                ll_event_other.setVisibility(View.GONE);
                ll_eventtype.setVisibility(View.GONE);
                et_address.setText("");
                et_uploadId.setText("");
                et_org_name.setText("");
                break;
            case R.id.btn_psh_event_submit:
//                drawer_layout.closeDrawers();

                RadioButton rbDistrict = (RadioButton) mContainer.findViewById(frgArea.getCheckedRadioButtonId());
                if (rbDistrict != null) {
                    distrct = rbDistrict.getText().toString();
                } else {
                    distrct = "全部";
                }

                RadioButton rbComponentType = (RadioButton) mContainer.findViewById(frgComponentType.getCheckedRadioButtonId());
                if (rbComponentType != null) {
                    componentType = rbComponentType.getText().toString();
                } else {
                    componentType = "全部";
                }

                RadioButton rbEvent = (RadioButton) mContainer.findViewById(frgEventType.getCheckedRadioButtonId());
                if (rbEvent != null) {
                    eventType = rbEvent.getText().toString();
                } else {
                    eventType = "全部";
                }
                if (eventType.equals("其他")) {
                    eventType = auto_psh_type_name.getText().toString().trim();
                }
                String address = et_address.getText().toString().trim();
                String orgname = et_org_name.getText().toString().trim();
                String uploadid = et_uploadId.getText().toString().trim();
                Long id = null;
                try {
                    id = Long.parseLong(TextUtils.isEmpty(uploadid) ? null : uploadid);
                } catch (Exception e) {

                }

                UploadFilterConditionEvent eventAffairConditionEvent = new UploadFilterConditionEvent(
                        "全部".equals(distrct) ? null : distrct, "全部".equals(componentType) ? null : componentType,
                        "全部".equals(eventType) ? null : eventType, startDate, endDate
                        , address, orgname, id);
                EventBus.getDefault().post(eventAffairConditionEvent);
                break;
            case R.id.tv_distrct:
                startAnim(frgArea, (TextView) v);
                break;
            case R.id.tv_component_type:
                startAnim(frgComponentType, (TextView) v);
                break;
            case R.id.tv_event_type:
                startAnim(frgEventType, (TextView) v);
                break;
            default:
                break;
        }
    }

    private void initEndDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);

        btn_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }

    private void initStartDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        startDate = new Date(year - 1900, 0, 1).getTime();
        btn_start_date.setText(year + "-" + 1 + "-" + 1);
    }


    public void showDatePickerDialog(final Button btn, Calendar calendar, final int type) {
        // Calendar 需要这样来得到
        // Calendar calendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(mContext,
                // 绑定监听器(How the parent is notified that the date is set.)
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
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 设置日期
     */
    public void display(Button dateDisplay, int year,
                        int monthOfYear, int dayOfMonth) {
        dateDisplay.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
    }

    /**
     * 重置时间
     */
    public void resetDate() {
        setStateDate();
        setCurrentDate();
    }

    @SuppressLint("WrongConstant")
    public void setStateDate() {
        cal = Calendar.getInstance();
        cal.add(Calendar.MONDAY, -2);   //获取前两个月
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        startDate = new Date(year - 1900, month - 1, day).getTime();
        btn_start_date.setText(year + "-" + month + "-" +day);
    }

    public void setCurrentDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        btn_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }


    /**
     * 重新设置isCollapse值，保存当前动画状态
     * 启动动画
     *
     * @param group
     */
    private void startAnim(FlexRadioGroup group, TextView view) {
        currentAnimId = group.getId();
        boolean isCollapse = !isCollapses.get(group.getId());
        isCollapses.put(group.getId(), isCollapse);
        if (isCollapse) {
            group.startAnimation(animCollapse);
        } else {
            group.startAnimation(animExpand);
        }
        setArrow(view, isCollapse);
    }

    /**
     * 设置箭头
     */
    private void setArrow(TextView view, boolean isCollapse) {
        if (!isCollapse) {
            if (dropUp == null) {
                dropUp = mContext.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                dropUp.setBounds(0, 0, dropUp.getMinimumWidth(), dropUp.getMinimumHeight());
            }
            view.setCompoundDrawables(null, null, dropUp, null);
        } else {
            if (dropDown == null) {
                dropDown = mContext.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                dropDown.setBounds(0, 0, dropDown.getMinimumWidth(), dropDown.getMinimumHeight());
            }
            view.setCompoundDrawables(null, null, dropDown, null);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (!isCollapses.get(currentAnimId)) {
            mContainer.findViewById(currentAnimId).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (isCollapses.get(currentAnimId)) {
            mContainer.findViewById(currentAnimId).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private String[] mergeArray(String[] a, String[] b) {
        // 合并两个数组
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private void sortDictionaryList(List<DictionaryItem> dictionaryItemList) {
        Collections.sort(dictionaryItemList, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem o1, DictionaryItem o2) {
                String code = o1.getCode();
                String target = code;
                if (code.length() > 1) {
                    target = code.replaceAll("[^(0-9)]", "");//去掉所有字母
                }

                String code2 = o2.getCode();
                String target2 = code;
                if (code2.length() > 1) {
                    target2 = code2.replaceAll("[^(0-9)]", "");//去掉所有字母
                }

                int num1 = Integer.valueOf(target);
                int num2 = Integer.valueOf(target2);
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });
    }
}
