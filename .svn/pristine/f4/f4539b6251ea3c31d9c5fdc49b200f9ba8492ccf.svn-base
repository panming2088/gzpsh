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
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.publicaffair.view.condition.EventAffairConditionEvent;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liangsh on 2017/11/20.
 */

public class EventAffairConditionView implements View.OnClickListener, Animation.AnimationListener {

    private View mContainer;
    private Context mContext;

    private FlexRadioGroup frgArea;
    private FlexRadioGroup frgComponentType;
    private View ll_eventtype;
    private FlexRadioGroup frgEventType;

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
    private int curPsChildPos=1;

    public EventAffairConditionView(View container, int curPsChildPos, Context context) {
        this.mContainer = container;
        this.mContext = context;
        this.curPsChildPos = curPsChildPos;
        initView();
    }

    public EventAffairConditionView(View container, Context context) {
        this.mContainer = container;
        this.mContext = context;
        initView();
    }


    private void initView() {
        frgArea = (FlexRadioGroup) mContainer.findViewById(R.id.frg_event_distrct);
        frgComponentType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_component_type);
        ll_eventtype = mContainer.findViewById(R.id.ll_eventtype);
        frgEventType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_event_type);
        // text = (TextView) findViewById(R.id.text);

        mContainer.findViewById(R.id.btn_event_clear).setOnClickListener(this);
        mContainer.findViewById(R.id.btn_event_submit).setOnClickListener(this);

        isCollapses = new SparseBooleanArray();
        String[] all = {"全部"};
        FacilityAffairService facilityAffairService = new FacilityAffairService(mContext.getApplicationContext());
        boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
        if (b || curPsChildPos == 0) {
            allDistricts = mergeArray(all, GzpsConstant.districtsSimple1);
            createRadioButton(allDistricts, allDistricts, frgArea);
        } else {
            String[] district = new String[]{facilityAffairService.getParentOrgOfCurrentUser()};
            createRadioButton(district, district, frgArea);
        }

        List<DictionaryItem> component_type_list = new TableDBService().getDictionaryByTypecodeInDB("A174");
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
        createComponentRadioButton(allComponentTypeArr, allComponentTypeCodeArr, frgComponentType);

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
    }

    private void createRadioButton(String[] keys, Object[] values, final FlexRadioGroup group) {
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
                        mProtectFromCheckedChange = false;
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    private void createComponentRadioButton(String[] keys, Object[] values, final FlexRadioGroup group) {
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
                        String code = radioButton.getTag().toString();
                        initEventType(code);
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
                    frgArea.check(allDistricts[0]);
                }

                if (allComponentTypeArr != null && allComponentTypeArr.length > 0) {
                    frgComponentType.check(allComponentTypeArr[0]);
                }

                if (allEventTypeArr != null && allEventTypeArr.length > 0) {
                    frgEventType.check(allEventTypeArr[0]);
                }

                break;
            case R.id.btn_event_submit:
//                drawer_layout.closeDrawers();

                RadioButton rbDistrict = (RadioButton) mContainer.findViewById(frgArea.getCheckedRadioButtonId());
                if (rbDistrict != null) {
                    distrct = rbDistrict.getText().toString();
                } else {
                    distrct = "全部";
                }

                RadioButton rbComponentType = (RadioButton) mContainer.findViewById(frgComponentType.getCheckedRadioButtonId());
                if (rbComponentType != null) {
                    componentType = rbComponentType.getTag().toString();
                } else {
                    componentType = "全部";
                }

                RadioButton rbEvent = (RadioButton) mContainer.findViewById(frgEventType.getCheckedRadioButtonId());
                if (rbEvent != null) {
                    eventType = rbEvent.getTag().toString();
                } else {
                    eventType = "全部";
                }

                EventAffairConditionEvent eventAffairConditionEvent = new EventAffairConditionEvent(
                        "全部".equals(distrct) ? null : distrct, "全部".equals(componentType) ? null : componentType, "全部".equals(eventType) ? null : eventType);
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
