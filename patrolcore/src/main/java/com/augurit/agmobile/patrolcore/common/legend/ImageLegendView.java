package com.augurit.agmobile.patrolcore.common.legend;

import android.app.Activity;
import android.content.Context;

import com.augurit.agmobile.mapengine.legend.model.GroupLegend;
import com.augurit.agmobile.mapengine.legend.model.LayerLegend;
import com.augurit.agmobile.mapengine.legend.model.Legend;
import com.augurit.agmobile.mapengine.legend.view.ILegendView;
import com.augurit.am.fw.common.BaseView;
import com.augurit.am.fw.utils.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcl on 2017/10/30.
 */

public class ImageLegendView extends BaseView<ILegendPresenter> implements ILegendView {

    private ImageLegendDialog lineDialog;
    private List<GroupLegend> allGpLegends;

    public ImageLegendView(Context context) {

        super(context);

        initView();
    }

    protected void initView() {

        lineDialog = ImageLegendDialog.newInstance();
        allGpLegends = new ArrayList<>();
    }


    @Override
    public void showLegends(List<Legend> legends) {

        showDialog();

    }

    @Override
    public void showLayerLegends(List<LayerLegend> legends) {
        showDialog();

    }

    private void showDialog() {
        if (!lineDialog.isAdded()) {
            lineDialog.show(((Activity) mContext).getFragmentManager(), "legendDialog");
        }
    }

    @Override
    public void showLoadingLegend() {

        showDialog();


    }

    @Override
    public void hideLoadingLegend() {

    }

    @Override
    public void showLoadingLegendError(String message) {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.shortToast(mContext,message);
    }

    @Override
    public void clearData() {
        allGpLegends.clear();
    }
}

