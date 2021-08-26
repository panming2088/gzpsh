package com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.selectcomponent.SelectLimitedComponentActivity;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadfacility.model.FacilityInfoErrorModel;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.PaifangKouAttributes;
import com.augurit.agmobile.gzps.uploadfacility.model.YinjingAttributes;
import com.augurit.agmobile.gzps.uploadfacility.model.YuShuiKouAttributes;
import com.augurit.agmobile.gzps.uploadfacility.util.PaifangKouAttributeViewUtil;
import com.augurit.agmobile.gzps.uploadfacility.util.YinjingAttributeViewUtil;
import com.augurit.agmobile.gzps.uploadfacility.util.YushuikouAttributeViewUtil;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.esri.core.geometry.Geometry;

import org.greenrobot.eventbus.EventBus;

/**
 * 信息错误视图
 * Created by xcl on 2017/11/12.
 */

public class FacilityInfoErrorView {

    private Context mContext;
    private TextItemTableItem textItemComponentType;
    private View root;
    private LinearLayout attributelistContainer;
    private TextView tvSelectOrCheckLocation;
    private Component mCurrentComponent;
    private Geometry mLastSelectLocation;
    private FacilityInfoErrorModel facilityInfoErrorModel;

    private YinjingAttributeViewUtil yinjingAttributeViewUtil;
    private YushuikouAttributeViewUtil yuShuiKouAttributes;
    private PaifangKouAttributeViewUtil paifangKouAttributeViewUtil;

    public FacilityInfoErrorView(Context context,
                                 Component component) {
        this.mContext = context;

        this.facilityInfoErrorModel = new FacilityInfoErrorModel();
        initView(component);
    }

    public FacilityInfoErrorView(Context context,
                                 ModifiedFacility modifiedFacility) {
        this.mContext = context;

        this.facilityInfoErrorModel = new FacilityInfoErrorModel();
        initView(modifiedFacility);
    }


    public void destroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (yinjingAttributeViewUtil != null) {
            yinjingAttributeViewUtil.onDestroy();
            yinjingAttributeViewUtil = null;
        }

        if (yuShuiKouAttributes != null) {
            yuShuiKouAttributes.onDestroy();
            yuShuiKouAttributes = null;
        }

        if (paifangKouAttributeViewUtil != null) {
            paifangKouAttributeViewUtil.onDestroy();
            paifangKouAttributeViewUtil = null;
        }

    }

    public void addTo(ViewGroup container) {
        container.addView(root);
    }


    private void initView(ModifiedFacility modifiedFacility) {

        root = View.inflate(mContext, R.layout.view_component_info_error, null);
        textItemComponentType = (TextItemTableItem) root.findViewById(R.id.textitem_component_type);
        attributelistContainer = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        tvSelectOrCheckLocation = (TextView) root.findViewById(R.id.tv_select_or_check_location);
        /**
         * 选择部件
         */
        root.findViewById(R.id.ll_select_component).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectLimitedComponentActivity.class);
                if (mCurrentComponent != null) {
                    intent.putExtra("component", mCurrentComponent);
                }
                mContext.startActivity(intent);
            }
        });
        root.findViewById(R.id.rl_select_location).setVisibility(View.GONE);

        /**
         * 根据部件类型进行显示属性
         */
        if (modifiedFacility == null) {
            return;
        }
        textItemComponentType.setText(modifiedFacility.getLayerName());
        textItemComponentType.setVisibility(View.VISIBLE);
        textItemComponentType.setReadOnly();
        attributelistContainer.setVisibility(View.VISIBLE);


        switch (modifiedFacility.getLayerName()) {
            case "窨井":
                attributelistContainer.removeAllViews();

                yinjingAttributeViewUtil = new YinjingAttributeViewUtil();

                yinjingAttributeViewUtil.addYinjingAttributes(mContext,
                        YinjingAttributeViewUtil.getOriginValue(modifiedFacility),
                        attributelistContainer,
                        YinjingAttributeViewUtil.getDefaultValue(modifiedFacility));
                break;
            case "雨水口":
                attributelistContainer.removeAllViews();
                yuShuiKouAttributes = new YushuikouAttributeViewUtil();

                yuShuiKouAttributes.addYushuikouAttributes(mContext,
                        YushuikouAttributeViewUtil.getOriginValue(modifiedFacility),
                        attributelistContainer,
                        YushuikouAttributeViewUtil.getDefaultValue(modifiedFacility));
                break;
            case "排放口":
                attributelistContainer.removeAllViews();
                paifangKouAttributeViewUtil = new PaifangKouAttributeViewUtil();

                paifangKouAttributeViewUtil.addYushuikouAttributes(mContext,
                        PaifangKouAttributeViewUtil.getOriginValue(modifiedFacility),
                        attributelistContainer,
                        PaifangKouAttributeViewUtil.getDefaultValue(modifiedFacility));
                break;
            default:
                break;
        }
    }


    private void initView(Component component) {
        root = View.inflate(mContext, R.layout.view_component_info_error, null);
        textItemComponentType = (TextItemTableItem) root.findViewById(R.id.textitem_component_type);
        attributelistContainer = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        tvSelectOrCheckLocation = (TextView) root.findViewById(R.id.tv_select_or_check_location);
        /**
         * 选择部件
         */
        root.findViewById(R.id.ll_select_component).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectLimitedComponentActivity.class);
                if (mCurrentComponent != null) {
                    intent.putExtra("component", mCurrentComponent);
                }
                mContext.startActivity(intent);
            }
        });
        root.findViewById(R.id.rl_select_location).setVisibility(View.GONE);

        /**
         * 根据部件类型进行显示属性
         */
        if (component == null) {
            return;
        }
        textItemComponentType.setText(component.getLayerName());
        textItemComponentType.setVisibility(View.VISIBLE);
        textItemComponentType.setReadOnly();
        attributelistContainer.setVisibility(View.VISIBLE);

        switch (component.getLayerName()) {
            case "窨井":
                attributelistContainer.removeAllViews();
                yinjingAttributeViewUtil = new YinjingAttributeViewUtil();
                yinjingAttributeViewUtil.addYinjingAttributes(mContext, component.getGraphic().getAttributes(), attributelistContainer, null);
                break;
            case "雨水口":
                attributelistContainer.removeAllViews();
                yuShuiKouAttributes = new YushuikouAttributeViewUtil();
                yuShuiKouAttributes.addYushuikouAttributes(mContext, component.getGraphic().getAttributes(), attributelistContainer, null);
                break;
            case "排放口":
                attributelistContainer.removeAllViews();
                paifangKouAttributeViewUtil = new PaifangKouAttributeViewUtil();
                paifangKouAttributeViewUtil.addYushuikouAttributes(mContext, component.getGraphic().getAttributes(), attributelistContainer, null);
                break;
            default:
                break;
        }
    }

    public FacilityInfoErrorModel getFacilityInfoErrorModel() {

        if (yinjingAttributeViewUtil != null) {
            YinjingAttributes yinjingAttributes = yinjingAttributeViewUtil.getYinjingAttributes();
            facilityInfoErrorModel.setAttrOne(yinjingAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrTwo(yinjingAttributes.getAttributeTwo());
            facilityInfoErrorModel.setAttrThree(yinjingAttributes.getAttributeThree());
            facilityInfoErrorModel.setAttrFour(yinjingAttributes.getAttributeFour());
            facilityInfoErrorModel.setAttrFive(yinjingAttributes.getAttributeFive());
            facilityInfoErrorModel.setHasModified(yinjingAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(yinjingAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(yinjingAttributes.getNotAllowUploadReason());
        }

        if (yuShuiKouAttributes != null) {
            YuShuiKouAttributes yuShuiKouAttributes = this.yuShuiKouAttributes.getYuShuiKouAttributes();
            facilityInfoErrorModel.setAttrOne(yuShuiKouAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrThree(yuShuiKouAttributes.getAttributeThree());
            facilityInfoErrorModel.setAttrFour(yuShuiKouAttributes.getAttributeFour());
            facilityInfoErrorModel.setHasModified(yuShuiKouAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(yuShuiKouAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(yuShuiKouAttributes.getNotAllowUploadReason());
        }

        if (paifangKouAttributeViewUtil != null) {
            PaifangKouAttributes paifangKouAttributes = paifangKouAttributeViewUtil.getPaifangKouAttributes();
            facilityInfoErrorModel.setAttrOne(paifangKouAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrTwo(paifangKouAttributes.getAttributeTwo());
            facilityInfoErrorModel.setAttrThree(paifangKouAttributes.getAttributeThree());
            facilityInfoErrorModel.setHasModified(paifangKouAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(paifangKouAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(paifangKouAttributes.getNotAllowUploadReason());
        }

        return facilityInfoErrorModel;
    }

    public FacilityInfoErrorModel getOriginalModel() {
        if (yinjingAttributeViewUtil != null) {
            YinjingAttributes yinjingAttributes = yinjingAttributeViewUtil.getOriginalYinjingAttributes();
            facilityInfoErrorModel.setAttrOne(yinjingAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrTwo(yinjingAttributes.getAttributeTwo());
            facilityInfoErrorModel.setAttrThree(yinjingAttributes.getAttributeThree());
            facilityInfoErrorModel.setAttrFour(yinjingAttributes.getAttributeFour());
            facilityInfoErrorModel.setAttrFive(yinjingAttributes.getAttributeFive());
            facilityInfoErrorModel.setHasModified(yinjingAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(yinjingAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(yinjingAttributes.getNotAllowUploadReason());
        }

        if (yuShuiKouAttributes != null) {
            YuShuiKouAttributes yuShuiKouAttributes = this.yuShuiKouAttributes.getOriginalYuShuiKouAttributes();
            facilityInfoErrorModel.setAttrOne(yuShuiKouAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrThree(yuShuiKouAttributes.getAttributeThree());
            facilityInfoErrorModel.setAttrFour(yuShuiKouAttributes.getAttributeFour());
            facilityInfoErrorModel.setHasModified(yuShuiKouAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(yuShuiKouAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(yuShuiKouAttributes.getNotAllowUploadReason());
        }

        if (paifangKouAttributeViewUtil != null) {
            PaifangKouAttributes paifangKouAttributes = paifangKouAttributeViewUtil.getOriginalPaifangKouAttributes();
            facilityInfoErrorModel.setAttrOne(paifangKouAttributes.getAttributeOne());
            facilityInfoErrorModel.setAttrTwo(paifangKouAttributes.getAttributeTwo());
            facilityInfoErrorModel.setAttrThree(paifangKouAttributes.getAttributeThree());
            facilityInfoErrorModel.setHasModified(paifangKouAttributes.isHasModified());
            facilityInfoErrorModel.setIfAllowUpload(paifangKouAttributes.ifAllowUpload());
            facilityInfoErrorModel.setNotAllowUploadReason(paifangKouAttributes.getNotAllowUploadReason());
        }
        return facilityInfoErrorModel;
    }

}
