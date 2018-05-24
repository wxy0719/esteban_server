package com.esteban.core.system.model;

import java.util.ArrayList;
import java.util.List;

public class InterfaceAdapterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InterfaceAdapterExample() {
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

        public Criteria andAdapterNoIsNull() {
            addCriterion("f_adapter_no is null");
            return (Criteria) this;
        }

        public Criteria andAdapterNoIsNotNull() {
            addCriterion("f_adapter_no is not null");
            return (Criteria) this;
        }

        public Criteria andAdapterNoEqualTo(String value) {
            addCriterion("f_adapter_no =", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoNotEqualTo(String value) {
            addCriterion("f_adapter_no <>", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoGreaterThan(String value) {
            addCriterion("f_adapter_no >", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoGreaterThanOrEqualTo(String value) {
            addCriterion("f_adapter_no >=", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoLessThan(String value) {
            addCriterion("f_adapter_no <", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoLessThanOrEqualTo(String value) {
            addCriterion("f_adapter_no <=", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoLike(String value) {
            addCriterion("f_adapter_no like", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoNotLike(String value) {
            addCriterion("f_adapter_no not like", value, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoIn(List<String> values) {
            addCriterion("f_adapter_no in", values, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoNotIn(List<String> values) {
            addCriterion("f_adapter_no not in", values, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoBetween(String value1, String value2) {
            addCriterion("f_adapter_no between", value1, value2, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andAdapterNoNotBetween(String value1, String value2) {
            addCriterion("f_adapter_no not between", value1, value2, "adapterNo");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("f_service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("f_service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("f_service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("f_service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("f_service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("f_service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("f_service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("f_service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("f_service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("f_service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("f_service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("f_service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("f_service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("f_service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIsNull() {
            addCriterion("f_service_method is null");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIsNotNull() {
            addCriterion("f_service_method is not null");
            return (Criteria) this;
        }

        public Criteria andServiceMethodEqualTo(String value) {
            addCriterion("f_service_method =", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotEqualTo(String value) {
            addCriterion("f_service_method <>", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodGreaterThan(String value) {
            addCriterion("f_service_method >", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodGreaterThanOrEqualTo(String value) {
            addCriterion("f_service_method >=", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLessThan(String value) {
            addCriterion("f_service_method <", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLessThanOrEqualTo(String value) {
            addCriterion("f_service_method <=", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodLike(String value) {
            addCriterion("f_service_method like", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotLike(String value) {
            addCriterion("f_service_method not like", value, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodIn(List<String> values) {
            addCriterion("f_service_method in", values, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotIn(List<String> values) {
            addCriterion("f_service_method not in", values, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodBetween(String value1, String value2) {
            addCriterion("f_service_method between", value1, value2, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceMethodNotBetween(String value1, String value2) {
            addCriterion("f_service_method not between", value1, value2, "serviceMethod");
            return (Criteria) this;
        }

        public Criteria andServiceDescIsNull() {
            addCriterion("f_service_desc is null");
            return (Criteria) this;
        }

        public Criteria andServiceDescIsNotNull() {
            addCriterion("f_service_desc is not null");
            return (Criteria) this;
        }

        public Criteria andServiceDescEqualTo(String value) {
            addCriterion("f_service_desc =", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescNotEqualTo(String value) {
            addCriterion("f_service_desc <>", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescGreaterThan(String value) {
            addCriterion("f_service_desc >", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescGreaterThanOrEqualTo(String value) {
            addCriterion("f_service_desc >=", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescLessThan(String value) {
            addCriterion("f_service_desc <", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescLessThanOrEqualTo(String value) {
            addCriterion("f_service_desc <=", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescLike(String value) {
            addCriterion("f_service_desc like", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescNotLike(String value) {
            addCriterion("f_service_desc not like", value, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescIn(List<String> values) {
            addCriterion("f_service_desc in", values, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescNotIn(List<String> values) {
            addCriterion("f_service_desc not in", values, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescBetween(String value1, String value2) {
            addCriterion("f_service_desc between", value1, value2, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andServiceDescNotBetween(String value1, String value2) {
            addCriterion("f_service_desc not between", value1, value2, "serviceDesc");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("f_is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("f_is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(String value) {
            addCriterion("f_is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(String value) {
            addCriterion("f_is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(String value) {
            addCriterion("f_is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(String value) {
            addCriterion("f_is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(String value) {
            addCriterion("f_is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(String value) {
            addCriterion("f_is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLike(String value) {
            addCriterion("f_is_valid like", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotLike(String value) {
            addCriterion("f_is_valid not like", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<String> values) {
            addCriterion("f_is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<String> values) {
            addCriterion("f_is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(String value1, String value2) {
            addCriterion("f_is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(String value1, String value2) {
            addCriterion("f_is_valid not between", value1, value2, "isValid");
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