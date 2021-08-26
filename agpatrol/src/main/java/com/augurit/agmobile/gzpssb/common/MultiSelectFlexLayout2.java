package com.augurit.agmobile.gzpssb.common;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.uploadfacility.view.facilityprobrem.IMultiSelectedFlexLayoutModel;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.cmpt.widget.popupview.util.DensityUtils;
import com.augurit.am.fw.utils.DensityUtil;
import com.google.android.flexbox.FlexboxLayout;

import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 多选，样式如下：
 * <p>
 * ------- 标题 ------
 * | 子item1   子item2 |
 * | 子item3   子item4 |
 * -------------------
 * <p>
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.uploadfacility.view.widget
 * @createTime 创建时间 ：18/1/26
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：18/1/26
 * @modifyMemo 修改备注：
 */

public class MultiSelectFlexLayout2 extends RelativeLayout {


    private FlexboxLayout flexboxLayout;
    private TextView tvTitle;
    private Map<String, Object> selectedItems = null;
//    private PopupWindow mCurPopupWindow;
    /**
     * 最开始选中的设施
     */
    private Map<String, DictionaryItem> originalDefaultCheckedItem;

    private List<CheckBox> items;

    /**
     * 标题的唯一标志码
     */
    private String titleCode;
    /**
     * 是否应该显示Popup
     */
    private boolean shouldShowPopup = true;

    public MultiSelectFlexLayout2(Context context) {
        // super(context);
        this(context, null);
    }

    public MultiSelectFlexLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_facility_select_view, this);
        flexboxLayout = (FlexboxLayout) findViewById(R.id.fl_item_container);
        tvTitle = (TextView) findViewById(R.id.tv_item_title);
    }

    /**
     * 添加item,添加子item
     *
     * @param title              标题
     * @param titleCode          标题的唯一标志码
     * @param choices            多选项
     * @param defaultCheckedItem 默认勾选的选项
     */
    public void addItems(String title, String titleCode, Map<String, IMultiSelectedFlexLayoutModel> choices, Map<String, IMultiSelectedFlexLayoutModel> defaultCheckedItem) {

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        this.titleCode = titleCode;
        selectedItems = new LinkedHashMap<>();
        float margin = DensityUtils.dp2px(getContext(), 60);
        float width = DensityUtils.getWidth(getContext());
        final Set<Map.Entry<String, IMultiSelectedFlexLayoutModel>> entries = choices.entrySet();
        for (final Map.Entry<String, IMultiSelectedFlexLayoutModel> entry : entries) {
            final CheckBox rb = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_checkbox, null);
            rb.setText(entry.getKey());
            if (defaultCheckedItem != null && defaultCheckedItem.get(entry.getKey()) != null) {
                rb.setChecked(true);
                selectedItems.put(rb.getText().toString(), entry.getValue());
            }
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(entries.size() == 1)
                        return;
                    if (b && selectedItems.get(compoundButton.getText().toString()) == null) {
                        selectedItems.put(compoundButton.getText().toString(), entry.getValue());
                    } else if (b && selectedItems.get(compoundButton.getText().toString()) != null) {
                        compoundButton.setChecked(false);
                    } else if (!b) {
                        selectedItems.remove(compoundButton.getText().toString());
                    }
//
//                    //如果是选中
//                    if (b) {
//                        mCurPopupWindow = showTipPopupWindow(rb, entry.getValue().getHint(), null);
//                    } else {
//                        //如果是取消选中
//                        if (mCurPopupWindow != null) {
//                            mCurPopupWindow.dismiss();
//                        }
//                    }
                }
            });
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.topMargin = DensityUtil.dp2px(getContext(), 8);
//            rb.setLayoutParams(lp);
            flexboxLayout.addView(rb);
        }
    }

    public void removeView(){
        if(flexboxLayout != null){
            flexboxLayout.removeAllViews();
        }
    }

    /**
     * 添加item,添加子item
     *
     * @param title              标题
     * @param titleCode          标题的唯一标志码
     * @param choices            多选项
     * @param defaultCheckedItem 默认勾选的选项
     */
    public void addItemsByDictionaryItem(String title, String titleCode, Map<String, DictionaryItem> choices, Map<String, DictionaryItem> defaultCheckedItem) {

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        this.titleCode = titleCode;
        this.originalDefaultCheckedItem = defaultCheckedItem;
        this.items = new ArrayList<>();
        selectedItems = new LinkedHashMap<>();
        float margin = DensityUtils.dp2px(getContext(), 60);
        float width = DensityUtils.getWidth(getContext());
        final Set<Map.Entry<String, DictionaryItem>> entries = choices.entrySet();
        for (final Map.Entry<String, DictionaryItem> entry : entries) {
            final CheckBox rb = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_checkbox, null);
            rb.setText(entry.getKey());
            items.add(rb);
            if (defaultCheckedItem != null && defaultCheckedItem.get(entry.getKey()) != null) {
                rb.setChecked(true);
                selectedItems.put(rb.getText().toString(), entry.getValue());
            }
            if(entries.size() > 1) {
                rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b && selectedItems.get(compoundButton.getText().toString()) == null) {
                            selectedItems.put(compoundButton.getText().toString(), entry.getValue());
                        } else if (b && selectedItems.get(compoundButton.getText().toString()) != null) {
                            compoundButton.setChecked(false);
                        } else if (!b) {
                            selectedItems.remove(compoundButton.getText().toString());
                        }

                        //如果是选中
//                    if (b && shouldShowPopup) {
//                        mCurPopupWindow = showTipPopupWindow(rb, entry.getValue().getNote(), null);
//                    } else {
//                        //如果是取消选中
//                        if (mCurPopupWindow != null) {
//                            mCurPopupWindow.dismiss();
//                        }
//                    }
                    }
                });
            }else{
                rb.setEnabled(false);
            }
//            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lp);
            flexboxLayout.addView(rb);
        }
    }

    /**
     * 添加item
     *
     * @param title              标题
     * @param titleCode          标题的唯一标志码
     * @param choices            多选项
     * @param defaultCheckedItem 默认勾选的选项
     * @param rb                 传进自定义的checkbox，用于修改checkbox的样式
     */
    public void addItems(String title, String titleCode, Map<String, IMultiSelectedFlexLayoutModel> choices, Map<String, IMultiSelectedFlexLayoutModel> defaultCheckedItem, final CheckBox rb) {

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        this.titleCode = titleCode;
        selectedItems = new LinkedHashMap<>();
        float margin = DensityUtils.dp2px(getContext(), 60);
        float width = DensityUtils.getWidth(getContext());
        Set<Map.Entry<String, IMultiSelectedFlexLayoutModel>> entries = choices.entrySet();
        for (final Map.Entry<String, IMultiSelectedFlexLayoutModel> entry : entries) {

            rb.setText(entry.getKey());
            if (defaultCheckedItem != null && defaultCheckedItem.get(entry.getKey()) != null) {
                rb.setChecked(true);
                selectedItems.put(rb.getText().toString(), entry.getValue());
            }
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b && selectedItems.get(compoundButton.getText().toString()) == null) {
                        selectedItems.put(compoundButton.getText().toString(), entry.getValue());
                    } else if (b && selectedItems.get(compoundButton.getText().toString()) != null) {
                        compoundButton.setChecked(false);
                    } else if (!b) {
                        selectedItems.remove(compoundButton.getText().toString());
                    }

//                    //如果是选中
//                    if (b) {
//                        mCurPopupWindow = showTipPopupWindow(rb, entry.getValue().getHint(), null);
//                    } else {
//                        //如果是取消选中
//                        if (mCurPopupWindow != null) {
//                            mCurPopupWindow.dismiss();
//                        }
//                    }
                }
            });
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams((int) (width - margin) / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = DensityUtil.dp2px(getContext(), 8);
            rb.setLayoutParams(lp);
            flexboxLayout.addView(rb);
        }
    }


    /**
     * 重置（暂时只适用于通过{@link #addItemsByDictionaryItem(String, String, Map, Map)}方法将选项添加进来的情况）
     */
    public void reset() {
        if (items == null || items.size() == 0) {
            return;
        }

        if (MapUtils.isEmpty(originalDefaultCheckedItem)) {
            //如果没有默认选中选项，全部不勾选
            for (CheckBox checkBox : items) {
                checkBox.setChecked(false);
            }
        } else {
            shouldShowPopup = false;
            for (CheckBox checkBox : items) {
                String s = checkBox.getText().toString();
                DictionaryItem dictionaryItem = originalDefaultCheckedItem.get(s);
                if (dictionaryItem != null) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
            shouldShowPopup = true;
        }
    }


    public PopupWindow showTipPopupWindow(final View anchorView, String hint, final OnClickListener onClickListener) {

        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.popuw_content_top_arrow_layout, null);
        contentView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        TextView textView = (TextView) contentView.findViewById(R.id.tip_text);
        textView.setText(hint);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        contentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 自动调整箭头的位置
                autoAdjustArrowPos(popupWindow, contentView, anchorView);
                contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        // 如果希望showAsDropDown方法能够在下面空间不足时自动在anchorView的上面弹出
        // 必须在创建PopupWindow的时候指定高度，不能用wrap_content
        popupWindow.showAsDropDown(anchorView);
        return popupWindow;
    }

    private void autoAdjustArrowPos(PopupWindow popupWindow, View contentView, View anchorView) {
        View upArrow = contentView.findViewById(R.id.up_arrow);
        View downArrow = contentView.findViewById(R.id.down_arrow);

        int pos[] = new int[2];
        contentView.getLocationOnScreen(pos);
        int popLeftPos = pos[0];
        anchorView.getLocationOnScreen(pos);
        int anchorLeftPos = pos[0];
        int arrowLeftMargin = anchorLeftPos - popLeftPos + anchorView.getWidth() / 2 - upArrow.getWidth() / 2;
        upArrow.setVisibility(popupWindow.isAboveAnchor() ? View.INVISIBLE : View.VISIBLE);
        downArrow.setVisibility(popupWindow.isAboveAnchor() ? View.VISIBLE : View.INVISIBLE);

        LayoutParams upArrowParams = (LayoutParams) upArrow.getLayoutParams();
        upArrowParams.leftMargin = arrowLeftMargin;
        LayoutParams downArrowParams = (LayoutParams) downArrow.getLayoutParams();
        downArrowParams.leftMargin = arrowLeftMargin;
    }


    public void onBackPressed() {
//        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
//            mCurPopupWindow.dismiss();
//        }
    }

    /*********get 方法 **************/
    public Map<String, Object> getSelectedItems() {
        return selectedItems;
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public String getTitleCode() {
        return titleCode;
    }
}
