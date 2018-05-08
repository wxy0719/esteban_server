package com.esteban.model;

public class MenuTree {
    private String id;

    private String name;

    private String isforder;

    private String url;

    private String nodeGrade;

    private String parentNode;

    private String nodeImg;

    private String status;

    private String order;

    private String rightNo;

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

    public String getIsforder() {
        return isforder;
    }

    public void setIsforder(String isforder) {
        this.isforder = isforder == null ? null : isforder.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getNodeGrade() {
        return nodeGrade;
    }

    public void setNodeGrade(String nodeGrade) {
        this.nodeGrade = nodeGrade == null ? null : nodeGrade.trim();
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode == null ? null : parentNode.trim();
    }

    public String getNodeImg() {
        return nodeImg;
    }

    public void setNodeImg(String nodeImg) {
        this.nodeImg = nodeImg == null ? null : nodeImg.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order == null ? null : order.trim();
    }

    public String getRightNo() {
        return rightNo;
    }

    public void setRightNo(String rightNo) {
        this.rightNo = rightNo == null ? null : rightNo.trim();
    }
}