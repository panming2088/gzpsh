package com.augurit.agmobile.gzps.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.journal.model.Journal;
import com.augurit.agmobile.gzps.journal.service.JournalsService;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * 日志列表界面
 *
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.journal
 * @createTime 创建时间 ：17/11/3
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/3
 * @modifyMemo 修改备注：
 */

public class JournalsActivity extends BaseActivity {

    private XRecyclerView rv_journals;
    private JournalsAdapter journalsAdapter;
    private ProgressLinearLayout pb_loading;

    private int page = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);

        Button btn_add = (Button) findViewById(R.id.btn_add_survey);
        btn_add.setText("新增日志");
        btn_add.setVisibility(View.VISIBLE);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(JournalsActivity.this,WriteJournalActivity2.class), 123);
            }
        });

        pb_loading = (ProgressLinearLayout) findViewById(R.id.pb_loading);

        rv_journals = (XRecyclerView) findViewById(R.id.rv_journals);
        rv_journals.setPullRefreshEnabled(true);
        rv_journals.setLoadingMoreEnabled(true);
        rv_journals.setLayoutManager(new LinearLayoutManager(this));
        journalsAdapter = new JournalsAdapter(this, new ArrayList<Journal>());
        rv_journals.setAdapter(journalsAdapter);
        journalsAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {

            }
        });

        rv_journals.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadJournals(false);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadJournals(false);
            }
        });

        ((TextView) findViewById(R.id.tv_title)).setText("日志列表");

        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadJournals(true);
    }

    private void loadJournals(boolean ifShowPb) {
        if (ifShowPb) {
            pb_loading.showLoading();
        }
        JournalsService journalsService = new JournalsService(JournalsActivity.this.getApplicationContext());
        journalsService.getMyJournals(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Journal>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pb_loading.showError("", "加载数据出错", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadJournals(true);
                            }
                        });
                    }

                    @Override
                    public void onNext(List<Journal> journals) {
                        /**
                         * 返回数据为空
                         */
                        if (ListUtil.isEmpty(journals)) {

                            if (page == 1) {
                                pb_loading.showError("", "数据为空", "刷新", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loadJournals(true);
                                    }
                                });
                            } else {
                                rv_journals.loadMoreComplete();
                                rv_journals.setNoMore(true);
                            }

                            return;
                        }

                        /**
                         * 有数据
                         */
                        Collections.sort(journals, new Comparator<Journal>() {
                            @Override
                            public int compare(Journal o1, Journal o2) {
                                if (o1.getRecordTime() > o2.getRecordTime()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        pb_loading.showContent();
                        if (page == 1) {
                            rv_journals.refreshComplete();
                            journalsAdapter.notifyDataChanged(journals);
                        } else {
                            rv_journals.loadMoreComplete();
                            journalsAdapter.addData(journals);
                            journalsAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if(requestCode == 123
                && resultCode == 123){
            page = 1;
            loadJournals(false);
        }
    }
}
