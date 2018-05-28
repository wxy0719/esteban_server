package com.esteban.core.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.esteban.core.framework.utils.WebUtils;

/**
 * @author esteban
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Login {

    public int expireSecond() default 1800;

    public String userType() default WebUtils.ADMIN_OPER;

}
