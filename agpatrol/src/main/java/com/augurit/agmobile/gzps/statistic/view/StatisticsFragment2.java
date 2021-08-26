package com.augurit.agmobile.gzps.statistic.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.LoginRoleConstant;
import com.augurit.agmobile.gzpssb.LoginRoleManager;
import com.augurit.agmobile.gzpssb.pshstatistics.event.LoadStatisticsEvent;
import com.augurit.agmobile.gzpssb.pshstatistics.view.CustomPopupView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangsh on 2017/11/6.
 * update 2018-04-11 sdb
 */

public class StatisticsFragment2 extends Fragment {
    private ViewPager viewPager;
    StatisticsFragmentPagerAdapter adapter;
    private ImageView iv_arrow;
    private String curLoginRole;
    private TextView tv_cur_select;
    private List<String> items = new ArrayList<>();//选择排水 农污 排水户 的选项tab
    private int curSelect;

    public final static int LOAD_TASK_PSH = 0;
    public final static int LOAD_APP_INSTALL_PSH = 2;
    public final static int LOAD_UPLOAD_PSH = 1;
    public final static int LOAD_SIGNINSTATS_PSH = 3;
    public final static int LOAD_SIGNINSTATS_PS = 5;
    public final static int LOAD_APP_INSTALL_PS = 6;

    public final static int FIRST_LOAD_APP_INSTALL_PSH = 7;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics2, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.ll_back).setVisibility(View.GONE);
        ((TextView) getView().findViewById(R.id.tv_title)).setText("统计");
        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);

        iv_arrow = ((ImageView) view.findViewById(R.id.iv_arrow));

        curLoginRole = LoginRoleManager.getCurLoginrRole();
//        spinner = (Spinner)view.findViewById(R.id.sp_top);
//        spinner.setVisibility(View.VISIBLE);
        tv_cur_select = (TextView) view.findViewById(R.id.tv_cur_select);
        tv_cur_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER) || curLoginRole.equals(LoginRoleConstant.LOGIN_PS) ||
                        curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS) || curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {
                    showPopupWindow(view);
                }

            }
        });

        //设置按角色权限展示的界面
        setAuthorityView();

        viewPager.setOffscreenPageLimit(4);
        adapter = new StatisticsFragmentPagerAdapter(getChildFragmentManager(),
                getActivity(),3,true);
        viewPager.setAdapter(adapter);

        //TabLayout
        final TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
//                    setIndicator(getContext(), tabLayout, 65, 65);
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       //TabLayout的监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(viewPager.getOffscreenPageLimit()==4){
                    if(position== LOAD_UPLOAD_PSH || position == LOAD_APP_INSTALL_PSH || position == LOAD_SIGNINSTATS_PSH || position == LOAD_TASK_PSH){
                        EventBus.getDefault().post(new LoadStatisticsEvent(position));
                    }
                }else if(viewPager.getOffscreenPageLimit()==3){
                    if(position == 0){
                        EventBus.getDefault().post(new LoadStatisticsEvent(LOAD_APP_INSTALL_PS));
                    }else if(position== 2){
                        //排水巡检签到统计
                        EventBus.getDefault().post(new LoadStatisticsEvent(LOAD_SIGNINSTATS_PS));
                    }
                }
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void setAuthorityView() {
        if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER) || curLoginRole.equals(LoginRoleConstant.LOGIN_PS_WS) ||
                curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS) || curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {
            iv_arrow.setVisibility(View.VISIBLE);
        } else {
            iv_arrow.setVisibility(View.INVISIBLE);
        }
        if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER)) {
            curSelect = 3;
            items.add("管网巡检");
            items.add("农污");
            items.add("排水户摸查");
            tv_cur_select.setText("排水户摸查");

        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)) {
            curSelect = 3;
            items.add("管网巡检");
            items.add("排水户摸查");
            tv_cur_select.setText("排水户摸查");
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {
            curSelect = 3;
            items.add("农污");
            items.add("排水户摸查");
            tv_cur_select.setText("排水户摸查");
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS_WS)) {
            curSelect = 1;
            items.add("管网巡检");
            items.add("农污");
            tv_cur_select.setText("管网巡检");
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS)) {
            curSelect = 1;
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_WS)) {
            curSelect = 2;
        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH)) {
            curSelect = 3;
        }


    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if ((isVisibleToUser && isResumed())) {
//            onResume();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getUserVisibleHint() && (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH)
//                || curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER) || curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)
//                || curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS))//避免viewpager没加载完成就发送Event
//                && TextUtils.isEmpty(tv_cur_select.getText().toString())) {
//            EventBus.getDefault().post(3);//0全部统计 1排水统计 2污水统计 3排水户
//            tv_cur_select.setText("排水户");
//        }
//    }


    private void showPopupWindow(View view) {


        final CustomPopupView popupWindow = new CustomPopupView(getActivity(), items);
        popupWindow.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();

                if (items.get(position).equals("管网巡检")) {
                    EventBus.getDefault().post(1);//0全部统计 1排水统计 2污水统计 3排水户统计
                    tv_cur_select.setText("管网巡检");
                    adapter = new StatisticsFragmentPagerAdapter(getChildFragmentManager(),
                            getActivity(),1, false);
                    viewPager.setOffscreenPageLimit(3);
                    viewPager.setAdapter(adapter);
                } else if (items.get(position).equals("农污")) {
                    EventBus.getDefault().post(2);//0全部统计 1排水统计 2污水统计 3排水户统计
                    tv_cur_select.setText("农污");
                } else if (items.get(position).equals("排水户摸查")) {
                    adapter = new StatisticsFragmentPagerAdapter(getChildFragmentManager(),
                            getActivity(),3, true);
                    viewPager.setOffscreenPageLimit(4);
                    viewPager.setAdapter(adapter);
                    EventBus.getDefault().post(3);//0全部统计 1排水统计 2污水统计 3排水户统计
                    tv_cur_select.setText("排水户摸查");
                }
            }
        });
        //根据后面的数字 手动调节窗口的宽度
        popupWindow.show(view, 3);
    }


}
