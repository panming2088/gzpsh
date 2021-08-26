package com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail;

import android.os.Bundle;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.databinding.FragmentFacilityDetailBinding;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzpssb.fragment.MyBaseFragment;
import com.augurit.agmobile.gzpssb.jhj.view.MyModifiedWellTableViewManager;
import com.augurit.agmobile.gzpssb.jhj.view.UploadWellTableViewManager;

/**
 * @author: liangsh
 * @createTime: 2021/4/6
 */
public class FacilityDetailFragment extends MyBaseFragment {

    private ModifiedFacility modifiedFacility;
    private UploadedFacility uploadedFacility;

    public static FacilityDetailFragment newInstance(Bundle bundle) {
        FacilityDetailFragment fragment = new FacilityDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initview() {
        return R.layout.fragment_facility_detail;
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        FragmentFacilityDetailBinding binding = getBind();
        Object modify = getArguments().getParcelable("modifiedFacility");
        if(modify != null){
            modifiedFacility = (ModifiedFacility) modify;
            MyModifiedWellTableViewManager myModifiedWellTableViewManager = new MyModifiedWellTableViewManager(getContext(),
                    modifiedFacility, true);
            myModifiedWellTableViewManager.addTo(binding.llContainer);
        }
        Object upload = getArguments().getParcelable("uploadedFacility");
        if(upload != null){
            uploadedFacility = (UploadedFacility) upload;
            UploadWellTableViewManager uploadWellTableViewManager = new UploadWellTableViewManager(getContext(),
                    uploadedFacility);
            uploadWellTableViewManager.addTo(binding.llContainer);
        }

    }
}
