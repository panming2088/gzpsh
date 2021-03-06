package com.augurit.agmobile.gzpssb.pshpublicaffair;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FlexRadioGroup;
import com.augurit.agmobile.gzps.publicaffair.service.FacilityAffairService;
import com.augurit.agmobile.gzps.publicaffair.view.condition.EventAffairConditionEvent;
import com.augurit.agmobile.gzps.publicaffair.view.condition.FacilityAffairFilterConditionEvent;
import com.augurit.agmobile.gzpssb.LoginRoleConstant;
import com.augurit.agmobile.gzpssb.LoginRoleManager;
import com.augurit.agmobile.gzpssb.common.MultiSelectFlexLayout2;
import com.augurit.agmobile.gzpssb.pshpublicaffair.adapter.PublicAffairPagerAdapter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.EventAffairConditionView;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PSHAffairFilterConditionEvent;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PSHEventAffairConditionView;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PshWtsbAffairConditionView;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PublicAffairSelectState;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.yviewpager.YViewPager;
import com.augurit.agmobile.gzpssb.pshstatistics.view.CustomPopupView;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.agmobile.patrolcore.selectlocation.view.IDrawerController;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.utils.TimeUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.android.flexbox.FlexboxLayout;

import org.apache.commons.collections4.MapUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.patrolcore.selectlocation.view
 * @createTime ???????????? ???17/7/27
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/7/27
 * @modifyMemo ???????????????
 * <p>
 * * @modifyBy ????????? ???sdb
 * @modifyTime ???????????? ???18/3/15
 * @modifyMemo ???????????????
 */

public class PublicAffairActivity extends AppCompatActivity implements IDrawerController, View.OnClickListener, Animation.AnimationListener {

    private ViewGroup progress_linearlayout;
    private DrawerLayout drawer_layout;
    private ImageView iv_open_search;
    private View ll_facility_condition;
    private View ll_psh_patrol_condition;
    private View ll_event_condition;
    private View ll_psh_event_condition;
    private PublicAffairPagerAdapter adapter;
    private PopupWindow popupWindow;
    private FlexRadioGroup fbDistrict;
    private FlexRadioGroup fbUploadType, fbFacilityType, fbFacilityType1;
    private Animation animCollapse, animExpand;

    private SparseBooleanArray isCollapses; //????????????

    private Drawable dropUp, dropDown;

    private int currentAnimId;//???????????????????????????ID

    private boolean mProtectFromCheckedChange = false;


    /**
     * ????????????????????????????????????
     */
    private String facilityType = null;

    /**
     * ?????????????????????????????????????????????
     */
    private String uploadType = null;

    /**
     * ???????????????
     */
    private String district = null;

    private Long startDate = null;
    private Long pshStartDate = null;

    private Long endDate = null;
    private Long pshEndDate = null;

    private static final int START_DATE = 1;
    private static final int END_DATE = 2;


    //    private String[] nwDist = {"??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????"};
    private String[] psDist = {"??????", "????????????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "????????????"};
    public static String[] uploadTypeConditions = {"??????", "????????????", "????????????"};

    //    private String[] nwFacilityTypeConditions = {"??????", "???", "?????????"};
    private String[] psFacilityTypeConditions = {"??????", "??????", "?????????", "?????????"};

    private String[] pshDist = {"??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????"};
    private Calendar cal;
    private Button btn_end_date;
    private Long TempEndDate = null;
    private Long pshTempEndDate = null;
    private Button btn_start_date;
    private ViewGroup ll_date;

    private YViewPager mViewPager;

    private int curPos;//0??????  1?????????

    //    private Spinner spinner;
//    private String[] spinnerArray = new String[]{"????????????????????????", "??????????????????"};
    private TextView tv_new_title, tv_title;
    private String curLoginRole;
    private ImageView iv_arrow;
    private TextView tv_event_type, tv_component_type;
    private Button btn_psh_start_date;
    private Button btn_psh_psh_end_date;
    private MultiSelectFlexLayout2 frg_psh_event_distrct;
    private View ll_psh_wtsb_condition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaffair);
        EventBus.getDefault().register(this);
        findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        iv_open_search = ((ImageView) findViewById(R.id.iv_open_search));
        tv_new_title = ((TextView) findViewById(R.id.tv_new_title));
        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_title.setBackgroundColor(Color.TRANSPARENT);
        iv_arrow = ((ImageView) findViewById(R.id.iv_arrow));
        iv_open_search.setImageResource(R.mipmap.ic_filter);
        ((TextView) findViewById(R.id.tv_search)).setText("??????");
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(null);
            }
        });

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //   ((TextView) findViewById(R.id.tv_new_title)).setText("????????????");
        mViewPager = (YViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        adapter = new PublicAffairPagerAdapter(getSupportFragmentManager(),
                this);
        mViewPager.addOnPageChangeListener(new YViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPos = position;
                setFacilityAffairDrawerData(curPos);
                setPshFacilityAffairDrawerData(curPos);
                if (curPos == 0) {
                    setEventOrFacilityCondtion(curPsChiidPos);
                    EventAffairConditionView eventAffairConditionView = new EventAffairConditionView(ll_event_condition, curPsChiidPos, PublicAffairActivity.this);
                } else {
                    setEventOrFacilityCondtion(curNwChiidPos);
                    PSHEventAffairConditionView pshEventAffairConditionView = new PSHEventAffairConditionView(ll_psh_event_condition, PublicAffairActivity.this);
                    PshWtsbAffairConditionView wtsbAffairConditionView = new PshWtsbAffairConditionView(ll_psh_wtsb_condition, PublicAffairActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(adapter);
        mViewPager.scrollEnable(false);

//        //TabLayout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setVisibility(View.GONE);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //??????????????????????????????????????????????????????
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);

        progress_linearlayout = (ViewGroup) findViewById(R.id.progress_linearlayout);
        ll_facility_condition = findViewById(R.id.ll_facility_condition);
        ll_psh_patrol_condition = findViewById(R.id.ll_psh_patrol_condition);
        ll_event_condition = findViewById(R.id.ll_event_condition);
        ll_psh_event_condition = findViewById(R.id.ll_psh_event_condition);
        ll_psh_wtsb_condition = findViewById(R.id.ll_psh_wtsb_condition);
        ll_date = (ViewGroup) findViewById(R.id.ll_date);
        tv_event_type = (TextView) findViewById(R.id.tv_event_type);
        tv_component_type = (TextView) findViewById(R.id.tv_component_type);

        frg_psh_event_distrct = (MultiSelectFlexLayout2) findViewById(R.id.frg_psh_event_distrct1);
        fbDistrict = (FlexRadioGroup) findViewById(R.id.fbl_filter_price);
        fbUploadType = (FlexRadioGroup) findViewById(R.id.fbl_filter_type);
        fbFacilityType = (FlexRadioGroup) findViewById(R.id.fbl_filter_structure);

        // text = (TextView) findViewById(R.id.text);

        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_psh_event_clear1).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_psh_event_submit1).setOnClickListener(this);

        isCollapses = new SparseBooleanArray();


        findViewById(R.id.tv_price).setOnClickListener(this);
        findViewById(R.id.tv_type).setOnClickListener(this);
        findViewById(R.id.tv_structure).setOnClickListener(this);
        findViewById(R.id.tv_date).setOnClickListener(this);

        animExpand = AnimationUtils.loadAnimation(this, R.anim.expand);
        animExpand.setAnimationListener(this);
        animExpand.setFillAfter(true);

        animCollapse = AnimationUtils.loadAnimation(this, R.anim.collapse);
        animCollapse.setAnimationListener(this);
        animCollapse.setFillAfter(true);

        /***
         * ????????????
         */
        btn_start_date = (Button) findViewById(R.id.btn_start_date);
        btn_psh_start_date = (Button) findViewById(R.id.btn_psh_start_date1);
        initStartDate();

        btn_end_date = (Button) findViewById(R.id.btn_end_date);
        btn_psh_psh_end_date = (Button) findViewById(R.id.btn_psh_psh_end_date1);
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

        btn_psh_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pshStartDate == null) {
                    showDatePickerDialog(btn_psh_start_date, cal, START_DATE);
                } else {
                    cal.setTimeInMillis(pshStartDate);
                    showPshDatePickerDialog(btn_psh_start_date, cal, START_DATE);
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

        btn_psh_psh_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pshTempEndDate == null) {
                    showDatePickerDialog(btn_psh_psh_end_date, cal, END_DATE);
                } else {
                    cal.setTimeInMillis(pshTempEndDate);
                    showPshDatePickerDialog(btn_psh_psh_end_date, cal, END_DATE);
                }
            }
        });

        EventAffairConditionView eventAffairConditionView = new EventAffairConditionView(ll_event_condition, curPsChiidPos, this);
        curLoginRole = LoginRoleManager.getCurLoginrRole();

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO ??????????????????????????????
                if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER)
                        || curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)) {
                    showPopupwindow(view);
                }
            }
        });

        tv_new_title.setText("????????????????????????");

        if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER)) {
            iv_arrow.setVisibility(View.VISIBLE);
            mViewPager.setCurrentItem(1);//????????????????????????
            tv_new_title.setText("???????????????????????????");
            setFacilityAffairDrawerData(1);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS)) {
            iv_arrow.setVisibility(View.GONE);
            tv_new_title.setText("????????????????????????");
            mViewPager.setCurrentItem(0);
            setFacilityAffairDrawerData(0);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_WS)) {//?????????????????????
            iv_arrow.setVisibility(View.GONE);
            mViewPager.setCurrentItem(1);//????????????????????????
            tv_new_title.setText("???????????????????????????");
            setFacilityAffairDrawerData(1);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH)) {
            iv_arrow.setVisibility(View.GONE);
            mViewPager.setCurrentItem(1);//????????????????????????
            tv_new_title.setText("???????????????????????????");
            setFacilityAffairDrawerData(1);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {//?????????????????????
            iv_arrow.setVisibility(View.GONE);
            mViewPager.setCurrentItem(1);//????????????????????????
            tv_new_title.setText("???????????????????????????");
            setFacilityAffairDrawerData(1);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)) {
            iv_arrow.setVisibility(View.VISIBLE);
            mViewPager.setCurrentItem(1);//????????????????????????
            tv_new_title.setText("???????????????????????????");
            setFacilityAffairDrawerData(1);
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS_WS)) {//??????????????????
            iv_arrow.setVisibility(View.GONE);
            tv_new_title.setText("????????????????????????");
            mViewPager.setCurrentItem(0);
            setFacilityAffairDrawerData(0);
        } else {
            mViewPager.setCurrentItem(0);
            setFacilityAffairDrawerData(0);
        }


    }

    private void showPopupwindow(View view) {
        final List<String> items = new ArrayList<>();
        items.add("????????????????????????");
        items.add("???????????????????????????");
        final CustomPopupView popupWindow = new CustomPopupView(PublicAffairActivity.this, items);
        popupWindow.setBackground(R.drawable.rect_circle_white_solid);
        popupWindow.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                tv_new_title.setText(position == 0 ? "????????????????????????" : "???????????????????????????");
                mViewPager.setCurrentItem(position);
            }
        });
        //????????????????????? ???????????????????????????
        popupWindow.show(view, 1);
    }


    private void setFacilityAffairDrawerData(int curPos) {
        //TODO ??????????????????
        FacilityAffairService facilityAffairService = new FacilityAffairService(this.getApplicationContext());
        boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
        if (b) {
//            createRadioButton(curPos == 0 ? psDist : nwDist, fbDistrict);
            createRadioButton(psDist, fbDistrict);
        } else {
            String[] district = new String[]{facilityAffairService.getParentOrgOfCurrentUser()};
            createRadioButton(district, fbDistrict);
        }

        createRadioButton(uploadTypeConditions, fbUploadType);
//        createRadioButton(curPos == 0 ? psFacilityTypeConditions : nwFacilityTypeConditions, fbFacilityType);
        createRadioButton(psFacilityTypeConditions, fbFacilityType);

    }

    private void setPshFacilityAffairDrawerData(int curPos) {
        //TODO ??????????????????
        FacilityAffairService facilityAffairService = new FacilityAffairService(this.getApplicationContext());
        boolean b = facilityAffairService.ifCurrentUserBelongToCityUser();
        if (b) {
//            createRadioButton(curPos == 0 ? psDist : nwDist, fbDistrict);
            createRadioButton(pshDist, frg_psh_event_distrct);
        } else {
            String[] district = new String[]{facilityAffairService.getParentOrgOfCurrentUser()};
            createRadioButton(district, frg_psh_event_distrct);
        }
    }

    private void initEndDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);

//        btn_end_date.setText(year + "-" + month + "-" + day);
//        btn_psh_psh_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        pshEndDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
        pshTempEndDate = new Date(year - 1900, month - 1, day).getTime();
        btn_end_date.setText(TimeUtil.getStringTimeYMD(new Date(TempEndDate)));
        btn_psh_psh_end_date.setText(TimeUtil.getStringTimeYMD(new Date(pshTempEndDate)));
    }

    private void initStartDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        startDate = getCurrentQuarterStartTime();
        pshStartDate = getCurrentQuarterStartTime();
        btn_start_date.setText(TimeUtil.getStringTimeYMD(new Date(startDate)));
        btn_psh_start_date.setText(TimeUtil.getStringTimeYMD(new Date(pshStartDate)));
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    public static Long getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Long date = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            int year = c.get(Calendar.YEAR);       //????????????????????????
            int month = c.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
            int day = c.get(Calendar.DAY_OF_MONTH);
            date = new Date(year - 1900, month - 1, day).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public void showDatePickerDialog(final Button btn, Calendar calendar, final int type) {
        // Calendar ?????????????????????
        // Calendar calendar = Calendar.getInstance();
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(this,
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

    public void showPshDatePickerDialog(final Button btn, Calendar calendar, final int type) {
        // Calendar ?????????????????????
        // Calendar calendar = Calendar.getInstance();
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(this,
                // ???????????????(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//                        display(btn, year, monthOfYear, dayOfMonth);
                        if (type == START_DATE) {
                            pshStartDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                            btn.setText(TimeUtil.getStringTimeYMD(new Date(pshStartDate)));
                        } else {
                            pshEndDate = new Date(year - 1900, monthOfYear, dayOfMonth + 1).getTime();
                            pshTempEndDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                            btn.setText(TimeUtil.getStringTimeYMD(new Date(pshTempEndDate)));
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


    private void createRadioButton(String[] filters, final FlexRadioGroup group) {

        group.removeAllViews();

        /**
         *  64dp???????????????{@link DrawerLayout#MIN_DRAWER_MARGIN}+10dp*2??????????????????padding=84dp
         */
        float margin = DensityUtils.dp2px(this, 60);
        float width = DensityUtils.getWidth(this);
        for (String filter : filters) {
            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.item_radiobutton, null);
            rb.setText(filter);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lp);
            group.addView(rb);
            if (filter.equals("??????") || filters.length == 1) {
                rb.setChecked(true);
            }

            /**
             * ?????????????????????????????????????????????????????????RadioButton?????????
             * ??????RadioButton??????{@link FlexRadioGroup#OnCheckedChangeListener}???????????????????????????{@link View#OnClickListener}
             * ???????????????RadioButton?????????????????????????????????OnCheckedChangeListener????????????????????????????????????????????????RadioButton?????????????????????????????????
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
                        // group.clearCheck();
                    } else {
                        mProtectFromCheckedChange = false;
                    }
                }
            });
        }
        isCollapses.put(group.getId(), false);
    }

    private void createRadioButton(String[] filters, final MultiSelectFlexLayout2 group) {

        Map<String, DictionaryItem> items = new LinkedHashMap<String, DictionaryItem>();
        Map<String, DictionaryItem> selectedItem = new HashMap<String, DictionaryItem>(2);
        for (int i = 0; i < filters.length; i++) {
            DictionaryItem item = new DictionaryItem();
            String key = filters[i];
            item.setName(key);
            items.put(key, item);
            if (key.equals("??????")) {
                selectedItem.put(key, item);
            } else if (filters.length == 1) {
                selectedItem.put(key, item);
            }

        }
        group.addItemsByDictionaryItem("", "key", items, selectedItem);
    }

    @Override
    public void openDrawer(final OnDrawerOpenListener listener) {
        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.END);    //????????????
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (listener != null) {
                    listener.onOpened(drawerView);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //??????????????????????????????????????????????????????
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void closeDrawer() {

    }

    @Override
    public ViewGroup getDrawerContainer() {
        return progress_linearlayout;
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsUtil2.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
//                fbDistrict.check(curPos == 0 ? psDist[0] : nwDist[0]);
                fbDistrict.check(psDist[0]);
                fbUploadType.check(uploadTypeConditions[0]);
//                fbFacilityType.check(curPos == 0 ? psFacilityTypeConditions[0] : nwFacilityTypeConditions[0]);
                fbFacilityType.check(psFacilityTypeConditions[0]);
                resetDate();
                break;
            case R.id.btn_psh_event_clear1:
//                fbDistrict.check(curPos == 0 ? psDist[0] : nwDist[0]);
                frg_psh_event_distrct.reset();
                resetPshDate();
                break;
            case R.id.btn_psh_event_submit1:
                if (pshStartDate > pshTempEndDate) {
                    ToastUtil.iconLongToast(this.getApplicationContext(), R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                    return;
                }

                drawer_layout.closeDrawers();

                district = "";

                Map<String, Object> selectedItems1 = frg_psh_event_distrct.getSelectedItems();
                if (MapUtils.isEmpty(selectedItems1)) {
                    district = "??????";
                } else {
                    Set<String> strings = selectedItems1.keySet();
                    String keys = "";
                    for (String key : strings) {
                        if (key.equals("??????")) {
                            district = "??????";
                            break;
                        } else {
                            keys += "," + key;
                        }
                    }
                    if (!"??????".equals(district)) {
                        district = keys.substring(1);
                    }
                }
                if (curPos == 1) {
                    FacilityAffairFilterConditionEvent facilityAffairFilterConditionEvent =
                            new FacilityAffairFilterConditionEvent(district, uploadType, facilityType, pshStartDate, pshEndDate);
                    EventBus.getDefault().post(facilityAffairFilterConditionEvent);
                }
                break;
            case R.id.btn_submit:


                if (pshStartDate > pshTempEndDate) {
                    ToastUtil.iconLongToast(this.getApplicationContext(), R.mipmap.ic_alert_yellow, "????????????????????????????????????");
                    return;
                }

                drawer_layout.closeDrawers();

                RadioButton rbPrice = (RadioButton) findViewById(fbDistrict.getCheckedRadioButtonId());
                if (rbPrice != null) {
                    district = rbPrice.getText().toString();
                }

                RadioButton rbType = (RadioButton) findViewById(fbUploadType.getCheckedRadioButtonId());
                if (rbType != null) {
                    uploadType = rbType.getText().toString();
                }

                RadioButton rbStructure = (RadioButton) findViewById(fbFacilityType.getCheckedRadioButtonId());
                if (rbStructure != null) {
                    facilityType = rbStructure.getText().toString();
                }

                if (curPos == 0) {
                    FacilityAffairFilterConditionEvent facilityAffairFilterConditionEvent =
                            new FacilityAffairFilterConditionEvent(district, uploadType, facilityType, startDate, endDate);
                    EventBus.getDefault().post(facilityAffairFilterConditionEvent);
                }
                break;
            case R.id.tv_type:
                startAnim(fbUploadType, (TextView) v);
                break;
            case R.id.tv_price:
                startAnim(fbDistrict, (TextView) v);
                break;
            case R.id.tv_structure:
                startAnim(fbFacilityType, (TextView) v);
                break;
            case R.id.tv_date:
                startAnim(ll_date, (TextView) v);
                break;
            default:
                break;
        }
    }


    /**
     * ????????????
     */
    private void setArrow(TextView view, boolean isCollapse) {
        if (!isCollapse) {
            if (dropUp == null) {
                dropUp = getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                dropUp.setBounds(0, 0, dropUp.getMinimumWidth(), dropUp.getMinimumHeight());
            }
            view.setCompoundDrawables(null, null, dropUp, null);
        } else {
            if (dropDown == null) {
                dropDown = getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                dropDown.setBounds(0, 0, dropDown.getMinimumWidth(), dropDown.getMinimumHeight());
            }
            view.setCompoundDrawables(null, null, dropDown, null);
        }


    }

    /**
     * ????????????isCollapse??????????????????????????????
     * ????????????
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
     * ????????????isCollapse??????????????????????????????
     * ????????????
     *
     * @param group
     */
    private void startAnim(ViewGroup group, TextView view) {
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
     * ????????????
     */
    public void resetDate() {
        setStateDate();
        setCurrentDate();
    }

    /**
     * ????????????
     */
    public void resetPshDate() {
        setPshStateDate();
        setPshCurrentDate();
    }

    @SuppressLint("WrongConstant")
    public void setStateDate() {
        cal = Calendar.getInstance();
        cal.add(Calendar.MONDAY, -2);   //??????????????????
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);
        startDate = new Date(year - 1900, month - 1, day).getTime();
        btn_start_date.setText(year + "-" + month + "-" + day);
    }

    @SuppressLint("WrongConstant")
    public void setPshStateDate() {
        cal = Calendar.getInstance();
        cal.add(Calendar.MONDAY, -2);   //??????????????????
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);
        pshStartDate = new Date(year - 1900, month - 1, day).getTime();
        btn_psh_start_date.setText(year + "-" + 1 + "-" + 1);
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

    public void setPshCurrentDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
        int day = cal.get(Calendar.DAY_OF_MONTH);
        btn_psh_psh_end_date.setText(year + "-" + month + "-" + day);
        pshEndDate = new Date(year - 1900, month - 1, day + 1).getTime();
        pshTempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onAnimationStart(Animation animation) {
        if (!isCollapses.get(currentAnimId)) {
            findViewById(currentAnimId).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (isCollapses.get(currentAnimId)) {
            findViewById(currentAnimId).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Subscribe
    public void onEventConditionChanged(EventAffairConditionEvent eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
    }

    @Subscribe
    public void onEventConditionChanged(PublicAffairActivity eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
    }


    @Subscribe
    public void onPSHEventConditionChanged(PSHAffairFilterConditionEvent eventAffairConditionEvent) {
        drawer_layout.closeDrawers();
    }

    private int curPsChiidPos, curNwChiidPos;

    @Subscribe
    public void onChildFragmentSelectChanged(PublicAffairSelectState selectState) {

        setEventOrFacilityCondtion(selectState.getCurPos());

        if (selectState.getCurSelect() == 0) {//????????????????????????????????????????????????????????????
            curPsChiidPos = selectState.getCurPos();
            EventAffairConditionView eventAffairConditionView = new EventAffairConditionView(ll_event_condition, curPsChiidPos, PublicAffairActivity.this);
        } else {//?????? ??????
//            curNwChiidPos = selectState.getCurPos();
//            NWEventAffairConditionView eventAffairConditionView = new NWEventAffairConditionView(ll_event_condition, curNwChiidPos,PublicAffairActivity.this);
        }

    }

    //??????Drawer?????????????????????????????????????????????????????????
    private void setEventOrFacilityCondtion(int curChildPos) {
        if (curPos == 0) {//??????

            if (curChildPos == 0) {
                ll_facility_condition.setVisibility(View.GONE);
                ll_event_condition.setVisibility(View.VISIBLE);
                ll_psh_event_condition.setVisibility(View.GONE);
                ll_psh_wtsb_condition.setVisibility(View.GONE);
                ll_psh_patrol_condition.setVisibility(View.GONE);
//                    iv_open_search.setVisibility(View.GONE);
            } else {
                ll_facility_condition.setVisibility(View.VISIBLE);
                ll_event_condition.setVisibility(View.GONE);
                ll_psh_event_condition.setVisibility(View.GONE);
                ll_psh_wtsb_condition.setVisibility(View.GONE);
                ll_psh_patrol_condition.setVisibility(View.GONE);
//                    iv_open_search.setVisibility(View.VISIBLE);
            }
        } else {//?????????
            if (curChildPos == 0) {
                ll_facility_condition.setVisibility(View.GONE);
                ll_event_condition.setVisibility(View.GONE);
                ll_psh_event_condition.setVisibility(View.GONE);
                ll_psh_wtsb_condition.setVisibility(View.VISIBLE);
                ll_psh_patrol_condition.setVisibility(View.GONE);
//                    iv_open_search.setVisibility(View.GONE);
            } else if (curChildPos == 1) {
                ll_facility_condition.setVisibility(View.GONE);
                ll_event_condition.setVisibility(View.GONE);
                ll_psh_wtsb_condition.setVisibility(View.GONE);
                ll_psh_event_condition.setVisibility(View.VISIBLE);
                ll_psh_patrol_condition.setVisibility(View.GONE);
//                    iv_open_search.setVisibility(View.VISIBLE);
            } else {
                ll_facility_condition.setVisibility(View.GONE);
                ll_psh_wtsb_condition.setVisibility(View.GONE);
                ll_event_condition.setVisibility(View.GONE);
                ll_psh_event_condition.setVisibility(View.GONE);
                ll_psh_patrol_condition.setVisibility(View.VISIBLE);
            }
        }
    }


    private void initPopupWindow() {
        popupWindow = new PopupWindow(this);
    }
}
