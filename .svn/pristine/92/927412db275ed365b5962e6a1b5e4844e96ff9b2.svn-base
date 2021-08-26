package com.augurit.agmobile.gzpssb.uploadfacility.view.tranship;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.uploadfacility.model.PipeBean;
import com.augurit.agmobile.patrolcore.common.model.Component;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 在详情界面中用于生成“上报信息”表的类
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.identification
 * @createTime 创建时间 ：17/11/10
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/10
 * @modifyMemo 修改备注：
 */

public class PipeTableViewManager {

    private Context mContext;
    private View root;

    private MultiTakePhotoTableItem take_photo_item;
    private TextItemTableItem tableitem_current_time;
    private TextItemTableItem tableitem_current_user;
    private TextFieldTableItem textitem_description;
    private TextItemTableItem tableitem_component_type;
    private TextItemTableItem tableitem_checkstate;
    private PipeBean uploadedFacility;
    private Component mComponent;
    private TextItemTableItem textitem_address;
    //    private TextItemTableItem textitem_road;
    private LinearLayout ll_attributelist_container;
    private TextItemTableItem tableitem_parent_org;
    private TextItemTableItem tableitem_last_modified_time;
    private TextItemTableItem tableitem_direct_org;
    private View ll__facility_problem;
    private TextFieldTableItem textitem_facility_problem;
    private TextItemTableItem textitem_upload_place;
    private TextItemTableItem textitem_gjlx;
    private TextItemTableItem textitem_gxcd;
    private String pipeType;
    private String direction;
    private String pipeGJ;
    private String length;
    private String checkState;

    public PipeTableViewManager(
            Context context,
            PipeBean modifiedIdentification) {

        this.mContext = context;
        this.uploadedFacility = modifiedIdentification;
        initView();
        initData(uploadedFacility);
    }

    public PipeTableViewManager(
            Context context,
            Component component) {

        this.mContext = context;
        this.mComponent = component;
        initView();
        initData(mComponent);
    }

    private void initData(PipeBean uploadedFacility) {
        if (uploadedFacility != null) {
            tableitem_component_type.setText(uploadedFacility.getPipeType());
            textitem_address.setText(uploadedFacility.getDirection());
            TableDBService dbService = new TableDBService(mContext);
            List<DictionaryItem> a205 = dbService.getDictionaryByTypecodeInDB("A185");
            final Map<String, Object> gjData = new LinkedHashMap<>();
            String pipeGJ = uploadedFacility.getPipeGj();
            String gjName = "";
            List<String> gjValues = new ArrayList<>();
            for (DictionaryItem dictionaryItem : a205) {
                if(dictionaryItem.getCode().equals(pipeGJ)){
                    gjName = dictionaryItem.getName();
                }
                gjValues.add(dictionaryItem.getName());
                gjData.put(dictionaryItem.getName(), dictionaryItem);
            }
            textitem_gjlx.setText(gjName);
            textitem_gjlx.setReadOnly();
            textitem_gxcd.setReadOnly();
            if(uploadedFacility.getLineLength() != 0 && uploadedFacility.getLineLength() !=-1) {
                textitem_gxcd.setText(uploadedFacility.getLineLength() + "");
            }else{
                textitem_gxcd.setText("");
            }
            tableitem_component_type.setReadOnly();
            textitem_address.setReadOnly();
            String oldPipeType = uploadedFacility.getOldPipeType();
            if (!StringUtil.isEmpty(oldPipeType) && !oldPipeType.equals(uploadedFacility.getPipeType())) {
                tableitem_component_type.setEditTextColor(Color.RED);
            }

            Long date = uploadedFacility.getMarkTime();
            if (date == null) {
                tableitem_current_time.setText("");
            }
            if (date != null) {
                tableitem_current_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(date)));
            }

            /**
             * 更新时间
             */
            Long updateTime = uploadedFacility.getUpdateTime();
            if (updateTime != null && updateTime > 0 && !updateTime.equals(date)) {
                tableitem_last_modified_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(updateTime)));
                tableitem_last_modified_time.setVisibility(View.VISIBLE);
                tableitem_last_modified_time.setReadOnly();
            }

            /**
             *  上报单位,判断规则：
             *  判断directOrg是否不为空，如果不为空显示directOrg，
             *  否则判断superviseOrg是否为空，如果superviseOrg为空，那么隐藏上报单位item
             */
            String directOrg = null;

            if (!TextUtils.isEmpty(this.uploadedFacility.getParentOrgName())) {
                directOrg = this.uploadedFacility.getParentOrgName();
            }

            if (directOrg == null) {
                tableitem_direct_org.setVisibility(View.GONE);
            } else {
                tableitem_direct_org.setText(directOrg);
                tableitem_direct_org.setVisibility(View.VISIBLE);
            }

            tableitem_current_time.setVisibility(View.VISIBLE);
            tableitem_current_user.setText(this.uploadedFacility.getMarkPerson());
            tableitem_current_time.setReadOnly();
            tableitem_current_user.setReadOnly();

            if (TextUtils.isEmpty(this.uploadedFacility.getDescription())) {
                textitem_description.setText("");
            } else {
                textitem_description.setText(this.uploadedFacility.getDescription());
            }
            textitem_description.setReadOnly();

            if (!TextUtils.isEmpty(this.uploadedFacility.getOldDirection())) {
                String oldAddress = this.uploadedFacility.getDirection();
                if (!this.uploadedFacility.getOldDirection().equals(oldAddress)) {
                    textitem_address.setEditTextColor(Color.RED);
                }
            }
            //审核状态
            tableitem_checkstate.setReadOnly();
            if (this.uploadedFacility.getCheckState() != null) {
                if (this.uploadedFacility.getCheckState().equals("1")) {
                    tableitem_checkstate.setText("未审核");
                    tableitem_checkstate.setEditTextColor(Color.RED);
                } else if (this.uploadedFacility.getCheckState().equals("2")) {
                    tableitem_checkstate.setText("审核通过");
                    tableitem_checkstate.setEditTextColor(Color.parseColor("#3EA500"));
                } else if (this.uploadedFacility.getCheckState().equals("3")) {
                    tableitem_checkstate.setText("存在疑问");
                    tableitem_checkstate.setEditTextColor(Color.parseColor("#FFFFC248"));
                }
            }
        }
    }

    private void initData(Component component) {
        if (component != null) {
            pipeType = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("SORT"), "");
            direction = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("DIRECTION"), "");
            pipeGJ = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("PIPE_GJ"), "");
            length = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("PIPE_LENGTH"), "");
            checkState = StringUtil.getNotNullString(component.getGraphic().getAttributeValue("CHECK_STATE"), "");
            tableitem_component_type.setText(pipeType);
            textitem_address.setText(direction);
            TableDBService dbService = new TableDBService(mContext);
            List<DictionaryItem> a205 = dbService.getDictionaryByTypecodeInDB("A185");
            final Map<String, Object> gjData = new LinkedHashMap<>();
            String gjName = "";
            List<String> gjValues = new ArrayList<>();
            for (DictionaryItem dictionaryItem : a205) {
                if(dictionaryItem.getCode().equals(pipeGJ)){
                    gjName = dictionaryItem.getName();
                }
                gjValues.add(dictionaryItem.getName());
                gjData.put(dictionaryItem.getName(), dictionaryItem);
            }
            textitem_gjlx.setText(gjName);
            textitem_gjlx.setReadOnly();
            textitem_gxcd.setReadOnly();
            if(!StringUtil.isEmpty(length) && !"-1".equals(length)) {
                textitem_gxcd.setText(length);
            }else{
                textitem_gxcd.setText("");
            }
            tableitem_component_type.setReadOnly();
            textitem_address.setReadOnly();

//            Long date = uploadedFacility.getMarkTime();
//            if (date == null) {
//                tableitem_current_time.setText("");
//            }
//            if (date != null) {
//                tableitem_current_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(date)));
//            }

//            /**
//             * 更新时间
//             */
//            Long updateTime = uploadedFacility.getUpdateTime();
//            if (updateTime != null && updateTime > 0 && !updateTime.equals(date)) {
//                tableitem_last_modified_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(updateTime)));
//                tableitem_last_modified_time.setVisibility(View.VISIBLE);
//                tableitem_last_modified_time.setReadOnly();
//            }
            tableitem_last_modified_time.setVisibility(View.GONE);
            tableitem_direct_org.setVisibility(View.GONE);


            tableitem_current_time.setVisibility(View.GONE);
            tableitem_current_time.setReadOnly();
            tableitem_current_user.setReadOnly();
            tableitem_current_user.setVisibility(View.GONE);
//            if (TextUtils.isEmpty(this.uploadedFacility.getDescription())) {
//                textitem_description.setText("");
//            } else {
//                textitem_description.setText(this.uploadedFacility.getDescription());
//            }
            textitem_description.setReadOnly();
            textitem_description.setVisibility(View.GONE);
            //审核状态
            tableitem_checkstate.setReadOnly();
            if (checkState != null) {
                if (checkState.equals("1")) {
                    tableitem_checkstate.setText("未审核");
                    tableitem_checkstate.setEditTextColor(Color.RED);
                } else if (checkState.equals("2")) {
                    tableitem_checkstate.setText("审核通过");
                    tableitem_checkstate.setEditTextColor(Color.parseColor("#3EA500"));
                } else if (checkState.equals("3")) {
                    tableitem_checkstate.setText("存在疑问");
                    tableitem_checkstate.setEditTextColor(Color.parseColor("#FFFFC248"));
                }
            }
        }
    }

    public void addTo(ViewGroup container) {
        container.removeAllViews();
        container.addView(root);
    }

    /**
     *
     */
    private void initView() {

        root = LayoutInflater.from(mContext).inflate(R.layout.view_upload_pipe_table_view, null);
        tableitem_checkstate = (TextItemTableItem) root.findViewById(R.id.tableitem_checkstate); //审核状态
        tableitem_component_type = (TextItemTableItem) root.findViewById(R.id.tableitem_component_type);
//        take_photo_item = (MultiTakePhotoTableItem) root.findViewById(R.id.take_photo_item);
        tableitem_current_time = (TextItemTableItem) root.findViewById(R.id.tableitem_current_time);
        /**
         * 最后修改时间
         */
        tableitem_last_modified_time = (TextItemTableItem) root.findViewById(R.id.tableitem_last_modified_time);
        /**
         * 填表人
         */
        tableitem_current_user = (TextItemTableItem) root.findViewById(R.id.tableitem_current_user);
        /**
         * 描述
         */
        textitem_description = (TextFieldTableItem) root.findViewById(R.id.textitem_description);

        /**
         * 地址
         */
        textitem_address = (TextItemTableItem) root.findViewById(R.id.textitem_address);
        /**
         * 管径类型
         */
        textitem_gjlx = (TextItemTableItem) root.findViewById(R.id.textitem_gjlx);
        /**
         * 管线长度
         */
        textitem_gxcd = (TextItemTableItem) root.findViewById(R.id.textitem_gxcd);

//        textitem_road = (TextItemTableItem) root.findViewById(R.id.textitem_road);

        /**
         * 属性容器
         */
        ll_attributelist_container = (LinearLayout) root.findViewById(R.id.ll_attributelist_container);
        /**
         * 业主单位
         */
        tableitem_parent_org = (TextItemTableItem) root.findViewById(R.id.tableitem_parent_Org);
        /**
         * 上报单位
         */
        tableitem_direct_org = (TextItemTableItem) root.findViewById(R.id.tableitem_direct_org);
        tableitem_direct_org.setReadOnly();
        /**
         * 设施问题
         */
//        ll__facility_problem = root.findViewById(R.id.ll__facility_problem);
//        textitem_facility_problem = (TextFieldTableItem) root.findViewById(R.id.textitem_facility_problem);
//        textitem_facility_problem.setReadOnly();

        /**
         * 管理状态
         */
//        textitem_upload_place = (TextItemTableItem) root.findViewById(R.id.textitem_upload_place);
//        textitem_upload_place.setReadOnly();
    }

}
