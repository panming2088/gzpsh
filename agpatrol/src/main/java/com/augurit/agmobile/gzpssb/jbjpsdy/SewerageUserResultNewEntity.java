package com.augurit.agmobile.gzpssb.jbjpsdy;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 排水户实体类
 *
 * @PROJECT GZPS
 * @USER Augurit
 * @CREATE 2020/11/9 10:53
 */
public class SewerageUserResultNewEntity {
    private List<Pair<String, Integer>> typeCounts = new ArrayList<>(13);
    private List<SewerageUserEntity> sewerageUserEntities;

    public List<Pair<String, Integer>> getTypeCounts() {
        return typeCounts;
    }

    public void setTypeCounts(List<Pair<String, Integer>> typeCounts) {
        this.typeCounts = typeCounts;
    }

    public void addTypeCount(Pair<String, Integer> typeCount) {
        typeCounts.add(typeCount);
    }

    public void addTypeCount(Pair<String, Integer> typeCount, int position) {
        typeCounts.add(position, typeCount);
    }

    public List<SewerageUserEntity> getSewerageUserEntities() {
        return sewerageUserEntities;
    }

    public void setSewerageUserEntities(List<SewerageUserEntity> sewerageUserEntities) {
        this.sewerageUserEntities = sewerageUserEntities;
    }
}
