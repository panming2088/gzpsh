package com.augurit.agmobile.gzpssb.journal.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.facilityownership.FacilityOwnerShipUnitView;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityAttributeModel;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzps.uploadfacility.util.YinjingAttributeViewUtil;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 添加设施属性
 * <p>
 * Created by xcl on 2017/11/12.
 */

public class DialyPatrolAttributeView {


    private Context mContext;
    private List<TextItemTableItem> addedTextItems = new ArrayList<>();
    private LinearLayout ll_attributelist_container;
    private View root;
    private Map<String, String> selectedAttributes = new HashMap<>(5);
    private String layerName = null;
    /**
     * 权属单位
     */
    private FacilityOwnerShipUnitView ownerShipUnit = null;
    private DialyPatrolAttributeViewUtil yinjingAttributeViewUtil;
    private boolean isDraft = false;

    public DialyPatrolAttributeView(Context context) {
        this.mContext = context;
        initView();
    }


    public DialyPatrolAttributeView(Context context, UploadedFacility uploadedFacility) {
        this.mContext = context;
        initView(uploadedFacility);
    }

    public DialyPatrolAttributeView(Context context, PSHJournal uploadedFacility) {
        this.mContext = context;
        initView(uploadedFacility);
    }

    public DialyPatrolAttributeView(Context context, UploadedFacility uploadedFacility, boolean isDraft) {
        this.mContext = context;
        this.isDraft = isDraft;
        initView(uploadedFacility);
    }


    public void addTo(ViewGroup container) {
        container.addView(root);
    }

    private void initView() {
        root = View.inflate(mContext, R.layout.view_add_new_journal, null);
        ll_attributelist_container = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        ll_attributelist_container.removeAllViews();
        yinjingAttributeViewUtil = new DialyPatrolAttributeViewUtil();
        yinjingAttributeViewUtil.addYinjingAttributes(mContext, null, ll_attributelist_container, null);
    }


    private void initView(final UploadedFacility uploadedFacility) {
        root = View.inflate(mContext, R.layout.view_add_new_journal, null);
        ll_attributelist_container = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        ll_attributelist_container.removeAllViews();
        Map<String, String> defaultSelectValue = null;
        if (uploadedFacility != null && layerName.equals(uploadedFacility.getLayerName())) {
            defaultSelectValue = new HashMap<>(5);
            defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_ONE, uploadedFacility.getAttrOne());
            defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_TWO, uploadedFacility.getAttrTwo());
            defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_THREE, uploadedFacility.getAttrThree());
            defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_FOUR, uploadedFacility.getAttrFour());
            if (uploadedFacility.getAttrFive() == null) {
                defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_FIVE, "");
            } else {
                defaultSelectValue.put(YinjingAttributeViewUtil.ATTRIBUTE_FIVE, uploadedFacility.getAttrFive());
            }
        }
        yinjingAttributeViewUtil = new DialyPatrolAttributeViewUtil();
        yinjingAttributeViewUtil.addYinjingAttributes(mContext, null, ll_attributelist_container, defaultSelectValue);

    }

    private void initView(final PSHJournal uploadedFacility) {
        root = View.inflate(mContext, R.layout.view_add_new_journal, null);
        ll_attributelist_container = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        ll_attributelist_container.removeAllViews();
        Map<String, String> defaultSelectValue = null;
        if (uploadedFacility != null) {
            defaultSelectValue = new HashMap<>(5);
            defaultSelectValue.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_ONE, uploadedFacility.getAttrOne());
            defaultSelectValue.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_TWO, uploadedFacility.getAttrTwo());
            defaultSelectValue.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_THREE, uploadedFacility.getAttrThree());
            defaultSelectValue.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_FOUR, uploadedFacility.getAttrFour());
            defaultSelectValue.put(DialyPatrolAttributeViewUtil.ATTRIBUTE_FIVE, uploadedFacility.getAttrFive());

        }
        yinjingAttributeViewUtil = new DialyPatrolAttributeViewUtil();
        yinjingAttributeViewUtil.addYinjingAttributes(mContext, null, ll_attributelist_container, defaultSelectValue);

    }


    public FacilityAttributeModel getFacilityAttributeModel() {

        FacilityAttributeModel facilityAttributeModel = new FacilityAttributeModel();
        DialyPatrolAttributes yinjingAttributes = yinjingAttributeViewUtil.getYinjingAttributes();
        facilityAttributeModel.setAttrOne(yinjingAttributes.getAttributeOne());
        facilityAttributeModel.setAttrTwo(yinjingAttributes.getAttributeTwo());
        facilityAttributeModel.setAttrThree(yinjingAttributes.getAttributeThree());
        facilityAttributeModel.setAttrFour(yinjingAttributes.getAttributeFour());
        facilityAttributeModel.setAttrFive(yinjingAttributes.getAttributeFive());
        facilityAttributeModel.setIfAllowUpload(yinjingAttributes.ifAllowUpload());
        facilityAttributeModel.setNotAllowUploadReason(yinjingAttributes.getNotAllowUploadReason());
        return facilityAttributeModel;
    }


    public void destroy() {
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }

        if (yinjingAttributeViewUtil != null) {
            yinjingAttributeViewUtil.onDestroy();
            yinjingAttributeViewUtil = null;
        }

    }
}
