package com.augurit.agmobile.gzpssb.pshdoorno.add.mode;

public class SecondLevelPshInfoManger {
    private static SecondLevelPshInfoManger sManger;
    private static SecondLevelPshInfo.SecondPshInfo sInfoBean;

    private SecondLevelPshInfoManger() {
    }
    public void clear() {
        sInfoBean = null;
    }

    public SecondLevelPshInfo.SecondPshInfo getInfoBean() {
        return sInfoBean;
    }
    public static synchronized SecondLevelPshInfoManger getInstance() {
        if (sManger == null) {
            sManger = new SecondLevelPshInfoManger();
        }
        if (sInfoBean == null) {
            sInfoBean = new SecondLevelPshInfo.SecondPshInfo();
        }
        return sManger;
    }
    public void setYjname(String yjname) {
        sInfoBean.setYjname(yjname);
    }
    public void setEjname(String ejname) {
        sInfoBean.setEjname(ejname);
    }
    public void setPshtype1(String pshtype1) {
        sInfoBean.setPshtype1(pshtype1);
    }

    public void setPshtype3(String pshtype3) {
        sInfoBean.setPshtype3(pshtype3);
    }
    public void setPshtype2(String pshtype2) {
        sInfoBean.setPshtype2(pshtype2);
    }
    public void setEjaddr(String ejaddr) {
        sInfoBean.setEjaddr(ejaddr);
    }
    public void setYjname_id(String yjname_id) {
        sInfoBean.setYjname_id(yjname_id);
    }
    public void setYjmenpai(String yjmenpai) {
        sInfoBean.setYjmenpai(yjmenpai);
    }
    public void setUnitId(String unitId) {
        sInfoBean.setUnitId(unitId);
    }

}
