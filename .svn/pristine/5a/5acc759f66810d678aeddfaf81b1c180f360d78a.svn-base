package com.augurit.agmobile.gzpssb.pshdoorno.doorlist.widge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzps.uploadfacility.model.ApprovalOpinion;
import com.augurit.agmobile.gzps.uploadfacility.service.ApprovalOpinionService;
import com.augurit.agmobile.gzps.uploadfacility.view.approvalopinion.ApprovalOpinionAdapter;
import com.augurit.agmobile.gzps.uploadfacility.view.approvalopinion.ApprovalOpinionView;
import com.augurit.agmobile.gzpssb.pshdoorno.doorlist.mode.UploadDoorNoDetailBean;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.loadinglayout.ProgressLinearLayout;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.TimeUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.uploadfacility.view.approvalopinion
 * @createTime 创建时间 ：17/12/26
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/12/26
 * @modifyMemo 修改备注：
 */

public class UploadDoorNoApprovalViewManager {
    private TextItemTableItem date;
    private TextItemTableItem state;
    private TextItemTableItem option;
    private EditText person;
    private TextView empty;
    private View personLayout;
    private View root;
    private ImageView iv_phone;
    private TextView tv_phone;
    private Context mContext;

    public UploadDoorNoApprovalViewManager(Context context, UploadDoorNoDetailBean uploadedDoorNo) {
        mContext = context;
        initViews(context);
        if (null == uploadedDoorNo || uploadedDoorNo.getCheckPerson() == null || uploadedDoorNo.getCheckDescription() == null) {
            hiddleViews();
        } else {
            initData(uploadedDoorNo.getMarkTime(), uploadedDoorNo.getCheckState(), uploadedDoorNo.getCheckDescription(), uploadedDoorNo.getMarkPerson(),"");
        }
    }

    public UploadDoorNoApprovalViewManager(Context context, PSHAffairDetail pshAffairDetail) {
        mContext = context;
        initViews(context);
        if (null == pshAffairDetail || pshAffairDetail.getData().getCheckPerson() == null || pshAffairDetail.getData().getCheckDesription() == null) {
            hiddleViews();
        } else {
            initData(pshAffairDetail.getData().getCheckTime(), pshAffairDetail.getData().getState(), pshAffairDetail.getData().getCheckDesription(), pshAffairDetail.getData().getCheckPerson(),"");
        }
    }

    private void initViews(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.view_doorno_opinions, null);
        date = (TextItemTableItem) root.findViewById(R.id.tv_approve_date);
        state = (TextItemTableItem) root.findViewById(R.id.tv_approve_state);
        option = (TextItemTableItem) root.findViewById(R.id.tv_option);
        personLayout = root.findViewById(R.id.person_layout);
        person = (EditText) root.findViewById(R.id.tv_approver);
        empty = (TextView) root.findViewById(R.id.tv_empty);
        iv_phone  = (ImageView) root.findViewById(R.id.iv_phone);
        tv_phone = (TextView) root.findViewById(R.id.tv_phone);
        person.setEnabled(false);
        date.setReadOnly();
        option.setReadOnly();
        state.setReadOnly();
    }

    private void hiddleViews() {
        empty.setVisibility(View.VISIBLE);
        person.setVisibility(View.INVISIBLE);
        personLayout.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        option.setVisibility(View.INVISIBLE);
        state.setVisibility(View.INVISIBLE);

    }


    private void initData(String checkTime, String CheckState, String CheckDescription, String MarkPerson , final String phone) {
        empty.setVisibility(View.GONE);
        date.setText(TimeUtil.getStringTimeMdHmChines(new Date(Long.valueOf(checkTime))));
        state.setText(CheckState.equals("1") ? "未审核" : CheckState.equals("2") ? "审核通过" : "存在疑问");
        option.setText(CheckDescription);
        person.setText(MarkPerson);
        /**
         * 手机为空，不显示
         */
        if (TextUtils.isEmpty(phone)){
            iv_phone.setVisibility(View.GONE);
            tv_phone.setVisibility(View.GONE);
        }else {
            tv_phone.setText(phone);
            root.findViewById(R.id.ll_phone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phone));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public View getView() {
        return root;
    }

    public void addTo(ViewGroup container) {
        if (container == null) {
            return;
        }
        container.addView(root);
    }
}
