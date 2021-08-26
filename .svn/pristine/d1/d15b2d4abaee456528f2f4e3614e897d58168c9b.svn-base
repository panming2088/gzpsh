package com.augurit.agmobile.gzpssb.secondpsh;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BR;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.adapter.BaseRecyleAdapter;
import com.augurit.agmobile.gzpssb.adapter.SewerageTableTwoAdapter;
import com.augurit.agmobile.gzpssb.bean.ItemSewerageItemInfo;
import com.augurit.agmobile.gzpssb.common.view.SpacesItemDecoration;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfo;
import com.augurit.agmobile.gzpssb.pshdoorno.add.mode.SecondLevelPshInfoManger;
import com.augurit.agmobile.gzpssb.utils.SetMaxLengthUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoyu on 2018/4/4.
 */

public class SecondPshTwoFragment extends Fragment {
    private List<ItemSewerageItemInfo> userInfo;
    private List<ItemSewerageItemInfo> userInfo2 = new ArrayList<>();
    private SewerageTableTwoAdapter baseRecyleAdapter;
    private GridLayoutManager myGridLayoutManager;
    private GridLayoutManager myGridLayoutManager1;
    SewerageTableTwoAdapter sewerageTableTwoChildAdapter;
    public static final  String lifeWater = "生活排污类";
    private String[] name = {"工业类", "餐饮类", "建筑类", "医疗类", "农贸市场类", "畜禽养殖类", "机关事业单位类", "汽修机洗类", "洗涤类", "垃圾收集处理类", "综合商业类", "居民类", "其他"};
    private String[] name1 = {"机关企事业单位", "学校", "商场", "居民住宅","其他"};
    private String[] name2 = {"餐饮店", "农家乐", "酒店", "大型食堂", "其他"};
    private String[] name3 = {"洗车、修车档", "沙场", "建筑工地", "养殖场","农贸市场","垃圾(收集)转运站", "其他"};
    private String[] name4 = {"化工", "印染", "电镀、小五金", "医疗", "洗涤","食品加工","制衣、皮革加工","行业管理排水户", "其他"};
    String[] selecet = name1;
    private SecondLevelPshInfo.SecondPshInfo pshAffairDetail;
    private boolean isAllowSaveLocalDraft;
    TextView  tv_notice ,tv_notice2;
    RecyclerView rv_table1 ,rv_table2;
    EditText edt_other_two;

    private Context mContext;
    public static SecondPshTwoFragment newInstance(Bundle bundle) {
        SecondPshTwoFragment sewerageTableTwoFragment = new SecondPshTwoFragment();
        sewerageTableTwoFragment.setArguments(bundle);
        return sewerageTableTwoFragment;
    }

    boolean EDIT_MODE;//设置true不可编辑，设置false可编辑
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.second_psh_fragment_two, null);
        initView(view);
        return view;
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

    private void initView(View view) {
        tv_notice = (TextView) view.findViewById(R.id.tv_notice);
        tv_notice2 = (TextView) view.findViewById(R.id.tv_notice2);
        rv_table1 = (RecyclerView) view.findViewById(R.id.rv_table1);
        rv_table2 =  (RecyclerView) view.findViewById(R.id.rv_table2);
        edt_other_two = (EditText) view.findViewById(R.id.edt_other_two);
    }

    private void setView(SecondLevelPshInfo.SecondPshInfo pshAffairDetail) {
        if (pshAffairDetail == null) {
            return;
        }

        //不选中大类别默认是餐饮类别，此处要重置大类别
        for (int i = 0; i < name.length; i++) {
            if (!TextUtils.isEmpty(pshAffairDetail.getPshtype1())
            && name[i].contains(pshAffairDetail.getPshtype2())) {
                selecet = i==0?name1:i==1?name2:i==2?name3:name4;
                break;
            }
        }
        if(!EDIT_MODE) {
            edt_other_two.setEnabled(false);
           tv_notice.setVisibility(View.GONE);
           tv_notice2.setVisibility(View.GONE);
        }else{
            edt_other_two.setEnabled(true);
           tv_notice.setVisibility(View.VISIBLE);
           tv_notice2.setVisibility(View.VISIBLE);
        }

        if(pshAffairDetail!=null){
            String dischargerType1 = pshAffairDetail.getPshtype3();
//            String dischargerType2 = pshAffairDetail.getPshtype2();


            SecondLevelPshInfoManger.getInstance().setPshtype3(dischargerType1);
//            SecondLevelPshInfoManger.getInstance().setPshtype2(dischargerType2);
            int bigTypePos = -1;
            for (int i = 0; i < name.length; i++) {
                if (!TextUtils.isEmpty(dischargerType1) && name[i].contains(dischargerType1)) {
                    bigTypePos = i;
                    break;
                }else if(dischargerType1.contains("其他") && name[i].equals("其他")) {
                    bigTypePos = i;
                    break;
                }

            }

            baseRecyleAdapter.setCurSelectPos(bigTypePos);
            if(!TextUtils.isEmpty(dischargerType1) &&dischargerType1.contains("其他")){
                String other = dischargerType1.substring(3);
                edt_other_two.setVisibility(View.VISIBLE);
                edt_other_two.setText(other);
            }

//            int smallTypePos = -1;
//            String[] smallTypename = null;
//            switch (bigTypePos) {
//                case 0:
//                    smallTypename = name1;
//                    break;
//                case 1:
//                    smallTypename = name2;
//                    break;
//                case 2:
//                    smallTypename = name3;
//                    break;
//                case 3:
//                    smallTypename = name4;
//                    break;
//            }
//
//            if (smallTypename != null) {
//                for (int i = 0; i < smallTypename.length; i++) {
//                    if (!TextUtils.isEmpty(dischargerType2) && dischargerType2.contains(smallTypename[i])) {
//                        smallTypePos = i;
//                        break;
//                    }
//
//                    if (i == smallTypename.length - 1 && !TextUtils.isEmpty(dischargerType2)) {
//                        smallTypePos = smallTypename.length - 1;
//                    }
//                }
//
//                setList1(smallTypename);
//                if(!TextUtils.isEmpty(dischargerType2) &&dischargerType2.contains("其他：")){
//                    String other = dischargerType2.substring(3);
//                    edt_other_two.setVisibility(View.VISIBLE);
//                    edt_other_two.setText(other);
//                }
//                sewerageTableTwoChildAdapter.clearsele();
//                sewerageTableTwoChildAdapter.setCurSelectPos(smallTypePos);
//            }

        }



    }

    public EditText getEditOther(){
        return edt_other_two;
    }

    public void setEditMode(boolean isEdit) {
        if (isEdit != EDIT_MODE) {
            EDIT_MODE = isEdit;
            setView(pshAffairDetail);
        }
    }



    protected void initData() {
        pshAffairDetail = (SecondLevelPshInfo.SecondPshInfo) getArguments().getSerializable("data");
        isAllowSaveLocalDraft = getArguments().getBoolean("isAllowSaveLocalDraft",false);
        userInfo = new ArrayList<>();
        setList();
        myGridLayoutManager = new GridLayoutManager(getActivity(), 2);
     rv_table1.addItemDecoration(new SpacesItemDecoration(10));
     rv_table1.setLayoutManager(myGridLayoutManager);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(R.layout.gv_item_sewersgetable_two, BR.tableinfo);

        baseRecyleAdapter = new SewerageTableTwoAdapter(userInfo, map,-1);

     rv_table1.setAdapter(baseRecyleAdapter);
//        baseRecyleAdapter.setCurSelectPos(0);


        baseRecyleAdapter.setOnRecycleitemOnClic(new BaseRecyleAdapter.OnRecycleitemOnClic() {
            @Override
            public void onItemClic(View view, int position) {
                if (!EDIT_MODE && pshAffairDetail != null) {
                    return;
                }
                edt_other_two.setVisibility(View.GONE);
                baseRecyleAdapter.onItemClic(view, position);
//                selecet = null;
//                switch (position) {
//                    case 0:
//                        selecet = name1;
//                        break;
//                    case 1:
//                        selecet = name2;
//                        break;
//                    case 2:
//                        selecet = name3;
//                        break;
//                    case 3:
//                        selecet = name4;
//                        break;
//                }
//                if (userInfo2.contains(selecet[0]))
//                    return;
//
//                setList1(selecet);
//                sewerageTableTwoChildAdapter.clearsele();
//                sewerageTableTwoChildAdapter.notifyDataSetChanged();
                if(name[position].equals("其他")){
                    edt_other_two.setVisibility(View.VISIBLE);
                    SecondLevelPshInfoManger.getInstance().setPshtype3(name[position]+"："+edt_other_two.getText().toString().trim());
                }else{
                    edt_other_two.setVisibility(View.GONE);
                    SecondLevelPshInfoManger.getInstance().setPshtype3(name[position]);
                }

//                SecondLevelPshInfoManger.getInstance().setPshtype3(name[position]);
//                SecondLevelPshInfoManger.getInstance().setPshtype2("");


            }
        });

        myGridLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        rv_table2.addItemDecoration(new SpacesItemDecoration(10));
        rv_table2.setLayoutManager(myGridLayoutManager1);
        Map<Integer, Integer> map1 = new HashMap<>();
        map1.put(R.layout.gv_item_sewersgetable_two, BR.tableinfo);

        edt_other_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString().trim();
                SecondLevelPshInfoManger.getInstance().setPshtype3("其他："+text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //走到这调用清除，即默认不选中小类
        selecet =  name1;
        setList1(selecet);
        sewerageTableTwoChildAdapter = new SewerageTableTwoAdapter(userInfo2, map1);
        sewerageTableTwoChildAdapter.clearsele();
//        SecondLevelPshInfoManger.getInstance().setPshtype3(name[0]);
        // sewerageTableTwoChildAdapter.setIsadds(false);//设置true多选，false为单选
        rv_table2.setAdapter(sewerageTableTwoChildAdapter);
//        sewerageTableTwoChildAdapter.setOnRecycleitemOnClic(new BaseRecyleAdapter.OnRecycleitemOnClic() {
//            @Override
//            public void onItemClic(View view, int position) {
//                if (!EDIT_MODE && pshAffairDetail != null) {
//                    return;
//                }
//                sewerageTableTwoChildAdapter.onItemClic(view, position);
//                if(selecet[position].equals("其他")){
//                    edt_other_two.setVisibility(View.VISIBLE);
//                    SecondLevelPshInfoManger.getInstance().setPshtype2(selecet[position]+edt_other_two.getText().toString().trim());
//                }else{
//                    edt_other_two.setVisibility(View.GONE);
//                }
//                SecondLevelPshInfoManger.getInstance().setPshtype2(selecet[position]);
//            }
//        });

        if (pshAffairDetail != null) {
            setView(pshAffairDetail);
        }else{
            tv_notice.setVisibility(View.VISIBLE);
            tv_notice2.setVisibility(View.VISIBLE);
        }
        SetMaxLengthUtils.setMaxLength(edt_other_two,50);
        if(isAllowSaveLocalDraft){
            setEditMode(true);
        }
    }
   /* public void setDefault(){
        SewerageBeanManger.getInstance().setDischargerType1(name[0]);
    }*/

    /*   public boolean checkOtherIsNull(){
           boolean isNull=false;
           if(TextUtils.isEmpty(edt_other_two.getText().toString())){
               isNull=true;
           }
           return isNull;
       }
   */
    private void setList() {
        for (int i = 0; i < name.length; i++) {
            ItemSewerageItemInfo userInfo = new ItemSewerageItemInfo();
            userInfo.setName(name[i]);
            this.userInfo.add(userInfo);
        }
    }


    private void setList1(String[] childname) {
        userInfo2.clear();
        for (int i = 0; i < childname.length; i++) {
            ItemSewerageItemInfo userInfo = new ItemSewerageItemInfo();
            userInfo.setName(childname[i]);
            this.userInfo2.add(userInfo);
        }
    }

    
    public interface onViewCreatedListener {
        void onViewCreated();
    }

    public SecondPshTwoFragment.onViewCreatedListener viewCreatedListener;

    public void setViewCreatedListener(SecondPshTwoFragment.onViewCreatedListener viewCreatedListener) {
        this.viewCreatedListener = viewCreatedListener;
    }

}

