package xyz.fusheng.model.common.aspect.annotation; /**
 * @author: code-fusheng
 * @Date: 2020/9/4 15:21
 */

import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.aspect.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @FileName: Log
 * @Author: code-fusheng
 * @Date: 2020/9/4 15:21
 * @version: 1.0
 * Description: 自定义操作日志记录注解
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.ADMIN;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

}
