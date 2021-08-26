package com.augurit.agmobile.gzpssb.worknews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FullyLinearLayoutManager;
import com.augurit.agmobile.gzps.worknews.WorkNewsAdapter;
import com.augurit.agmobile.gzps.worknews.model.WorkNews;
import com.augurit.agmobile.gzps.worknews.service.WorkNewsService;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressRelativeLayout;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PSDynamicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PSDynamicFragment extends Fragment {

    private ProgressRelativeLayout pb_loading;
    private RecyclerView rv_work_news;
    private WorkNewsAdapter workNewsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_psdynamic, container, false);

        pb_loading = (ProgressRelativeLayout) view.findViewById(R.id.pb_loading);

        loadWorkNews();

        rv_work_news = (RecyclerView) view.findViewById(R.id.rv_work_news);
        List<WorkNews> searchResultList = new ArrayList<>();
        workNewsAdapter = new WorkNewsAdapter(getActivity(), searchResultList);
        rv_work_news.setAdapter(workNewsAdapter);
        rv_work_news.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        /**
         * 禁止掉RecyclerView的滑动
         */
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) ;
        rv_work_news.setLayoutManager(linearLayoutManager);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    /**
     * 加载巡检动态
     */
    private void loadWorkNews() {
        //pb_loading.showLoading();
        WorkNewsService workNewsService = new WorkNewsService(getContext());
        workNewsService.getWorkNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WorkNews>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pb_loading.showError("", "获取数据失败!", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadWorkNews();
                            }
                        });
                    }

                    @Override
                    public void onNext(List<WorkNews> workNewses) {
                        if (ListUtil.isEmpty(workNewses)) {
                            pb_loading.showError("", "暂无巡检动态", "刷新", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadWorkNews();
                                }
                            });
                            return;
                        }

                        pb_loading.showContent();
                        workNewsAdapter.notifyDataChanged(workNewses);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

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
