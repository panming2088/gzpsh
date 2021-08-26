package com.augurit.agmobile.gzps.setting.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.patrolcore.common.table.dao.local.TableDBService;
import com.augurit.agmobile.patrolcore.common.table.model.DictionaryItem;
import com.augurit.am.fw.utils.AMFileUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by liangsh on 2018/1/3.
 */

public class UpdateNoticeDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.dialog_update_notice, container);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        final View btn_sure = view.findViewById(R.id.btn_sure);
        window.setBackgroundDrawableResource(R.drawable.corner_color_write);//注意此处
        window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;

        try {
            TableDBService dbService = new TableDBService(getContext());
            List<DictionaryItem> dictionaryItems = dbService.getDictionaryByTypecodeInDB("A207");
//            String htmlStr = AMFileUtil.readStringFromAsset(getContext(), "flsm.html", Charset.defaultCharset());
            tv.setText("        "+dictionaryItems.get(0).getNote());
//            tv.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        return view;
    }

}
