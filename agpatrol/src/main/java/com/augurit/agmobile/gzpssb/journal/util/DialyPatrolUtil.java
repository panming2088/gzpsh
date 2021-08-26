package com.augurit.agmobile.gzpssb.journal.util;

import com.augurit.agmobile.gzps.common.model.ServerAttachment;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournalDetail;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.fw.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 设施纠错工具
 * Created by xcl on 2017/11/17.
 */

public class DialyPatrolUtil {

    /**
     *
     * @param modifyFacilityDetail
     * @return
     */
    public static PSHJournal getModifiedFacility(PSHJournalDetail modifyFacilityDetail){

        List<ServerAttachment.ServerAttachmentDataBean> rows = modifyFacilityDetail.getRows();
        if(!ListUtil.isEmpty(rows)){
            List<Photo> photos = new ArrayList<>();
            for (ServerAttachment.ServerAttachmentDataBean dataBean : rows){
                Photo photo = new Photo();
                photo.setId(Long.valueOf(dataBean.getId()));
                photo.setPhotoPath(dataBean.getAttPath());
                photo.setPhotoName(dataBean.getAttName());
                photo.setThumbPath(dataBean.getThumPath());
                photos.add(photo);
            }
            modifyFacilityDetail.getData().setPhotos(photos);
        }

        return modifyFacilityDetail.getData();
    }
}
