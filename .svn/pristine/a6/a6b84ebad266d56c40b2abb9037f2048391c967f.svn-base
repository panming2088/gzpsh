package com.augurit.agmobile.gzpssb.common;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;


import com.augurit.agmobile.gzps.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @PROJECT MyApplication
 * @USER Augurit
 * @CREATE 2020/11/18 14:28
 */
public class TabLayoutHelper {
    public static void addTabs(TabLayout tabLayout, String... texts) {
        for (int i = 0; i < texts.length; i++) {
            addTab(tabLayout, texts[i], i == 0, i == texts.length - 1);
        }
    }

    private static void addTab(TabLayout tabLayout, String text, boolean isStart, boolean isEnd) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        final Drawable drawable;
        if (isStart) {
            drawable = ResourcesCompat.getDrawable(tabLayout.getResources(), R.drawable.selector_tab_left_bg, null);
        } else if (isEnd) {
            drawable = ResourcesCompat.getDrawable(tabLayout.getResources(), R.drawable.selector_tab_right_bg, null);
        } else {
            drawable = ResourcesCompat.getDrawable(tabLayout.getResources(), R.drawable.selector_tab_middle_bg, null);
        }
        try {
            Class<TabLayout.Tab> tabClass = TabLayout.Tab.class;
            Field viewField = tabClass.getDeclaredField("mView");
            viewField.setAccessible(true);
            Object viewObj = viewField.get(tab);
            Method setBackground = View.class.getMethod("setBackground", Drawable.class);
            setBackground.setAccessible(true);
            setBackground.invoke(viewObj, drawable);
//            Method setBackground = View.class.getMethod("setBackground", Drawable.class);
//            setBackground.setAccessible(true);
//            setBackground.invoke(tab.view, drawable);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabLayout.addTab(tab);
    }

    public static void updateText(TabLayout tabLayout,String... txts) {
        if (txts.length!=0) {
            for (int i = 0; i < txts.length; i++) {
                tabLayout.getTabAt(i).setText(txts[i]);
            }
        }
    }
}
