package org.junle.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptField {

    /**
     * 权限字符
     */
    String permi();

    /**
     * 加密掩码类型
     */
    String mask() default "****";
}