package com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.publicaffair.view.condition.EventAffairConditionEvent;
import com.augurit.agmobile.gzpssb.common.MultiSelectFlexLayout2;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.apache.commons.collections4.MapUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liangsh on 2017/11/20.
 */

public class PshWtsbAffairConditionView implements View.OnClickListener, Animation.AnimationListener {

    private View mContainer;
    private Context mContext;

    private MultiSelectFlexLayout2 frgArea;
    private FlexRadioGroup frg_facility_type;
    private View ll_component_type;
    private MultiSelectFlexLayout2 frgComponentType;
    private View ll_eventtype;
    private MultiSelectFlexLayout2 frgEventType;
    private ViewGroup ll_psdy_name;
    private TextView tv_psdy_name;
    private EditText et_psdy_name;

    private Animation animCollapse, animExpand;

    private SparseBooleanArray isCollapses; //是否收缩

    private Drawable dropUp, dropDown;

    private int currentAnimId;//当前正在执行动画的ID

    private boolean mProtectFromCheckedChange = false;

    String[] dist = {"天河", "番禺", "黄埔", "白云", "南沙", "海珠", "荔湾", "花都", "越秀", "增城", "从化"};

    String[] facilitys = {"全部", "排水户", "井", "其他"};
    String[] facilityCodes = {"全部", "psh", "well", "other"};
    /**
     * 筛选的区域
     */
    private String distrct = null;
    private String facilityType = null;
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
    private int curPsChildPos = 1;

    public PshWtsbAffairConditionView(View container, int curPsChildPos, Context context) {
        this.mContainer = container;
        this.mContext = context;
        this.curPsChildPos = curPsChildPos;
        initView();
    }

    public PshWtsbAffairConditionView(View container, Context context) {
        this.mContainer = container;
        this.mContext = context;
        initView();
    }


    private void initView() {
        frgArea = (MultiSelectFlexLayout2) mContainer.findViewById(R.id.frg_event_distrct);
        frg_facility_type = mContainer.findViewById(R.id.frg_facility_type);
        ll_component_type = mContainer.findViewById(R.id.ll_component_type);
        frgComponentType = (MultiSelectFlexLayout2) mContainer.findViewById(R.id.frg_component_type);
        ll_eventtype = mContainer.findViewById(R.id.ll_eventtype);
        frgEventType = (MultiSelectFlexLayout2) mContainer.findViewById(R.id.frg_event_type);
        // text = (TextView) findViewById(R.id.text);
        ll_psdy_name = mContainer.findViewById(R.id.ll_psdy_name);
        tv_psdy_name = mContainer.findViewById(R.id.tv_psdy_name);
        et_psdy_name = mContainer.findViewById(R.id.et_psdy_name);

        mContainer.findViewById(R.id.btn_event_clear).setOnClickListener(this);
        mContainer.findViewById(R.id.btn_event_submit).setOnClickListener(this);

        isCollapses = new SparseBooleanArray();
        isCollapses.put(et_psdy_name.getId(), false);
        String[] all = {"全部"};
        FacilityAffairService facilityAffairService = new FacilityAffairService(mContext.getApplicationContext());
        boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
//        if (b || curPsChildPos == 0) {
        allDistricts = mergeArray(all, dist);
        createRadioButton(allDistricts, allDistricts, frgArea);
//        } else {
//            String[] district = new String[]{facilityAffairService.getParentOrgOfCurrentUser()};
//            createRadioButton(district, district, frgArea);
//        }

        createFacilityRadioButton(facilitys, facilityCodes, frg_facility_type);

        List<DictionaryItem> component_type_list = new TableDBService().getDictionaryByTypecodeInDB("A204");
        sortDictionaryList(component_type_list);
        String[] componentTypeNameArr = new String[component_type_list.size()];
        String[] componentTypeCodeArr = new String[component_type_list.size()];
        for (int i = 0; i < component_type_list.size(); i++) {
            DictionaryItem dictionaryItem = component_type_list.get(i);
            componentTypeNameArr[i] = dictionaryItem.getName();
            componentTypeCodeArr[i] = dictionaryItem.getCode();
        }
        allComponentTypeArr = mergeArray(all, componentTypeNameArr);
        String[] allComponentTypeCodeArr = mergeArray(all, componentTypeCodeArr);
        ll_component_type.setVisibility(View.GONE);
//        createComponentRadioButton(allComponentTypeArr, allComponentTypeCodeArr, frgComponentType);

        mContainer.findViewById(R.id.tv_distrct).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_facility_type).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_component_type).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_event_type).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_psdy_name).setOnClickListener(this);

        animExpand = AnimationUtils.loadAnimation(mContext, R.anim.expand);
        animExpand.setAnimationListener(this);
        animExpand.setFillAfter(true);

        animCollapse = AnimationUtils.loadAnimation(mContext, R.anim.collapse);
        animCollapse.setAnimationListener(this);
        animCollapse.setFillAfter(true);

        ll_eventtype.setVisibility(View.GONE);
    }

    private void createRadioButton(String[] keys, Object[] values, final MultiSelectFlexLayout2 group) {
        Map<String, DictionaryItem> items = new LinkedHashMap<String, DictionaryItem>();
        Map<String, DictionaryItem> selectedItem = new HashMap<String, DictionaryItem>(2);
        for (int i = 0; i < keys.length; i++) {
            DictionaryItem item = new DictionaryItem();
            String key = keys[i];
            item.setName(key);
            items.put(key, item);
            if (key.equals("全部")) {
                selectedItem.put(key, item);
            } else if (keys.length == 1) {
                selectedItem.put(key, item);
            }

        }
        group.addItemsByDictionaryItem("", "key", items, selectedItem);
    }

    private void createFacilityRadioButton(String[] keys, Object[] values, final FlexRadioGroup group) {
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
            rb.setTag(values[i]);
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
                        String code = radioButton.getTag().toString();
                        initComponentType(code);
                        mProtectFromCheckedChange = false;
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    private void createComponentRadioButton(String[] keys, Object[] values, final MultiSelectFlexLayout2 group) {
        group.removeView();
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
            item.setCode((String) values[i]);
            items.put(key, item);
            if (key.equals("全部")) {
                selectedItem.put(key, item);
            } else if (keys.length == 1) {
                selectedItem.put(key, item);
            }

        }
        group.addItemsByDictionaryItem("", "key", items, selectedItem);
//        isCollapses.put(group.getId(), false);
    }

    private void initComponentType(String code) {
        frgEventType.removeAllViews();
        if ("全部".equals(code)) {
            componentType = null;
            ll_component_type.setVisibility(View.GONE);
            return;
        }
        ll_component_type.setVisibility(View.VISIBLE);
        allComponentTypeArr = new String[0];
        String[] all = {"全部"};
        List<DictionaryItem> dictionaryItemList = new ArrayList<>();

        if ("psh".equals(code)) {
            dictionaryItemList.addAll(new TableDBService().getDictionaryByTypecodeInDB("A204"));
        } else if ("other".equals(code)) {
            dictionaryItemList.addAll(new TableDBService().getDictionaryByTypecodeInDB("A213"));
        } else {
            List<DictionaryItem> a208 = new TableDBService().getDictionaryByTypecodeInDB("A208");
            for (DictionaryItem d : a208) {
                if (!dictionaryItemList.contains(d)) {
                    dictionaryItemList.add(d);
                }
            }
            List<DictionaryItem> a209 = new TableDBService().getDictionaryByTypecodeInDB("A209");
            for (DictionaryItem d : a209) {
                if (!dictionaryItemList.contains(d)) {
                    dictionaryItemList.add(d);
                }
            }
            List<DictionaryItem> a210 = new TableDBService().getDictionaryByTypecodeInDB("A210");
            for (DictionaryItem d : a210) {
                if (!dictionaryItemList.contains(d)) {
                    dictionaryItemList.add(d);
                }
            }
            List<DictionaryItem> a211 = new TableDBService().getDictionaryByTypecodeInDB("A211");
            for (DictionaryItem d : a211) {
                if (!dictionaryItemList.contains(d)) {
                    dictionaryItemList.add(d);
                }
            }
        }
//        List<DictionaryItem> dictionaryItemList = new TableDBService().getChildDictionaryByPCodeInDB(code);
        List<DictionaryItem> itemList = new ArrayList<>();
        for (DictionaryItem item : dictionaryItemList) {
            if (item.getValue().equals("0")) {
                continue;
            }
            itemList.add(item);
        }
        sortDictionaryList(itemList);
        String[] eventTypeNameArr = new String[itemList.size()];
        String[] eventTypeCodeArr = new String[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            DictionaryItem dictionaryItem = itemList.get(i);
            eventTypeNameArr[i] = dictionaryItem.getName();
            eventTypeCodeArr[i] = dictionaryItem.getCode();
        }
        allComponentTypeArr = mergeArray(all, eventTypeNameArr);
        String[] allComponentTypeCodeArr = mergeArray(all, eventTypeCodeArr);
        createComponentRadioButton(allComponentTypeArr, allComponentTypeCodeArr, frgComponentType);
    }

    private void initEventType(String code) {
        frgEventType.removeAllViews();
        if ("全部".equals(code)) {
            ll_eventtype.setVisibility(View.GONE);
            return;
        }
        ll_eventtype.setVisibility(View.VISIBLE);
        allEventTypeArr = new String[0];
        String[] all = {"全部"};
        List<DictionaryItem> dictionaryItemList = new TableDBService().getChildDictionaryByPCodeInDB(code);
        sortDictionaryList(dictionaryItemList);
        String[] eventTypeNameArr = new String[dictionaryItemList.size()];
        String[] eventTypeCodeArr = new String[dictionaryItemList.size()];
        for (int i = 0; i < dictionaryItemList.size(); i++) {
            DictionaryItem dictionaryItem = dictionaryItemList.get(i);
            eventTypeNameArr[i] = dictionaryItem.getName();
            eventTypeCodeArr[i] = dictionaryItem.getCode();
        }
        allEventTypeArr = mergeArray(all, eventTypeNameArr);
        String[] allEventTypeCodeArr = mergeArray(all, eventTypeCodeArr);
        createRadioButton(allEventTypeArr, allEventTypeCodeArr, frgEventType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event_clear:

                if (allDistricts != null && allDistricts.length > 0) {
                    frgArea.reset();
                }

                if (allComponentTypeArr != null && allComponentTypeArr.length > 0) {
                    frgComponentType.reset();
                }

//                if (allEventTypeArr != null && allEventTypeArr.length > 0) {
//                    frgEventType.check(allEventTypeArr[0]);
//                }

                et_psdy_name.setText("");

                break;
            case R.id.btn_event_submit:
//                drawer_layout.closeDrawers();

                distrct = "";
                componentType = "";

                Map<String, Object> selectedItems1 = frgArea.getSelectedItems();
                if (MapUtils.isEmpty(selectedItems1)) {
                    distrct = "全部";
                } else {
                    Set<String> strings = selectedItems1.keySet();
                    String keys = "";
                    for (String key : strings) {
                        if (key.equals("全部")) {
                            distrct = "全部";
                            break;
                        } else {
                            keys += "," + key + "区";
                        }
                    }
                    if (!"全部".equals(distrct)) {
                        distrct = keys.substring(1);
                    }
                }

                RadioButton rbFacilityType = (RadioButton) mContainer.findViewById(frg_facility_type.getCheckedRadioButtonId());
                if (rbFacilityType != null) {
                    facilityType = rbFacilityType.getTag().toString();
                } else {
                    facilityType = "全部";
                }

                Map<String, Object> selectedItems = frgComponentType.getSelectedItems();
                if (MapUtils.isEmpty(selectedItems)) {
                    componentType = "全部";
                } else {
                    Set<String> strings = selectedItems.keySet();
                    String keys = "";
                    for (Map.Entry<String, Object> entry : selectedItems.entrySet()) {
                        DictionaryItem item = (DictionaryItem) entry.getValue();
                        if (item.getCode().equals("全部")) {
                            componentType = "全部";
                            break;
                        } else {
                            keys += "," + item.getCode();
                        }
                    }

                    if (!"全部".equals(componentType)) {
                        componentType = keys.substring(1);
                    }
                }


//                RadioButton rbEvent = (RadioButton) mContainer.findViewById(frgEventType.getCheckedRadioButtonId());
//                if (rbEvent != null) {
//                    eventType = rbEvent.getTag().toString();
//                } else {
//                    eventType = "全部";
//                }

                String psdyName = et_psdy_name.getText().toString();

                EventAffairConditionEvent eventAffairConditionEvent = new EventAffairConditionEvent(
                        "全部".equals(distrct) ? null : distrct, null, "全部".equals(componentType) ? null : componentType);
                eventAffairConditionEvent.sslx = "全部".equals(facilityType) ? null : facilityType;
                eventAffairConditionEvent.psdyName = psdyName;
                EventBus.getDefault().post(eventAffairConditionEvent);
                break;
            case R.id.tv_distrct:
                startAnim(frgArea, (TextView) v);
                break;
            case R.id.tv_facility_type:
//                startAnim(frg_facility_type, (TextView) v);
                break;
            case R.id.tv_component_type:
                startAnim(frgComponentType, (TextView) v);
                break;
            case R.id.tv_event_type:
                startAnim(frgEventType, (TextView) v);
                break;
            case R.id.tv_psdy_name:
                startAnim(et_psdy_name, (TextView) v);
                break;
            default:
                break;
        }
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
     * 重新设置isCollapse值，保存当前动画状态
     * 启动动画
     *
     * @param group
     */
    private void startAnim(View group, TextView view) {
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
        List<DictionaryItem> itemList = new ArrayList<>();
        for (DictionaryItem item : dictionaryItemList) {
            if (item.getValue().equals("0")) {
                continue;
            }
            itemList.add(item);
        }
        Collections.sort(itemList, new Comparator<DictionaryItem>() {
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
