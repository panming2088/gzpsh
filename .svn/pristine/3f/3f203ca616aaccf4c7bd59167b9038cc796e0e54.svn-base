package com.augurit.agmobile.gzps.drainage_unit_monitor.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.uploadevent.model.ProblemFeatureOrAddr;
import com.augurit.agmobile.gzpssb.uploadevent.model.SelectFinishEvent;
import com.augurit.agmobile.gzpssb.uploadevent.view.eventdetail.PshSelectLocationActivity;
import com.augurit.agmobile.gzpssb.uploadevent.view.uploadevent.EventSelectMapActivity;
import com.augurit.agmobile.mapengine.map.geometry.LatLng;
import com.augurit.agmobile.patrolcore.layer.view.PatrolLayerPresenter;
import com.augurit.agmobile.patrolcore.selectlocation.util.SelectLocationConstant;
import com.augurit.am.fw.utils.StringUtil;
import com.augurit.am.fw.utils.view.ToastUtil;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author: liangsh
 * @createTime: 2021/5/19
 */
public class SelectFacilityView extends LinearLayout {

    protected TextView tv_title;
    protected TextView tv_requiredTag;
    protected EditText et_addr;
    protected View iv_location;

    private ProblemFeatureOrAddr featureOrAddr;
    private double problemX = 0;
    private double problemY = 0;
    private String addr;
    private boolean addrEditale = true;
    private boolean readOnly = false;

    private int selectType = 0; //0选择所有（排水户、井，空地）；1排水户、空地；2井、空地；3空地


    public SelectFacilityView(Context context) {
        this(context, null);
    }

    public SelectFacilityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectFacilityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        LayoutInflater.from(context).inflate(R.layout.layout_select_facility_view, this);
        tv_title = findViewById(R.id.tv_title);
        tv_requiredTag = findViewById(R.id.tv_requiredTag);
        et_addr = findViewById(R.id.et_addr);
        iv_location = findViewById(R.id.iv_location);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectFacilityView);
            String title = a.getString(R.styleable.SelectFacilityView_sfv_title);
            if (!TextUtils.isEmpty(title)) {
                tv_title.setVisibility(VISIBLE);
            }
            a.recycle();
        }
        iv_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readOnly) {
                    if (problemX <= 0 || problemY <= 0) {
                        ToastUtil.shortToast(getContext(), "地址信息缺失");
                        return;
                    }
                    Intent intent = new Intent(getContext(), PshSelectLocationActivity.class);
                    intent.putExtra(SelectLocationConstant.IF_READ_ONLY, true);
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_LOCATION, new LatLng(problemY,
                            problemX));
                    intent.putExtra(SelectLocationConstant.DESTINATION_OR_LASTTIME_SELECT_ADDRESS, addr);
                    intent.putExtra(SelectLocationConstant.INITIAL_SCALE, PatrolLayerPresenter.initScale);
                    getContext().startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getContext(), EventSelectMapActivity.class);
                intent.putExtra("title", "请选择井或空地");
                intent.putExtra("selectType", selectType);
                if (problemX != 0 && problemY != 0) {
                    Point point = new Point();
                    point.setX(problemX);
                    point.setY(problemY);
                    intent.putExtra("geometry", point);
                }
                getContext().startActivity(intent);
            }
        });
        setAddrEditable(addrEditale);
    }

    /**
     * 选择位置或设施后的事件回调
     *
     * @param event
     */
    @Subscribe
    public void onReceivedFinishedSelectEvent2(SelectFinishEvent event) {
        if (event != null) {
            featureOrAddr = event.featureOrAddr;
//            if(TextUtils.isEmpty(featureOrAddr.getAddr())){
//                et_addr.setText(featureOrAddr.getName());
//            } else {
            et_addr.setText(featureOrAddr.getAddr());
//            }
            Geometry geometry = event.mGeometry;
            if (geometry instanceof Point) {
                Point point = (Point) geometry;
                problemX = point.getX();
                problemY = point.getY();
            }
        }
    }

    public ProblemFeatureOrAddr getValue() {
        if (featureOrAddr == null) {
            return null;
        }
        featureOrAddr.setAddr(et_addr.getText().toString());
        return featureOrAddr;
    }

    public void setValue(ProblemFeatureOrAddr featureOrAddr) {
        this.featureOrAddr = featureOrAddr;
        addr = featureOrAddr.getAddr();
        problemX = featureOrAddr.getX();
        problemY = featureOrAddr.getY();
        et_addr.setText(addr);
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public void setAddrEditable(boolean editable) {
        this.addrEditale = editable;
        et_addr.setEnabled(editable);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        setAddrEditable(!readOnly);
    }

    public void setRequired(boolean required) {
        tv_requiredTag.setVisibility(required ? VISIBLE : GONE);
    }

    public void clear() {
        et_addr.setText("");
        problemX = 0;
        problemY = 0;
        featureOrAddr = null;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
