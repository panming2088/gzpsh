package com.augurit.agmobile.gzps.test;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import com.augurit.agmobile.gzps.BaseActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.gzps.PatrolMainActivity;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.LoginConstant;
import com.augurit.agmobile.gzps.common.service.ConfigureNetCallback;
import com.augurit.agmobile.gzps.common.service.TestConfigureService;
import com.augurit.agmobile.gzps.common.util.KeyBoardHelper;
import com.augurit.agmobile.gzps.common.util.SystemUtils;
import com.augurit.agmobile.gzps.login.FunUpdateInfoService;
import com.augurit.agmobile.gzps.login.ILoginCallback;
import com.augurit.agmobile.gzps.login.PatrolLoginService;
import com.augurit.agmobile.gzps.login.model.FunUpdateInfoModel;
import com.augurit.agmobile.gzps.login.utils.FunUpdateConstant;
import com.augurit.agmobile.mapengine.common.utils.FilePathUtil;
import com.augurit.agmobile.mapengine.layermanage.service.LayerServiceFactory;
import com.augurit.agmobile.patrolcore.common.action.dao.local.ActionDBLogic;
import com.augurit.agmobile.patrolcore.common.action.dao.remote.ActionNetLogic;
import com.augurit.agmobile.patrolcore.common.action.model.ActionModel;
import com.augurit.agmobile.patrolcore.common.table.dao.TableDataManager;
import com.augurit.agmobile.patrolcore.common.table.dao.remote.TableNetCallback;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.common.Callback2;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.login.model.User;
import com.augurit.am.cmpt.login.service.ILoginService;
import com.augurit.am.cmpt.login.view.adapter.UserNameAdapter;
import com.augurit.am.cmpt.maintenance.traffic.service.TrafficService;
import com.augurit.am.cmpt.permission.PermissionsUtil;
import com.augurit.am.cmpt.update.utils.CheckUpdateUtils;
import com.augurit.am.cmpt.update.utils.UpdateState;
import com.augurit.am.cmpt.update.view.ApkUpdateManager;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.log.util.NetUtil;
import com.augurit.am.fw.utils.MaxLengthInputFilter;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.common.ValidateUtil;
import com.augurit.am.fw.utils.file.SharedPreferencesUtil;
import com.augurit.am.fw.utils.log.LogUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ???????????????Activity
 *
 * @author ????????? ???xiejiexin
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agpatrol
 * @createTime ???????????? ???2017-03-03
 * @modifyBy ????????? ???xiejiexin
 * @modifyTime ???????????? ???2017-03-03
 * @modifyMemo ???????????????
 */
public class LoginActivity2 extends BaseActivity {
    private ILoginService mLoginService;
    private View btn_setting;
    private AutoCompleteTextView at_userName;
    private EditText et_pwd;
    private TextInputLayout til_login_name;
    private TextInputLayout til_login_password;
    private View btn_login;
    private ViewGroup ll_cb_save_password;      //??????????????????
    private CheckBox cb_save_pwd;
    private boolean mIsUserNameExists = true;      // ?????????????????????
    private boolean mIsSelectLoginName = false;
    private boolean mIsUpdateProject = true; // ??????????????????
    private boolean mDistUpdate = false;//????????????????????????
    private boolean mProjectUpdate = false;//????????????????????????
    private boolean mMenuItemUpdate = false;//????????????????????????

    private ActionNetLogic mActionNetLogic;
    private ActionDBLogic mLocalMenuStorageDao;

    private FunUpdateInfoService mFunUpdateInfoService;//????????????
    private SharedPreferencesUtil mSharedPreferencesUtil;
    private final static String UPDATE_TIME = "update_time";
    private SimpleDateFormat CurrentTime;
    private View layoutContent;
    private View layoutBottom;
    private KeyBoardHelper boardHelper;
    private int bottomHeight;
    private String[] appUpdateUrlArr = {LoginConstant.APP_UPDATE_ONE, LoginConstant.APP_UPDATE_TWO};
    private String inStall_flag;
    private boolean firstInit = true;

    private View ll_top;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        mLocalMenuStorageDao = new ActionDBLogic();
        mLoginService = new PatrolLoginService(this, AMDatabase.getInstance());//xcl 2017-03-21 ??????service????????????
        initView();
        initValue();
        checkVersionUpdate();
        checkLowestSystem();
        initListener();
        createNoMediaFile();

        // AMDatabase.init(getApplicationContext());
        // startTrafficStatsService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * ??????APP????????????
     */
    private void checkLowestSystem() {
        SystemUtils.checkSystemInfo(this, new SystemUtils.CheckCallBack() {
            @Override
            public void onSuccess(boolean lowest_memory, boolean lowest_version) {
                if (!lowest_memory && !lowest_version) {
                    showSystemWarning("?????????????????????3G?????????????????????5.0??????");
                } else if (!lowest_memory) {
                    showSystemWarning("?????????????????????3G??????");
                } else if (!lowest_version) {
                    showSystemWarning("????????????????????????5.0??????");
                }
            }
        });
    }

    /**
     * ?????????????????????
     */
    private void showSystemWarning(String warning) {
        // ???????????????
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("????????????")
                .setMessage(warning)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK == keyCode) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.login_lyrl_dialog);
    }

    private void createNoMediaFile() {
        try {
            String savePath = new FilePathUtil(this.getApplicationContext()).getSavePath();
            String noMediaFile = savePath + "/.nomedia";
            File file = new File(noMediaFile);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {

        }
    }

    /**
     * ????????????????????????????????????
     */
    private KeyBoardHelper.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelper.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;
            if (bottomHeight > height) {
                layoutBottom.setVisibility(View.GONE);
            } else {
                int offset = bottomHeight - height;
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                        .getLayoutParams();
                lp.topMargin = offset;
                layoutContent.setLayoutParams(lp);
            }

        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != layoutBottom.getVisibility()) {
                layoutBottom.setVisibility(View.VISIBLE);
            }
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutContent
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                layoutContent.setLayoutParams(lp);
            }

        }
    };

    /**
     * ???????????????
     */
    private void initView() {
        btn_setting = findViewById(R.id.btn_login_setting);
        btn_login = findViewById(R.id.btn_login);
        at_userName = (AutoCompleteTextView) findViewById(R.id.at_login_name);
        et_pwd = (EditText) findViewById(R.id.et_login_password);
        til_login_name = (TextInputLayout) findViewById(R.id.til_login_name);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);
        cb_save_pwd = (CheckBox) findViewById(R.id.cb_login_save_password);
        ll_cb_save_password = (ViewGroup) findViewById(R.id.ll_cb_save_password);
        if (!LoginConstant.SAVE_PASSWORD) {
            // ????????????????????????????????????????????????????????????
            ll_cb_save_password.setVisibility(View.GONE);
        }
        layoutContent = findViewById(R.id.rl_login_form);
        layoutBottom = findViewById(R.id.ll_bottm);
        inStall_flag = new SharedPreferencesUtil(LoginActivity2.this).getString("install_flag", "");
        //        if (TextUtils.isEmpty(BaseInfoManager.getUserName(LoginActivity.this))) {
        //            ToastUtil.longToast(LoginActivity.this,"????????????????????????,????????????123456");
        //        }

        ll_top = findViewById(R.id.ll_top);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
    }

    /**
     * ????????????
     */
    private void initValue() {
        // ??????????????????
        //at_userName.setText(LoginConstant.DEFAULT_USERNAME);    // ???????????????????????????
        String loginName = BaseInfoManager.getLoginName(LoginActivity2.this);
        if (!TextUtils.isEmpty(loginName) && LoginConstant.SAVE_PASSWORD) {
            at_userName.setText(loginName);
        }
        User user = mLoginService.getUser();   // ???????????????????????????
        if (!ValidateUtil.isObjectNull(user)) {
            if (LoginConstant.SAVE_USERNAME) {   // ????????????????????????????????????????????????
                at_userName.setText(user.getLoginName());
                at_userName.setSelection(user.getLoginName().length());
            }
            // ??????
            if (LoginConstant.SAVE_PASSWORD && !TextUtils.isEmpty(user.getRemark()) && user.getRemark().equals("1")) { // ???????????????????????????????????????????????????mLoginService.getPasswordState()
                et_pwd.setText(user.getPassword());
                et_pwd.setSelection(user.getPassword().length());
                cb_save_pwd.setChecked(true);
            }
        }
        // ????????????
        String serverUrl = mLoginService.getServerUrl();
        if (!TextUtils.isEmpty(serverUrl)) {
            LoginConstant.SERVER_URL = serverUrl;
        }
        String supportUrl = mLoginService.getSupportUrl();
        if (!TextUtils.isEmpty(supportUrl)) {
            LoginConstant.SUPPORT_URL = supportUrl;
        }
        mIsUpdateProject = mLoginService.getUpdateProjectState();
        CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mSharedPreferencesUtil = new SharedPreferencesUtil(this);
    }

    /**
     * ???????????????
     */
    private void initListener() {
        btn_setting.setOnClickListener(mOnClickListener);
        btn_login.setOnClickListener(mOnClickListener);
        ll_cb_save_password.setOnClickListener(mOnClickListener);
        // ???????????????????????????
        at_userName.setFilters(new InputFilter[]{new MaxLengthInputFilter(LoginConstant.MAX_LENGTH_USERNAME,
                til_login_name, at_userName, getString(R.string.login_error_max_length)).setDismissErrorDelay(1500)});
        // ???????????????????????????
        final List<User> userList = mLoginService.getUsersShowInHistory(); // ??????????????????????????????????????????
        final UserNameAdapter userNameAdapter = new UserNameAdapter(this, userList);
        userNameAdapter.setOnDeleteClickListener(new UserNameAdapter.OnItemClickListener() {
            @Override
            public void onNameClick(View v, User user) {
                // ????????????????????????????????????
                mIsSelectLoginName = true;
                at_userName.setText(user.getLoginName());
                at_userName.setSelection(user.getLoginName().length());
                at_userName.dismissDropDown();
                if (LoginConstant.SAVE_PASSWORD && !TextUtils.isEmpty(user.getRemark()) && user.getRemark().equals("1")) {//mLoginService.getPasswordState()
                    et_pwd.setText(user.getPassword());
                    cb_save_pwd.setChecked(true);
                } else {
                    cb_save_pwd.setChecked(false);
                }
                mIsSelectLoginName = false;
            }

            @Override
            public void onDeleteClick(View v, User user) {
                // ???????????????????????????????????????????????????????????????
                mLoginService.saveUser(user, false);
            }
        });
        at_userName.setThreshold(1);
        at_userName.setAdapter(userNameAdapter);
        at_userName.setDropDownVerticalOffset(2);
        at_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                if (LoginConstant.SAVE_USERNAME) {   // ????????????????????????????????????????????????
                    if (TextUtils.isEmpty(inputText)) {
                        return;
                    }
                    if (!firstInit) {
                        userNameAdapter.getFilter().filter(inputText);
                        at_userName.showDropDown();
                        firstInit = false;
                    }
                }
                if (!mIsUserNameExists) { // ???????????????????????????????????????????????????????????????
                    checkUserName();
                }
                if (!mIsSelectLoginName) {
                    et_pwd.setText("");
                }
                for (User user : userList) {
                    if (LoginConstant.SAVE_PASSWORD
                            && !TextUtils.isEmpty(user.getRemark())
                            && user.getRemark().equals("1") && user.getLoginName().equals(inputText)) {
                        et_pwd.setText(user.getPassword());
                        cb_save_pwd.setChecked(true);
                        break;
                    } else {
                        cb_save_pwd.setChecked(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        at_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {   // ??????????????????????????????
                    checkUserName();
                }
            }
        });
        // ????????????????????????
        et_pwd.setFilters(new InputFilter[]{new MaxLengthInputFilter(LoginConstant.MAX_LENGTH_PASSWORD,
                til_login_password, et_pwd, getString(R.string.login_error_max_length)).setDismissErrorDelay(1500)});
        et_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                                && KeyEvent.ACTION_DOWN == event.getAction())) {
                    loginWithCheck();   // ????????????Enter???????????????
                }
                return false;
            }
        });

        boardHelper = new KeyBoardHelper(this);
        boardHelper.onCreate();
        boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        layoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = layoutBottom.getHeight();
            }
        });
        // hideInput(this,at_userName);


        /**
         * ???????????????????????????????????????
         */
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old????????????????????????????????????????????????old??????????????????????????????????????????
              ???????????????????????????Activity???????????????????????????1/3????????????????????????????????????*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>"+(oldBottom - bottom));
                    int dist = content.getBottom() - bottom;
                    if (dist>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomIn(ll_top, dist);
                    }
                    service.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>"+(bottom - oldBottom));
                    if ((content.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //??????????????????logo???????????????????????????????????????????????????
                        zoomOut(ll_top);
                    }
                    service.setVisibility(View.VISIBLE);
                }
            }
        });

        service = findViewById(R.id.version);
        content = findViewById(R.id.rl_login_form);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //??????????????????
        keyHeight = screenHeight / 3;//??????????????????????????????1/3
    }

    private int screenHeight = 0;//????????????
    private int keyHeight = 0; //??????????????????????????????
    private float scale = 0.4f; //logo????????????
    private View service,content;
    private int height = 0 ;
    /**
     * ??????
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * f??????
     * @param view
     */
    public void zoomOut(final View view) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    private void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * ????????????????????????
     */
    private void startTrafficStatsService() {
        Intent MainService = new Intent(this, TrafficService.class);
        startService(MainService);//????????????
    }

    /**
     * ????????????
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    if (NetUtil.isConnected(LoginActivity2.this.getApplicationContext())) {
                        //??????
                        loginWithCheck();
                    } else {
                       // ToastUtil.shortToast(LoginActivity.this, "????????????????????????????????????");
                      //  loginOfflineWithCheck();
                        ToastUtil.shortToast(LoginActivity2.this, "????????????????????????????????????");
                    }
                    break;
                case R.id.btn_login_setting:
                    setting();
                    break;
                case R.id.ll_cb_save_password:
                    cb_save_pwd.performClick();
                    break;
            }
        }
    };

    /**
     * ??????????????????
     */
    private void checkFunUpdateInfo() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "??????", "????????????????????????");
        mFunUpdateInfoService = new FunUpdateInfoService(this);
        mFunUpdateInfoService.getFunUpdateInfo().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<FunUpdateInfoModel>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtil.shortToast(LoginActivity2.this, "????????????????????????");
            }

            @Override
            public void onNext(List<FunUpdateInfoModel> funUpdateInfoModels) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                String time = mSharedPreferencesUtil.getString(UPDATE_TIME, "0000-00-00 00:00:00.0");
                Date lastTime;
                try {
                    lastTime = CurrentTime.parse(time);

                    for (FunUpdateInfoModel model : funUpdateInfoModels) {
                        if (model.getType().equals(FunUpdateConstant.DICITIONARY)
                                && CurrentTime.parse(model.getDate()).getTime() > lastTime.getTime()) {//????????????
                            mDistUpdate = true;
                        } else if (model.getType().equals(FunUpdateConstant.LAYER_PROJECT)
                                && CurrentTime.parse(model.getDate()).getTime() > lastTime.getTime()) {//????????????
                            mProjectUpdate = true;
                        } else if (model.getType().equals(FunUpdateConstant.FUN_MENU)
                                && CurrentTime.parse(model.getDate()).getTime() > lastTime.getTime()) {//????????????
                            mMenuItemUpdate = true;
                        }
                    }
                    //??????????????????
                    updateConfigureData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * ???????????????????????????
     */
    public void loginWithCheck() {
        /*PermissionsUtil2.getInstance()
                .requestPermissions(
                        LoginActivity2.this,
                        "????????????????????????????????????????????????????????????", 0,
                        new PermissionsUtil2.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                // checkGPSState();
                                login();
                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.KILL_BACKGROUND_PROCESSES,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION);*/

        PermissionsUtil.getInstance()
                .requestPermissions(
                        LoginActivity2.this,
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                // checkGPSState();
                                if(perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        && perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                    login();
                                } else {
                                    ToastUtil.shortToast(LoginActivity2.this, "????????????????????????????????????");
                                }
                            }

                            @Override
                            public void onPermissionsDenied(List<String> perms) {

                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.KILL_BACKGROUND_PROCESSES,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void checkGPSState() {
        //????????????????????????????????????GPS????????????
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ok) {
            login();
        } else {
            ToastUtil.shortToast(this, "????????????????????????GPS????????????");
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 323);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 323) {
            //????????????????????????????????????GPS????????????
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!ok) {
                ToastUtil.longToast(this, "GPS?????????,???????????????????????????");
            }
            login();
        }
    }

    /**
     * ??????
     */
    // @NeedPermission(permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void login() {
        final String userName = at_userName.getText().toString().trim();
        final String password = et_pwd.getText().toString().trim();
        final User lastUser = mLoginService.getOnlineUser();
        if (!verifyInput()) {   // ?????????????????????????????????
            return;
        }
        final String url = "http://" + LoginConstant.SERVER_URL + LoginConstant.LOGIN_URL + userName + "/" + password;
        // ???????????????????????????
        btn_login.setEnabled(false);
        mLoginService.loginGet(url, new Callback2<User>() {
            @Override
            public void onSuccess(User user) {
                if (StringUtil.isEmpty(user.getLoginName())
                        || StringUtil.isEmpty(user.getPassword())) {
                    // ???????????????????????????????????????
                    ToastUtil.shortToast(LoginActivity2.this, "???????????????????????????");
                    // ??????????????????
                    btn_login.setEnabled(true);
                    return;
                }
                if (inStall_flag.equals("") || inStall_flag.equals("ceshi")) {
                    //???????????????????????????????????????
                    String serial = android.os.Build.SERIAL;
                    checkInstallInfo(user.getLoginName(), user.getUserName(), serial);
                }
                //??????????????????url
                BaseInfoManager.setUserId(LoginActivity2.this, user.getId());
                BaseInfoManager.setUserOrg(LoginActivity2.this, user.getOrgName());
                BaseInfoManager.setUserName(LoginActivity2.this, user.getUserName());
                BaseInfoManager.setUserId(LoginActivity2.this, user.getUserName());
                BaseInfoManager.setLoginName(LoginActivity2.this, user.getLoginName());
                BaseInfoManager.setServerUrl(LoginActivity2.this, LoginConstant.SERVER_URL);
                BaseInfoManager.setSupportUrl(LoginActivity2.this, LoginConstant.SUPPORT_URL);
                //??????imtoken
                BaseInfoManager.setImToken(LoginActivity2.this, user.getToken());
                mActionNetLogic = new ActionNetLogic(LoginActivity2.this, "http://" + LoginConstant.SERVER_URL + "/");
                // ????????????
                LogUtil.json(new Gson().toJson(user));
                //????????????token

                // ?????????????????????
                user.setPassword(password);// ???????????????????????????
                user.setRemark(cb_save_pwd.isChecked() ? "1" : "0");
                mLoginService.saveUser(user);   // ????????????
                mLoginService.saveOnlineUser(user);
                mLoginService.savePasswordState(cb_save_pwd.isChecked());   // ??????????????????CheckBox????????????
                // ???????????????????????????
                if (lastUser == null || !lastUser.getLoginName().equals(userName)) {
                    mIsUpdateProject = true;
                }
                if (mIsUpdateProject) {//|| BuildConfig.API_ENV
                    updateConfigureData();
                } else {
                    // TODO: 17/8/10 ??????????????????
                    checkFunUpdateInfo();
                }
                btn_login.setEnabled(true);
            }

            @Override
            public void onFail(Exception error) {
                error.printStackTrace();
                // ??????????????????
                btn_login.setEnabled(true);
             //   if (!error.getLocalizedMessage().contains("????????????????????????") && !error.getLocalizedMessage().contains("????????????")) {
             //       showLoginOfflineDialog();
             //   } else {
                    ToastUtil.shortToast(LoginActivity2.this, error.getLocalizedMessage());
             //   }
            }
        });
    }

    private void checkInstallInfo(final String loginName, final String userName, final String ime) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                String url = "http://" + LoginConstant.GZPSH_AGSUPPORT + "/rest/installRecord/saveInstallInf";
                OkHttpClient client = new OkHttpClient.Builder().build();
                RequestBody formBody = new FormBody.Builder().add("login_name", loginName).add
                        ("user_name", userName).add
                        ("device_code", ime).build();
                Request request = new Request.Builder().url(url).post(formBody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            subscriber.onNext(response.body().string());
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("message");
                    if (msg.equals("????????????")) {
                        new SharedPreferencesUtil(LoginActivity2.this).setString("install_flag",
                                loginName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    private void updateConfigureData() {
        if (mIsUpdateProject || mDistUpdate || mProjectUpdate || mMenuItemUpdate) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "??????", "??????????????????...");
            //????????????????????????
            String time = CurrentTime.format(new Date(System.currentTimeMillis()));
            mSharedPreferencesUtil.setString(UPDATE_TIME, time);

            final TestConfigureService configureService = new TestConfigureService(this);
            String url = "http://" + LoginConstant.SERVER_URL + "/";
            if (mIsUpdateProject || mProjectUpdate) {
                progressDialog.setMessage("????????????????????????");
                deleteProjectFolder();//?????????????????????????????????????????????
            }
            if (mIsUpdateProject || mDistUpdate) {
                progressDialog.setMessage("????????????????????????");
                configureService.updateFromNet(url, new ConfigureNetCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        if (mIsUpdateProject || mMenuItemUpdate) {
                            progressDialog.setMessage("????????????????????????");
                            updateMenuConfigure(new ILoginCallback() {
                                @Override
                                public void onSuccess(String msg) {
                                    progressDialog.dismiss();
                                    mLoginService.saveUpdateProjectState(false);
                                    Toast.makeText(LoginActivity2.this, "????????????", Toast.LENGTH_SHORT).show();
                                    jumpToMain();
                                }

                                @Override
                                public void onError(String msg) {
                                    Toast.makeText(LoginActivity2.this, msg, Toast.LENGTH_SHORT).show();
                                    btn_login.setEnabled(true);
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity2.this, "????????????", Toast.LENGTH_SHORT).show();
                            jumpToMain();
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        progressDialog.dismiss();
                        btn_login.setEnabled(true);
                        ToastUtil.longToast(LoginActivity2.this, msg);
                    }
                });
            } else if (mMenuItemUpdate) {
                progressDialog.setMessage("????????????????????????");
                updateMenuConfigure(new ILoginCallback() {
                    @Override
                    public void onSuccess(String msg) {
                        progressDialog.dismiss();
                        mLoginService.saveUpdateProjectState(false);
                        Toast.makeText(LoginActivity2.this, "????????????", Toast.LENGTH_SHORT).show();
                        jumpToMain();
                    }

                    @Override
                    public void onError(String msg) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity2.this, msg, Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(true);
                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity2.this, "????????????", Toast.LENGTH_SHORT).show();
                jumpToMain();
            }
        } else {
            Toast.makeText(LoginActivity2.this, "????????????", Toast.LENGTH_SHORT).show();
            jumpToMain();
        }
    }

    /**
     * ???????????????????????????
     */
    public void loginOfflineWithCheck() {
        /*PermissionsUtil2.getInstance()
                .requestPermissions(
                        LoginActivity2.this,
                        "????????????????????????????????????????????????????????????", 0,
                        new PermissionsUtil2.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                loginOffline();
                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);*/

        PermissionsUtil.getInstance()
                .requestPermissions(
                        LoginActivity2.this,
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                if(perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        && perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)){
                                    loginOffline();
                                } else {
                                    ToastUtil.shortToast(LoginActivity2.this, "????????????????????????????????????");
                                }
                            }

                            @Override
                            public void onPermissionsDenied(List<String> perms) {

                            }
                        },
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * ????????????
     */
    private void loginOffline() {
        btn_login.setEnabled(false);
        final String userName = at_userName.getText().toString().trim();
        final String password = et_pwd.getText().toString().trim();
        if (!verifyInput()) {
            return;
        }
        mLoginService.loginOffline(userName, password, new Callback2<User>() {
            @Override
            public void onSuccess(User user) {
                if (StringUtil.isEmpty(user.getLoginName())
                        || StringUtil.isEmpty(user.getPassword())) {
                    // ???????????????????????????????????????
                    ToastUtil.shortToast(LoginActivity2.this, "???????????????????????????");
                    // ??????????????????
                    btn_login.setEnabled(true);
                    return;
                }
                //??????????????????url
                BaseInfoManager.setUserId(LoginActivity2.this, user.getId());
                BaseInfoManager.setServerUrl(LoginActivity2.this, LoginConstant.SERVER_URL);

                // ????????????
                LogUtil.json(new Gson().toJson(user));
                // ?????????????????????
                user.setPassword(password);     // ???????????????????????????
                mLoginService.saveUser(user);   // ????????????
                mLoginService.savePasswordState(cb_save_pwd.isChecked());   // ??????????????????CheckBox????????????

                Toast.makeText(LoginActivity2.this, "??????????????????", Toast.LENGTH_SHORT).show();
                jumpToMain();
            }

            @Override
            public void onFail(Exception error) {
                error.printStackTrace();
                btn_login.setEnabled(true);
                // ??????????????????
                Toast.makeText(LoginActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                btn_login.setEnabled(true);
            }
        });
    }

    /**
     * ????????????
     */
    private void checkVersionUpdate() {

        checkVersionUpdateWithPermissonCheck();

    }

    private void checkVersionUpdateWithPermissonCheck() {
        String updateUrl = appUpdateUrlArr[new Random().nextInt(appUpdateUrlArr.length)];
        //String updateUrl = "http://" + LoginConstant.BASE_GZPS_URL + "/appFile/apk_version.json";
        CheckUpdateUtils.setServerUrl(updateUrl);
        new ApkUpdateManager(this, UpdateState.INNER_UPDATE, new ApkUpdateManager.NoneUpdateCallback() {
            @Override
            public void onFinish(boolean isNeedUpdate) {

            }
        }).checkUpdate();
    }

    /**
     * ????????????????????????????????????????????????
     */
    private void showLoginOfflineDialog() {
        if (mLoginService.canLoginOffline()) {
            View view = View.inflate(this, R.layout.login_alert_dialog, null);
            View btn_positive = view.findViewById(R.id.btn_positive);
            View btn_negative = view.findViewById(R.id.btn_negative);
            final Dialog dialog = new Dialog(this);
            btn_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //??????????????????????????????
                    loginOfflineWithCheck();
                }
            });
            btn_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            dialog.show();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (KeyEvent.KEYCODE_BACK == keyCode) {
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(R.drawable.bookmark_lyrl_dialog);
        }
    }

    /**
     * ?????????????????????
     */
    private void setting() {
        // ???????????????
        final ViewGroup view_setting = (ViewGroup) getLayoutInflater().inflate(R.layout.login_dialog_settings, null);
        Button btn_save = (Button) view_setting.findViewById(R.id.btn_save);        // ????????????
        final TextInputLayout til_server_url = (TextInputLayout) view_setting.findViewById(R.id.til_server_url);
        final EditText et_server_url = (EditText) view_setting.findViewById(R.id.et_server_url);  // ???????????????
        final Switch sw_login_update_proj = (Switch) view_setting.findViewById(R.id.sw_login_update_proj);    // ??????????????????
        // ????????????
        et_server_url.setText(TextUtils.isEmpty(mLoginService.getServerUrl()) ?
                LoginConstant.SERVER_URL : mLoginService.getServerUrl());
        et_server_url.setSelection(et_server_url.getText().length());
        sw_login_update_proj.setChecked(mLoginService.getUpdateProjectState());
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("??????")
                .setView(view_setting)
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // ????????????
                        String serverUrl = et_server_url.getText().toString();
                        if (serverUrl.isEmpty()) {
                            til_server_url.setError("??????????????????");
                            return;
                        }
                        // ?????????????????????
                        mLoginService.saveServerUrl(serverUrl);
                        BaseInfoManager.setServerUrl(LoginActivity2.this, serverUrl);
                        mLoginService.saveUpdateProjectState(sw_login_update_proj.isChecked());
                        //????????????ip????????????????????????static?????????
                        LoginConstant.SERVER_URL = serverUrl;
                        //????????????????????????????????????
                        mIsUpdateProject = sw_login_update_proj.isChecked();

                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view_setting);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_BACK == keyCode) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.drawable.login_lyrl_dialog);
    }

    /**
     * ???????????????????????????
     */
    private void checkUserName() {
        String userName = at_userName.getText().toString();
        String url = "http://" + LoginConstant.SERVER_URL + LoginConstant.CHECK_USERNAME_URL + userName;
        mLoginService.checkUserName(url, new Callback2<Boolean>() {
            @Override
            public void onSuccess(final Boolean aBoolean) {
                mIsUserNameExists = aBoolean;
                if (!aBoolean) {
                    til_login_name.setError("??????????????????");
                }

            }

            @Override
            public void onFail(Exception error) {
            }
        });
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @return ????????????????????????
     */
    private boolean verifyInput() {
        String userName = at_userName.getText().toString();
        String password = et_pwd.getText().toString();
        if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
            til_login_name.setError("??????????????????");
            til_login_password.setError("???????????????");
            at_userName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            til_login_name.setError("??????????????????");
            at_userName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            til_login_password.setError("???????????????");
            et_pwd.requestFocus();
            return false;
        }
        return mIsUserNameExists;
    }

    public void updateNetData() {
        if (mIsUpdateProject) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "??????", "????????????????????????!");
            final TableDataManager tableDataManager = new TableDataManager(this);
            String serverUrl = BaseInfoManager.getBaseServerUrl(this);
            String url = serverUrl + "rest/agdic/allDics";

            tableDataManager.getDictionaryByNet(url, new TableNetCallback() {
                @Override
                public void onSuccess(Object data) {
                    tableDataManager.setDictionaryToDB((List<DictionaryItem>) data);
                    progressDialog.dismiss();
                    jumpToMain();
                }

                @Override
                public void onError(String msg) {
                    ToastUtil.longToast(LoginActivity2.this, msg);
                }
            });
        } else {
            jumpToMain();
        }
    }

    /**
     * ????????????Activity
     */
    private void jumpToMain() {
        startActivity(new Intent(this, PatrolMainActivity.class));
        //startActivity(new Intent(this,TestBaiduLocationWithArcgisActivity.class));
        finish();
    }

    /**
     * ??????????????????
     */
    private void updateMenuConfigure(final ILoginCallback callback) {
        if (mActionNetLogic == null) {
            mActionNetLogic = new ActionNetLogic(LoginActivity2.this, "http://" + LoginConstant.SERVER_URL + "/");
        }
        mActionNetLogic.getAllfeatures().map(new Func1<List<ActionModel>, List<ActionModel>>() {

            @Override
            public List<ActionModel> call(List<ActionModel> actionModels) {
                for (ActionModel model : actionModels) {
                    model.setUserId(BaseInfoManager.getUserId(LoginActivity2.this));
                }
                return actionModels;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ActionModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("??????????????????????????????");
                    }

                    @Override
                    public void onNext(List<ActionModel> actionModels) {
                        mLocalMenuStorageDao.setTableItemsToDB(actionModels, BaseInfoManager.getUserId(LoginActivity2.this));
                        callback.onSuccess("??????????????????????????????");
                    }
                });
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void deleteProjectFolder() {
        LayerServiceFactory.provideLayerService(this).deleteProjectFolder();//??????/project ?????????
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boardHelper.onDestory();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsUtil2.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}
