package com.augurit.agmobile.gzpssb.journal.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.journal.model.TeamMember;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.ReadOnlyAttributeView;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.JsonUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.gzps.R.id.tableitem_parent_Org;

/**
 * 在设施纠错地图界面中用于生成“纠错信息”表的类
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.identification
 * @createTime 创建时间 ：17/11/10
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/11/10
 * @modifyMemo 修改备注：
 */

public class DialyPatrolTableViewManager {

    private Context mContext;
    private View root;

    private MultiTakePhotoTableItem take_photo_item;
    private PSHJournal modifiedIdentification;
    private TextView tv_select_or_check_location;
    private TextItemTableItem textitem_component_type;
    private TextItemTableItem tableitem_current_time;
    private TextItemTableItem tableitem_current_user;
    private View ll_decription;
    private TextFieldTableItem textitem_description;

    private LinearLayout ll_container;
    private TextItemTableItem tableitem_correct_type;
    private TextItemTableItem tableitem_parent_org;
    private TextItemTableItem tableitem_checkstate;
    private TextItemTableItem textitem_addr;
    private TextItemTableItem tableitem_last_modified_time;
    private TextItemTableItem tableitem_direct_org;
    private View ll__facility_problem;
    private TextFieldTableItem textitem_facility_problem;
    private TextItemTableItem textitem_upload_place;
    private TextItemTableItem tableitem_group_person;
    private View ll__psk_video;
    private ImageView iv_video;
    private View ll_zgjy;
    private TextFieldTableItem problem_tab_zgjy;

    public DialyPatrolTableViewManager(
            Context context,
            PSHJournal modifiedIdentification,
            boolean ifReadOnly) {

        this.mContext = context;
        this.modifiedIdentification = modifiedIdentification;
        initView();
    }


    public void addTo(ViewGroup container) {
        container.removeAllViews();
        container.addView(root);
    }

    public void setReadOnly(PSHJournal modifiedFacility, Map<String, Object> originAttributes) {
        if (modifiedFacility != null) {
            setPhotos();
            tableitem_correct_type.setVisibility(View.GONE);
            tableitem_current_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(modifiedFacility.getRecordTime())));
            tableitem_current_time.setVisibility(View.VISIBLE);
            tableitem_current_user.setText(modifiedFacility.getWriterName());
            tableitem_current_time.setReadOnly();
            tableitem_current_user.setReadOnly();

            /**
             * 更新时间
             */
            if (modifiedFacility.getUpdateTime() != null && modifiedFacility.getUpdateTime() > 0
                    && !modifiedFacility.getUpdateTime().equals(modifiedFacility.getRecordTime())) {
                tableitem_last_modified_time.setText(TimeUtil.getStringTimeYMDMChines(new Date(modifiedFacility.getUpdateTime())));
                tableitem_last_modified_time.setVisibility(View.VISIBLE);
                tableitem_last_modified_time.setReadOnly();
            } else {
                tableitem_last_modified_time.setVisibility(View.GONE);
            }

            /**
             *  上报单位,判断规则：
             *  判断directOrg是否不为空，如果不为空显示directOrg，
             *  否则判断superviseOrg是否为空，如果superviseOrg为空，那么隐藏上报单位item
             */
            String directOrg = null;
            if (!TextUtils.isEmpty(modifiedFacility.getDirectOrgName())) {
                directOrg = modifiedFacility.getDirectOrgName();
            } else if (!TextUtils.isEmpty(modifiedFacility.getSuperviseOrgName())) {
                directOrg = modifiedFacility.getSuperviseOrgName();
            }

            if (directOrg == null) {
                tableitem_direct_org.setVisibility(View.GONE);
            } else {
                tableitem_direct_org.setText(directOrg);
                tableitem_direct_org.setVisibility(View.VISIBLE);
            }
            String teamMember = modifiedFacility.getTeamMember();
            String strTeam = "";
            if (!StringUtil.isEmpty(teamMember)) {
                List<TeamMember> teamMembers = JsonUtil.getObject(teamMember, new TypeToken<List<TeamMember>>() {
                }.getType());
                if (!ListUtil.isEmpty(teamMembers)) {
                    for (TeamMember teamMember1 : teamMembers) {
                        strTeam += "," + teamMember1.getUserName();
                    }
                }
            }

            if (!StringUtil.isEmpty(strTeam) && strTeam.length() > 1) {
                strTeam = strTeam.substring(1);
                tableitem_group_person.setText(strTeam);
            }

            if (TextUtils.isEmpty(modifiedFacility.getDescription())) {
                textitem_description.setText("");
            } else {
                textitem_description.setText(modifiedFacility.getDescription());
            }
            textitem_description.setReadOnly();

            if (TextUtils.isEmpty(modifiedFacility.getZgjy())) {
                ll_zgjy.setVisibility(View.GONE);
                problem_tab_zgjy.setText("");
            } else {
                ll_zgjy.setVisibility(View.VISIBLE);
                problem_tab_zgjy.setText(modifiedFacility.getZgjy());
            }
            problem_tab_zgjy.setReadOnly();

            tableitem_parent_org.setText(modifiedFacility.getParentOrgName());
            tableitem_parent_org.setReadOnly();
            tableitem_parent_org.setVisibility(View.VISIBLE);
            ReadOnlyAttributeView pshName = new ReadOnlyAttributeView(mContext, "排水户名称",
                    modifiedFacility.getPshName(),
                    modifiedFacility.getPshName());
            pshName.addTo(ll_container);
            ReadOnlyAttributeView menpai = new ReadOnlyAttributeView(mContext, "门牌号",
                    modifiedFacility.getMph(),
                    modifiedFacility.getMph());
            menpai.addTo(ll_container);
            ReadOnlyAttributeView addr = new ReadOnlyAttributeView(mContext, "排水户位置",
                    modifiedFacility.getAddr(),
                    modifiedFacility.getAddr());
            addr.addTo(ll_container);
            ReadOnlyAttributeView readOnlyAttributeView = new ReadOnlyAttributeView(mContext, "所在道路",
                    modifiedFacility.getRoad(),
                    modifiedFacility.getRoad());
            readOnlyAttributeView.addTo(ll_container);

            setReadOnlyAttributes(originAttributes);
//            if (modifiedFacility.getCorrectType() != null) {
//                if (modifiedFacility.getCorrectType().equals("设施不存在")) {
//                    ll_decription.setVisibility(View.GONE);
//                    TextItemTableItem tableItem = new TextItemTableItem(mContext);
//                    tableItem.setTextViewName("设施");
//                    tableItem.setText(modifiedFacility.getLayerName());
//                    tableItem.setReadOnly();
//                    ll_container.addView(tableItem);
//                } else if (modifiedFacility.getCorrectType().equals("位置错误")) {
//                    setReadOnlyAttributes(originAttributes);
//                } else if (modifiedFacility.getCorrectType().equals("位置与信息错误")) {
//                    setReadOnlyAttributes(originAttributes);
//                } else if (modifiedFacility.getCorrectType().equals("信息错误")) {
//                    setReadOnlyAttributes(originAttributes);
//                } else if (modifiedFacility.getCorrectType().equals("数据确认")) {
//                    setReadOnlyAttributes(originAttributes);
//                }
//
//                //审核状态
//                if (modifiedFacility.getCheckState() != null) {
//                    if (modifiedFacility.getCheckState().equals("1")) {
//                        tableitem_checkstate.setText("未审核");
//                        tableitem_checkstate.setEditTextColor(Color.RED);
//                        //tableitem_checkstate.setReadOnly();
//                    } else if (modifiedFacility.getCheckState().equals("2")) {
//                        tableitem_checkstate.setText("审核通过");
//                        tableitem_checkstate.setEditTextColor(Color.parseColor("#3EA500"));
//                        //tableitem_checkstate.setReadOnly();
//                    } else if (modifiedFacility.getCheckState().equals("3")) {
//                        tableitem_checkstate.setText("存在疑问");
//                        tableitem_checkstate.setEditTextColor(Color.parseColor("#FFFFC248"));
//                    }
//                }
//
//            }

            //设施问题
            TableDBService dbService = new TableDBService(mContext);
            String pCodes = modifiedFacility.getPcode();
            String childCodes = modifiedFacility.getChildCode();
            if (!TextUtils.isEmpty(pCodes) && !TextUtils.isEmpty(childCodes)) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] split = pCodes.split(",");
                for (String pCode : split) {

                    List<DictionaryItem> childProblems = dbService.getChildDictionaryByPCodeInDB(pCode);
                    List<DictionaryItem> parentProblems = dbService.getDictionaryByCode(pCode);
                    if (ListUtil.isEmpty(childProblems) || ListUtil.isEmpty(parentProblems)) {
                        continue;
                    }
//                    stringBuilder.append(parentProblems.get(0).getName());
//                    stringBuilder.append(" : ");

                    for (DictionaryItem childProblem : childProblems) {
                        if (childCodes.contains(childProblem.getCode())) {
                            if (!childProblem.getName().contains("其他")) {
                                stringBuilder.append(childProblem.getName());
                                stringBuilder.append("；");
                            }
                        }
                    }
//                    //删除掉末尾的";"
//                    int i = stringBuilder.lastIndexOf("；");
//                    if (i == stringBuilder.length() - 1) {
//                        stringBuilder.deleteCharAt(i);
//                    }
                    stringBuilder.append("\n");
                }
                if (childCodes.contains("其他")) {
                    String otherPro = childCodes.split("其他")[1];
                    stringBuilder.append("其他" + otherPro);
                    stringBuilder.append("\n");
                }

                if (!TextUtils.isEmpty(stringBuilder.toString())) {
                    ll__facility_problem.setVisibility(View.VISIBLE);
                    //删除掉末尾的"\n"
                    int i = stringBuilder.lastIndexOf("\n");
                    String facilityDes = stringBuilder.toString().substring(0, i);
                    textitem_facility_problem.setText(facilityDes);
                }


            }

//            /**
//             * 城中村/三不管
//             */
//            if (!TextUtils.isEmpty(modifiedFacility.getCityVillage())) {
//                textitem_upload_place.setText(modifiedFacility.getCityVillage());
//            } else {
//                textitem_upload_place.setVisibility(View.GONE);
//            }
        }

        take_photo_item.setAddPhotoEnable(false);
    }


    private void setReadOnlyAttributes(Map<String, Object> originAttrs) {
        DialyPatrolAttributes yinjingAttributes = new DialyPatrolAttributes();
        yinjingAttributes.setAttributeOne(modifiedIdentification.getAttrOne());
        yinjingAttributes.setAttributeTwo(modifiedIdentification.getAttrTwo());
        yinjingAttributes.setAttributeThree(modifiedIdentification.getAttrThree());
        yinjingAttributes.setAttributeFour(modifiedIdentification.getAttrFour());
        yinjingAttributes.setAttributeFive(modifiedIdentification.getAttrFive());
        DialyPatrolAttributeViewUtil.addReadOnlyAttributes(mContext, originAttrs, yinjingAttributes, ll_container);
    }

    private void setPhotos() {

        List<Photo> photos = modifiedIdentification.getPhotos();
        if (!ListUtil.isEmpty(photos)) {

            take_photo_item.setSelectedPhotos(photos);
        } else {

            take_photo_item.setVisibility(View.GONE);
        }
        take_photo_item.setReadOnly();
    }

    private void initView() {

        root = LayoutInflater.from(mContext).inflate(R.layout.view_myjournal_table_view, null);

        tableitem_correct_type = (TextItemTableItem) root.findViewById(R.id.tableitem_correct_type);

        tableitem_checkstate = (TextItemTableItem) root.findViewById(R.id.tableitem_checkstate); //审核状态
        tableitem_checkstate.setReadOnly();
        take_photo_item = (MultiTakePhotoTableItem) root.findViewById(R.id.take_photo_item);
        root.findViewById(R.id.rl).setVisibility(View.GONE);
        ((TextView) root.findViewById(R.id.tv_title)).setText("设施纠错");

        tv_select_or_check_location = (TextView) root.findViewById(R.id.tv_select_or_check_location);

        /**
         * 当前时间
         */
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
        ll_decription = root.findViewById(R.id.ll_decription);
        textitem_description = (TextFieldTableItem) root.findViewById(R.id.textitem_description);
        /**
         * 整改建议
         */
        ll_zgjy = root.findViewById(R.id.ll_zgjy);
        problem_tab_zgjy = (TextFieldTableItem) root.findViewById(R.id.problem_tab_zgjy);

        textitem_addr = (TextItemTableItem) root.findViewById(R.id.textitem_addr);

        /**
         * 属性容器
         */
        ll_container = (LinearLayout) root.findViewById(R.id.ll_container);
        /**
         * 业主单位
         */
        tableitem_parent_org = (TextItemTableItem) root.findViewById(tableitem_parent_Org);
        /**
         * 上报单位
         */
        tableitem_direct_org = (TextItemTableItem) root.findViewById(R.id.tableitem_direct_org);
        tableitem_direct_org.setReadOnly();

        /**
         * 班组成员
         */
        tableitem_group_person = (TextItemTableItem) root.findViewById(R.id.tableitem_group_person);
        tableitem_group_person.setReadOnly();
        tableitem_group_person.setVisibility(View.GONE);

        /**
         * 设施问题
         */
        ll__facility_problem = root.findViewById(R.id.ll__facility_problem);
        textitem_facility_problem = (TextFieldTableItem) root.findViewById(R.id.textitem_facility_problem);
        textitem_facility_problem.setReadOnly();
        /**
         * 城中村/三不管
         */
        textitem_upload_place = (TextItemTableItem) root.findViewById(R.id.textitem_upload_place);
        textitem_upload_place.setReadOnly();
        textitem_upload_place.setVisibility(View.GONE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (take_photo_item != null) {
            take_photo_item.onActivityResult(requestCode, resultCode, data);
        }
    }

}
