package com.augurit.agmobile.gzpssb.secondpsh;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.TextFieldTableItem;
import com.augurit.agmobile.gzps.common.widget.TextItemTableItem;
import com.augurit.agmobile.gzpssb.bean.SewerageItemBean;
import com.augurit.agmobile.gzpssb.common.MyApplication;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfoManger;

public class SecondLevelPshBaseInfoFragment extends Fragment {

    private TextItemTableItem text_item_one, text_item_two, text_item_three, text_item_four, text_item_five
            ,text_item_seven,text_item_eight;
    private TextFieldTableItem text_item_six;
    private Context mContext;
    SecondLevelPshInfo.SecondPshInfo info;
    private View botttom_view;
    private SewerageItemBean.UnitListBean unitListBean;
    public static SecondLevelPshBaseInfoFragment newInstance(Bundle bundle) {
        SecondLevelPshBaseInfoFragment baseInfoFragment = new SecondLevelPshBaseInfoFragment();
        baseInfoFragment.setArguments(bundle);
        return baseInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragent_second_psh_detail, null);
        initView(view);
        return view;


    }

    private void initView(View view) {
        text_item_one = (TextItemTableItem) view.findViewById(R.id.text_item_one);
        text_item_two = (TextItemTableItem) view.findViewById(R.id.text_item_two);
        text_item_one.setEditable(false);
        text_item_two.setEditable(false);


        text_item_three = (TextItemTableItem) view.findViewById(R.id.text_item_three);
        text_item_four = (TextItemTableItem) view.findViewById(R.id.text_item_four);
        text_item_five = (TextItemTableItem) view.findViewById(R.id.text_item_five);
        text_item_six = (TextFieldTableItem) view.findViewById(R.id.text_item_six);
        text_item_seven = (TextItemTableItem) view.findViewById(R.id.text_item_seven);
        text_item_eight = (TextItemTableItem) view.findViewById(R.id.text_item_eight);
        botttom_view = view.findViewById(R.id.botttom_view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewCreatedListener != null) {
            viewCreatedListener.onViewCreated();
        }
        initData();
    }


    private void initData() {
        unitListBean = (SewerageItemBean.UnitListBean) getArguments().getSerializable("unitListBean");
        info = (SecondLevelPshInfo.SecondPshInfo) getArguments().getSerializable("data");
        text_item_one.setText(unitListBean.getName());
        text_item_two.setText(TextUtils.isEmpty(unitListBean.getAddr())? (MyApplication.SEWERAGEITEMBEAN == null ? "":MyApplication.SEWERAGEITEMBEAN.getMpwzhm()):unitListBean.getAddr());
        if (info != null) {
            text_item_three.setText(info.getEjaddr());
            text_item_four.setText(info.getEjname());
//            text_item_five.setText(TextUtils.isEmpty(info.getLoginName())?"":info.getLoginName());
//            botttom_view.setVisibility(View.VISIBLE);
            setView(false);
        }else{
            setViewTag(true);
//            botttom_view.setVisibility(View.GONE);
        }
    }

    boolean EDIT_MODE;//设置true不可编辑，设置false可编辑

    public void setEditMode(boolean isEdit) {
        if (isEdit != EDIT_MODE) {
            EDIT_MODE = isEdit;
            setView(isEdit);
        }
    }
    private void setViewTag(boolean isEnable) {
        String starStr;
        if(EDIT_MODE||isEnable){
            starStr="*";
        }else{
            starStr="";
        }
        text_item_one.setTextViewName(Html.fromHtml("一级排水户名称<font color='#FF0000'>"+starStr+"</font>"));
        text_item_two.setTextViewName(Html.fromHtml("一级排水户门牌<font color='#FF0000'>"+starStr+"</font>"));
        text_item_three.setTextViewName(Html.fromHtml("二级排水户位置<font color='#FF0000'>"+starStr+"</font>"));
        text_item_four.setTextViewName(Html.fromHtml("二级排水户名称<font color='#FF0000'>"+starStr+"</font>"));

    }
    private void setView(boolean isEnable) {
//        text_item_one.setEditable(isEnable);
//        text_item_two.setEditable(isEnable);
        text_item_three.setEditable(isEnable);
        text_item_four.setEditable(isEnable);
        text_item_five.setEditable(isEnable);
        text_item_six.setEnableEdit(isEnable);
        text_item_seven.setEditable(isEnable);
        text_item_eight.setEditable(isEnable);
        setViewTag(isEnable);
        if(isEnable){
            botttom_view.setVisibility(View.GONE);
        }
    }

    public interface onViewCreatedListener {
        void onViewCreated();
    }

    public SecondLevelPshBaseInfoFragment.onViewCreatedListener viewCreatedListener;

    public void setViewCreatedListener(SecondLevelPshBaseInfoFragment.onViewCreatedListener viewCreatedListener) {
        this.viewCreatedListener = viewCreatedListener;
    }

    public void getData() {
        SecondLevelPshInfoManger.getInstance().setYjname(text_item_one.getText().toString());
        SecondLevelPshInfoManger.getInstance().setYjmenpai(text_item_two.getText().toString());
        SecondLevelPshInfoManger.getInstance().setEjaddr(text_item_three.getText().toString());
        SecondLevelPshInfoManger.getInstance().setEjname(text_item_four.getText().toString());
        //TODO 新增的情况下需要手动设置进去
//        SecondLevelPshInfoManger.getInstance().setYjname_id("");
    }
}
