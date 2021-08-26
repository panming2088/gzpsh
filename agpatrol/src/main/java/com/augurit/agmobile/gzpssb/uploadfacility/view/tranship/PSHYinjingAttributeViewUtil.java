package com.augurit.agmobile.gzpssb.uploadfacility.view.tranship;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.OnSpinnerChangedEvent;
import com.augurit.agmobile.gzps.common.widget.SpinnerTableItem;
import com.augurit.agmobile.gzps.componentmaintenance.util.ComponentFieldKeyConstant;
import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.YinjingAttributes;
import com.augurit.agmobile.gzps.uploadfacility.view.correctorconfirmfacility.ReadOnlyAttributeView;
import com.augurit.agmobile.gzpssb.common.facilityownership.PSHFacilityOwnerShipUnitView;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.login.router.LoginRouter;
import com.augurit.am.cmpt.widget.spinner.AMSpinner;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.upload.util
 * @createTime 创建时间 ：17/12/18
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：17/12/18
 * @modifyMemo 修改备注：
 */

public class PSHYinjingAttributeViewUtil {

    /**
     * 属性名称
     */
    public static final String ATTRIBUTE_ONE = "窨井类型";
    public static final String ATTRIBUTE_TWO = "雨污类别";
    public static final String ATTRIBUTE_THREE = "井盖材质";
    public static final String ATTRIBUTE_FOUR = "权属单位";
    public static final String ATTRIBUTE_FIVE = "已挂牌编号";

    /**
     * 属性在设施图层中的key
     */
    public static final String ATTRIBUTE_ONE_KEY = ComponentFieldKeyConstant.SUBTYPE;
    public static final String ATTRIBUTE_TWO_KEY = ComponentFieldKeyConstant.SORT;
    public static final String ATTRIBUTE_THREE_KEY = ComponentFieldKeyConstant.MATERIAL;
    public static final String ATTRIBUTE_FOUR_KEY = ComponentFieldKeyConstant.OWNERDEPT;
    public static final String ATTRIBUTE_FIVE_KEY = ComponentFieldKeyConstant.LICENSE;

    private YinjingAttributes yinjingAttributes;

    private PSHFacilityOwnerShipUnitView facilityOwnerShipUnitView;

//    private TextItemTableItem attributeFiveItem;

    private EditText attributeFiveItem;
    private boolean fiveItemChecked = true;

    private Map<String, String> originAttributeValue = new HashMap<>(5);


    /**
     * 排序下拉字典数组
     *
     * @param list
     */
    private static void processOrder(List<DictionaryItem> list) {
        for (DictionaryItem item : list) {
            String code = item.getCode();
            String target = code;
            if (code.length() > 1) {
                target = code.replaceAll("[^(0-9)]", "");//去掉所有字母
            }
            item.setCode(target);
        }
        //再排序
        Collections.sort(list, new Comparator<DictionaryItem>() {
            @Override
            public int compare(DictionaryItem t1, DictionaryItem t2) {
                int num1 = Integer.valueOf(t1.getCode());
                int num2 = Integer.valueOf(t2.getCode());
                int result = 0;
                if (num1 > num2) {
                    result = 1;
                }

                if (num1 < num2) {
                    result = -1;
                }
                return result;
            }
        });

    }


    public void addYinjingAttributes(Context context, Map<String, Object> originValue, ViewGroup attributeContainer,
                                     Map<String, String> defaultSelectedValue) {

        this.yinjingAttributes = new YinjingAttributes();

        if (defaultSelectedValue != null) {
            addAttributeOne(context, originValue, defaultSelectedValue.get(ATTRIBUTE_ONE), attributeContainer);
            addAttributeTwo(context, originValue, defaultSelectedValue.get(ATTRIBUTE_TWO), attributeContainer);
            addAttributeThree(context, originValue, defaultSelectedValue.get(ATTRIBUTE_THREE), attributeContainer);
            addAttributeFour(context, originValue, defaultSelectedValue.get(ATTRIBUTE_FOUR), attributeContainer);
            addAttributeFive(context, originValue, defaultSelectedValue.get(ATTRIBUTE_FIVE), attributeContainer);

        } else {
            addAttributeOne(context, originValue, null, attributeContainer);
            addAttributeTwo(context, originValue, null, attributeContainer);
            addAttributeThree(context, originValue, null, attributeContainer);
            addAttributeFour(context, originValue, null, attributeContainer);
            addAttributeFive(context, originValue, null, attributeContainer);
        }

    }


    /**
     * 窨井属性一
     *
     * @param context
     * @param originValue          设施图层中的属性，当不是核准时，为null
     * @param defaultSelectedValue 默认选中的值
     * @param container
     */
    private void addAttributeOne(Context context, Map<String, Object> originValue, String defaultSelectedValue, ViewGroup container) {

        TableDBService dbService = new TableDBService(context);
        List<DictionaryItem> a158 = dbService.getDictionaryByTypecodeInDB("A212");
        processOrder(a158);

        List<String> allowValues = new ArrayList<>();
        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        for (DictionaryItem dictionaryItem : a158) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }

        this.yinjingAttributes.setAttributeOneAllowValues(allowValues);

        if (defaultSelectedValue != null && " ".equals(defaultSelectedValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        String componentOriginValue = getComponentOriginValue(originValue, ATTRIBUTE_ONE);
        if (componentOriginValue != null && " ".equals(componentOriginValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        SpinnerTableItem spinnerTableItem = new SpinnerTableItem(context);
        spinnerTableItem.setSpinnerData(spinnerData);
        spinnerTableItem.setTextViewName(ATTRIBUTE_ONE);
        spinnerTableItem.setRequereTag();
        spinnerTableItem.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                DictionaryItem dictionaryItem = ((DictionaryItem) item);
                yinjingAttributes.setAttributeOne(dictionaryItem.getName());
                EventBus.getDefault().post(new OnSpinnerChangedEvent(ATTRIBUTE_ONE, dictionaryItem.getName(), dictionaryItem));
            }
        });


        /**
         * 先判断OriginValue是否有值，如果有，那么将其放入originAttributeValue中，此时如果defaultValue没有值，那么默认的下拉框显示的就是originValue，
         * 如果defaultValue有值，显示的是defaultValue；
         */

        if (componentOriginValue != null) {
            originAttributeValue.put(ATTRIBUTE_ONE, componentOriginValue);
            if (defaultSelectedValue == null) {
                boolean b = spinnerTableItem.selectItem(componentOriginValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeOne(componentOriginValue);
            } else {
                boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeOne(defaultSelectedValue);
            }
        } else if (defaultSelectedValue != null) {
            //如果originValue为空，但是defaultValue不为空
            boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
            if (!b){
                DictionaryItem item = new DictionaryItem();
                item.setName(componentOriginValue);
                spinnerTableItem.put(componentOriginValue, item);
                spinnerTableItem.selectItem(componentOriginValue);

            }
            originAttributeValue.put(ATTRIBUTE_ONE, defaultSelectedValue);
            yinjingAttributes.setAttributeOne(defaultSelectedValue);
        } else {
            spinnerTableItem.selectItem(0);
        }
        container.addView(spinnerTableItem);
    }

    /***
     * 属性二
     *
     * @param context
     * @param originValue 设施图层中的属性，当不是核准时，为null
     */
    private void addAttributeTwo(final Context context, final Map<String, Object> originValue, String defaultSelectedValue, ViewGroup container) {

        TableDBService dbService = new TableDBService(context);
        List<DictionaryItem> a163 = dbService.getDictionaryByTypecodeInDB("A163");
        processOrder(a163);

        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        List<String> allowValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : a163) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }
        this.yinjingAttributes.setAttributeTwoAllowValues(allowValues);

        if (defaultSelectedValue != null && " ".equals(defaultSelectedValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        String componentOriginValue = getComponentOriginValue(originValue, ATTRIBUTE_TWO);
        if (componentOriginValue != null && " ".equals(componentOriginValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        final SpinnerTableItem spinnerTableItem = new SpinnerTableItem(context);
        spinnerTableItem.setSpinnerData(spinnerData);
        spinnerTableItem.setTextViewName(ATTRIBUTE_TWO);
        spinnerTableItem.setRequereTag();
        spinnerTableItem.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, final Object item) {
                /**
                 * 满足以下条件时弹出对话框提示：
                 * 1. 当初处于核准
                 * 2. 前后选择的选项不一样时
                 */
                if (originValue != null && yinjingAttributes != null
                        && yinjingAttributes.getAttributeTwo() != null
                        && !yinjingAttributes.getAttributeTwo().equals(((DictionaryItem) item).getName())) {
                    new AlertDialog.Builder(context)
                            .setMessage("是否确认要修改雨污属性?")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    yinjingAttributes.setAttributeTwo(((DictionaryItem) item).getName());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    spinnerTableItem.selectItem(yinjingAttributes.getAttributeTwo());
                                }
                            })
                            .show();
                } else {
                    yinjingAttributes.setAttributeTwo(((DictionaryItem) item).getName());
                }
            }
        });

        /**
         * 先判断OriginValue是否有值，如果有，那么将其放入originAttributeValue中，此时如果defaultValue没有值，那么默认的下拉框显示的就是originValue，
         * 如果defaultValue有值，显示的是defaultValue；
         */
        if (componentOriginValue != null) {
            originAttributeValue.put(ATTRIBUTE_TWO, componentOriginValue);
            if (defaultSelectedValue == null) {
                boolean b = spinnerTableItem.selectItem(componentOriginValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeTwo(componentOriginValue);
            } else {
                boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeTwo(defaultSelectedValue);
            }
        } else if (defaultSelectedValue != null) {
            //如果originValue为空，但是defaultValue不为空
            boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
            if (!b){
                DictionaryItem item = new DictionaryItem();
                item.setName(componentOriginValue);
                spinnerTableItem.put(componentOriginValue, item);
                spinnerTableItem.selectItem(componentOriginValue);

            }
            originAttributeValue.put(ATTRIBUTE_TWO, defaultSelectedValue);
            yinjingAttributes.setAttributeTwo(defaultSelectedValue);
        } else {
            spinnerTableItem.selectItem(0);
        }
        container.addView(spinnerTableItem);
    }

    /***
     * 属性三
     *
     * @param context
     * @param originValue 设施图层中的属性，当不是核准时，为null
     */
    private void addAttributeThree(Context context, Map<String, Object> originValue, String defaultSelectedValue, ViewGroup container) {
        TableDBService dbService = new TableDBService(context);
        List<DictionaryItem> a159 = dbService.getDictionaryByTypecodeInDB("A159");
        processOrder(a159);

        final Map<String, Object> spinnerData = new LinkedHashMap<>();
        List<String> allowValues = new ArrayList<>();
        for (DictionaryItem dictionaryItem : a159) {
            allowValues.add(dictionaryItem.getName());
            spinnerData.put(dictionaryItem.getName(), dictionaryItem);
        }
        this.yinjingAttributes.setAttributeThreeAllowValues(allowValues);

        if (defaultSelectedValue != null && " ".equals(defaultSelectedValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        String componentOriginValue = getComponentOriginValue(originValue, ATTRIBUTE_THREE);
        if (componentOriginValue != null && " ".equals(componentOriginValue)) {
            DictionaryItem dictionaryItem = new DictionaryItem();
            dictionaryItem.setName(" ");
            spinnerData.put(" ", dictionaryItem);
        }

        SpinnerTableItem spinnerTableItem = new SpinnerTableItem(context);
        spinnerTableItem.setSpinnerData(spinnerData);
        spinnerTableItem.setTextViewName(ATTRIBUTE_THREE);
        spinnerTableItem.setRequereTag();
        spinnerTableItem.setOnSpinnerChangeListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                yinjingAttributes.setAttributeThree(((DictionaryItem) item).getName());
            }
        });

        /**
         * 先判断OriginValue是否有值，如果有，那么将其放入originAttributeValue中，此时如果defaultValue没有值，那么默认的下拉框显示的就是originValue，
         * 如果defaultValue有值，显示的是defaultValue；
         */

        if (componentOriginValue != null) {
            originAttributeValue.put(ATTRIBUTE_THREE, componentOriginValue);
            if (defaultSelectedValue == null) {
                boolean b = spinnerTableItem.selectItem(componentOriginValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeThree(componentOriginValue);
            } else {
                boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
                if (!b){
                    DictionaryItem item = new DictionaryItem();
                    item.setName(componentOriginValue);
                    spinnerTableItem.put(componentOriginValue, item);
                    spinnerTableItem.selectItem(componentOriginValue);

                }
                yinjingAttributes.setAttributeThree(defaultSelectedValue);
            }
        } else if (defaultSelectedValue != null) {
            //如果originValue为空，但是defaultValue不为空
            boolean b = spinnerTableItem.selectItem(defaultSelectedValue);
            if (!b){
                DictionaryItem item = new DictionaryItem();
                item.setName(componentOriginValue);
                spinnerTableItem.put(componentOriginValue, item);
                spinnerTableItem.selectItem(componentOriginValue);

            }
            originAttributeValue.put(ATTRIBUTE_THREE, defaultSelectedValue);
            yinjingAttributes.setAttributeThree(defaultSelectedValue);
        } else {
            spinnerTableItem.selectItem(0);
        }
        container.addView(spinnerTableItem);
    }

    /***
     * 属性四
     *
     * @param context
     * @param originValue 设施图层中的属性，当不是核准时，为null
     * @param
     */
    private void addAttributeFour(Context context,
                                  Map<String, Object> originValue, String defaultValue, ViewGroup attributelistContainer) {
        String componentOriginValue = getComponentOriginValue(originValue, ATTRIBUTE_FOUR);
        String loginName = new LoginRouter(context, AMDatabase.getInstance()).getUser().getLoginName();

        if (componentOriginValue != null) {
            originAttributeValue.put(ATTRIBUTE_FOUR, componentOriginValue);
        } else {
            originAttributeValue.put(ATTRIBUTE_FOUR, defaultValue);
        }


        String text = "";
        if (defaultValue == null) {
            if (componentOriginValue != null) {
                text = componentOriginValue;
            }
        } else {
            text = defaultValue;
        }
        facilityOwnerShipUnitView = new PSHFacilityOwnerShipUnitView(context, loginName, text);
        facilityOwnerShipUnitView.addTo(attributelistContainer);
        yinjingAttributes.setAttributeFour(text);
    }


    /**
     * 属性五
     * @param originMap 设施图层中的属性，当不是核准时，为null
     */
    private void addAttributeFive(Context context, Map<String, Object> originMap, String defaultValue, ViewGroup attributelistContainer) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_attribute_five, null);
        final EditText et = (EditText) view.findViewById(R.id.et_1);
        attributeFiveItem = et;
        final CheckBox cb_yes = (CheckBox) view.findViewById(R.id.cb_yes);
        final CheckBox cb_no = (CheckBox) view.findViewById(R.id.cb_no);
        cb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fiveItemChecked = true;
                    et.setEnabled(true);
                } else {
                    fiveItemChecked = false;
                    et.setEnabled(false);
                }
                cb_no.setChecked(!isChecked);
            }
        });
        cb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fiveItemChecked = false;
                    et.setEnabled(false);
                } else {
                    fiveItemChecked = true;
                    et.setEnabled(true);
                }
                cb_yes.setChecked(!isChecked);
            }
        });

        if ("无".equals(defaultValue)
                || "".equals(defaultValue)) {
            cb_no.setChecked(true);
        } else {
            et.setText(defaultValue);
            cb_yes.setChecked(true);
        }

        attributelistContainer.addView(view);
    }


    public static String getComponentOriginValue(Map<String, Object> originValue, String attributeName) {

        if (originValue == null) {
            return null;
        }
        /**
         * 窨井
         */
        if (attributeName.contains(ATTRIBUTE_ONE)) {
            Object o = originValue.get(ATTRIBUTE_ONE_KEY);
            if (o != null) {
                return o.toString();
            }
        } else if (attributeName.contains(ATTRIBUTE_TWO)) {
            Object o = originValue.get(ATTRIBUTE_TWO_KEY);
            if (o != null) {
                return o.toString();
            }
        } else if (attributeName.contains(ATTRIBUTE_THREE)) {
            Object o = originValue.get(ATTRIBUTE_THREE_KEY);
            if (o != null) {
                return o.toString();
            }
        } else if (attributeName.contains(ATTRIBUTE_FOUR)) {
            Object o = originValue.get(ATTRIBUTE_FOUR_KEY);
            if (o != null) {
                return o.toString();
            }
        } else if (attributeName.contains(ATTRIBUTE_FIVE)) {
            Object o = originValue.get(ATTRIBUTE_FIVE_KEY);
            if (o != null) {
                return o.toString();
            }
        }
        return null;
    }


    public YinjingAttributes getYinjingAttributes() {

        if (facilityOwnerShipUnitView != null) {
            yinjingAttributes.setAttributeFour(facilityOwnerShipUnitView.getText());
        }

        if (fiveItemChecked && StringUtil.isEmpty(attributeFiveItem.getText().toString().replace(" ", ""))) {
            yinjingAttributes.setAttributeFive(null);
        } else if (attributeFiveItem != null && fiveItemChecked) {
            yinjingAttributes.setAttributeFive(attributeFiveItem.getText().toString());
        } else {
            yinjingAttributes.setAttributeFive("无");
        }

        //对比属性
        if (yinjingAttributes.getAttributeOne() != null && !yinjingAttributes.getAttributeOne().equals(originAttributeValue.get(ATTRIBUTE_ONE))) {
            yinjingAttributes.setHasModified(true);
        } else if (yinjingAttributes.getAttributeTwo() != null && !yinjingAttributes.getAttributeTwo().equals(originAttributeValue.get(ATTRIBUTE_TWO))) {
            yinjingAttributes.setHasModified(true);
        } else if (yinjingAttributes.getAttributeThree() != null && !yinjingAttributes.getAttributeThree().equals(originAttributeValue.get(ATTRIBUTE_THREE))) {
            yinjingAttributes.setHasModified(true);
        } else if (yinjingAttributes.getAttributeFour() != null && !yinjingAttributes.getAttributeFour().equals(originAttributeValue.get(ATTRIBUTE_FOUR))) {
            yinjingAttributes.setHasModified(true);
        } else if (yinjingAttributes.getAttributeFive() != null && !yinjingAttributes.getAttributeFive().equals(originAttributeValue.get(ATTRIBUTE_FIVE))) {
            yinjingAttributes.setHasModified(true);
        }

        return yinjingAttributes;

    }

    public YinjingAttributes getOriginalYinjingAttributes() {


        YinjingAttributes yinjingAttributes = new YinjingAttributes();

        String attributeOne = originAttributeValue.get(ATTRIBUTE_ONE);
        if (attributeOne != null) {
            yinjingAttributes.setAttributeOne(attributeOne);
        }

        String attributeTwo = originAttributeValue.get(ATTRIBUTE_TWO);
        if (attributeTwo != null) {
            yinjingAttributes.setAttributeTwo(attributeTwo);
        }


        String attributeThree = originAttributeValue.get(ATTRIBUTE_THREE);
        if (attributeThree != null) {
            yinjingAttributes.setAttributeThree(attributeThree);
        }

        String attributeFour = originAttributeValue.get(ATTRIBUTE_FOUR);
        if (attributeFour != null) {
            yinjingAttributes.setAttributeFour(attributeFour);
        }

        String attributeFive = originAttributeValue.get(ATTRIBUTE_FIVE);
        if (attributeFive != null) {
            yinjingAttributes.setAttributeFive(attributeFive);
        }

        return yinjingAttributes;

    }


    public static void addReadOnlyAttributes(Context context,
                                             Map<String, Object> originValue,
                                             YinjingAttributes yinjingAttributes,
                                             ViewGroup llContainer) {
        //属性一
        String componentOriginValue = getComponentOriginValue(originValue, ATTRIBUTE_ONE);
        ReadOnlyAttributeView readOnlyAttributeView = new ReadOnlyAttributeView(context,
                ATTRIBUTE_ONE, componentOriginValue, yinjingAttributes.getAttributeOne());
        readOnlyAttributeView.addTo(llContainer);

        //属性二
        String attributeTwo = getComponentOriginValue(originValue, ATTRIBUTE_TWO);
        ReadOnlyAttributeView readOnlyAttributeView2 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_TWO, attributeTwo, yinjingAttributes.getAttributeTwo());
        readOnlyAttributeView2.addTo(llContainer);


        //属性3
        String componentOriginValue3 = getComponentOriginValue(originValue, ATTRIBUTE_THREE);
        ReadOnlyAttributeView readOnlyAttributeView3 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_THREE, componentOriginValue3, yinjingAttributes.getAttributeThree());
        readOnlyAttributeView3.addTo(llContainer);

        //属性4
        String componentOriginValue4 = getComponentOriginValue(originValue, ATTRIBUTE_FOUR);
        ReadOnlyAttributeView readOnlyAttributeView4 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_FOUR, componentOriginValue4, yinjingAttributes.getAttributeFour());
        readOnlyAttributeView4.addTo(llContainer);

        //属性5
        String componentOriginValue5 = getComponentOriginValue(originValue, ATTRIBUTE_FIVE);
        ReadOnlyAttributeView readOnlyAttributeView5 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_FIVE, componentOriginValue5, yinjingAttributes.getAttributeFive());
        readOnlyAttributeView5.addTo(llContainer);
    }


    public static void addReadOnlyAttributes(Context context,
                                             YinjingAttributes yinjingAttributes,
                                             ViewGroup llContainer) {
        //属性一
        ReadOnlyAttributeView readOnlyAttributeView = new ReadOnlyAttributeView(context,
                ATTRIBUTE_ONE, yinjingAttributes.getAttributeOne());
        readOnlyAttributeView.addTo(llContainer);

        //属性二
        ReadOnlyAttributeView readOnlyAttributeView2 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_TWO, yinjingAttributes.getAttributeTwo());
        readOnlyAttributeView2.addTo(llContainer);


        //属性3
        ReadOnlyAttributeView readOnlyAttributeView3 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_THREE, yinjingAttributes.getAttributeThree());
        readOnlyAttributeView3.addTo(llContainer);

        //属性4

        ReadOnlyAttributeView readOnlyAttributeView4 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_FOUR, yinjingAttributes.getAttributeFour());
        readOnlyAttributeView4.addTo(llContainer);

        //属性5

        ReadOnlyAttributeView readOnlyAttributeView5 = new ReadOnlyAttributeView(context,
                ATTRIBUTE_FIVE, yinjingAttributes.getAttributeFive());
        readOnlyAttributeView5.addTo(llContainer);
    }


    public static Map<String, String> getDefaultValue(ModifiedFacility modifiedFacility) {

        Map<String, String> defaultSelectValue = new HashMap<>(5);
        defaultSelectValue.put(ATTRIBUTE_ONE, modifiedFacility.getAttrOne());
        defaultSelectValue.put(ATTRIBUTE_TWO, modifiedFacility.getAttrTwo());
        defaultSelectValue.put(ATTRIBUTE_THREE, modifiedFacility.getAttrThree());
        defaultSelectValue.put(ATTRIBUTE_FOUR, modifiedFacility.getAttrFour());
        defaultSelectValue.put(ATTRIBUTE_FIVE, modifiedFacility.getAttrFive());
        if (modifiedFacility.getAttrFive() == null) {
            defaultSelectValue.put(ATTRIBUTE_FIVE, "");
        } else {
            defaultSelectValue.put(ATTRIBUTE_FIVE, modifiedFacility.getAttrFive());
        }
        return defaultSelectValue;
    }

    public static Map<String, Object> getOriginValue(ModifiedFacility modifiedFacility) {

        Map<String, Object> originValue = new HashMap<>(5);
        if (modifiedFacility.getOriginAttrOne() != null) {
            originValue.put(ATTRIBUTE_ONE_KEY, modifiedFacility.getOriginAttrOne());
        } else {
            originValue.put(ATTRIBUTE_ONE_KEY, "");
        }

        if (modifiedFacility.getOriginAttrTwo() != null) {
            originValue.put(ATTRIBUTE_TWO_KEY, modifiedFacility.getOriginAttrTwo());
        } else {
            originValue.put(ATTRIBUTE_TWO_KEY, "");
        }

        if (modifiedFacility.getOriginAttrThree() != null) {
            originValue.put(ATTRIBUTE_THREE_KEY, modifiedFacility.getOriginAttrThree());
        } else {
            originValue.put(ATTRIBUTE_THREE_KEY, "");
        }
        if (modifiedFacility.getOriginAttrFour() != null) {
            originValue.put(ATTRIBUTE_FOUR_KEY, modifiedFacility.getOriginAttrFour());
        } else {
            originValue.put(ATTRIBUTE_FOUR_KEY, "");
        }
        if (modifiedFacility.getOriginAttrFive() != null) {
            originValue.put(ATTRIBUTE_FIVE_KEY, modifiedFacility.getOriginAttrFive());
        } else {
            originValue.put(ATTRIBUTE_FIVE_KEY, "");
        }

        return originValue;
    }


    public void onDestroy(){
        if (facilityOwnerShipUnitView != null){
            facilityOwnerShipUnitView.onDestroy();
            facilityOwnerShipUnitView = null;
        }
    }

}
