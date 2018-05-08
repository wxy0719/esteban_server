package com.esteban.model;

public class UserLog {
    private String userlogid;

    private String content;

    private String userid;

    private String ipaddr;

    private String time;

    public String getUserlogid() {
        return userlogid;
    }

    public void setUserlogid(String userlogid) {
        this.userlogid = userlogid == null ? null : userlogid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr == null ? null : ipaddr.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }
}