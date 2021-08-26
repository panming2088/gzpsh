package com.augurit.agmobile.gzps.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.am.cmpt.widget.filepicker.utils.FileTypeUtils;
import com.augurit.am.cmpt.widget.filepicker.utils.FileUtils;
import com.augurit.am.fw.utils.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.augurit.agmobile.common.view.filepicker.utils.FileUtils;

/**
 * 微信风格的文件选择Adapter
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agriver.common.ui.filepicker
 * @createTime 创建时间 ：2018-01-10
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018-01-10
 * @modifyMemo 修改备注：
 */

public class WEUIDirectoryAdapter extends RecyclerView.Adapter<WEUIDirectoryAdapter.DirectoryViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class DirectoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView mFileImage;
        private TextView mFileTitle;
        private TextView mFileSubtitle;
        private View mCheckView;
        private SuperCheckBox mCheckBox;

        public DirectoryViewHolder(View itemView, final OnItemClickListener clickListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

            mFileImage = (ImageView) itemView.findViewById(R.id.item_file_image);
            mFileTitle = (TextView) itemView.findViewById(R.id.item_file_title);
            mFileSubtitle = (TextView) itemView.findViewById(R.id.item_file_subtitle);
            mCheckView = itemView.findViewById(R.id.view_check);
            mCheckBox = (SuperCheckBox) itemView.findViewById(R.id.cb_check);

            mCheckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCheckBox.performClick();
                }
            });
        }
    }

    private List<File> mFiles;
    private List<String> mSelected; // 已选中的文件
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private SparseBooleanArray mSelection;
    private boolean mSelectionEnabled;
    private int mLimit = 9;

    public WEUIDirectoryAdapter(Context context, List<File> files, List<String> selected) {
        mContext = context;
        mFiles = files;
        mSelected = selected;
        initSelection();
    }

    private void initSelection() {
        if (mSelection == null) mSelection = new SparseBooleanArray();
        mSelection.clear();
        for (int i = 0; i < getItemCount(); i++) {
            mSelection.put(i, false);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public DirectoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_filepicker_item_file, parent, false);
        return new DirectoryViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final DirectoryViewHolder holder, final int position) {
        final File currentFile = mFiles.get(position);

        String description = "";
        if (currentFile.isDirectory() && currentFile.list()!=null) {
            description = "文件：" + currentFile.list().length;
        } else if (currentFile.isFile()) {
            long fileSize = FileUtils.getFileSize(currentFile);
//            long fileSize = FileUtils.getFileSize(currentFile);
            description += FileUtils.formatFileSize(fileSize) + " ";
            Date date = new Date(currentFile.lastModified());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int fileYear = calendar.get(Calendar.YEAR);
            calendar.setTime(new Date());
            int curYear = calendar.get(Calendar.YEAR);
            SimpleDateFormat format;
            if (fileYear == curYear) {
                format = new SimpleDateFormat("MM月dd日");
            } else {
                format = new SimpleDateFormat("yyyy年MM月dd日");
            }
            description += format.format(date);
        }

        FileTypeUtils.FileType fileType = FileTypeUtils.getFileType(currentFile);
        holder.mFileImage.setImageResource(fileType.getIcon());
        holder.mFileSubtitle.setText(description);
        holder.mFileTitle.setText(currentFile.getName());

        if (currentFile.isDirectory()) {
            holder.mCheckView.setVisibility(View.GONE);
        } else {
            holder.mCheckView.setVisibility(View.VISIBLE);
        }

        if (mSelected != null && mSelected.contains(currentFile.getPath())) {
            mSelection.put(position, true);
        }

        holder.mCheckBox.setChecked(mSelection.get(position));
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSelectionEnabled && holder.mCheckBox.isChecked()) {
                    holder.mCheckBox.setChecked(false);
//                    ToastUtil.shortToast(mContext, "最多只能选择" + mLimit + "个文件");
                    ToastUtil.shortToast(mContext,mContext.getString(R.string.validate_file_number_full));
                } else {
                    long fileSize = FileUtils.getFileSize(currentFile);
                    if (fileSize > 10 * 1024 * 1024) {
                        holder.mCheckBox.setChecked(false);
                        ToastUtil.shortToast(mContext, "单张pdf文件不能超过10M");
                        return;
                    }
                    mSelection.put(position, holder.mCheckBox.isChecked());
                    EventBus.getDefault().post(new WEUIFilePickerActivity.FileSelectEvent(currentFile,
                            holder.mCheckBox.isChecked()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public void setSelectionEnabled(boolean enabled, int limit) {
        mSelectionEnabled = enabled;
        mLimit = limit;
    }

    public File getModel(int index) {
        return mFiles.get(index);
    }
}