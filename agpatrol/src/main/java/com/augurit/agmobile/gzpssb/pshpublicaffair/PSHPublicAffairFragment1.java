package com.augurit.agmobile.gzpssb.pshpublicaffair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.publicaffair.PublicAffairPagerAdapter;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.pshpublicaffair.adapter.PSHAffairAdapter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.adapter.PSHPublicAffairPagerAdapter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHEventBean;
import com.augurit.agmobile.gzpssb.pshpublicaffair.service.PSHAffairService;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailContract;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.affairdetail.PSHAffairDetailPresenter;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PSHAffairFilterConditionEvent;
import com.augurit.agmobile.gzpssb.pshpublicaffair.view.condition.PublicAffairSelectState;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.cmpt.widget.searchview.util.Util;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.augurit.agmobile.gzpssb.pshaffair
 * Created by sdb on 2018/4/10  15:18.
 * Desc???
 */

public class PSHPublicAffairFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PSHPublicAffairFragment1.OnFragmentInteractionListener mListener;
    private PSHPublicAffairPagerAdapter adapter;

    public int mPSselectItem;

    public PSHPublicAffairFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PSPublicAffairFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PSHPublicAffairFragment1 newInstance(String param1, String param2) {
        PSHPublicAffairFragment1 fragment = new PSHPublicAffairFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_pspublic_affair, container, false);

        final ViewPager viewPager = (ViewPager) mainView.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new PSHPublicAffairPagerAdapter(getChildFragmentManager(),
                getContext());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPSselectItem = position;
                EventBus.getDefault().post(new PublicAffairSelectState(0,mPSselectItem));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);

        //TabLayout
        final TabLayout tabLayout = (TabLayout) mainView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        return mainView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
