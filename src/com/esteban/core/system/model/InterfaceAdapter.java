package com.esteban.core.system.model;

public class InterfaceAdapter {
    private String adapterNo;

    private String serviceName;

    private String serviceMethod;

    private String serviceDesc;

    private String isValid;

    public String getAdapterNo() {
        return adapterNo;
    }

    public void setAdapterNo(String adapterNo) {
        this.adapterNo = adapterNo == null ? null : adapterNo.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod == null ? null : serviceMethod.trim();
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc == null ? null : serviceDesc.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }
}