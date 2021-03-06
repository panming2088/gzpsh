package com.augurit.am.cmpt.widget.searchview.suggestions;


/**
 * 描述：推荐搜索列表数据适配器
 *
 * @author 创建人 ：guokunhu
 * @version 1.0
 * @package 包名 ：com.augurit.am.cmpt.widget.searchview.suggestions
 * @createTime 创建时间 ：17/4/11
 * @modifyBy 修改人 ：guokunhu
 * @modifyTime 修改时间 ：17/4/11
 * @modifyMemo 修改备注：
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.augurit.am.cmpt.R;
import com.augurit.am.cmpt.widget.searchview.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "SearchSuggestionsAdapter";

    private List<SearchSuggestion> mSearchSuggestions = new ArrayList<>();

    private Listener mListener;

    private Context mContext;

    private Drawable mRightIconDrawable;
    private boolean mShowRightMoveUpBtn = false;
    private int mBodyTextSizePx;
    private int mTextColor = -1;
    private int mRightIconColor = -1;

    public interface OnBindSuggestionCallback {

        void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView,
                              SearchSuggestion item, int itemPosition);
    }

    private OnBindSuggestionCallback mOnBindSuggestionCallback;

    public interface Listener {

        void onItemSelected(SearchSuggestion item);

        void onMoveItemToSearchClicked(SearchSuggestion item);
    }

    public static class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {

        public TextView body;
        public ImageView leftIcon;
        public ImageView rightIcon;

        private Listener mListener;

        public interface Listener {

            void onItemClicked(int adapterPosition);

            void onMoveItemToSearchClicked(int adapterPosition);
        }

        public SearchSuggestionViewHolder(View v, Listener listener) {
            super(v);

            mListener = listener;
            body = (TextView) v.findViewById(R.id.body);
            leftIcon = (ImageView) v.findViewById(R.id.left_icon);
            rightIcon = (ImageView) v.findViewById(R.id.right_icon);

            rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        mListener.onMoveItemToSearchClicked(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        mListener.onItemClicked(adapterPosition);
                    }
                }
            });
        }
    }

    public SearchSuggestionsAdapter(Context context, int suggestionTextSize, Listener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mBodyTextSizePx = suggestionTextSize;

        mRightIconDrawable = Util.getWrappedDrawable(mContext, R.drawable.ic_arrow_back_black_24dp);
        DrawableCompat.setTint(mRightIconDrawable, Util.getColor(mContext, R.color.gray_active_icon));
    }

    public void swapData(List<? extends SearchSuggestion> searchSuggestions) {
        mSearchSuggestions.clear();
        mSearchSuggestions.addAll(searchSuggestions);
        notifyDataSetChanged();
    }

    public List<? extends SearchSuggestion> getDataSet() {
        return mSearchSuggestions;
    }

    public void setOnBindSuggestionCallback(OnBindSuggestionCallback callback) {
        this.mOnBindSuggestionCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_suggestion_item, viewGroup, false);
        SearchSuggestionViewHolder viewHolder = new SearchSuggestionViewHolder(view,
                new SearchSuggestionViewHolder.Listener() {

                    @Override
                    public void onItemClicked(int adapterPosition) {

                        if (mListener != null) {
                            mListener.onItemSelected(mSearchSuggestions.get(adapterPosition));
                        }
                    }

                    @Override
                    public void onMoveItemToSearchClicked(int adapterPosition) {

                        if (mListener != null) {
                            mListener.onMoveItemToSearchClicked(mSearchSuggestions
                                    .get(adapterPosition));
                        }
                    }

                });

        viewHolder.rightIcon.setImageDrawable(mRightIconDrawable);
        viewHolder.body.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBodyTextSizePx);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        SearchSuggestionViewHolder viewHolder = (SearchSuggestionViewHolder) vh;

        if (!mShowRightMoveUpBtn) {
            viewHolder.rightIcon.setEnabled(false);
            viewHolder.rightIcon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.rightIcon.setEnabled(true);
            viewHolder.rightIcon.setVisibility(View.VISIBLE);
        }

        SearchSuggestion suggestionItem = mSearchSuggestions.get(position);
        viewHolder.body.setText(suggestionItem.getBody());

        if (mOnBindSuggestionCallback != null) {
            mOnBindSuggestionCallback.onBindSuggestion(viewHolder.itemView, viewHolder.leftIcon, viewHolder.body,
                    suggestionItem, position);
        }
    }

    @Override
    public int getItemCount() {
        return mSearchSuggestions != null ? mSearchSuggestions.size() : 0;
    }

    public void setTextColor(int color) {

        boolean notify = false;
        if (this.mTextColor != color) {
            notify = true;
        }
        this.mTextColor = color;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setRightIconColor(int color) {

        boolean notify = false;
        if (this.mRightIconColor != color) {
            notify = true;
        }
        this.mRightIconColor = color;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setShowMoveUpIcon(boolean show) {

        boolean notify = false;
        if (this.mShowRightMoveUpBtn != show) {
            notify = true;
        }
        this.mShowRightMoveUpBtn = show;
        if (notify) {
            notifyDataSetChanged();
        }
    }
}
