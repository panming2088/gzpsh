package com.augurit.agmobile.gzps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.billboard.BillboardActivity;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.common.model.BannerUrl;
import com.augurit.agmobile.gzps.common.model.Result2;
import com.augurit.agmobile.gzps.common.project.ProjectListActivity2;
import com.augurit.agmobile.gzps.common.service.GzpsService;
import com.augurit.agmobile.gzps.common.webview.WebViewActivity;
import com.augurit.agmobile.gzps.common.webview.WebViewConstant;
import com.augurit.agmobile.gzps.componentmaintenance.ComponentMaintenanceActivityBase;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.DrainageUnitMonitorActivity;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.JbjMonitorActivity;
import com.augurit.agmobile.gzps.drainage_unit_monitor.view.RiserPipeProblemActivity;
import com.augurit.agmobile.gzps.lawsandregulation.LawsAndRegulationsActivity;
import com.augurit.agmobile.gzps.newaddedcomponent.NewAddedComponentActivityBase;
import com.augurit.agmobile.gzps.uploadevent.view.uploadevent.EventUploadActivity;
import com.augurit.agmobile.gzps.uploadfacility.UploadFacilityMapActivity;
import com.augurit.agmobile.gzpssb.LoginRoleConstant;
import com.augurit.agmobile.gzpssb.LoginRoleManager;
import com.augurit.agmobile.gzpssb.activity.SewerageMapActivity;
import com.augurit.agmobile.gzpssb.common.PortSelectUtil;
import com.augurit.agmobile.gzpssb.draft.PSHLocalDraftActivity;
import com.augurit.agmobile.gzpssb.fire.PSHUploadFireMapActivity;
import com.augurit.agmobile.gzpssb.jbjpsdy.JbjPsdyMapActivity;
import com.augurit.agmobile.gzpssb.journal.view.DialyPatrolMapActivity;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.view.PSHUploadDoorNoMapActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.PublicAffairActivity;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventCount;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventlist.PSHEventListActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventlist.PshMyUploadEventListActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent.PSHEventUploadActivity;
import com.augurit.agmobile.gzpssb.uploadfacility.view.myuploadlist.SewerageMyUploadActivity;
import com.augurit.agmobile.gzpssb.uploadfacility.view.tranship.PSHUploadFacilityMapActivity;
import com.augurit.agmobile.gzpssb.worknews.CustomViewPager;
import com.augurit.agmobile.gzpssb.worknews.DynamicFragmentPagerAdapter;
import com.augurit.agmobile.gzpssb.worknews.TabLayoutIndicatorUtil;
import com.augurit.agmobile.patrolcore.common.table.TableViewManager;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.dao.remote.TableNetCallback;
import com.augurit.agmobile.patrolcore.common.table.model.Project;
import com.augurit.agmobile.patrolcore.search.view.SearchFragmentWithoutMap;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.net.util.NetworkUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agpatrol
 * @createTime 创建时间 ：17/10/12
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/10/12
 * @modifyMemo 修改备注：
 */

public class PatrolMainFragment2 extends Fragment implements View.OnClickListener {

    private Context mContext;

    private View ll_handling;
    private View ll_handled;
    private View ll_handled_finish;
    private View ll_handled_upload;
    private Badge badge_handling;
    private Badge badge_handled;
    private Badge badge_handled_finish;
    private Badge badge_handled_upload;
    //    private RecyclerView rv_work_news;
    private RollPagerView mRollViewPager;
    //    private ProgressRelativeLayout pb_loading;
//    private WorkNewsAdapter workNewsAdapter;
//    private LinearLayout ll_news_container;
    private ArrayList<Integer> views = new ArrayList<>();
    private QBadgeView recent_news_qBadgeView;
    private QBadgeView notifications_qBadgeView;
    private QBadgeView experiment_share_qBadgeView;
    private QBadgeView laws_and_regulations_qBadgeView;
    private QBadgeView billboard_qBadgeView;
    private QBadgeView operate_qBadgeView;
    private ConvenientBanner convenientBanner;

    private TabLayout tabLayout;
    private View mainView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patrol_main2, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //        view.findViewById(R.id.iv_add_component).setOnClickListener(new View.OnClickListener() { //新增部件
        //            @Override
        //            public void onClick(View v) {//新增部件
        //                startActivity(new Intent(getActivity(), NewAddedComponentActivityBase.class));
        //            }
        //        });
        //
        //        view.findViewById(R.id.iv_correct_data).setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {  //部件维护
        //                startActivity(new Intent(getActivity(), ComponentMaintenanceActivityBase.class)); //部件维护
        //
        //            }
        //        });
        //   if (!EventBus.getDefault().isRegistered(this)) {
        ////            EventBus.getDefault().register(this);
        ////        }

//

        convenientBanner = (ConvenientBanner) view.findViewById(R.id
                .convenientBanner);
        initBanner();
        //待受理
        ll_handling = view.findViewById(R.id.ll_handling);
        ll_handling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PSHEventListActivity.class);
                intent.putExtra(GzpsConstant.EVENT_P_STATE_KEY, GzpsConstant.EVENT_P_STATE_HANDLING);
                startActivity(intent);
            }
        });

        //处理中
        ll_handled = view.findViewById(R.id.ll_handled);
        ll_handled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PSHEventListActivity.class);
                intent.putExtra(GzpsConstant.EVENT_P_STATE_KEY, GzpsConstant.EVENT_P_STATE_HANDLED);
                startActivity(intent);
            }
        });

        //已办结
        ll_handled_finish = view.findViewById(R.id.ll_handled_finish);
        ll_handled_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PSHEventListActivity.class);
                intent.putExtra(GzpsConstant.EVENT_P_STATE_KEY, GzpsConstant.EVENT_P_STATE_FINISHED);
                startActivity(intent);
            }
        });

        //已上报
        ll_handled_upload = view.findViewById(R.id.ll_handled_uploaded);
        ll_handled_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PshMyUploadEventListActivity.class);
                intent.putExtra(GzpsConstant.EVENT_P_STATE_KEY, GzpsConstant.EVENT_P_STATE_UPLOADED);
                startActivity(intent);
            }
        });

        //新闻动态
        View recent_news_view = view.findViewById(R.id.iv_recent_news);
        recent_news_qBadgeView = new QBadgeView(getContext());
        recent_news_view.setOnClickListener(this);
        recent_news_qBadgeView.bindTarget(recent_news_view).setBadgeText("新").setBadgeGravity
                (Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        recent_news_qBadgeView.setVisibility(View.GONE);
        //通知公告
        View notifications = view.findViewById(R.id.iv_notifications);
        notifications_qBadgeView = new QBadgeView(getContext());
        notifications_qBadgeView.bindTarget(notifications).setBadgeText("新").setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        notifications_qBadgeView.setVisibility(View.GONE);
        notifications.setOnClickListener(this);
        //经验交流
        View experiment_share = view.findViewById(R.id.iv_experiment_share);
        experiment_share_qBadgeView = new QBadgeView(getContext());
        experiment_share_qBadgeView.bindTarget(experiment_share).setBadgeText("新").setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        experiment_share_qBadgeView.setVisibility(View.GONE);
        experiment_share.setOnClickListener(this);
        //政策法规
        View laws_and_regulations = view.findViewById(R.id.iv_laws_and_regulations);
        laws_and_regulations_qBadgeView = new QBadgeView(getContext());
        laws_and_regulations_qBadgeView.bindTarget(laws_and_regulations).setBadgeText("新").setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        laws_and_regulations_qBadgeView.setVisibility(View.GONE);
        laws_and_regulations.setOnClickListener(this);
        //红黑榜
        View billboard = view.findViewById(R.id.iv_billboard);
        billboard_qBadgeView = new QBadgeView(getContext());
        billboard_qBadgeView.bindTarget(billboard).setBadgeText("新").setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        billboard_qBadgeView.setVisibility(View.GONE);
        billboard.setOnClickListener(this);

        //操作须知
        View operate = view.findViewById(R.id.iv_operate);
        operate_qBadgeView = new QBadgeView(getContext());
        operate_qBadgeView.bindTarget(operate).setBadgeText("新").setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(-3, -2, true);
        operate_qBadgeView.setVisibility(View.GONE);
        view.findViewById(R.id.ll_operate).setOnClickListener(this);

        //事务公开
        view.findViewById(R.id.ll_event_affair).setOnClickListener(this);

        //view.findViewById(R.id.iv_facility_affair).setOnClickListener(this);

        view.findViewById(R.id.iv_upload_error_data).setOnClickListener(this);
        view.findViewById(R.id.iv_psh_error_data).setOnClickListener(this);

        //数据上报
//        view.findViewById(R.id.iv_upload_new_data).setOnClickListener(this);

        //数据上报
        view.findViewById(R.id.iv_psh_new_data).setOnClickListener(this);
        //接驳井数据上报
        view.findViewById(R.id.iv_psh_new_well).setOnClickListener(this);

        //排水户日常巡检
        view.findViewById(R.id.ll_psh_journal).setOnClickListener(this);
        //排水户问题上报
        view.findViewById(R.id.iv_psh_upload_problem).setOnClickListener(this);

        // view.findViewById(R.id.iv_amend_data).setOnClickListener(this);
        view.findViewById(R.id.iv_psh_new_door_no).setOnClickListener(this);
        //数据专业修补测
        view.findViewById(R.id.ic_modified_error_data).setOnClickListener(this);

        //问题上报
        view.findViewById(R.id.iv_upload_problem).setOnClickListener(this);
        //本地草稿
        view.findViewById(R.id.iv_local_draft).setOnClickListener(this);
        //工业排水户
        view.findViewById(R.id.ll_psh_industry).setOnClickListener(this);
        view.findViewById(R.id.ll_jbjpsdy).setOnClickListener(this);
        //地上式消防栓
        view.findViewById(R.id.ll_ground_fire).setOnClickListener(this);
        // 排水单元监管
        view.findViewById(R.id.ll_psdyjg).setOnClickListener(this);

//        pb_loading = (ProgressRelativeLayout) view.findViewById(R.id.pb_loading);

        //巡检动态
//        loadWorkNews();
        //ll_news_container = (LinearLayout) view.findViewById(R.id.ll_news_container);
//        rv_work_news = (RecyclerView) view.findViewById(R.id.rv_work_news);
//        List<WorkNews> searchResultList = new ArrayList<>();
//        workNewsAdapter = new WorkNewsAdapter(getActivity(), searchResultList);
//        rv_work_news.setAdapter(workNewsAdapter);
//        rv_work_news.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
//        /**
//         * 禁止掉RecyclerView的滑动
//         */
//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
                /*{
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        }*/
        ;
//        rv_work_news.setLayoutManager(linearLayoutManager);

        TextView tv_username = (TextView) view.findViewById(R.id.daily_work_username);
        String userName = BaseInfoManager.getUserName(getContext());
        if (TextUtils.isEmpty(userName)) {
            tv_username.setText("未知用户");
        } else {
            tv_username.setText(userName);
        }


//        BannerView bannerView = (BannerView) view.findViewById(R.id.roll_view_pager);
//        bannerView.setViewList(views);
//        bannerView.setTransformAnim(true);
//        bannerView.startLoop(true);

        //        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        //        initScrollPager();

        downloadProjects();
        // rv_work_news.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        TableViewManager.OLD_LAYER_OBJECTID_FIELD_IN_NEW = ComponentFieldKeyConstant.OLD_LAYER_OBJECTID_FIELD_IN_NEW;
        //        showBadgeAtProblemBtn();
        badge_handling = new QBadgeView(getContext()).bindTarget(ll_handling)
                .setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        badge_handled = new QBadgeView(getContext()).bindTarget(ll_handled)
                .setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        badge_handled_finish = new QBadgeView(getContext()).bindTarget(ll_handled_finish)
                .setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        badge_handled_upload = new QBadgeView(getContext()).bindTarget(ll_handled_upload)
                .setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP);
        mainView = view;
        initDymanicView();
        refreshViewForLoginRole();
    }

    //根据当前登录角色来设置界面显示与隐藏
    private void refreshViewForLoginRole() {
        tabLayout.setVisibility(View.GONE);
        String curLoginRole = LoginRoleManager.getCurLoginrRole();
        ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("巡检动态");
//        if (curLoginRole.equals(LoginRoleConstant.LOGIN_LEADER)) {
//
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS)) {
//            tabLayout.setVisibility(View.GONE);
//            ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("排水巡检动态");
//            mainView.findViewById(R.id.ll_psh_shortcut).setVisibility(View.GONE);
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_WS)) {//目前先看排水户
//            tabLayout.setVisibility(View.GONE);
//            ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("排水巡检动态");
//            mainView.findViewById(R.id.ll_psh_shortcut).setVisibility(View.GONE);
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH)) {
//            tabLayout.setVisibility(View.GONE);
//            ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("排水户巡检动态");
//            mainView.findViewById(R.id.ll_ps_shortcut).setVisibility(View.GONE);
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_WS)) {//目前先看排水户
//            tabLayout.setVisibility(View.GONE);
//            ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("排水户巡检动态");
//            mainView.findViewById(R.id.ll_ps_shortcut).setVisibility(View.GONE);
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PSH_PS)) {
//
//        } else if (curLoginRole.equals(LoginRoleConstant.LOGIN_PS_WS)) {//目前先看排水
//            tabLayout.setVisibility(View.GONE);
//            ((TextView) mainView.findViewById(R.id.tv_dynamic_title)).setText("排水巡检动态");
//            mainView.findViewById(R.id.ll_psh_shortcut).setVisibility(View.GONE);
//        } else {
//
//        }
        User user = new LoginRouter(mContext, AMDatabase.getInstance()).getUser();
        String role = user.getRoleCode();
        if (role.contains("PSH_DSSXFX")) {//包含地上消防
            mainView.findViewById(R.id.ll_ground_fire).setVisibility(View.VISIBLE);
        } else {
            mainView.findViewById(R.id.ll_ground_fire).setVisibility(View.INVISIBLE);
        }

    }

    private void initDymanicView() {
        DynamicFragmentPagerAdapter adapter = new DynamicFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        final CustomViewPager viewPager = (CustomViewPager) mainView.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        //TabLayout
        tabLayout = (TabLayout) mainView.findViewById(R.id.tabLayout);
        //必须要在Tablayout渲染出来后调用,我们可以选择view.post()方法来实现
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                // setIndicator(getContext(), tabLayout, 65, 65);
                TabLayoutIndicatorUtil.setIndicator(tabLayout, 40, 40);
            }
        });

        if (adapter.getCount() > 1) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity != null){
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private void initBanner() {

        new GzpsService(mContext).banner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result2<List<BannerUrl>>>() {
                    @Override
                    public void onCompleted() {
                        convenientBanner
                                //设置指示器是否可见
                                .setPointViewVisible(true)
                                //设置自动切换（同时设置了切换时间间隔）
                                .startTurning(5000)
                                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                                //.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                                //设置指示器的方向（左、中、右）
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                                //设置手动影响（设置了该项无法手动切换）
                                .setScrollDuration(1500);
                        //  .setManualPageable(true);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        int[] imgs = {R.mipmap.water, R.mipmap.gzpsic2, R.mipmap.gzpsic3, R.mipmap.gzpsic4, R.mipmap.gzpsic1};
                        //初始化轮播图
                        for (int i = 0; i < imgs.length; i++) {
                            views.add(imgs[i]);
                        }
                        convenientBanner.setPages(new CBViewHolderCreator() {
                            @Override
                            public Object createHolder() {
                                return new NetworkHolderView();
                            }
                        }, views);

                    }

                    @Override
                    public void onNext(Result2<List<BannerUrl>> bannerUrlResult2) {
                        if (bannerUrlResult2 == null
                                || bannerUrlResult2.getCode() != 200
                                || bannerUrlResult2.getData() == null
                                || bannerUrlResult2.getData().size() == 0) {
                            int[] imgs = {R.mipmap.water, R.mipmap.gzpsic2, R.mipmap.gzpsic3, R.mipmap.gzpsic4, R.mipmap.gzpsic1};
                            //初始化轮播图
                            for (int i = 0; i < imgs.length; i++) {
                                views.add(imgs[i]);
                            }
                            convenientBanner.setPages(new CBViewHolderCreator() {
                                @Override
                                public Object createHolder() {
                                    return new NetworkHolderView();
                                }
                            }, views);
                        } else {
                            List<String> urls = new ArrayList<>();
//                            urls.add("http://mpic.tiankong.com/105/f38/105f38da532db0b8ef28c1d9e184ff39/640.jpg");
//                            urls.add("http://mpic.tiankong.com/a40/e58/a40e588527eba8e0a5c3197ac2c91e3c/488-8229.jpg");
//                            urls.add("http://mpic.tiankong.com/66e/8e3/66e8e395e601cfecb3bb5b718733a191/east-ep-a11-1283370.jpg");
//                            urls.add("http://mpic.tiankong.com/048/5b9/0485b9b28893b1eedfb9802b5577461e/640.jpg");
                            List<BannerUrl> bannerUrls = bannerUrlResult2.getData();
                            for (BannerUrl bannerUrl : bannerUrls) {
                                //过滤掉登陆图片
                                if (!bannerUrl.getType().equals(BannerUrl.LOGIN_BG)) {
                                    urls.add(bannerUrl.getUrl());
                                }
                            }
                            convenientBanner.setPages(new CBViewHolderCreator() {
                                @Override
                                public Object createHolder() {
                                    return new NetworkHolderView2();
                                }
                            }, urls);
                        }
                    }
                });
    }


    public class NetworkHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);  //设置图片
        }
    }


    public class NetworkHolderView2 implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String url) {
            if(context != null && !(context instanceof Activity && ((Activity) context).isDestroyed())) {
                Glide.with(PatrolMainFragment2.this).load(url)
                        .error(R.mipmap.gzpsic3).into(imageView);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            doShowBadgeAtProblemBtn();
            checkColumnNewData();
//            loadWorkNews();
        }
    }

    private void checkColumnNewData() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                String agSupportPortBaseURL = PortSelectUtil.getAgSupportPortBaseURL(mContext);
//                String url = "http://" + LoginConstant.SUPPORT_URL + LoginConstant.COLUMNSUNREAD_URL + BaseInfoManager.getUserId(getActivity());
                String url = agSupportPortBaseURL+"rest/columnsUnread/getUnreadInfo/" + BaseInfoManager.getUserId(getActivity());
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {

                        subscriber.onNext(response.body().string());
                    }
                } catch (IOException e) {

                }

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int xwdtUnread = jsonObject.getInt("xwdtUnread");//新闻动态
                                if (xwdtUnread != 0 && !TextUtils.isEmpty(xwdtUnread + "")) {
                                    recent_news_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    recent_news_qBadgeView.setVisibility(View.GONE);
                                }

                                int tzggUnread = jsonObject.getInt("tzggUnread");//通知公告
                                if (tzggUnread != 0 && !TextUtils.isEmpty(tzggUnread + "")) {
                                    notifications_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    notifications_qBadgeView.setVisibility(View.GONE);
                                }

                                int jyjlUnread = jsonObject.getInt("jyjlUnread");//经验交流
                                if (jyjlUnread != 0 && !TextUtils.isEmpty(jyjlUnread + "")) {
                                    experiment_share_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    experiment_share_qBadgeView.setVisibility(View.GONE);
                                }

                                int czxzUnread = jsonObject.getInt("czxzUnread");//操作须知
                                if (czxzUnread != 0 && !TextUtils.isEmpty(czxzUnread + "")) {
                                    operate_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    operate_qBadgeView.setVisibility(View.GONE);
                                }

                                int zcfgUnread = jsonObject.getInt("zcfgUnread");//政策法规
                                int bzgfUnread = jsonObject.getInt("bzgfUnread");//标准法规
                                if (zcfgUnread != 0 || bzgfUnread != 0) {
                                    laws_and_regulations_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    laws_and_regulations_qBadgeView.setVisibility(View.GONE);
                                }

                                int hongbUnread = jsonObject.getInt("hongbUnread");//红榜
                                int heibUnread = jsonObject.getInt("heibUnread");//黑榜

                                if (hongbUnread != 0 || heibUnread != 0) {
                                    billboard_qBadgeView.setVisibility(View.VISIBLE);
                                } else {
                                    billboard_qBadgeView.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    /**
     * 获取待受理（待办）、处理中（已办）的数量，并以红点的形式显示在按钮右上角
     */
    private void showBadgeAtProblemBtn() {
        //每10秒种请求一次网络
        Observable.interval(0, 10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        doShowBadgeAtProblemBtn();
                    }
                });

    }

//    @Subscribe
//    public void onRefreshEventCountEvent(OnRefreshEvent onRefreshEvent) {
//        doShowBadgeAtProblemBtn();
//        checkColumnNewData();
////        loadWorkNews();
//
//    }

    /**
     * 获取待受理（待办）、处理中（已办）的数量，并以红点的形式显示在按钮右上角
     */
    private void doShowBadgeAtProblemBtn() {
        new PSHUploadEventService(mContext).getEventCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PSHEventCount>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(PSHEventCount eventCount) {
                        if (eventCount == null) {
                            return;
                        }
                        badge_handling.setBadgeNumber(eventCount.getDcl());
                        badge_handled.setBadgeNumber(eventCount.getDbj());
                        badge_handled_finish.setBadgeNumber(eventCount.getYbj());
                        badge_handled_upload.setBadgeNumber(eventCount.getSb());
                    }
                });
    }

//    /**
//     * 加载巡检动态
//     */
//    private void loadWorkNews() {
//        //pb_loading.showLoading();
////        WorkNService workNewsService = new WorkNewsService(mContext);//排水
//        PSHWorkNewsService workNewsService = new PSHWorkNewsService(mContext);//排水户
//
//        workNewsService.getWorkNews()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<WorkNews>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        pb_loading.showError("", "获取数据失败!", "重试", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                loadWorkNews();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onNext(List<WorkNews> workNewses) {
//                        if (ListUtil.isEmpty(workNewses)) {
//                            pb_loading.showError("", "暂无巡检动态", "刷新", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    loadWorkNews();
//                                }
//                            });
//                            return;
//                        }
//
//                        pb_loading.showContent();
//                        workNewsAdapter.notifyDataChanged(workNewses);
//                    }
//                });
//    }

    /**
     * 下载所有的表单
     */
    private void downloadProjects() {
        final TableDataManager tableDataManager = new TableDataManager(getActivity());
        List<Project> projectFromDB = tableDataManager.getProjectFromDB();
        if (ListUtil.isEmpty(projectFromDB)) {
//            String serverUrl1 = BaseInfoManager.getBaseServerUrl(getActivity());
            String serverUrl = PortSelectUtil.getAgWebPortBaseURL(mContext);
            String userId = BaseInfoManager.getUserId(getActivity());
            String url = serverUrl + "rest/patrol/getAllProject/" + userId;
            NetworkUtil.setContext(getActivity());
            if (NetworkUtil.isNetworkAvailable()) {
                tableDataManager.getAllProjectByNet(url, new TableNetCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        List<Project> projects = (List<Project>) data;
                        List<Project> oneProject = new ArrayList<Project>();
                        tableDataManager.setProjectToDB(projects);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.shortToast(getActivity(), "加载表单失败");
                    }
                });
            }
        }

    }

    /*
    private void initScrollPager() {

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(5000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(1000);
        //设置适配器
        mRollViewPager.setAdapter(new BannerAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        mRollViewPager.setHintView(null);
    }
    */

    @Override
    public void onClick(View v) {
        String componentName = "";
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_facility_affair:
                /**
                 * 设施公开（数据公开）
                 */
                intent = new Intent(getActivity(), com.augurit.agmobile.gzps.publicaffair.PublicAffairActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_event_affair:
                /**
                 * 事件公开
                 */
                intent = new Intent(getActivity(), PublicAffairActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_recent_news:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewConstant.WEBVIEW_URL_PATH, WebViewConstant.H5_URLS
                        .RECENT_NEWS);
                intent.putExtra(WebViewConstant.WEBVIEW_TITLE, "新闻动态");
                startActivity(intent);
                break;
            case R.id.iv_notifications:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewConstant.WEBVIEW_URL_PATH, WebViewConstant.H5_URLS
                        .NOTIFICATIONS);
                intent.putExtra(WebViewConstant.WEBVIEW_TITLE, "通知公告");
                startActivity(intent);
                break;
            case R.id.iv_experiment_share:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewConstant.WEBVIEW_URL_PATH, WebViewConstant.H5_URLS
                        .EXPERIMENT_SHARE_URL);
                intent.putExtra(WebViewConstant.WEBVIEW_TITLE, "经验交流");
                startActivity(intent);
                break;
            case R.id.iv_laws_and_regulations:
                intent = new Intent(getActivity(), LawsAndRegulationsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_billboard:
                intent = new Intent(getActivity(), BillboardActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_operate:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewConstant.WEBVIEW_URL_PATH, WebViewConstant.H5_URLS
                        .OPERATE_KNOW);
                intent.putExtra(WebViewConstant.WEBVIEW_TITLE, "操作须知");
                startActivity(intent);
                break;

            /**
             * 数据纠错
             */
            case R.id.iv_upload_error_data:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), ModifyFacilityTabActivity.class);
                //                startActivity(intent);
//                intent = new Intent(getActivity(), ModifyFacilityListActivity.class);
//                startActivity(intent);
                break;
            /**
             * 数据新报
             */
            case R.id.iv_upload_new_data:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), UploadFacilityTabActivity.class);
                //                startActivity(intent);
                intent = new Intent(getActivity(), UploadFacilityMapActivity.class);
                startActivity(intent);
                break;
            /**
             * 排水户
             */
            case R.id.iv_psh_new_data:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), UploadFacilityTabActivity.class);
                //                startActivity(intent);
                intent = new Intent(getActivity(), SewerageMapActivity.class);
                startActivity(intent);
                break;
            /**
             * 接驳井
             */
            case R.id.iv_psh_new_well:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), UploadFacilityTabActivity.class);
                //                startActivity(intent);
                intent = new Intent(getActivity(), PSHUploadFacilityMapActivity.class);
                startActivity(intent);
                break;

            /**
             * 排水户日常巡检
             */
            case R.id.ll_psh_journal:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), UploadFacilityTabActivity.class);
                //                startActivity(intent);
                intent = new Intent(getActivity(), DialyPatrolMapActivity.class);
                startActivity(intent);
                break;

            /**
             * 排水户问题上报
             */
            case R.id.iv_psh_upload_problem:
                //两个tab改成一个tab
                //                intent = new Intent(getActivity(), UploadFacilityTabActivity.class);
                //                startActivity(intent);
                intent = new Intent(getActivity(), PSHEventUploadActivity.class);
                startActivity(intent);
                break;

            /**
             * 新增门牌和查看、编辑门牌
             */
            case R.id.iv_psh_new_door_no:
                intent = new Intent(getActivity(), PSHUploadDoorNoMapActivity.class);
                startActivity(intent);
                break;

            /**
             * 数据修正
             */
            case R.id.ic_modified_error_data:
                if (true) {
                    ToastUtil.shortToast(getContext(), "目前暂对专业修补测人员开放");
                } else {
                    showChoice();
                }
//                                intent = new Intent(getActivity(), ComponentMaintenanceActivityBase.class);
//                                startActivity(intent);
                break;
            /**
             * 数据新增
             */
            //            case R.id.iv_add_new_data:
            //                intent = new Intent(getActivity(), AddComponentActivity2.class);
            //                startActivity(intent);
            //                break;
            /**
             * 问题上报
             */
            case R.id.iv_upload_problem:
                TableViewManager.isEditingFeatureLayer = false;
                //intent = new Intent(getActivity(), EditTableActivity.class);
                intent = new Intent(getActivity(), EventUploadActivity.class);
                intent.putExtra("projectId", SearchFragmentWithoutMap.PROJECT_ID);
                intent.putExtra("projectName", "问题上报");
                startActivity(intent);
                break;
            case R.id.iv_local_draft:
                intent = new Intent(getActivity(), PSHLocalDraftActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_psh_industry:
//                ToastUtil.shortToast(mContext,"建设中");
                intent = new Intent(getActivity(), SewerageMyUploadActivity.class);
                intent.putExtra("isIndustry", true);
                startActivity(intent);
                break;
            case R.id.ll_jbjpsdy:
//                ToastUtil.shortToast(mContext,"建设中");
                intent = new Intent(getActivity(), JbjPsdyMapActivity.class);
                intent.putExtra("isIndustry", true);
                startActivity(intent);
                break;
            case R.id.ll_psdyjg:
                intent = new Intent(getActivity(), DrainageUnitMonitorActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_ground_fire:
//                ToastUtil.shortToast(mContext,"建设中");
                intent = new Intent(getActivity(), PSHUploadFireMapActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        if (StringUtil.isEmpty(componentName)) {
            return;
        }
        String featureLayerUrl = LayerUrlConstant.getNewLayerUrlByLayerName(componentName);
        TableViewManager.isEditingFeatureLayer = true;
        TableViewManager.graphic = null;
        TableViewManager.geometry = null;
        TableViewManager.featueLayerUrl = featureLayerUrl;
        addComponent(componentName);
    }

    private void addComponent(String componentName) {
        if (StringUtil.isEmpty(componentName)) {
            return;
        }
        Intent intent = new Intent(getActivity(), ProjectListActivity2.class);
        //        Intent intent = new Intent(getActivity(), AddComponentActivity2.class);
        intent.putExtra("componentName", componentName);
        startActivity(intent);
    }

    public void showChoice() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .create();
        View dialog = View.inflate(getActivity(), R.layout.dialog_choose_modified_type, null);
        dialog.findViewById(R.id.iv_add_new_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(getActivity(), NewAddedComponentActivityBase.class);
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.iv_amend_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(getActivity(), ComponentMaintenanceActivityBase.class);
                startActivity(intent);
            }
        });
        alertDialog.setView(dialog);
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
