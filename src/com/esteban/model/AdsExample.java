package com.esteban.model;

import java.util.ArrayList;
import java.util.List;

public class AdsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("f_id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("f_id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("f_id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("f_id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("f_id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("f_id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("f_id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("f_id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("f_id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("f_id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("f_id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("f_id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("f_id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("f_id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("f_title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("f_title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("f_title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("f_title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("f_title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("f_title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("f_title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("f_title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("f_title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("f_title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("f_title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("f_title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("f_title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("f_title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andShortdesIsNull() {
            addCriterion("f_shortDes is null");
            return (Criteria) this;
        }

        public Criteria andShortdesIsNotNull() {
            addCriterion("f_shortDes is not null");
            return (Criteria) this;
        }

        public Criteria andShortdesEqualTo(String value) {
            addCriterion("f_shortDes =", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesNotEqualTo(String value) {
            addCriterion("f_shortDes <>", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesGreaterThan(String value) {
            addCriterion("f_shortDes >", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesGreaterThanOrEqualTo(String value) {
            addCriterion("f_shortDes >=", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesLessThan(String value) {
            addCriterion("f_shortDes <", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesLessThanOrEqualTo(String value) {
            addCriterion("f_shortDes <=", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesLike(String value) {
            addCriterion("f_shortDes like", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesNotLike(String value) {
            addCriterion("f_shortDes not like", value, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesIn(List<String> values) {
            addCriterion("f_shortDes in", values, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesNotIn(List<String> values) {
            addCriterion("f_shortDes not in", values, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesBetween(String value1, String value2) {
            addCriterion("f_shortDes between", value1, value2, "shortdes");
            return (Criteria) this;
        }

        public Criteria andShortdesNotBetween(String value1, String value2) {
            addCriterion("f_shortDes not between", value1, value2, "shortdes");
            return (Criteria) this;
        }

        public Criteria andArtIsNull() {
            addCriterion("f_art is null");
            return (Criteria) this;
        }

        public Criteria andArtIsNotNull() {
            addCriterion("f_art is not null");
            return (Criteria) this;
        }

        public Criteria andArtEqualTo(String value) {
            addCriterion("f_art =", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtNotEqualTo(String value) {
            addCriterion("f_art <>", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtGreaterThan(String value) {
            addCriterion("f_art >", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtGreaterThanOrEqualTo(String value) {
            addCriterion("f_art >=", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtLessThan(String value) {
            addCriterion("f_art <", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtLessThanOrEqualTo(String value) {
            addCriterion("f_art <=", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtLike(String value) {
            addCriterion("f_art like", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtNotLike(String value) {
            addCriterion("f_art not like", value, "art");
            return (Criteria) this;
        }

        public Criteria andArtIn(List<String> values) {
            addCriterion("f_art in", values, "art");
            return (Criteria) this;
        }

        public Criteria andArtNotIn(List<String> values) {
            addCriterion("f_art not in", values, "art");
            return (Criteria) this;
        }

        public Criteria andArtBetween(String value1, String value2) {
            addCriterion("f_art between", value1, value2, "art");
            return (Criteria) this;
        }

        public Criteria andArtNotBetween(String value1, String value2) {
            addCriterion("f_art not between", value1, value2, "art");
            return (Criteria) this;
        }

        public Criteria andDesIsNull() {
            addCriterion("f_des is null");
            return (Criteria) this;
        }

        public Criteria andDesIsNotNull() {
            addCriterion("f_des is not null");
            return (Criteria) this;
        }

        public Criteria andDesEqualTo(String value) {
            addCriterion("f_des =", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesNotEqualTo(String value) {
            addCriterion("f_des <>", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesGreaterThan(String value) {
            addCriterion("f_des >", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesGreaterThanOrEqualTo(String value) {
            addCriterion("f_des >=", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesLessThan(String value) {
            addCriterion("f_des <", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesLessThanOrEqualTo(String value) {
            addCriterion("f_des <=", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesLike(String value) {
            addCriterion("f_des like", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesNotLike(String value) {
            addCriterion("f_des not like", value, "des");
            return (Criteria) this;
        }

        public Criteria andDesIn(List<String> values) {
            addCriterion("f_des in", values, "des");
            return (Criteria) this;
        }

        public Criteria andDesNotIn(List<String> values) {
            addCriterion("f_des not in", values, "des");
            return (Criteria) this;
        }

        public Criteria andDesBetween(String value1, String value2) {
            addCriterion("f_des between", value1, value2, "des");
            return (Criteria) this;
        }

        public Criteria andDesNotBetween(String value1, String value2) {
            addCriterion("f_des not between", value1, value2, "des");
            return (Criteria) this;
        }

        public Criteria andImgPathIsNull() {
            addCriterion("f_img_path is null");
            return (Criteria) this;
        }

        public Criteria andImgPathIsNotNull() {
            addCriterion("f_img_path is not null");
            return (Criteria) this;
        }

        public Criteria andImgPathEqualTo(String value) {
            addCriterion("f_img_path =", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotEqualTo(String value) {
            addCriterion("f_img_path <>", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathGreaterThan(String value) {
            addCriterion("f_img_path >", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathGreaterThanOrEqualTo(String value) {
            addCriterion("f_img_path >=", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLessThan(String value) {
            addCriterion("f_img_path <", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLessThanOrEqualTo(String value) {
            addCriterion("f_img_path <=", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathLike(String value) {
            addCriterion("f_img_path like", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotLike(String value) {
            addCriterion("f_img_path not like", value, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathIn(List<String> values) {
            addCriterion("f_img_path in", values, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotIn(List<String> values) {
            addCriterion("f_img_path not in", values, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathBetween(String value1, String value2) {
            addCriterion("f_img_path between", value1, value2, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgPathNotBetween(String value1, String value2) {
            addCriterion("f_img_path not between", value1, value2, "imgPath");
            return (Criteria) this;
        }

        public Criteria andImgDetailIsNull() {
            addCriterion("f_img_detail is null");
            return (Criteria) this;
        }

        public Criteria andImgDetailIsNotNull() {
            addCriterion("f_img_detail is not null");
            return (Criteria) this;
        }

        public Criteria andImgDetailEqualTo(String value) {
            addCriterion("f_img_detail =", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailNotEqualTo(String value) {
            addCriterion("f_img_detail <>", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailGreaterThan(String value) {
            addCriterion("f_img_detail >", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailGreaterThanOrEqualTo(String value) {
            addCriterion("f_img_detail >=", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailLessThan(String value) {
            addCriterion("f_img_detail <", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailLessThanOrEqualTo(String value) {
            addCriterion("f_img_detail <=", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailLike(String value) {
            addCriterion("f_img_detail like", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailNotLike(String value) {
            addCriterion("f_img_detail not like", value, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailIn(List<String> values) {
            addCriterion("f_img_detail in", values, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailNotIn(List<String> values) {
            addCriterion("f_img_detail not in", values, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailBetween(String value1, String value2) {
            addCriterion("f_img_detail between", value1, value2, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andImgDetailNotBetween(String value1, String value2) {
            addCriterion("f_img_detail not between", value1, value2, "imgDetail");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("f_create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("f_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("f_create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("f_create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("f_create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("f_create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("f_create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("f_create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("f_create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("f_create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("f_create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("f_create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("f_create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("f_create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateOperIsNull() {
            addCriterion("f_create_oper is null");
            return (Criteria) this;
        }

        public Criteria andCreateOperIsNotNull() {
            addCriterion("f_create_oper is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOperEqualTo(String value) {
            addCriterion("f_create_oper =", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperNotEqualTo(String value) {
            addCriterion("f_create_oper <>", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperGreaterThan(String value) {
            addCriterion("f_create_oper >", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperGreaterThanOrEqualTo(String value) {
            addCriterion("f_create_oper >=", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperLessThan(String value) {
            addCriterion("f_create_oper <", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperLessThanOrEqualTo(String value) {
            addCriterion("f_create_oper <=", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperLike(String value) {
            addCriterion("f_create_oper like", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperNotLike(String value) {
            addCriterion("f_create_oper not like", value, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperIn(List<String> values) {
            addCriterion("f_create_oper in", values, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperNotIn(List<String> values) {
            addCriterion("f_create_oper not in", values, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperBetween(String value1, String value2) {
            addCriterion("f_create_oper between", value1, value2, "createOper");
            return (Criteria) this;
        }

        public Criteria andCreateOperNotBetween(String value1, String value2) {
            addCriterion("f_create_oper not between", value1, value2, "createOper");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("f_url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("f_url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("f_url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("f_url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("f_url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("f_url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("f_url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("f_url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("f_url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("f_url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("f_url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("f_url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("f_url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("f_url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andIstopIsNull() {
            addCriterion("f_isTop is null");
            return (Criteria) this;
        }

        public Criteria andIstopIsNotNull() {
            addCriterion("f_isTop is not null");
            return (Criteria) this;
        }

        public Criteria andIstopEqualTo(String value) {
            addCriterion("f_isTop =", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopNotEqualTo(String value) {
            addCriterion("f_isTop <>", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopGreaterThan(String value) {
            addCriterion("f_isTop >", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopGreaterThanOrEqualTo(String value) {
            addCriterion("f_isTop >=", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopLessThan(String value) {
            addCriterion("f_isTop <", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopLessThanOrEqualTo(String value) {
            addCriterion("f_isTop <=", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopLike(String value) {
            addCriterion("f_isTop like", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopNotLike(String value) {
            addCriterion("f_isTop not like", value, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopIn(List<String> values) {
            addCriterion("f_isTop in", values, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopNotIn(List<String> values) {
            addCriterion("f_isTop not in", values, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopBetween(String value1, String value2) {
            addCriterion("f_isTop between", value1, value2, "istop");
            return (Criteria) this;
        }

        public Criteria andIstopNotBetween(String value1, String value2) {
            addCriterion("f_isTop not between", value1, value2, "istop");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("f_status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("f_status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("f_status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("f_status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("f_status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("f_status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("f_status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("f_status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("f_status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("f_status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("f_status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("f_status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("f_status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("f_status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}