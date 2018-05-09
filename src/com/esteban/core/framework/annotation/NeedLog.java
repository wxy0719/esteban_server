package com.esteban.core.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by CPR269 on 2018/5/9.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface NeedLog {
    public String operateContent() default "";
}
