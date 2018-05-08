package com.esteban.model;

public class AdviceUser {
    private String id;

    private String userid;

    private String ipadr;

    private String time;

    private String type;

    private String areaname;

    private String provname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getIpadr() {
        return ipadr;
    }

    public void setIpadr(String ipadr) {
        this.ipadr = ipadr == null ? null : ipadr.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public String getProvname() {
        return provname;
    }

    public void setProvname(String provname) {
        this.provname = provname == null ? null : provname.trim();
    }
}