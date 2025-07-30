package org.junle.common.annotation;

import org.junle.common.enums.ProcessType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptDataProcess {
    /**
     * 处理类型：ENCRYPT(加密), DECRYPT(解密), AUTO(自动判断)
     */
    ProcessType type() default ProcessType.AUTO;

    /**
     * 是否处理参数
     */
    boolean processParams() default true;

    /**
     * 是否处理返回值
     */
    boolean processResult() default true;
}
