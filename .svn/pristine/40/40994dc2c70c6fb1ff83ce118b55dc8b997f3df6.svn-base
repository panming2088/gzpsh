package com.augurit.agmobile.gzpssb.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.bean.DrainageUserBean;
import com.augurit.agmobile.gzpssb.common.callback.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排水户列表上拉视图
 *
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/2 15:15
 */
public class DrainageUserListBottomSheetView extends LinearLayout {

    private LinearLayout llHeaders;
    private List<View> listHeaders;
    private RecyclerView rvDatas;
    private DrainageUserListAdapter adptDatas;
    private List<DrainageUserBean> listDatas;

    private View currClickHeader;
    private int currClickIndex = -1;

    private OnHeaderAndItemClickListener listener;

    public DrainageUserListBottomSheetView(Context context) {
        super(context);
        init(context);
    }

    public DrainageUserListBottomSheetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrainageUserListBottomSheetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // ==============================自定义方法==============================

    public void setOnHeaderAndItemClickListener(OnHeaderAndItemClickListener listener) {
        this.listener = listener;
    }

    public void setHeaders(List<Pair<String, String>> headers) {
        listHeaders.clear();
        llHeaders.removeAllViews();
        if (headers == null) {
            return;
        }
        for (Pair<String, String> header : headers) {
            createHeaderItem(header.first, header.second);
        }
        currClickHeader = listHeaders.get(0);
        currClickHeader.setSelected(true);
        currClickIndex = 0;
    }

    public void setDatas(List<DrainageUserBean> drainageUserBeans) {
        listDatas.clear();
        if (drainageUserBeans != null && drainageUserBeans.size() > 0) {
            listDatas.addAll(drainageUserBeans);
        }
        rvDatas.post(new Runnable() {
            @Override
            public void run() {
                adptDatas.update(listDatas);
            }
        });
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_drainage_user_list_bottom_sheet, this);
        initData(context);
        initView(context);

//        test();
    }

    private void initData(Context context) {
        adptDatas = new DrainageUserListAdapter();
        adptDatas.setListener(new OnHeaderAndItemClickListener() {
            @Override
            public void onHeaderClick(int position) {

            }

            @Override
            public void onItemClick(Object object, int position) {
                if (listener != null) {
                    listener.onItemClick(object, position);
                }
            }

            @Override
            public void onItemInspection(Object object, int position) {
                if (listener != null) {
                    listener.onItemInspection(object, position);
                }
            }

            @Override
            public void onItemHangUpWell(Object object, int position) {
                if (listener != null) {
                    listener.onItemHangUpWell(object, position);
                }
            }

            @Override
            public void onItemDeleteLine(Object object, int position) {
                if (listener != null) {
                    listener.onItemDeleteLine(object, position);
                }
            }
        });
        listHeaders = new ArrayList<>(14);
        listDatas = new ArrayList<>();
    }

    private void initView(Context context) {
        llHeaders = findViewById(R.id.listHeaderContainer);
        rvDatas = findViewById(R.id.datas);
        rvDatas.setAdapter(adptDatas);
//        rvDatas.addOnItemTouchListener(new OnRecyclerItemClickListener(rvDatas) {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
//                // 列表Item的点击事件
//                if (listener != null) {
//                    listener.onItemClick(listDatas.get(viewHolder.getLayoutPosition()), viewHolder.getLayoutPosition());
//                }
//            }
//        });
    }

    private void createHeaderItem(String name, String value) {
        final FrameLayout root = new FrameLayout(getContext());
        root.setTag(listHeaders.size());
        root.setPadding(0, (int) dp2px(10), 0, (int) dp2px(10));
        root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 标题的点击事件
                try {
                    onHeaderClick(Integer.parseInt(String.valueOf(v.getTag())));
                } catch (NumberFormatException ignored) {
                }
            }
        });
        final LayoutParams rootParams = new LayoutParams((int) dp2px(100f), LayoutParams.WRAP_CONTENT);

        final TextView tvName = new CustomTextView(getContext());
        tvName.setGravity(Gravity.CENTER);
        tvName.setDuplicateParentStateEnabled(true);
        tvName.setTextSize(13);
        final FrameLayout.LayoutParams nameParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) dp2px(30));
        tvName.setText(name);

        final TextView tvValue = new CustomTextView(getContext());
        tvValue.setGravity(Gravity.CENTER);
        tvValue.setDuplicateParentStateEnabled(true);
        tvValue.setTextSize(13);
        final FrameLayout.LayoutParams valueParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) dp2px(30));
        valueParams.topMargin = (int) dp2px(30);
        tvValue.setText(value);

        root.addView(tvName, nameParams);
        root.addView(tvValue, valueParams);
        llHeaders.addView(root, rootParams);
        listHeaders.add(root);

    }

    private float dp2px(float dimen) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen, Resources.getSystem().getDisplayMetrics());
    }

    private void onHeaderClick(int position) {
        if (position == currClickIndex) {
            return;
        }
        currClickIndex = position;
        currClickHeader.setSelected(false);
        currClickHeader = listHeaders.get(position);
        currClickHeader.setSelected(true);
        if (listener != null) {
            listener.onHeaderClick(position);
        }
    }

    private void test() {
        final List<String> names = Arrays.asList("全部", "工业类", "餐饮类", "建筑类", "医疗类", "农贸市场类", "畜禽养殖类", "机关事业单位类", "汽修机洗类", "洗涤类", "垃圾收集处理类", "综合商业类", "居民类", "其他");
        final List<String> values = Arrays.asList("50", "50", "50", "50", "50", "50", "50", "50", "50", "50", "50", "50", "50", "50");
        final List<Pair<String, String>> headers = new ArrayList<>(names.size());
        for (int i = 0; i < names.size(); i++) {
//            createHeaderItem(names.get(i), values.get(i));
            headers.add(new Pair<>(names.get(i), values.get(i)));
        }
        setHeaders(headers);

        final List<DrainageUserBean> testDatas = new ArrayList<>(names.size());
        for (int i = 0; i < 10; i++) {
            final DrainageUserBean bean = new DrainageUserBean("" + i);
            bean.setAddress("罗哥快请客");
            bean.setState("目前还没请");
            bean.setName("罗哥快请我吃饭");
            bean.setLevel("最高等级");
            testDatas.add(bean);
        }
//        adptDatas.update(listDatas);
        setDatas(testDatas);
    }

    // ==============================自定义类==============================

    public interface OnHeaderAndItemClickListener {
        void onHeaderClick(int position);

        void onItemClick(Object object, int position);

        void onItemInspection(Object object, int position);

        void onItemHangUpWell(Object object, int position);

        void onItemDeleteLine(Object object, int position);
    }

    private static class DrainageUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<DrainageUserBean> datas = new ArrayList<>();
        private OnHeaderAndItemClickListener listener;

        public void setListener(OnHeaderAndItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drainage_user_list, parent, false);
            return new DrainageUserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof DrainageUserViewHolder) {
                ((DrainageUserViewHolder) holder).setData(datas.get(position), listener, position);
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void update(List<DrainageUserBean> newDatas) {
            final int initDataCounts = datas.size();
            datas.clear();
            if (newDatas == null || newDatas.size() == 0) {
                notifyItemRangeRemoved(0, initDataCounts);
            } else {
                datas.addAll(newDatas);
//                notifyItemRangeChanged(0, datas.size());
                notifyDataSetChanged();
            }
        }

        private static class DrainageUserViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvAddress;
            private final TextView tvLegalPerson;
            private final TextView tvTown;
            private final View btnRoot;
            private final Button btnSecond;
            private final Button btnJhjGj;
            private final Button btnDeleteLine;
            private final TextView tvRightUp;


            public DrainageUserViewHolder(View itemView) {
                super(itemView);

                tvAddress = itemView.findViewById(R.id.address);
                tvLegalPerson = itemView.findViewById(R.id.legalPerson);
                tvTown = itemView.findViewById(R.id.town);
                btnRoot = itemView.findViewById(R.id.btnRoot);
                btnSecond = itemView.findViewById(R.id.btn_psh_ej);
                btnJhjGj = itemView.findViewById(R.id.btn_jhj_gj);
                btnDeleteLine = itemView.findViewById(R.id.btn_jhj_delete);
                tvRightUp = itemView.findViewById(R.id.tv_right_up);
            }

            public void setData(final DrainageUserBean bean, final OnHeaderAndItemClickListener listener, final int position) {
                tvAddress.setText(bean.getAddress());
                tvLegalPerson.setText(bean.getName());
                tvTown.setText(bean.getTown());
                if ("2".equals(bean.getState())) {
                    tvRightUp.setText("已审核");
                    tvRightUp.setTextColor(Color.parseColor("#3EA500"));
                } else if ("1".equals(bean.getState())) {
                    tvRightUp.setText("未审核");
                    tvRightUp.setTextColor(Color.parseColor("#FFFF0000"));
                } else if ("0".equals(bean.getState())) {
                    tvRightUp.setText("已注销");
                    tvRightUp.setTextColor(Color.parseColor("#b1afab"));
                } else if ("3".equals(bean.getState())) {
                    tvRightUp.setText("存在疑问");
                    tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
                } else if ("4".equals(bean.getState())) {
                    tvRightUp.setText("暂存");
                    tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
                } else if ("5".equals(bean.getState())) {
                    tvRightUp.setText("编辑");
                    tvRightUp.setTextColor(Color.parseColor("#FFFFC248"));
                }
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(bean, position);
                        }
                    }
                });
                btnRoot.setVisibility("0".equals(bean.getState()) ? View.GONE : View.VISIBLE);
//                btnSecond.setVisibility("0".equals(bean.getState()) ? View.INVISIBLE : View.VISIBLE);
                btnSecond.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemInspection(bean, position);
                        }
                    }
                });
//                btnJhjGj.setVisibility("0".equals(bean.getState()) ? View.GONE : View.VISIBLE);
                btnJhjGj.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemHangUpWell(bean, position);
                        }
                    }
                });
                btnDeleteLine.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemDeleteLine(bean, position);
                        }
                    }
                });
            }
        }
    }
}
