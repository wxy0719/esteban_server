package com.esteban.model;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuTreeExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("f_name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("f_name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("f_name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("f_name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("f_name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("f_name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("f_name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("f_name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("f_name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("f_name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("f_name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("f_name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("f_name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("f_name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIsforderIsNull() {
            addCriterion("f_isforder is null");
            return (Criteria) this;
        }

        public Criteria andIsforderIsNotNull() {
            addCriterion("f_isforder is not null");
            return (Criteria) this;
        }

        public Criteria andIsforderEqualTo(String value) {
            addCriterion("f_isforder =", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderNotEqualTo(String value) {
            addCriterion("f_isforder <>", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderGreaterThan(String value) {
            addCriterion("f_isforder >", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderGreaterThanOrEqualTo(String value) {
            addCriterion("f_isforder >=", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderLessThan(String value) {
            addCriterion("f_isforder <", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderLessThanOrEqualTo(String value) {
            addCriterion("f_isforder <=", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderLike(String value) {
            addCriterion("f_isforder like", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderNotLike(String value) {
            addCriterion("f_isforder not like", value, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderIn(List<String> values) {
            addCriterion("f_isforder in", values, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderNotIn(List<String> values) {
            addCriterion("f_isforder not in", values, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderBetween(String value1, String value2) {
            addCriterion("f_isforder between", value1, value2, "isforder");
            return (Criteria) this;
        }

        public Criteria andIsforderNotBetween(String value1, String value2) {
            addCriterion("f_isforder not between", value1, value2, "isforder");
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

        public Criteria andNodeGradeIsNull() {
            addCriterion("f_node_grade is null");
            return (Criteria) this;
        }

        public Criteria andNodeGradeIsNotNull() {
            addCriterion("f_node_grade is not null");
            return (Criteria) this;
        }

        public Criteria andNodeGradeEqualTo(String value) {
            addCriterion("f_node_grade =", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeNotEqualTo(String value) {
            addCriterion("f_node_grade <>", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeGreaterThan(String value) {
            addCriterion("f_node_grade >", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeGreaterThanOrEqualTo(String value) {
            addCriterion("f_node_grade >=", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeLessThan(String value) {
            addCriterion("f_node_grade <", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeLessThanOrEqualTo(String value) {
            addCriterion("f_node_grade <=", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeLike(String value) {
            addCriterion("f_node_grade like", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeNotLike(String value) {
            addCriterion("f_node_grade not like", value, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeIn(List<String> values) {
            addCriterion("f_node_grade in", values, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeNotIn(List<String> values) {
            addCriterion("f_node_grade not in", values, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeBetween(String value1, String value2) {
            addCriterion("f_node_grade between", value1, value2, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andNodeGradeNotBetween(String value1, String value2) {
            addCriterion("f_node_grade not between", value1, value2, "nodeGrade");
            return (Criteria) this;
        }

        public Criteria andParentNodeIsNull() {
            addCriterion("f_parent_node is null");
            return (Criteria) this;
        }

        public Criteria andParentNodeIsNotNull() {
            addCriterion("f_parent_node is not null");
            return (Criteria) this;
        }

        public Criteria andParentNodeEqualTo(String value) {
            addCriterion("f_parent_node =", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotEqualTo(String value) {
            addCriterion("f_parent_node <>", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeGreaterThan(String value) {
            addCriterion("f_parent_node >", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeGreaterThanOrEqualTo(String value) {
            addCriterion("f_parent_node >=", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeLessThan(String value) {
            addCriterion("f_parent_node <", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeLessThanOrEqualTo(String value) {
            addCriterion("f_parent_node <=", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeLike(String value) {
            addCriterion("f_parent_node like", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotLike(String value) {
            addCriterion("f_parent_node not like", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeIn(List<String> values) {
            addCriterion("f_parent_node in", values, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotIn(List<String> values) {
            addCriterion("f_parent_node not in", values, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeBetween(String value1, String value2) {
            addCriterion("f_parent_node between", value1, value2, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotBetween(String value1, String value2) {
            addCriterion("f_parent_node not between", value1, value2, "parentNode");
            return (Criteria) this;
        }

        public Criteria andNodeImgIsNull() {
            addCriterion("f_node_img is null");
            return (Criteria) this;
        }

        public Criteria andNodeImgIsNotNull() {
            addCriterion("f_node_img is not null");
            return (Criteria) this;
        }

        public Criteria andNodeImgEqualTo(String value) {
            addCriterion("f_node_img =", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgNotEqualTo(String value) {
            addCriterion("f_node_img <>", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgGreaterThan(String value) {
            addCriterion("f_node_img >", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgGreaterThanOrEqualTo(String value) {
            addCriterion("f_node_img >=", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgLessThan(String value) {
            addCriterion("f_node_img <", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgLessThanOrEqualTo(String value) {
            addCriterion("f_node_img <=", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgLike(String value) {
            addCriterion("f_node_img like", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgNotLike(String value) {
            addCriterion("f_node_img not like", value, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgIn(List<String> values) {
            addCriterion("f_node_img in", values, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgNotIn(List<String> values) {
            addCriterion("f_node_img not in", values, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgBetween(String value1, String value2) {
            addCriterion("f_node_img between", value1, value2, "nodeImg");
            return (Criteria) this;
        }

        public Criteria andNodeImgNotBetween(String value1, String value2) {
            addCriterion("f_node_img not between", value1, value2, "nodeImg");
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

        public Criteria andOrderIsNull() {
            addCriterion("f_order is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("f_order is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(String value) {
            addCriterion("f_order =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(String value) {
            addCriterion("f_order <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(String value) {
            addCriterion("f_order >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(String value) {
            addCriterion("f_order >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(String value) {
            addCriterion("f_order <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(String value) {
            addCriterion("f_order <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLike(String value) {
            addCriterion("f_order like", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotLike(String value) {
            addCriterion("f_order not like", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<String> values) {
            addCriterion("f_order in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<String> values) {
            addCriterion("f_order not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(String value1, String value2) {
            addCriterion("f_order between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(String value1, String value2) {
            addCriterion("f_order not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andRightNoIsNull() {
            addCriterion("f_right_no is null");
            return (Criteria) this;
        }

        public Criteria andRightNoIsNotNull() {
            addCriterion("f_right_no is not null");
            return (Criteria) this;
        }

        public Criteria andRightNoEqualTo(String value) {
            addCriterion("f_right_no =", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoNotEqualTo(String value) {
            addCriterion("f_right_no <>", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoGreaterThan(String value) {
            addCriterion("f_right_no >", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoGreaterThanOrEqualTo(String value) {
            addCriterion("f_right_no >=", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoLessThan(String value) {
            addCriterion("f_right_no <", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoLessThanOrEqualTo(String value) {
            addCriterion("f_right_no <=", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoLike(String value) {
            addCriterion("f_right_no like", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoNotLike(String value) {
            addCriterion("f_right_no not like", value, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoIn(List<String> values) {
            addCriterion("f_right_no in", values, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoNotIn(List<String> values) {
            addCriterion("f_right_no not in", values, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoBetween(String value1, String value2) {
            addCriterion("f_right_no between", value1, value2, "rightNo");
            return (Criteria) this;
        }

        public Criteria andRightNoNotBetween(String value1, String value2) {
            addCriterion("f_right_no not between", value1, value2, "rightNo");
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