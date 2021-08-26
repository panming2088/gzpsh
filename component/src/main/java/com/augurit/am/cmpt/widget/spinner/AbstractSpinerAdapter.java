package com.augurit.am.cmpt.widget.spinner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.augurit.am.cmpt.R;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpinerAdapter<T> extends BaseAdapter {

    public interface IOnItemSelectListener {
        void onItemClick(int pos);
    }

    private Context mContext;
    private List<T> mObjects = new ArrayList<T>();
    private int mSelectItem = 0;
    private int mItemLayoutId;
    private LayoutInflater mInflater;

    public AbstractSpinerAdapter(Context context) {
        this(context, R.layout.spinner_item_layout);
    }

    public AbstractSpinerAdapter(Context context, int itemLayoutId) {
        mItemLayoutId = itemLayoutId;
        init(context);
    }

    public void refreshData(List<T> objects, int selIndex) {
        mObjects = objects;
        if (selIndex < 0) {
            selIndex = 0;
        }
        if (selIndex >= mObjects.size()) {
            selIndex = mObjects.size() - 1;
        }

        mSelectItem = selIndex;
    }

    private void init(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return mObjects.size();
    }

    @Override
    public Object getItem(int pos) {
        return mObjects.get(pos).toString();
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(mItemLayoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Object item = getItem(pos);
        viewHolder.mTextView.setText(item.toString());
        if(mSelectItem == pos){
            viewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            viewHolder.mTextView.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    public void setSelectedPosition(int selectPosition) {
        this.mSelectItem = selectPosition;
    }

    public static class ViewHolder {
        public TextView mTextView;
    }


}
