package xyz.fusheng.model.common.aspect.enums; /**
 * @author: code-fusheng
 * @Date: 2020/9/4 15:22
 */

import lombok.Getter;

/**
 * @FileName: BusinessType
 * @Author: code-fusheng
 * @Date: 2020/9/4 15:22
 * @version: 1.0
 * Description: 操作类型枚举
 */

@Getter
public enum BusinessType {

    /**
     * 操作类型枚举
     */
    OTHER(0, "其它"),
    INSERT(1, "新增"),
    UPDATE(2, "修改"),
    DELETE(3, "删除"),
    SELECT(4, "查询"),
    GRANT(5, "授权"),
    EXPORT(6, "导出"),
    IMPORT(7, "导入"),
    FORCE(8, "强退"),
    ENABLE(10, "启用"),
    DISABLE(11, "弃用"),
    READ(12, "阅读"),
    REGISTER(13, "注册");

    /**
     * code 枚举码
     * msg 枚举信息
     */
    private Integer code;
    private String msg;

    BusinessType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
