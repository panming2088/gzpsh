package com.augurit.agmobile.gzps.common.util;

import android.os.Handler;
import android.os.Looper;

/**
 * @PROJECT GZPSH
 * @USER Augurit
 * @CREATE 2021/3/31 19:55
 */
public class MainThreadUtil {
    public static void run(Runnable runnable) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}
