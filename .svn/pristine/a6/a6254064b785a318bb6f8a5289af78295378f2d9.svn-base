package com.augurit.agmobile.gzpssb.uploadevent.view.eventflow;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.constant.GzpsConstant;
import com.augurit.agmobile.gzps.uploadevent.model.EventDetail;
import com.augurit.agmobile.gzps.uploadevent.model.EventProcess;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventDetail;
import com.augurit.agmobile.gzpssb.uploadevent.model.PSHEventProcess;
import com.augurit.agmobile.patrolcore.common.preview.view.PhotoPagerActivity;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.HorizontalScrollPhotoView;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.HorizontalScrollPhotoViewAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事件处理情况
 *
 * Created by xcl on 2017/11/11.
 */

public class PSHEventProcessView {

    private Context mContext;
    private TextView tv_index;
    private TextView tv_link;
    private TextView tv_upload_user;
    private TextView tv_phone;
    private TextView tv_upload_date;
    private TextView tv_upload_advice;
    private TextView tv_next_handler;
    private TextView tv_next_handler_phone;
    private HorizontalScrollPhotoView photoView;
    private View root;

    private View ll_left;
    private View ll_right;

    private List<Photo> selectedPhotos;
    private HorizontalScrollPhotoViewAdapter adapter;

    public PSHEventProcessView(Context context){
        this.mContext = context;
    }

    public void initView(PSHEventProcess eventProcess, boolean isFinished, int index, int listCount) {
        root = LayoutInflater.from(mContext).inflate(R.layout.item_handle_process, null);
        tv_index = (TextView) root.findViewById(R.id.tv_index);
        tv_link = (TextView) root.findViewById(R.id.tv_link);
        tv_upload_user = (TextView) root.findViewById(R.id.tv_upload_user);
        View ll_opuser_phone = root.findViewById(R.id.ll_opuser_phone);
        tv_phone = (TextView) root.findViewById(R.id.tv_phone);
        tv_upload_date = (TextView) root.findViewById(R.id.tv_upload_date);
        tv_upload_advice = (TextView) root.findViewById(R.id.tv_upload_advice);
        tv_next_handler = (TextView) root.findViewById(R.id.tv_next_handler);
        View ll_next_handler = root.findViewById(R.id.ll_next_handler);
        View ll_next_op_phone = root.findViewById(R.id.ll_next_op_phone);
        tv_next_handler_phone = (TextView) root.findViewById(R.id.tv_next_handler_phone);
        photoView = (HorizontalScrollPhotoView) root.findViewById(R.id.horizontalScrollPhotoView);
        ll_left = root.findViewById(R.id.ll_left);
        ll_right = root.findViewById(R.id.ll_right);


        if(StringUtil.isEmpty(eventProcess.getActiveName())){
            //如果没有环节名，则为施工日志
            tv_link.setText((index+1) + ". " + "施工日志");
            tv_next_handler.setVisibility(View.GONE);
            ll_next_op_phone.setVisibility(View.GONE);
        } else {
            if(index == 0){
                //列表的第一项是问题上报，暂时写死是巡查员上报
              //  tv_link.setText((index+1) + ". " + eventProcess.getLinkName() + "（巡查员上报）");
                tv_link.setText((index+1) + ". " + eventProcess.getActiveName());
            } else {
                tv_link.setText((index+1) + ". " + eventProcess.getActiveName());
            }
        }

        if(isFinished && (index == listCount-1)){
            //如果流程已经结束，则当前项没有下一环节处理人和电话
            tv_next_handler.setVisibility(View.GONE);
            ll_next_op_phone.setVisibility(View.GONE);
        }


        tv_phone.setText(StringUtil.getNotNullString(eventProcess.getPhone(), ""));
        if(eventProcess.getAppTime() == 0){
            tv_upload_date.setVisibility(View.GONE);
        } else {
            tv_upload_date.setText("时间：" + com.augurit.am.fw.utils.TimeUtil.getStringTimeYMDS(new Date(eventProcess.getAppTime())));
        }
        if(GzpsConstant.LINK_PROBLEM_REPORT_NAME.equals(eventProcess.getActiveName())){
            tv_upload_user.setText("上报人：" + eventProcess.getUsername());
            tv_upload_advice.setVisibility(View.GONE);
        } else {
            String content = StringUtil.getNotNullString(eventProcess.getContent(), "");
            String name = StringUtil.getNotNullString(eventProcess.getName(), "");
            if(StringUtil.isEmpty(eventProcess.getActiveName())){
                //施工日志
                tv_upload_user.setText("填写人：" + eventProcess.getUsername());
                if(StringUtil.isEmpty(name)){
                    tv_upload_advice.setText("日志内容：" + content);
                }else{
                    tv_upload_advice.setText(name + "：" + content);
                }
            } else {
                tv_upload_user.setText("经办人：" + eventProcess.getUsername());
                if(StringUtil.isEmpty(name)){
                    tv_upload_advice.setText("处理意见：" + content);
                }else{
                    tv_upload_advice.setText(name + "：" + content);
                }
            }
        }

        tv_next_handler.setText("下一环节处理人：" + StringUtil.getNotNullString(eventProcess.getUsername(), ""));
//        tv_next_handler_phone.setText(StringUtil.getNotNullString(eventProcess.get(), ""));

        if(StringUtil.isEmpty(eventProcess.getPhone())){
            ll_opuser_phone.setVisibility(View.GONE);
        }
//        if(StringUtil.isEmpty(eventProcess.get())){
            ll_next_op_phone.setVisibility(View.GONE);
//        }

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isEmpty(((TextView)v).getText())){
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + ((TextView)v).getText());
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });

        tv_next_handler_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isEmpty(((TextView)v).getText())){
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + ((TextView)v).getText());
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });

        selectedPhotos = new ArrayList<>();
        if(ListUtil.isEmpty(eventProcess.getFiles())){
            photoView.setVisibility(View.GONE);
        } else {
            for(PSHEventDetail.FormBean.FilesBean fileBean : eventProcess.getFiles()){
               Photo photo = new Photo();
               photo.setPhotoPath(fileBean.getPath());
               photo.setThumbPath(fileBean.getThumbPath());
               photo.setPhotoName(fileBean.getName());
               selectedPhotos.add(photo);
            }
            adapter = new HorizontalScrollPhotoViewAdapter(
                    mContext, photoView, selectedPhotos);

            photoView.setOnItemClickListener(new HorizontalScrollPhotoView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    /*view.setBackgroundDrawable(mContext.getResources().getDrawable(
                            com.augurit.agmobile.patrolcore.R.drawable.itme_background_checked));*/
                    if (!ListUtil.isEmpty(selectedPhotos)) {
                        Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                        intent.putExtra("BITMAPLIST", (ArrayList<Photo>) selectedPhotos);
                        intent.putExtra("POSITION", position);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, "shareTransition").toBundle());
                        } else {
                            mContext.startActivity(intent);
                        }

                    }
                }
            });
            photoView.initDatas(adapter);
        }

        //动态设置右边的高度
        /*
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)ll_right.getLayoutParams();
        int height = params.height;
        LinearLayout.LayoutParams paramsNew = (LinearLayout.LayoutParams)ll_left.getLayoutParams();
        paramsNew.height = height;
        ll_left.setLayoutParams(paramsNew);
        ll_left.requestLayout();
        */

        //如果是撤回环节,不要显示处理意见和下一环节处理人
        if(!StringUtil.isEmpty(eventProcess.getActiveName())){
            if(eventProcess.getActiveName().equals("撤回")){
                //tv_upload_advice.setVisibility(View.GONE);
                tv_next_handler.setVisibility(View.GONE);
                tv_next_handler_phone.setVisibility(View.GONE);
            }
        }

//        if(StringUtil.isEmpty(eventProcess.get())){
            ll_next_handler.setVisibility(View.GONE);
            ll_next_op_phone.setVisibility(View.GONE);
//        }
    }

    public void setSelectedPhotos(List<Photo> selectedPhotos) {
        this.selectedPhotos = selectedPhotos;
        adapter.notifyDataSetChanged(selectedPhotos);
        refreshPhotoViewToFirst();
    }

    private void refreshPhotoViewToFirst() {
        adapter.notifyDataSetChanged();
        photoView.initDatas(adapter);
    }

    public void addTo(ViewGroup container){
        container.addView(root);
    }

}
