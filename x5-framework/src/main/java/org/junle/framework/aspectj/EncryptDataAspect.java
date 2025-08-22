package org.junle.framework.aspectj;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junle.common.annotation.EncryptDataProcess;
import org.junle.common.annotation.EncryptField;
import org.junle.common.enums.ProcessType;
import org.junle.common.utils.AESUtil;
import org.junle.common.utils.spring.SpringUtils;
import org.junle.framework.web.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

@Aspect
@Component
public class EncryptDataAspect {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PermissionService permissionService;

    // 定义切点：拦截所有 Service 实现类中的 public 方法
    // 您可以根据需要调整切点表达式，例如拦截特定的包或方法
    @Pointcut("@annotation(org.junle.common.annotation.EncryptDataProcess)")
    public void sensitiveDataPointcut() {
    }

    @Around("sensitiveDataPointcut() && @annotation(encryptDataProcess)")
    public Object handleSensitiveData(ProceedingJoinPoint joinPoint, EncryptDataProcess encryptDataProcess) throws Throwable {

        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.debug("处理敏感数据 - 方法: {}", methodName);

        // 处理参数（加密）
        Object[] args = joinPoint.getArgs();
        if (encryptDataProcess.processParams() && shouldEncryptParams(methodName, encryptDataProcess.type())) {
            for (Object arg : args) {
                process(arg, encryptDataProcess.type());
            }
        }

        // 执行原方法
        Object result = joinPoint.proceed(args);

        // 处理返回值（解密或脱敏）
        if (encryptDataProcess.processResult() && shouldDecryptResult(methodName, encryptDataProcess.type())) {
            process(result, encryptDataProcess.type());
        }

        return result;
    }

    /**
     * 处理对象或集合（递归）
     *
     * @param object        待处理对象
     * @param processType   操作类型 (加密/解密)
     */
    private void process(Object object, ProcessType processType) throws IllegalAccessException {
        if (Objects.isNull(object)) {
            return;
        }

        // 处理集合
        if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            if (CollectionUtils.isEmpty(collection)) {
                return;
            }
            for (Object item : collection) {
                process(item, processType); // 递归处理集合元素
            }
        }
        // 处理数组 (如果需要)
        else if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            for (Object item : array) {
                process(item, processType); // 递归处理数组元素
            }
        }
        // 处理 Map (如果需要)
        // else if (object instanceof Map) { ... }

        // 处理普通对象
        else {
            processFields(object, processType);
        }
    }


    /**
     * 处理对象字段
     */
    private void processFields(Object obj, ProcessType processType) throws IllegalAccessException {
        if (Objects.isNull(obj)) {
            return;
        }

        AESUtil aesUtil = SpringUtils.getBean(AESUtil.class);

        Class<?> clazz = obj.getClass();
        // 获取所有字段，包括父类的
        for (Field field : clazz.getDeclaredFields()) {
            // 检查是否有 @SensitiveData 注解
            if (field.isAnnotationPresent(EncryptField.class)) {
                EncryptField annotation = field.getAnnotation(EncryptField.class);

                // 确保字段是 String 类型
                if (field.getType().equals(String.class)) {
                    field.setAccessible(true); // 允许访问私有字段
                    String value = (String) field.get(obj);

                    if (value != null) {

                        if (ProcessType.ENCRYPT.equals(processType)) {
                            if (permissionService.hasPermi(annotation.permi())) {
                                field.set(obj, aesUtil.encrypt(value));
                                log.trace("加密字段: {}", field.getName());
                            }
                        } else {
                            // 检查用户是否有查看敏感数据的权限
                            if (permissionService.hasPermi(annotation.permi())) {
                                field.set(obj, aesUtil.decrypt(value));
                                log.trace("解密字段: {}", field.getName());
                            } else {
                                // 无权限，显示脱敏数据
                                field.set(obj, annotation.mask());
                            }
                        }
                    }
                    field.setAccessible(false); // 恢复访问权限
                }
            }
            // 如果字段是自定义对象类型，可以考虑递归处理
             else if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.")) {
                field.setAccessible(true);
                process(field.get(obj), processType);
                field.setAccessible(false);
             }
        }
    }

    /**
     * 自动模式下根据方法名判断是否需要加密参数
     */
    private boolean shouldEncryptParams(String methodName, ProcessType processType) {
        if (processType == ProcessType.ENCRYPT) {
            return true;
        }
        if (processType == ProcessType.AUTO) {
            // 根据方法名自动判断
            return methodName.startsWith("save") || methodName.startsWith("insert") ||
                    methodName.startsWith("update") || methodName.startsWith("create");
        }
        return false;
    }

    /**
     * 自动模式下根据方法名判断是否需要解密返回值
     */
    private boolean shouldDecryptResult(String methodName, ProcessType processType) {
        if (processType == ProcessType.DECRYPT) {
            return true;
        }
        if (processType == ProcessType.AUTO) {
            // 根据方法名自动判断
            return methodName.startsWith("get") || methodName.startsWith("find") ||
                    methodName.startsWith("select") || methodName.startsWith("query") ||
                    methodName.startsWith("list");
        }
        return false;
    }
}
