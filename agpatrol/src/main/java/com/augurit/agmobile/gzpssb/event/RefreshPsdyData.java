package com.augurit.agmobile.gzpssb.event;

/**
 * 刷新门牌信息
 *
 * Created by xcl on 2017/11/14.
 */

public class RefreshPsdyData {
    private double x;
    private double y;

    public RefreshPsdyData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
