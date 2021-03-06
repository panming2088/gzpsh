package com.augurit.agmobile.gzpssb.common.view;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzpssb.common.callback.Callback3;
import com.augurit.agmobile.patrolcore.common.preview.view.PhotoPagerActivity;
import com.augurit.am.cmpt.media.MediaScannerFile;
import com.augurit.am.cmpt.permission.PermissionsUtil;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.FileHeaderConstant;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.HSPVFileUtil;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.HorizontalScrollPhotoView;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.HorizontalScrollPhotoViewAdapter;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.ImageUtil;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.Photo;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.PhotoButtonUtil;
import com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.PhotoExtra;
import com.augurit.am.cmpt.widget.filepicker.utils.FileUtils;
import com.augurit.am.fw.utils.CompressPictureUtil;
import com.augurit.am.fw.utils.ListUtil;
import com.augurit.am.fw.utils.UrlUtil;
import com.augurit.am.fw.utils.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.bean.Image;
import rx.Observable;
import rx.Subscriber;

import static com.augurit.am.cmpt.widget.HorizontalScrollPhotoView.PhotoButtonUtil.RESULT_CAPTURE_PHOTO;

/**
 * ???????????????
 *
 * @author ????????? ???xuciluan
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.gzps.common.widget
 * @createTime ???????????? ???17/11/7
 * @modifyBy ????????? ???xuciluan
 * @modifyTime ???????????? ???17/11/7
 * @modifyMemo ??????????????? ????????????????????????
 */

public class MultiTakePhotoTableItem extends LinearLayout {
    boolean showCamera = false;
    private static final int REQUEST_IMAGE = 2;
    private int mMode = MultiImageSelectorActivity.MODE_MULTI;
    private List<Photo> selectedPhotos;
    private View openPhotoView;
    protected TextView tvTitle;
    protected TextView tv_requiredTag;
    protected TextView tv_photo_num;   //????????????????????????????????????
    private HorizontalScrollPhotoView photoView;
    private View vg_action_open_photo;
    private HorizontalScrollPhotoViewAdapter adapter;
    private Intent mPhotoIntentData;
    private PhotoExtra mPhotoExtra;
    private PhotoExtra mPicExtra;
    private TextView mExample;
    private boolean exampleEnable = false;  //????????????????????????
    private Callback3<Photo, Photo> onDeletePhotoListener;
    private Callback3<Photo, Photo> onAddPhotoListener;
    private boolean ifReadOnly = false;
    private List<Image> mImages;
    private List<Photo> selThumbPhotos;   //?????????

    /**
     * ??????????????????
     */
    private List<Photo> deletedPhotos;
    private boolean isWorking = false;//?????????????????????????????????
    private int maxPhotoNum = 9;     //????????????????????????15?????????
    private RelativeLayout ll_tv_select_title_container;

    public MultiTakePhotoTableItem(Context context) {
        this(context, null);
    }

    public MultiTakePhotoTableItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        selectedPhotos = new ArrayList<>();
        selThumbPhotos = new ArrayList<>();
        openPhotoView = LayoutInflater.from(context).inflate(getLayoutId(), this);
        tvTitle = (TextView) findViewById(R.id.title);
        tv_requiredTag = (TextView) findViewById(R.id.tv_requiredTag);
        tv_photo_num = (TextView) findViewById(R.id.tv_photo_num);
        photoView = (HorizontalScrollPhotoView) findViewById(R.id.horizontalScrollPhotoView);
        mExample = (TextView) findViewById(R.id.example);
        ll_tv_select_title_container = (RelativeLayout) findViewById(R.id.ll_tv_select_title_container);
//        iv_open_photo = (ImageView) findViewById(R.id.iv_open_photo);
        vg_action_open_photo = findViewById(R.id.action_open_photo);

        vg_action_open_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomsheetDialog();
            }
        });
        adapter = new HorizontalScrollPhotoViewAdapter(
                getContext(), photoView, selectedPhotos);
        photoView.setOnItemClickListener(new HorizontalScrollPhotoView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setBackgroundDrawable(getResources().getDrawable(
                        com.augurit.agmobile.patrolcore.R.drawable.itme_background_checked));
                if (!ListUtil.isEmpty(selectedPhotos)) {
                    Intent intent = new Intent(getContext(), PhotoPagerActivity.class);
                    intent.putExtra("BITMAPLIST", (ArrayList<Photo>) selectedPhotos);
                    intent.putExtra("POSITION", position);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), view, "shareTransition").toBundle());
                    } else {
                        getContext().startActivity(intent);
                    }

                }
            }
        });
        photoView.setCurrentImageChangeListener(new HorizontalScrollPhotoView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
                if (viewIndicator != null) {
                    viewIndicator.setBackgroundDrawable(getResources().getDrawable(
                            com.augurit.agmobile.patrolcore.R.drawable.itme_background_checked));
                }
            }
        });

        photoView.setOnItemLongClickListener(new HorizontalScrollPhotoView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                if (ListUtil.isEmpty(selectedPhotos)) {
                    return true;
                }
                if (ifReadOnly) {
                    return true;
                }

                view.setBackgroundDrawable(getResources().getDrawable(
                        com.augurit.agmobile.patrolcore.R.drawable.itme_background_checked));
                ImageUtil.deleteImage(getContext(),
                        selectedPhotos.get(position).getLocalPath(),
                        new ImageUtil.OnDeletedPhotoListener() {
                            @Override
                            public void onDeletedPhoto() {
                                if (deletedPhotos == null) {
                                    deletedPhotos = new ArrayList<>();
                                }
                                if (deletedThumbPhotos == null) {
                                    deletedThumbPhotos = new ArrayList<>();
                                }
                                deletedPhotos.add(selectedPhotos.get(position));
                                deleteThumbnail(selectedPhotos.get(position));
                                selectedPhotos.remove(position);
                                refreshPhotoViewToFirst();
                                refreshPhotoNum();
                                ToastUtil.shortToast(getContext(), "????????????!");
                            }
                        });
                return true;
            }
        });


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TakePhotoTableItem);
        String textName = a.getString(R.styleable.TakePhotoTableItem_photoText);

        TextView tv_title = (TextView) openPhotoView.findViewById(R.id.title);
        tv_title.setText(textName);
        a.recycle();
        refreshPhotoNum();
    }

    protected int getLayoutId() {
        return R.layout.view_take_photo;
    }

    public TextView getmExample() {
        return mExample;
    }

    private void refreshPhotoViewToFirst() {
        adapter.notifyDataSetChanged();
        photoView.initDatas(adapter);
    }

    private void refreshPhotoNum() {
        tv_photo_num.setText("???" + selectedPhotos.size() + "/" + maxPhotoNum + "???");
        if (selectedPhotos.size() >= maxPhotoNum
                || ifReadOnly) {
            vg_action_open_photo.setVisibility(View.GONE);
        } else {
            vg_action_open_photo.setVisibility(View.VISIBLE);
        }
    }

    public void showBottomsheetDialog() {
        PermissionsUtil.getInstance()
                .requestPermissions(
                        (Activity) getContext(),
                        true,
                        new PermissionsUtil.OnPermissionsCallback() {
                            @Override
                            public void onPermissionsGranted(List<String> perms) {
                                showBottomsheetDialogWithCheck();
                            }
                            @Override
                            public void onPermissionsDenied(List<String> perms) {
                                ToastUtil.shortToast(getContext(), "???????????????????????????????????????");
                            }
                        },
                        Manifest.permission.CAMERA);
    }

    private void showBottomsheetDialogWithCheck() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View popupWindowView = LayoutInflater.from(getContext()).inflate(com.augurit.agmobile.patrolcore.R.layout.bottom_pop, null);
        ImageButton camera = (ImageButton) popupWindowView.findViewById(com.augurit.agmobile.patrolcore.R.id.camera);
        ImageButton photo = (ImageButton) popupWindowView.findViewById(com.augurit.agmobile.patrolcore.R.id.photo);
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode = MultiImageSelectorActivity.MODE_SINGLE;
                mPhotoExtra = PhotoButtonUtil.registTakePhotoButton((Activity) getContext(), HSPVFileUtil.getTakePathPhoto());
                isWorking = true;
                bottomSheetDialog.dismiss();
            }
        });
        photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicExtra = PhotoButtonUtil.registOpenPhotoButton2((Activity) getContext(), HSPVFileUtil.getPathPhoto());
                isWorking = true;
                mMode = MultiImageSelectorActivity.MODE_MULTI;
                MultiImageSelector selector = MultiImageSelector.create(getContext());
                selector.showCamera(showCamera);
                selector.count(maxPhotoNum - selectedPhotos.size());
                selector.multi();
                selector.start((Activity) getContext(), REQUEST_IMAGE);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(popupWindowView);
        bottomSheetDialog.show();
    }


    private void refreshImageAdapter() {
        if (mPhotoExtra != null) {
            final String thumbnailFileName = FileUtils.getFilenameWithoutSubffix(mPhotoExtra.getFilePath())
                    + "_thumbnail."
                    + FileUtils.getFileSuffix(mPhotoExtra.getFilePath());
            final String thumbnailPath = UrlUtil.buildURL(thumbnailFileName, HSPVFileUtil.getPathThumbnail());

            CompressPictureUtil
                    .createThumbnail(
                            getContext(),
                            mPhotoExtra.getFilePath(),
                            thumbnailPath,
                            false,
                            new CompressPictureUtil.OnCompressPictureOverListener() {
                                @Override
                                public void onCompressPictureOver(String filePath) {
                                    Photo photo = new Photo();
                                    photo.setLocalPath(filePath);
                                    photo.setPhotoPath(thumbnailPath);
                                    photo.setPhotoName(thumbnailFileName);
                                    photo.setPhotoTime(mPhotoExtra.getPhotoTime());
                                    selThumbPhotos.add(photo);
                                    CompressPictureUtil
                                            .startAsyAsyncTaskOrNot(
                                                    (Activity) getContext(),
                                                    mPhotoExtra.getFilePath(),
                                                    new CompressPictureUtil.OnCompressPictureOverListener() {
                                                        @Override
                                                        public void onCompressPictureOver(String filePath) {
                                                            addPhotoAndUpdateImageAdapter();
                                                        }
                                                    });
                                }
                            });
        }
    }

    private void compressPictures() {
        Observable.from(mImages).subscribe(new Subscriber<Image>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final Image image) {
                final String thumbnailFileName = FileUtils.getFilenameWithoutSubffix(mPicExtra.getFilePath() + image.getFilename())
                        + "_thumbnail."
                        + FileUtils.getFileSuffix(mPicExtra.getFilePath() + image.getFilename());

                final String thumbnailPath = UrlUtil.buildURL(thumbnailFileName, HSPVFileUtil.getPathThumbnail());
                CompressPictureUtil
                        .createThumbnail(
                                getContext(),
                                mPicExtra.getFilePath() + image.getFilename(),
                                thumbnailPath,
                                false,
                                new CompressPictureUtil.OnCompressPictureOverListener() {
                                    @Override
                                    public void onCompressPictureOver(String filePath) {
                                        Photo photo = new Photo();
                                        photo.setLocalPath(filePath);
                                        photo.setPhotoPath(thumbnailPath);
                                        photo.setPhotoName(thumbnailFileName);
                                        photo.setPhotoTime(image.getPhotoTime());
                                        selThumbPhotos.add(photo);
                                        CompressPictureUtil.startAsyAsyncTaskOrNot(
                                                (Activity) getContext(),
                                                mPicExtra.getFilePath() + image.getFilename(),
                                                new CompressPictureUtil.OnCompressPictureOverListener() {
                                                    @Override
                                                    public void onCompressPictureOver(String filePath) {
                                                                addPicsAndRefreshAdapter(image);
                                                    }
                                                });
                                    }
                                });
            }
        });
    }

    private void addPhotoAndUpdateImageAdapter() {
        if (mPhotoExtra != null) {
            Photo photo = new Photo();
            photo.setLocalPath(mPhotoExtra.getFilePath());
            photo.setPhotoPath(FileHeaderConstant.SDCARD_URL_BASE
                    + mPhotoExtra.getFilePath());
            photo.setPhotoName(mPhotoExtra.getFilename());
            photo.setPhotoTime(mPhotoExtra.getPhotoTime());
            selectedPhotos.add(photo);
            if (onAddPhotoListener != null) {
                onAddPhotoListener.onCallback(photo, selThumbPhotos.get(selThumbPhotos.size() - 1));
            }
            MediaScannerFile.scanFile(getContext(), new String[]{mPhotoExtra.getFilePath()}, new String[]{"image/jpeg"}, null);
            refreshPhotoViewToLast();
            refreshPhotoNum();
        }

    }

    private void addPicsAndRefreshAdapter(Image imageSrc) {
        if (imageSrc != null) {
            Photo photo = new Photo();
            photo.setLocalPath(mPicExtra.getFilePath() + imageSrc.getFilename());
            photo.setPhotoPath(FileHeaderConstant.SDCARD_URL_BASE +mPicExtra.getFilePath() + imageSrc.getFilename());
            photo.setPhotoName(imageSrc.getFilename());
            photo.setPhotoTime(imageSrc.getPhotoTime());
            selectedPhotos.add(photo);
            if (onAddPhotoListener != null) {
                onAddPhotoListener.onCallback(photo, selThumbPhotos.get(selThumbPhotos.size() - 1));
            }
            MediaScannerFile.scanFile(getContext(), new String[]{imageSrc.getFilePath()}, new String[]{"image/jpeg"}, null);
            refreshPhotoViewToLast();
            refreshPhotoNum();
        }
    }

    public void doCopyPhoto() {
        try {
            if (mMode == MultiImageSelectorActivity.MODE_MULTI) {
                mImages = (List<Image>) mPhotoIntentData.getSerializableExtra(MultiImageSelector.EXTRA_RESULT);
                for (Image img : mImages) {
                    PhotoButtonUtil.copyFile(img.getFilePath(), mPicExtra.getFilePath() + img.getFilename());
//                    PhotoButtonUtil.openPhotoCopy((Activity) getContext(), img.getBitmapDataUri(),img.getId(), mPicExtra.getFilePath() + img.getFilename());
                }
            }
            compressPictures();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshPhotoViewToLast() {
        adapter.notifyDataSetChanged();
        photoView.notifyDataSetChanged(adapter);
    }

    private void deleteThumbnail(Photo srcPhoto) {
//        if (ListUtil.isEmpty(selThumbPhotos)) {
//            return;
//        }
        String srcFileNameWithoutSuffix = FileUtils.getFilenameWithoutSubffix(srcPhoto.getPhotoName());
        Photo waitToDelPhoto = null;
        for (Photo photo : selThumbPhotos) {
            if (photo.getPhotoName().contains(srcFileNameWithoutSuffix)) {
                waitToDelPhoto = photo;
                break;
            }
        }
        if(waitToDelPhoto != null){
            selThumbPhotos.remove(waitToDelPhoto);
            deletedThumbPhotos.add(waitToDelPhoto);
        }
        if (waitToDelPhoto != null) {
            selThumbPhotos.remove(waitToDelPhoto);
        }

        if (onDeletePhotoListener != null) {
            onDeletePhotoListener.onCallback(srcPhoto, waitToDelPhoto);
        }
    }

    public void setAddPhotoEnable(boolean enable) {
        if (enable) {
            tvTitle.setText("????????????");
            vg_action_open_photo.setVisibility(VISIBLE);
            if (exampleEnable) {
                mExample.setVisibility(VISIBLE);
            }
        } else {
            tvTitle.setText("????????????");
            vg_action_open_photo.setVisibility(GONE);
            mExample.setVisibility(GONE);
        }
        ifReadOnly = !enable;
        openPhotoView.setClickable(enable);

    }

    public void setRequired(boolean required) {
        if (required) {
            tv_requiredTag.setVisibility(VISIBLE);
        } else {
            tv_requiredTag.setVisibility(GONE);
        }
    }

    /**
     * ????????????????????????
     *
     * @param isshow
     */
    public void setImageIsShow(boolean isshow) {
        if (isshow) {
            vg_action_open_photo.setVisibility(VISIBLE);
        } else {
            vg_action_open_photo.setVisibility(GONE);
        }
        refreshPhotoNum();
    }


    public void setNumContentVisible(int visible) {
        ll_tv_select_title_container.setVisibility(visible);
    }

    /**
     * ?????????????????????????????????
     *
     * @param show   ????????????????????????
     * @param maxNum ????????????
     */
    public void setPhotoNumShow(boolean show, int maxNum) {
        if (show) {
            tv_photo_num.setVisibility(View.VISIBLE);
        } else {
            tv_photo_num.setVisibility(View.GONE);
        }
        this.maxPhotoNum = maxNum;
        refreshPhotoNum();
    }

    public void setPhotoExampleEnable(boolean enable) {
        exampleEnable = enable;
        if (enable) {
            mExample.setVisibility(VISIBLE);
        } else {
            mExample.setVisibility(GONE);
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOnDeletePhotoListener(Callback3<Photo, Photo> onDeletePhotoListener) {
        this.onDeletePhotoListener = onDeletePhotoListener;
    }

    public void setOnAddPhotoListener(Callback3<Photo, Photo> onAddPhotoListener) {
        this.onAddPhotoListener = onAddPhotoListener;
    }

    /**
     * ?????????????????????????????????????????????????????????Activity???????????????
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (isWorking) {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case RESULT_CAPTURE_PHOTO://????????????
                        refreshImageAdapter();
                        break;
                    case REQUEST_IMAGE://??????????????????
                        if (null == data) {
                            return;
                        }
                        mPhotoIntentData = data;
                        doCopyPhoto();
                        break;
                    default:
                        break;

                }
            }
        }

        isWorking = false;
    }

    public List<Photo> getSelectedPhotos() {
        List<Photo> photos = new ArrayList<>();
        for (Photo photo : selectedPhotos) {
            Photo copy = new Photo();
            copy.setId(photo.getId());
            copy.setPhotoName(photo.getPhotoName());
            copy.setPhotoPath(photo.getPhotoPath());
            copy.setLocalPath(photo.getLocalPath());
            copy.setThumbPath(photo.getThumbPath());
            copy.setPhotoTime(photo.getPhotoTime());
            photos.add(copy);
        }
        return photos;
    }

    public void setSelectedPhotos(List<Photo> selectedPhotos) {
        if (ListUtil.isEmpty(selectedPhotos)) {
            return;
        }
        this.selectedPhotos = selectedPhotos;
        adapter.notifyDataSetChanged(selectedPhotos);
        refreshPhotoViewToFirst();
        refreshPhotoNum();
    }

    public void setSelThumbPhotos(List<Photo> selThumbPhotos) {
        if (ListUtil.isEmpty(selectedPhotos)) {
            return;
        }
        this.selThumbPhotos = selThumbPhotos;
    }

    public List<Photo> getThumbnailPhotos() {
        List<Photo> photos = new ArrayList<>();
        if(ListUtil.isEmpty(selThumbPhotos)){
            return photos;
        }
        for (Photo photo : selThumbPhotos) {
            Photo copy = new Photo();
            copy.setId(photo.getId());
            copy.setPhotoName(photo.getPhotoName());
            copy.setPhotoPath(photo.getPhotoPath());
            copy.setLocalPath(photo.getLocalPath());
            copy.setThumbPath(photo.getThumbPath());
            photos.add(copy);
        }
        return photos;
    }

    public void setReadOnly() {
        this.ifReadOnly = true;
        if (tv_photo_num != null) {
            tv_photo_num.setVisibility(GONE);
        }
    }

    public void setEditable() {
        this.ifReadOnly = false;
    }

    /**
     * ????????????????????????
     *
     * @return ?????????????????????????????????null
     */
    public List<Photo> getDeletedPhotos() {
        return deletedPhotos;
    }

    private List<Photo> deletedThumbPhotos;

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    public List<Photo> getDeletedThumbPhotos() {
        return deletedThumbPhotos;
    }

    public void clear(){
        this.selectedPhotos = new ArrayList<>();
        this.selThumbPhotos = new ArrayList<>();
        adapter.notifyDataSetChanged(selectedPhotos);
        refreshPhotoViewToFirst();
        refreshPhotoNum();
    }
}