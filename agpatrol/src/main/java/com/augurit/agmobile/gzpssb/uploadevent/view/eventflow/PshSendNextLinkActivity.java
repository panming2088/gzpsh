package com.augurit.agmobile.gzpssb.uploadevent.view.eventflow;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.BaseActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.model.Result;
import com.augurit.agmobile.gzps.common.widget.AutoBreakViewGroup;
import com.augurit.agmobile.gzps.common.widget.MyGridView;
import com.augurit.agmobile.gzps.uploadevent.adapter.NextLinkAssigneersAdapter;
import com.augurit.agmobile.gzps.uploadevent.dao.GetPersonByOrgApiData;
import com.augurit.agmobile.gzps.uploadevent.model.Assigneers;
import com.augurit.agmobile.gzps.uploadevent.model.NextLinkOrg;
import com.augurit.agmobile.gzps.uploadevent.model.OrgItem;
import com.augurit.agmobile.gzps.uploadevent.model.Person;
import com.augurit.agmobile.gzps.uploadevent.service.UploadEventService;
import com.augurit.agmobile.gzpssb.common.view.MultiTakePhotoTableItem;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.service.PSHUploadEventService;
import com.augurit.agmobile.mapengine.common.base.OnRecyclerItemClickListener;
import com.augurit.agmobile.patrolcore.common.util.MaxLengthInputFilter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.searchview.util.adapter.TextWatcherAdapter;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.DialogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ?????????????????????
 * <p>
 * Created by liangsh on 2017/11/15.
 */

public class PshSendNextLinkActivity extends BaseActivity {

    private String taskInstId;
    private String currLinkCode;
    private ArrayList<PSHEventDetail.NextlinkBean> nextlinkBeans;

    private AutoBreakViewGroup radio_nextlink;   //???????????????????????????
    private View ll_nextlilnk_org;     //?????????????????????????????????

    private AutoBreakViewGroup radio_nextlink_org;   //???????????????????????????
    private MyGridView gv_assignee;

    private NextLinkAssigneersAdapter assigneersAdapter;
    private List<Assigneers> mAssigneersList;      //?????????????????????
    private Assigneers.Assigneer selAssignee;     //????????????????????????????????????
    private MultiTakePhotoTableItem photo_item;
    private EditText et_content;
    private View btn_cancel;
    private View btn_submit;
    private RadioGroup.LayoutParams params;
    private ProgressDialog pd;

    private PSHEventDetail.NextlinkBean selectedNextlinkBean;    //???????????????????????????
    private List<NextLinkOrg> mNextLinkOrgList;               //??????????????????(R2)
    private NextLinkOrg selectedNextLinkOrg;                  //???????????????????????????

    private String procInstId;
    private String sjid;
    private String isSendMessage = "0";//???????????????,1?????????

    private ViewGroup ll_nextlilnk_org_Rg_Rm;
    private AutoBreakViewGroup radio_nextlink_org_Rg_Rm;
    private List<OrgItem> orgItems;

    private CheckBox cb_is_send_message;
    private int wtsbId;
    private String state = "";
    private TextView tv_requiredTag1;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonYes;
    private RadioButton mRadioButtonNo;
    private LinearLayout ll_zhifa;
    private boolean takePhoto = false;
    private boolean isZhifa = false;
    private boolean isContent = true;
    private Long sfzf = null;
    private String name;
    private TextView next_opintion;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psh_send_nextlink);
        this.taskInstId = getIntent().getStringExtra("taskInstId");
        wtsbId = getIntent().getIntExtra("wtsbId", 0);
        this.currLinkCode = getIntent().getStringExtra("currLinkCode");
        this.name = getIntent().getStringExtra("name");
        this.state = getIntent().getStringExtra("state");
        this.procInstId = getIntent().getStringExtra("procInstId");
        this.sjid = getIntent().getStringExtra("sjid");

        this.nextlinkBeans = (ArrayList<PSHEventDetail.NextlinkBean>) getIntent().getSerializableExtra("nextLink");
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initListener();
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("?????????????????????");
        radio_nextlink = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink);
        ll_nextlilnk_org = findViewById(R.id.ll_nextlilnk_org);

        ll_zhifa = (LinearLayout) findViewById(R.id.ll_zhifa);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_zhifa);
        mRadioButtonYes = (RadioButton) findViewById(R.id.rb_zhifa_yes);
        mRadioButtonNo = (RadioButton) findViewById(R.id.rb_zhifa_no);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_zhifa_yes:
                        sfzf = 1l;
                        photo_item.setRequired(true);
                        takePhoto = true;
                        tv_requiredTag1.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_zhifa_no:
                        sfzf = 0l;
                        photo_item.setRequired(false);
                        takePhoto = false;
                        tv_requiredTag1.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //??????????????????
        radio_nextlink_org = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink_org);
        //?????????????????????
        gv_assignee = (MyGridView) findViewById(R.id.gv_assignee);
        tv_requiredTag1 = (TextView) findViewById(R.id.tv_requiredTag1);
        next_opintion = (TextView) findViewById(R.id.next_opintion);
        if(!StringUtil.isEmpty(name)){
            next_opintion.setText(name+"???");
        }

        //Rg??????Rm??????????????????????????????
        ll_nextlilnk_org_Rg_Rm = (ViewGroup) findViewById(R.id.ll_nextlilnk_org_Rg_Rm);
        radio_nextlink_org_Rg_Rm = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink_org_Rg_Rm);

        photo_item = (MultiTakePhotoTableItem) findViewById(R.id.photo_item);
        photo_item.setPhotoExampleEnable(false);
        if(!StringUtil.isEmpty(state) && "0".equals(state)){
            photo_item.setRequired(true);
            takePhoto = true;
            tv_requiredTag1.setVisibility(View.VISIBLE);
        }else if(!StringUtil.isEmpty(state) && "1".equals(state)){
            ll_zhifa.setVisibility(View.VISIBLE);
            tv_requiredTag1.setVisibility(View.VISIBLE);
            isZhifa = true;
        }

        if(!StringUtil.isEmpty(state) && "2".equals(state)){
            isContent = false;
            tv_requiredTag1.setVisibility(View.GONE);
        }
        et_content = (EditText) findViewById(R.id.textfield_content);


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);


        /*if(!GzpsConstant.LINK_SEND_TASK.equals(currLinkCode)){
            ll_nextlilnk_org.setVisibility(View.GONE);
        }*/
        assigneersAdapter = new NextLinkAssigneersAdapter(this);
        gv_assignee.setAdapter(assigneersAdapter);
        assigneersAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<Assigneers.Assigneer>() {
            @Override
            public void onItemClick(View view, int position, Assigneers.Assigneer selectedData) {
                selAssignee = selectedData;
            }

            @Override
            public void onItemLongClick(View view, int position, Assigneers.Assigneer selectedData) {

            }
        });


        int screenWidths = getScreenWidths();
        params = new AutoBreakViewGroup.LayoutParams(screenWidths / 3, AutoBreakViewGroup
                .LayoutParams
                .WRAP_CONTENT);
        for (int i = 0; i < nextlinkBeans.size(); i++) {
            RadioButton radioButton = new RadioButton(PshSendNextLinkActivity.this);
            radioButton.setText(nextlinkBeans.get(i).getLinkname());
            radioButton.setLayoutParams(params);
            radio_nextlink.addView(radioButton);
        }
        if (!ListUtil.isEmpty(nextlinkBeans)) {
//            radio_nextlink.check(0);
        }

        //?????????R2?????????(????????????)
//        if(GzpsConstant.LINK_SEND_TASK.equals(currLinkCode)){
//            ll_nextlilnk_org.setVisibility(View.VISIBLE);
//            //???????????????????????????????????????
//
//            getNextLinkOrgLst();
//        }
//
//        //???????????????R0???????????????
//        if(GzpsConstant.LINK_PROBLEM_REPORT.equals(currLinkCode)
//                || GzpsConstant.LINK_GET_TASK.equals(currLinkCode)
//                || GzpsConstant.LINK_TASK_FH.equals(currLinkCode)
//                || GzpsConstant.LINK_REPORT_TASK_FH.equals(currLinkCode)){
//
//            User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
//            if(GzpsConstant.LINK_PROBLEM_REPORT.equals(currLinkCode) && (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4]) )){
//                //Rg??????Rm
//                ll_nextlilnk_org_Rg_Rm.setVisibility(View.VISIBLE);
//                ll_nextlilnk_org.setVisibility(View.VISIBLE);
//                getOrgList();
//
//                //??????????????????
//
//            }else {
//                //????????????????????????????????????
//                getNextLinkAssigneers();
//                ll_nextlilnk_org_Rg_Rm.setVisibility(View.GONE);
//
//            }
//
//        }

        cb_is_send_message = (CheckBox) findViewById(R.id.cb_is_send_message);
        //cb_is_send_message.setVisibility(View.GONE);//???????????????????????????
    }

    private void initListener() {
        final TextView tv_size = (TextView) findViewById(R.id.tv_size);
        final int maxTotal = 200;
        et_content.setFilters(new InputFilter[]{new MaxLengthInputFilter(maxTotal,
                null, et_content, "??????????????????" + maxTotal + "??????").setDismissErrorDelay(1500)});
        et_content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                try {
                    String inputText = s.toString();
                    if (TextUtils.isEmpty(inputText)) {
                        tv_size.setText("0/" + maxTotal);
                        return;
                    }
                    tv_size.setText(inputText.getBytes("GB2312").length / 2 + "/" + maxTotal);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        radio_nextlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                int index = ((RadioGroup) radioButton.getParent()).indexOfChild(radioButton);
                selectedNextlinkBean = nextlinkBeans.get(index);

//                //Rg??????Rm selAssignee?????????
//                User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
//                if(GzpsConstant.LINK_PROBLEM_REPORT.equals(currLinkCode) && (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                        || user.getRoleCode().contains(GzpsConstant.roleCodes[4]) )) {
//
//                }else {
//                    selAssignee = null;
//                }
//
//
//                //????????????????????????????????????????????????????????????????????????????????????
//                if(GzpsConstant.LINK_END.equals(selectedNextlinkBean.getLinkcode())){
//                    ll_nextlilnk_org.setVisibility(View.GONE);
//                    return;
//                }
//                if(!ListUtil.isEmpty(mAssigneersList)){
//                    List<Assigneers.Assigneer> assigneers = null;
//                    //????????????????????????????????????????????????????????????
//                    for(Assigneers a : mAssigneersList){
//                        if(a.getActivityName().equals(selectedNextlinkBean.getLinkcode())){
//                            assigneers = a.getAssigneers();
//                            break;
//                        }
//                    }
//                    if(ListUtil.isEmpty(assigneers)){
//                        ll_nextlilnk_org.setVisibility(View.GONE);
//                    } else {
//                        ll_nextlilnk_org.setVisibility(View.VISIBLE);
//                        int selPosition = -1;
//                        for(int i=0; i<assigneers.size(); i++){
//                            if(assigneers.get(i).isDefault()){
//                                selPosition = i;
//                                selAssignee = assigneers.get(i);
//                                break;
//                            }
//                        }
//                        assigneersAdapter.notifyDatasetChanged(assigneers, selPosition);
//                    }
//                } else if(!ListUtil.isEmpty(mNextLinkOrgList)){
//                    ll_nextlilnk_org.setVisibility(View.VISIBLE);
//                    assigneersAdapter.setSelectedPosition(-1);
//                }
            }
        });
        radio_nextlink_org.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selAssignee = null;
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                int index = ((RadioGroup) radioButton.getParent()).indexOfChild(radioButton);
                selectedNextLinkOrg = mNextLinkOrgList.get(index);
                assigneersAdapter.notifyDatasetChanged(selectedNextLinkOrg.getUserFormList());
                assigneersAdapter.setSelectedPosition(-1);
            }
        });


        radio_nextlink_org_Rg_Rm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
                int index = ((RadioGroup) radioButton.getParent()).indexOfChild(radioButton);
                OrgItem orgItem = orgItems.get(index);
                getPersonByOrgCodeAndName(orgItem.getName(), orgItem.getCode());

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RxView.clicks(btn_submit)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (selectedNextlinkBean == null) {
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "?????????????????????");
                            return;
                        }
//                        if(GzpsConstant.LINK_SEND_TASK.equals(currLinkCode)
//                                && !GzpsConstant.LINK_END.equals(selectedNextlinkBean.getLinkcode())){
//                            //?????????????????????????????????????????????????????????
//                            if(selectedNextLinkOrg == null
//                                    || selAssignee == null){
//                                ToastUtil.shortToast(PshSendNextLinkActivity.this, "??????????????????????????????");
//                                return;
//                            }
//                        }
//                        if(GzpsConstant.LINK_PROBLEM_REPORT.equals(currLinkCode)
//                                || GzpsConstant.LINK_GET_TASK.equals(currLinkCode)
//                                || GzpsConstant.LINK_TASK_FH.equals(currLinkCode)
//                                || GzpsConstant.LINK_REPORT_TASK_FH.equals(currLinkCode)){
//
//                            User user = new LoginService(getApplicationContext(), AMDatabase.getInstance()).getUser();
//                            if(GzpsConstant.LINK_PROBLEM_REPORT.equals(currLinkCode) && (user.getRoleCode().contains(GzpsConstant.roleCodes[3])
//                                    || user.getRoleCode().contains(GzpsConstant.roleCodes[4]) )) {
//                                //Rg??????Rm
//                                if(selAssignee == null){
//                                    ToastUtil.shortToast(PshSendNextLinkActivity.this, "??????????????????????????????");
//                                    return;
//                                }
//                            }else {
//                                List<Assigneers.Assigneer> assigneers = null;
//                                if(mAssigneersList == null){
//                                    ToastUtil.shortToast(PshSendNextLinkActivity.this, "??????????????????????????????");
//                                    return;
//                                }
//                                for (Assigneers assigneers1 : mAssigneersList) {
//                                    if (assigneers1.getActivityName().equals(selectedNextlinkBean.getLinkcode())) {
//                                        assigneers = assigneers1.getAssigneers();
//                                    }
//                                }
//                                if (!ListUtil.isEmpty(assigneers) && selAssignee == null) {
//                                    ToastUtil.shortToast(PshSendNextLinkActivity.this, "??????????????????????????????");
//                                    return;
//                                }
//                            }
//                        }
                        if(isZhifa && sfzf == null){
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "????????????????????????");
                            return;
                        }

                        List<Photo> photos = photo_item.getSelectedPhotos();
                        if(takePhoto && (ListUtil.isEmpty(photos) ||  photos.size()<2)){
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "??????????????????????????????");
                            return;
                        }
                        if (isContent && StringUtil.isEmpty(et_content.getText().toString())) {
                            if(!StringUtil.isEmpty(name)){
                                ToastUtil.shortToast(PshSendNextLinkActivity.this, "?????????"+name);
                            }else {
                                ToastUtil.shortToast(PshSendNextLinkActivity.this, "???????????????");
                            }
                            return;
                        }

                        //??????????????????
                        if (cb_is_send_message.isChecked()) {
                            isSendMessage = "1";
                        } else {
                            isSendMessage = "0";
                        }

//                        String nextLinkOrg = null;
//                        if(selectedNextLinkOrg != null){
//                            nextLinkOrg = selectedNextLinkOrg.getCode();
//                        }
//                        String assigneeCode = null;
//                        String assigneeName = null;
//                        if(selAssignee != null){
//                            assigneeCode = selAssignee.getUserCode();
//                            assigneeName = selAssignee.getUserName();
//                        }

                        String advice = et_content.getText().toString();
                        final PSHUploadEventService eventService = new PSHUploadEventService(PshSendNextLinkActivity.this);
                        eventService.wfSend(wtsbId,
                                selectedNextlinkBean.getLinkcode(), Integer.parseInt(state),
                                sfzf,
                                advice,
                                photos)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        pd = new ProgressDialog(PshSendNextLinkActivity.this);
                                        pd.show();
                                    }
                                })
                                .subscribe(new Subscriber<Boolean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        pd.dismiss();
                                        ToastUtil.shortToast(PshSendNextLinkActivity.this, "???????????????????????????");
                                    }

                                    @Override
                                    public void onNext(Boolean s) {
                                        if (!s) {
                                            ToastUtil.shortToast(getApplicationContext(), "????????????????????????");
                                            pd.dismiss();
                                            return;
                                        }
                                        pd.dismiss();
                                        setResult(456);
                                        finish();
                                        ToastUtil.shortToast(PshSendNextLinkActivity.this, "????????????");

                                    }
                                });
                    }
                });
    }

    private void getPersonByOrgCodeAndName(String name, String code) {
        new UploadEventService(getApplicationContext()).getPersonByOrgApiDataObservable(code, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetPersonByOrgApiData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetPersonByOrgApiData getPersonByOrgApiData) {
                        List<Person> personList = getPersonByOrgApiData.getUserFormList();
                        if (personList != null && !personList.isEmpty()) {
                            List<Assigneers.Assigneer> assigneeList = new ArrayList<>();
                            for (Person person : personList) {
                                assigneeList.add(new Assigneers.Assigneer(person.getCode(), person.getName()));
                            }
                            assigneersAdapter.notifyDatasetChanged(assigneeList);
                        }
                    }
                });
    }

    private void getOrgList() {
        new UploadEventService(getApplicationContext()).getOrgItemList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrgItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrgItem> list) {
                        if (list != null &&
                                !list.isEmpty()) {

                            orgItems = list;
                            int size = orgItems.size();
                            for (int i = 0; i < size; i++) {
                                RadioButton radioButton = new RadioButton(PshSendNextLinkActivity.this);
                                radioButton.setText(orgItems.get(i).getName());
                                //    radioButton.setTag(nextLinkOrgs);
                                radioButton.setLayoutParams(params);
                                radio_nextlink_org_Rg_Rm.addView(radioButton);

                                if (i == 0) {
                                    radioButton.setChecked(true);
                                }
                            }


                        }

                    }
                });
    }

    private int getScreenWidths() {
        WindowManager manager = PshSendNextLinkActivity.this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }


    /**
     * ?????????????????????????????????????????????????????????????????????
     * ?????????????????????????????????(??????????????????????)
     */
    private void getNextLinkOrgLst() {
        UploadEventService eventService = new UploadEventService(PshSendNextLinkActivity.this);
        eventService.getNextLinkOrg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NextLinkOrg>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<NextLinkOrg> nextLinkOrgs) {
                        if (ListUtil.isEmpty(nextLinkOrgs)) {
                            ll_nextlilnk_org.setVisibility(View.GONE);
                            return;
                        }
                        mNextLinkOrgList = nextLinkOrgs;
                        for (int i = 0; i < nextLinkOrgs.size(); i++) {
                            RadioButton radioButton = new RadioButton(PshSendNextLinkActivity.this);
                            radioButton.setText(nextLinkOrgs.get(i).getName());
                            radioButton.setTag(nextLinkOrgs);
                            radioButton.setLayoutParams(params);
                            radio_nextlink_org.addView(radioButton);
                        }
//                        radio_nextlink_org.check(0);
                    }
                });
    }

    /**
     * ????????????????????????????????????
     */
    private void getNextLinkAssigneers() {
        new UploadEventService(getApplicationContext()).getNextActivityInfo(taskInstId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pd = new ProgressDialog(PshSendNextLinkActivity.this);
                        pd.setMessage("???????????????????????????????????????...");
                        pd.show();
                        radio_nextlink.setEnabled(false);
                    }
                })
                .subscribe(new Subscriber<Result<List<Assigneers>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        pd.dismiss();
                        ToastUtil.shortToast(PshSendNextLinkActivity.this, "?????????????????????????????????");
                    }

                    @Override
                    public void onNext(Result<List<Assigneers>> assigneersList) {
                        pd.dismiss();
                        if (!assigneersList.isSuccess()) {
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "?????????????????????????????????");
                            return;
                        }
                        mAssigneersList = assigneersList.getResult();
                        ll_nextlilnk_org.setVisibility(View.VISIBLE);
                        radio_nextlink_org.setVisibility(View.GONE);
                        radio_nextlink.setEnabled(true);
                    }
                });
    }


    private void saveJdFile() {
        UploadEventService uploadEventService = new UploadEventService(this.getApplicationContext());
        uploadEventService.saveJdFile(taskInstId, photo_item.getSelectedPhotos())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        pd.dismiss();
                        ToastUtil.longToast(PshSendNextLinkActivity.this, "???????????????????????????????????????????????????");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        pd.dismiss();
                        if (aBoolean) {
                            setResult(456);
                            finish();
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "????????????");
                        } else {
                            ToastUtil.shortToast(PshSendNextLinkActivity.this, "???????????????");
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        DialogUtil.MessageBox(PshSendNextLinkActivity.this, "??????", "?????????????????????????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * ?????????????????????????????????????????????????????????Activity???????????????
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {

        if (photo_item != null) {
            photo_item.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
