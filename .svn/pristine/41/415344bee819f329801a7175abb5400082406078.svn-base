package com.augurit.agmobile.gzps.uploadfacility.view;

import com.augurit.agmobile.gzps.uploadfacility.model.ModifiedFacility;
import com.augurit.agmobile.gzps.uploadfacility.model.UploadedFacility;
import com.augurit.agmobile.gzpssb.journal.model.PSHJournal;

/**
 * Created by xcl on 2017/12/2.
 */

public class UploadFacilitySuccessEvent {

    private ModifiedFacility modifiedFacility;
    private UploadedFacility uploadedFacility;
    private PSHJournal mPSHJournal;

    public UploadFacilitySuccessEvent(){

    }

    public UploadFacilitySuccessEvent(ModifiedFacility modifiedFacility){
        this.modifiedFacility = modifiedFacility;
    }

    public UploadFacilitySuccessEvent(UploadedFacility uploadedFacility){
        this.uploadedFacility = uploadedFacility;
    }

    public UploadFacilitySuccessEvent(PSHJournal PSHJournal) {
        mPSHJournal = PSHJournal;
    }

    public ModifiedFacility getModifiedFacility() {
        return modifiedFacility;
    }

    public UploadedFacility getUploadedFacility() {
        return uploadedFacility;
    }

    public PSHJournal getPSHJournal() {
        return mPSHJournal;
    }
}
