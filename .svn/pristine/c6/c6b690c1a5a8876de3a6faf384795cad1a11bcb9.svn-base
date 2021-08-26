package com.augurit.agmobile.gzps.common.widget;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.gzps.R;
import com.augurit.am.cmpt.widget.filepicker.filter.CompositeFilter;
import com.augurit.am.cmpt.widget.filepicker.utils.FileUtils;
import com.augurit.am.cmpt.widget.filepicker.widget.EmptyRecyclerView;

import java.io.File;
import java.util.ArrayList;

/**
 * 微信风格的文件选择Fragment
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agriver.common.ui.filepicker
 * @createTime 创建时间 ：2018-01-10
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018-01-10
 * @modifyMemo 修改备注：
 */
public class WEUIDirectoryFragment extends Fragment {
    public interface FileClickListener {
        void onFileClicked(File clickedFile);
    }

    private static final String ARG_FILE_PATH = "arg_file_path";
    private static final String ARG_FILTER = "arg_filter";
    private static final String ARG_FILE_SELECTED = "ARG_FILE_SELECTED";
    private static final String ARG_SEL_ENABLED = "ARG_SEL_ENABLED";
    private static final String ARG_LIMIT = "ARG_LIMIT";

    private View mEmptyView;
    private String mPath;
    private ArrayList<String> mFileSelected;
    private CompositeFilter mFilter;

    private EmptyRecyclerView mDirectoryRecyclerView;
    private WEUIDirectoryAdapter mDirectoryAdapter;
    private FileClickListener mFileClickListener;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFileClickListener = (FileClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFileClickListener = null;
    }

    public static WEUIDirectoryFragment getInstance(
            String path, CompositeFilter filter, ArrayList<String> selected, boolean selectionEnabled, int limit) {
        WEUIDirectoryFragment instance = new WEUIDirectoryFragment();

        Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, path);
        args.putSerializable(ARG_FILTER, filter);
        args.putStringArrayList(ARG_FILE_SELECTED, selected);
        args.putBoolean(ARG_SEL_ENABLED, selectionEnabled);
        args.putInt(ARG_LIMIT, limit);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);
        mDirectoryRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.directory_recycler_view);
        mEmptyView = view.findViewById(R.id.directory_empty_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initArgs();
        initFilesList();
    }

    private void initFilesList() {
        mDirectoryAdapter = new WEUIDirectoryAdapter(getActivity(),
                FileUtils.getFileListByDirPath(mPath, mFilter), mFileSelected);

        mDirectoryAdapter.setOnItemClickListener(new WEUIDirectoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mFileClickListener != null) {
                    mFileClickListener.onFileClicked(mDirectoryAdapter.getModel(position));
                }
            }
        });
        mDirectoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDirectoryRecyclerView.setAdapter(mDirectoryAdapter);
        mDirectoryRecyclerView.setEmptyView(mEmptyView);

        boolean selEnabled = getArguments().getBoolean(ARG_SEL_ENABLED, true);
        int limit = getArguments().getInt(ARG_LIMIT);
        mDirectoryAdapter.setSelectionEnabled(selEnabled, limit);
    }

    @SuppressWarnings("unchecked")
    private void initArgs() {
        if (getArguments().getString(ARG_FILE_PATH) != null) {
            mPath = getArguments().getString(ARG_FILE_PATH);
        }

        mFilter = (CompositeFilter) getArguments().getSerializable(ARG_FILTER);
        mFileSelected = getArguments().getStringArrayList(ARG_FILE_SELECTED);
    }

    public void setSelectionEnabled(boolean enabled, int limit) {
        mDirectoryAdapter.setSelectionEnabled(enabled, limit);
    }
}
