package com.augurit.agmobile.gzpssb.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.SewerageInvestBean;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.okhttp.Iview.ISewerageItemView;

/**
 * Created by xiaoyu on 2018/4/3.
 */

public class SeweragePopulationFragment extends MyBaseFragment implements ISewerageItemView {

    private FgSewerage1Binding fgSewerageBinding;

    public static SeweragePopulationFragment newInstance() {
        SeweragePopulationFragment seweragePopulationFragment = new SeweragePopulationFragment();
        return seweragePopulationFragment;
    }


    @Override
    public int initview()  {
        return R.layout.fg_sewerage1;
    }

    @Override
    protected void initData() {
        super.initData();
        fgSewerageBinding = getBind();
        fgSewerageBinding.setFgsewerageonclic(new MyOnclick());
    }

    @Override
    public void setSewerageItemList(SewerageItemBean sewerageItemList) {
        Log.e("TAG","00");
        fgSewerageBinding.allInstallCount.setText(sewerageItemList.getUnit_total()+"");
        fgSewerageBinding.installCount.setText(sewerageItemList.getUnit_survey()+"");
        fgSewerageBinding.noInstallCount.setText(sewerageItemList.getUnit_no_survey()+"");
    }

    @Override
    public void onInvestCode(SewerageInvestBean code) {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onCompelete() {

    }

    private class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_add:
                    Intent intent = new Intent( view.getContext(), SewerageTableActivity.class);
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
