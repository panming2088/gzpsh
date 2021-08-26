package com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzpssb.common.MultiSelectFlexLayout2;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.apache.commons.collections4.MapUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liangsh on 2017/11/20.
 */

public class PSHEventAffairConditionView implements View.OnClickListener, Animation.AnimationListener {

    private View mContainer;
    private Context mContext;

    private MultiSelectFlexLayout2 frgArea;
    private MultiSelectFlexLayout2 frgComponentType;
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
    private boolean isCityUser;

    public PSHEventAffairConditionView(View container, int curNwChiidPos, Context context) {
        this.mContainer = container;
        this.mContext = context;
        this.curNwChildPos = curNwChiidPos;
        initView();
    }

    public PSHEventAffairConditionView(View container, Context context) {
        this.mContainer = container;
        this.mContext = context;
        initView();
    }

    private void initView() {


        frgArea = (MultiSelectFlexLayout2) mContainer.findViewById(R.id.frg_psh_event_distrct);
        frgComponentType = (MultiSelectFlexLayout2) mContainer.findViewById(R.id.frg_psh_component_type);
        ll_eventtype = mContainer.findViewById(R.id.ll_psh_eventtype);
        ll_event_other = mContainer.findViewById(R.id.ll_event_other);
        auto_psh_type_name = (EditText) mContainer.findViewById(R.id.auto_psh_type_name);
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
        isCityUser = facilityAffairService.ifCurrentUserBelongToCityUser();
        if (isCityUser) {
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
        String[] componentTypeNameArr = new String[]{"工业类", "餐饮类", "建筑类", "医疗类", "农贸市场类", "畜禽养殖类"
                , "机关事业单位类", "汽修机洗类", "洗涤类", "垃圾收集处理类"
                , "综合商业类", "居民类", "其他"};
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

    private void createRadioButton(String[] keys, final MultiSelectFlexLayout2 group) {
        Map<String, DictionaryItem> items = new LinkedHashMap<String, DictionaryItem>();
        Map<String, DictionaryItem> selectedItem = new HashMap<String, DictionaryItem>(2);
        for (int i = 0; i < keys.length; i++) {
            DictionaryItem item = new DictionaryItem();
            String key = keys[i];
            item.setName(key);
            items.put(key, item);
            if (key.equals("全部")) {
                selectedItem.put(key, item);
            }else if(keys.length==1){
                selectedItem.put(key, item);
            }

        }
        group.addItemsByDictionaryItem("", "key", items, selectedItem);
    }

    private void createComponentRadioButton(String[] keys, final MultiSelectFlexLayout2 group) {
//        group.removeAllViews();
//
//        /**
//         *  64dp菜单的边距{@link DrawerLayout#MIN_DRAWER_MARGIN}+10dp*2为菜单内部的padding=84dp
//         */

        Map<String, DictionaryItem> items = new LinkedHashMap<String, DictionaryItem>();
        Map<String, DictionaryItem> selectedItem = new HashMap<String, DictionaryItem>(2);
        float margin = DensityUtils.dp2px(mContext, 60);
        float width = DensityUtils.getWidth(mContext);
        for (int i = 0; i < keys.length; i++) {
            DictionaryItem item = new DictionaryItem();
            String key = keys[i];
            item.setName(key);
            items.put(key, item);
            if (key.equals("全部")) {
                selectedItem.put(key, item);
            }else if(keys.length==1){
                selectedItem.put(key, item);
            }

        }
        group.addItemsByDictionaryItem("", "key", items, selectedItem);
//        isCollapses.put(group.getId(), false);
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
            allEventTypeArr = new String[]{"全部", "洗车、修车档", "沙场", "建筑工地", "养殖场", "农贸市场", "垃圾(收集)转运站", "其他"};
        } else if (code.equals("有毒有害")) {
            allEventTypeArr = new String[]{"全部", "化工", "印染", "电镀", "医疗", "其他"};
        }

//        String[] allEventTypeCodeArr = mergeArray(all, eventTypeCodeArr);
//        createRadioButton(allEventTypeArr, frgEventType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_psh_event_clear:

                if (allDistricts != null && allDistricts.length > 0) {
                    frgArea.reset();
                }

                if (allComponentTypeArr != null && allComponentTypeArr.length > 0) {
                    frgComponentType.reset();
                }

                if (allEventTypeArr != null && allEventTypeArr.length > 0) {
                    frgEventType.check(allEventTypeArr[0]);
                }

                resetDate();
                auto_psh_type_name.setText("");
                ll_event_other.setVisibility(View.GONE);
                ll_eventtype.setVisibility(View.GONE);

                break;
            case R.id.btn_psh_event_submit:
//                drawer_layout.closeDrawers();
                if (startDate > TempEndDate) {
                    ToastUtil.iconShortToast(mContext, R.mipmap.ic_alert_yellow, "开始时间不能比结束时间大");
                    return;
                }
//                RadioButton rbDistrict = (RadioButton) mContainer.findViewById(frgArea.getCheckedRadioButtonId());
//                if (rbDistrict != null) {
//                    distrct = rbDistrict.getText().toString();
//                } else {
//                    distrct = "全部";
//                }
                distrct = "";
                componentType = "";

                Map<String, Object> selectedItems1 = frgArea.getSelectedItems();
                if (MapUtils.isEmpty(selectedItems1)) {
                    distrct = "全部";
                }else{
                    Set<String> strings = selectedItems1.keySet();
                    String keys = "";
                    for(String key:strings){
                        if(key.equals("全部")){
                            distrct = "全部";
                            break;
                        }else{
                            keys+=","+key+"区";
                        }
                    }
                    if(!"全部".equals(distrct)) {
                        distrct = keys.substring(1);
                    }
                }

//                RadioButton rbComponentType = (RadioButton) mContainer.findViewById(frgComponentType.getCheckedRadioButtonId());
//                if (rbComponentType != null) {
//                    componentType = rbComponentType.getText().toString();
//                } else {
//                    componentType = "全部";
//                }
//
//                RadioButton rbEvent = (RadioButton) mContainer.findViewById(frgEventType.getCheckedRadioButtonId());
//                if (rbEvent != null) {
//                    eventType = rbEvent.getText().toString();
//                } else {
//                    eventType = "全部";
//                }
//                if (eventType.equals("其他")) {
//                    eventType = auto_psh_type_name.getText().toString().trim();
//                }
                Map<String, Object> selectedItems = frgComponentType.getSelectedItems();
                if (MapUtils.isEmpty(selectedItems)) {
                    componentType = "全部";
                }else{
                    Set<String> strings = selectedItems.keySet();
                    String keys = "";
                    for(String key:strings){
                        if(key.equals("全部")){
                            componentType = "全部";
                            break;
                        }else{
                            keys+=","+key;
                        }
                    }
                    if(!"全部".equals(componentType)) {
                        componentType = keys.substring(1);
                    }
                }


                PSHAffairFilterConditionEvent eventAffairConditionEvent = new PSHAffairFilterConditionEvent(
                        "全部".equals(distrct) ? null : distrct, "全部".equals(componentType) ? null : componentType, "全部".equals(eventType) ? null : eventType, startDate, endDate);
                EventBus.getDefault().post(eventAffairConditionEvent);
                break;
            case R.id.tv_distrct:
                startAnim(frgArea, (TextView) v);
                break;
            case R.id.tv_component_type:
//                startAnim(frgComponentType, (TextView) v);
                break;
            case R.id.tv_event_type:
//                startAnim(frgEventType, (TextView) v);
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

//        btn_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
        btn_end_date.setText(TimeUtil.getStringTimeYMD(new Date(TempEndDate)));
    }

    private void initStartDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        startDate = new Date(2018 - 1900, 0, 1).getTime();
//        btn_start_date.setText(year + "-" + 1 + "-" + 1);
        btn_start_date.setText(TimeUtil.getStringTimeYMD(new Date(startDate)));
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
//                        display(btn, year, monthOfYear, dayOfMonth);
                        if (type == START_DATE) {
                            startDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                            btn.setText(TimeUtil.getStringTimeYMD(new Date(startDate)));
                        } else {
                            endDate = new Date(year - 1900, monthOfYear, dayOfMonth + 1).getTime();
                            TempEndDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                            btn.setText(TimeUtil.getStringTimeYMD(new Date(TempEndDate)));
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
//        cal.add(Calendar.MONDAY, -2);   //获取前两个月
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        startDate = new Date(year - 1900, 0, 1).getTime();
        btn_start_date.setText(year + "-" + 1 + "-" + 1);
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
    private void startAnim(MultiSelectFlexLayout2 group, TextView view) {
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
