package xyz.fusheng.model.common.aspect.enums; /**
 * @author: code-fusheng
 * @Date: 2020/9/4 15:25
 */

import lombok.Getter;

/**
 * @FileName: BusinessStatus
 * @Author: code-fusheng
 * @Date: 2020/9/4 15:25
 * @version: 1.0
 * Description: 操作状态枚举
 */

@Getter
public enum BusinessStatus {

    /**
     * 操作状态枚举
     */
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败");

    /**
     * code 枚举码
     * msg 枚举信息
     */
    private Integer code;
    private String msg;

    BusinessStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
