package com.augurit.agmobile.gzpssb.fire;

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
import com.augurit.agmobile.gzpssb.uploadfacility.view.condiction.UploadFilterConditionEvent;
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

public class FireListFilterConditionView implements View.OnClickListener, Animation.AnimationListener {

    private View mContainer;
    private Context mContext;

    private FlexRadioGroup frgArea;
    private FlexRadioGroup frgsfwhType;
    private FlexRadioGroup frgsflsType;
    private FlexRadioGroup frgszwzType;

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
    //是否完好
    private String sfwh = null;
    //是否漏水
    private String sfls = null;
    //所在位置
    private String szwz = null;


    private Long startDate = null;

    private Long endDate = null;
    private String[] allDistricts;
    private String[] allsfwh;
    private String[] allsfls;
    private String[] allszwz;
    private int curNwChildPos;
    private Button btn_start_date, btn_end_date;
    private Calendar cal;
    private Long TempEndDate = null;
    //上报人名称，地址，上报编号
    private EditText et_org_name, et_address, et_uploadId;
    public FireListFilterConditionView(View container, int curNwChiidPos, Context context) {
        this.mContainer = container;
        this.mContext = context;
        this.curNwChildPos = curNwChiidPos;
        initView();
    }


    private void initView() {


        frgArea = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_event_distrct);
        frgsfwhType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_sfwh_type);
        frgsflsType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_sfls_type);
        frgszwzType = (FlexRadioGroup) mContainer.findViewById(R.id.frg_psh_szwz_type);
        et_org_name = (EditText) mContainer.findViewById(R.id.auto_org_name);
        et_address = (EditText) mContainer.findViewById(R.id.et_address);
        et_uploadId = (EditText) mContainer.findViewById(R.id.et_mark_id);
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
        if (b ) {
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
        String[] sswhArr = new String[]{"是", "否"};
        allsfwh = mergeArray(all, sswhArr);
        createRadioButton(allsfwh, frgsfwhType);

        String[] sslsArr = new String[]{"是", "否"};
        allsfls = mergeArray(all, sslsArr);
        createRadioButton(allsfls, frgsflsType);

        String[] szwzArr = new String[]{"公共区域", "排水单元内部"};
        allszwz = mergeArray(all, szwzArr);
        createRadioButton(allszwz, frgszwzType);

        mContainer.findViewById(R.id.tv_distrct).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_sfwh_event).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_sfls_event).setOnClickListener(this);
        mContainer.findViewById(R.id.tv_szwz_event).setOnClickListener(this);

        animExpand = AnimationUtils.loadAnimation(mContext, R.anim.expand);
        animExpand.setAnimationListener(this);
        animExpand.setFillAfter(true);

        animCollapse = AnimationUtils.loadAnimation(mContext, R.anim.collapse);
        animCollapse.setAnimationListener(this);
        animCollapse.setFillAfter(true);


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
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_psh_event_clear:

                if (allDistricts != null && allDistricts.length > 0) {
                    frgArea.check(allDistricts[0]);
                }

                if (allsfwh != null && allsfwh.length > 0) {
                    frgsfwhType.check(allsfwh[0]);
                }

                if (allsfls != null && allsfls.length > 0) {
                    frgsflsType.check(allsfls[0]);
                }
                if (allszwz != null && allszwz.length > 0) {
                    frgszwzType.check(allszwz[0]);
                }

                resetDate();
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

                RadioButton rbsfwh = (RadioButton) mContainer.findViewById(frgsfwhType.getCheckedRadioButtonId());
                if (rbsfwh != null) {
                    sfwh = rbsfwh.getText().toString();
                } else {
                    sfwh = "全部";
                }

                RadioButton rbsfls = (RadioButton) mContainer.findViewById(frgsflsType.getCheckedRadioButtonId());
                if (rbsfls != null) {
                    sfls = rbsfls.getText().toString();
                } else {
                    sfls = "全部";
                }
                RadioButton rbszwz = (RadioButton) mContainer.findViewById(frgszwzType.getCheckedRadioButtonId());
                if (rbszwz != null) {
                    szwz = rbszwz.getText().toString();
                } else {
                    szwz = "全部";
                }

                String address = et_address.getText().toString().trim();
                String orgname = et_org_name.getText().toString().trim();
                String uploadid = et_uploadId.getText().toString().trim();
                Long id = null;
                try {
                    id = Long.parseLong(TextUtils.isEmpty(uploadid) ? null : uploadid);
                } catch (Exception e) {

                }

                UploadFireFilterConditionEvent eventAffairConditionEvent = new UploadFireFilterConditionEvent(
                        "全部".equals(distrct) ? null : distrct,
                        "全部".equals(sfwh) ? null : sfwh,
                        "全部".equals(sfls) ? null : sfls,
                        "全部".equals(szwz) ? null : szwz,
                        address,
                        orgname,
                        startDate,
                        endDate,
                        id
                        );
                EventBus.getDefault().post(eventAffairConditionEvent);
                break;
            case R.id.tv_distrct:
                startAnim(frgArea, (TextView) v);
                break;
            case R.id.tv_sfwh_event:
                startAnim(frgsfwhType, (TextView) v);
                break;
            case R.id.tv_sfls_event:
                startAnim(frgsflsType, (TextView) v);
                break;
            case R.id.tv_szwz_event:
                startAnim(frgszwzType, (TextView) v);
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
