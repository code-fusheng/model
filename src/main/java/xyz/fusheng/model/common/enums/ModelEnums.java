/**
 * @FileName: ModelEnums
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:22
 * Description: 模版枚举
 */
package xyz.fusheng.model.common.enums;

import lombok.Getter;

/**
 * @author code-fusheng
 *
 * Enum : 枚举 什么是枚举？ 一类对象有限且固定 例如: 季节（春、夏、秋、冬）、星期（一 ~ 日）、状态、结果、、、
 *
 * 1. @Getter 用 lombok 插件提供的 @Getter 注解提供 Getter 方法，以便于在其他位置调用
 */

@Getter
public enum ModelEnums {

    ;

    /**
     * code 枚举码
     * msg 枚举信息
     */
    private Integer code;
    private String msg;

    ModelEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
