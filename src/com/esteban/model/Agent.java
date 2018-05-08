package com.esteban.model;

/**
 * @author esteban
 * @since 2014年5月22日
 */
public class Agent {

    private String mobile;// F_MOBILE Varchar2(32) N 电信手机号码
    private String password;// F_PASSWORD Varchar2(32) 密码
    private Integer type;// F_TYPE Number N 账号类型：1=网站代理，2=个人代理
    private String name;// F_NAME Varchar2(32) 姓名
    private String idNum;// F_IDNUM Varchar2(32) 证件号码
    private String time;// F_TIME Varchar2(14) N 申请时间
    private int status;// F_STATUS Number N 状态：0=申请，1=审核通过，2=审核不通过 ，3
                           // 审核通过未回复

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the idNum
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * @param idNum the idNum to set
     */
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
