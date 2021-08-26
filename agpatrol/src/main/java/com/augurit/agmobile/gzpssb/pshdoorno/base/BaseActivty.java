package com.augurit.agmobile.gzpssb.pshdoorno.base;

import android.app.Activity;
import android.os.Bundle;

import com.augurit.agmobile.gzpssb.pshdoorno.base.BasePersenter;

/**
 * V  IGrilView接口
 *
 */

public abstract  class BaseActivty<V,T extends BasePersenter<V>> extends Activity {
    protected  T mPresent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresent=createPresent();
        mPresent.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        mPresent.detach();
        super.onDestroy();

    }
    protected abstract T createPresent() ;
}
