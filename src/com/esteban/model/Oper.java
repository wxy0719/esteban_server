package com.esteban.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oper {
    private String id;

    private String name;

    private String passwd;

    private String des;

    private String status;

    private String createTime;

    private String createOper;

    private String role;

    private String rights;
    
    private List<String> listRights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateOper() {
        return createOper;
    }

    public void setCreateOper(String createOper) {
        this.createOper = createOper == null ? null : createOper.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights == null ? null : rights.trim();
    }
    
    public List<String> getListRights() { 
		if(!"".equals(this.getRights())&&null!=this.getRights()){
			listRights=new ArrayList<String>(Arrays.asList(this.getRights().split(",")));
		}
		return listRights;
	}
	public void setListRights(List<String> listRights) {
		this.listRights = listRights;
	}
}