package com.augurit.agmobile.gzpssb.draft;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.common.widget.FileBean;
import com.augurit.agmobile.gzps.uploadevent.util.PhotoUploadType;
import com.augurit.agmobile.gzpssb.activity.SewerageTableActivity;
import com.augurit.agmobile.gzpssb.bean.SewerageInfoBean;
import com.augurit.agmobile.gzpssb.pshpublicaffair.model.PSHAffairDetail;
import com.augurit.agmobile.mapengine.common.base.BaseRecyclerAdapter;
import com.augurit.am.cmpt.common.base.BaseInfoManager;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.db.AMDatabase;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.view.DialogUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by liangsh on 2017/11/21.
 */

public class PSHDraftListFragment extends Fragment {
    private RecyclerView rv_upload_draft;
    private PSHUploadDraftListAdapter uploadDraftAdapter;
    private TextView tv_draft_empty;
    private Context mContext;
    private List<SewerageInfoBean> infoBeans = new ArrayList<>();
    private int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_psh_draft_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_upload_draft = (RecyclerView) view.findViewById(R.id.rv_event_draft);
        tv_draft_empty = (TextView) view.findViewById(R.id.tv_draft_empty);
        tv_draft_empty.setText("排水户的本地草稿为空");
        uploadDraftAdapter = new PSHUploadDraftListAdapter(mContext,infoBeans);
        rv_upload_draft.setLayoutManager(new LinearLayoutManager(mContext));
        rv_upload_draft.setAdapter(uploadDraftAdapter);
        uploadDraftAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                currentPosition = position;
                SewerageInfoBean sewerageInfoBean = infoBeans.get(position);
                Intent intent = new Intent(mContext, SewerageTableActivity.class);
                PSHAffairDetail pshAffairDetail = switchBean(sewerageInfoBean);
                intent.putExtra("pshAffair", pshAffairDetail);
                intent.putExtra("draftBean", sewerageInfoBean);
                intent.putExtra("isAllowSaveLocalDraft", true);
                startActivityForResult(intent, 123);
            }
        });
        uploadDraftAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position, long id) {
                DialogUtil.MessageBox(mContext, null, "确定删除草稿吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SewerageInfoBean infoBean = infoBeans.get(position);
                        AMDatabase.getInstance().deleteWhere(FileBean.class, "problem_id", infoBean.getDbid() + "");
                        AMDatabase.getInstance().deleteWhere(Photo.class, "problem_id", infoBean.getDbid() + "");
                        AMDatabase.getInstance().deleteWhere(SewerageInfoBean.WellBeen.class, "problem_id", infoBean.getDbid() + "");
                        AMDatabase.getInstance().delete(infoBean);
                        initData();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
        initData();
    }

    private PSHAffairDetail switchBean(SewerageInfoBean sewerageInfoBean) {
        PSHAffairDetail pshAffairDetail = new PSHAffairDetail();
        pshAffairDetail.setCode(200);
        pshAffairDetail.setIsEdit("false");
        PSHAffairDetail.DataBean  dataBean = new PSHAffairDetail.DataBean();
        dataBean.setMph(sewerageInfoBean.getMph());
        dataBean.setStreet(sewerageInfoBean.getStreet());
        dataBean.setFac1(sewerageInfoBean.getFac1());
        dataBean.setFac1Cont(sewerageInfoBean.getFac1Cont());
        dataBean.setFac1Main(sewerageInfoBean.getFac1Main());
        dataBean.setFac1Record(sewerageInfoBean.getFac1Record());
        dataBean.setFac2(sewerageInfoBean.getFac2());
        dataBean.setFac2Cont(sewerageInfoBean.getFac2Cont());
        dataBean.setFac2Main(sewerageInfoBean.getFac2Main());
        dataBean.setFac2Record(sewerageInfoBean.getFac2Record());
        dataBean.setFac3(sewerageInfoBean.getFac3());
        dataBean.setFac3Cont(sewerageInfoBean.getFac3Cont());
        dataBean.setFac3Main(sewerageInfoBean.getFac3Main());
        dataBean.setFac3Record(sewerageInfoBean.getFac3Record());
        dataBean.setFac4(sewerageInfoBean.getFac4());
        dataBean.setFac4Cont(sewerageInfoBean.getFac4Cont());
        dataBean.setFac4Main(sewerageInfoBean.getFac4Main());
        dataBean.setFac4Record(sewerageInfoBean.getFac4Record());
        dataBean.setAddr(sewerageInfoBean.getAddr());
        dataBean.setVillage(sewerageInfoBean.getVillage());
        dataBean.setJzwcode(sewerageInfoBean.getJzwcode());
        dataBean.setDischargerType1(sewerageInfoBean.getDischargerType1());
        dataBean.setDischargerType2(sewerageInfoBean.getDischargerType2());
        dataBean.setDescription(sewerageInfoBean.getDescription());
        dataBean.setCert1Code(sewerageInfoBean.getCert1Code());
        dataBean.setCert3Code(sewerageInfoBean.getCert3Code());
        dataBean.setCert4Code(sewerageInfoBean.getCert4Code());
        dataBean.setOperatorTele(sewerageInfoBean.getOperatorTele());
        dataBean.setLoginName(sewerageInfoBean.getLoginName());
        dataBean.setDoorplateAddressCode(sewerageInfoBean.getDoorplateAddressCode());
        dataBean.setParentOrgId(sewerageInfoBean.getParentOrgId());
        dataBean.setDirectOrgId(dataBean.getDirectOrgId());
        dataBean.setFqname(sewerageInfoBean.getFqname());
        dataBean.setOwnerTele(sewerageInfoBean.getOwnerTele());
        dataBean.setPsdy(sewerageInfoBean.getPsdy());
        dataBean.setHouseIdFlag(sewerageInfoBean.getHouseIdFlag());
        dataBean.setOwner(sewerageInfoBean.getOwner());
        dataBean.setTown(sewerageInfoBean.getTown());
        dataBean.setHouseId(sewerageInfoBean.getHouseId());
        dataBean.setUnitId(dataBean.getUnitId());
        dataBean.setMarkPerson(sewerageInfoBean.getMarkPerson());
        dataBean.setParentOrgName(sewerageInfoBean.getParentOrgName());
        dataBean.setState(sewerageInfoBean.getState());
//        dataBean.setHasCert1Attachment(sewerageInfoBean.);
        dataBean.setDirectOrgName(sewerageInfoBean.getDirectOrgName());
        dataBean.setId(sewerageInfoBean.getId());
        dataBean.setArea(sewerageInfoBean.getArea());
        dataBean.setName(sewerageInfoBean.getName());
        dataBean.setTeamOrgName(sewerageInfoBean.getTeamOrgName());
        dataBean.setHasCert1(sewerageInfoBean.getHasCert1());
        dataBean.setHasCert2(sewerageInfoBean.getHasCert2());
        dataBean.setHasCert3(sewerageInfoBean.getHasCert3());
        dataBean.setHasCert4(sewerageInfoBean.getHasCert4());
        dataBean.setHasCert5(sewerageInfoBean.getHasCert5());
        dataBean.setHasCert7(sewerageInfoBean.getHasCert7());

        dataBean.setMarkPersonId(sewerageInfoBean.getMarkPersonId());
        dataBean.setTeamOrgId(sewerageInfoBean.getTeamOrgId());
        dataBean.setWellBeen(sewerageInfoBean.getWellBeen());
        dataBean.setVolume(sewerageInfoBean.getVolume());
        dataBean.setOperator(sewerageInfoBean.getOperator());
        dataBean.setX(sewerageInfoBean.getX());
        dataBean.setY(sewerageInfoBean.getY());
        dataBean.setPsxkFzrq(sewerageInfoBean.getPsxkFzrq());
        dataBean.setPsxkJzrq(sewerageInfoBean.getPsxkJzrq());
        dataBean.setPsxkLx(sewerageInfoBean.getPsxkLx());
        dataBean.setCheckPerson(sewerageInfoBean.getCheckPerson());
        dataBean.setSfgypsh(sewerageInfoBean.isSfgypsh());
        dataBean.setWaterNo(sewerageInfoBean.getWaterNo());

        dataBean.setPsdyId(sewerageInfoBean.getPsdyId());
        dataBean.setPsdyName(sewerageInfoBean.getPsdyName());
        dataBean.setDischargerType3(sewerageInfoBean.getDischargerType3());

        pshAffairDetail.setData(dataBean);
        return pshAffairDetail;
    }

    private void initData(){
        infoBeans = AMDatabase.getInstance().getQueryByWhere(
                SewerageInfoBean.class,"markPersonId", BaseInfoManager.getUserId(mContext));
        for(SewerageInfoBean infoBean:infoBeans){
            List<Photo> allPhotos = AMDatabase.getInstance().getQueryByWhere(Photo.class, "problem_id", infoBean.getDbid() + "");
            List<Photo> photoList0 = new ArrayList<>();
            List<Photo> thumPhotoList0 = new ArrayList<>();
            List<Photo> photoList1 = new ArrayList<>();
            List<Photo> thumPhotoList1 = new ArrayList<>();
            List<Photo> photoList2 = new ArrayList<>();
            List<Photo> thumPhotoList2= new ArrayList<>();
            List<Photo> photoList3 = new ArrayList<>();
            List<Photo> thumPhotoList3 = new ArrayList<>();
            List<Photo> photoList4 = new ArrayList<>();
            List<Photo> thumPhotoList4 = new ArrayList<>();
            List<Photo> photoList5 = new ArrayList<>();
            List<Photo> thumPhotoList5 = new ArrayList<>();
            List<Photo> photoList7 = new ArrayList<>();
            List<Photo> thumPhotoList7 = new ArrayList<>();

            for(Photo photo:allPhotos){
                if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS0)!=-1){
                    photo.setPhotoName(photo.getPhotoName().substring(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS0)+PhotoUploadType.UPLOAD_FOR_PHOTOS0.length()));
                    if(photo.getType() == 1){
                        photoList0.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList0.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS1)!=-1){
                    if(photo.getType() == 1){
                        photoList1.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList1.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS2)!=-1){
                    if(photo.getType() == 1){
                        photoList2.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList2.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS3)!=-1){
                    if(photo.getType() == 1){
                        photoList3.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList3.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS4)!=-1){
                    if(photo.getType() == 1){
                        photoList4.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList4.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS5)!=-1){
                    if(photo.getType() == 1){
                        photoList5.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList5.add(photo);
                    }
                }else  if(photo.getPhotoName().indexOf(PhotoUploadType.UPLOAD_FOR_PHOTOS7)!=-1){
                    if(photo.getType() == 1){
                        photoList7.add(photo);
                    }else if(photo.getType() == 2){
                        thumPhotoList7.add(photo);
                    }
                }
            }
            infoBean.setPhotos0(photoList0);
            infoBean.setThumbnailPhotos0(thumPhotoList0);
            infoBean.setPhotos1(photoList1);
            infoBean.setThumbnailPhotos1(thumPhotoList1);
            infoBean.setPhotos2(photoList2);
            infoBean.setThumbnailPhotos2(thumPhotoList2);
            infoBean.setPhotos3(photoList3);
            infoBean.setThumbnailPhotos3(thumPhotoList3);
            infoBean.setPhotos4(photoList4);
            infoBean.setThumbnailPhotos4(thumPhotoList4);
            infoBean.setPhotos5(photoList5);
            infoBean.setThumbnailPhotos5(thumPhotoList5);
            infoBean.setPhotos7(photoList7);
            infoBean.setThumbnailPhotos7(thumPhotoList7);
            List<FileBean> fileBeans = AMDatabase.getInstance().getQueryByWhere(FileBean.class, "problem_id", infoBean.getDbid() + "");
            infoBean.setFiles(fileBeans);
            List<SewerageInfoBean.WellBeen> wellBeens = AMDatabase.getInstance().getQueryByWhere(SewerageInfoBean.WellBeen.class, "problem_id", infoBean.getDbid() + "");
            infoBean.setWellBeen(wellBeens);
        }
        Collections.sort(infoBeans, new Comparator<SewerageInfoBean>() {
            @Override
            public int compare(SewerageInfoBean o1, SewerageInfoBean o2) {
                if(o1.getSaveTime() > o2.getSaveTime()){
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        uploadDraftAdapter.addData(infoBeans);
        uploadDraftAdapter.notifyDataSetChanged();
        if(ListUtil.isEmpty(infoBeans)){
            tv_draft_empty.setVisibility(View.VISIBLE);
            rv_upload_draft.setVisibility(View.GONE);
        } else {
            tv_draft_empty.setVisibility(View.GONE);
            rv_upload_draft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 123:
//                    SewerageInfoBean infoBean = infoBeans.get(currentPosition);
//                    AMDatabase.getInstance().deleteWhere(Photo.class, "problem_id", infoBean.getDbid() + "");
//                    AMDatabase.getInstance().deleteWhere(SewerageInfoBean.WellBeen.class, "problem_id", infoBean.getDbid() + "");
//                    AMDatabase.getInstance().delete(infoBean);
                    initData();
                    break;
            }
        }
        if(requestCode == 123
                && resultCode == 123){
            initData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
