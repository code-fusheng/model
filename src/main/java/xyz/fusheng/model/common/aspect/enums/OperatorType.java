package xyz.fusheng.model.common.aspect.enums; /**
 * @author: code-fusheng
 * @Date: 2020/9/4 15:23
 */

import lombok.Getter;

/**
 * @FileName: OperatorType
 * @Author: code-fusheng
 * @Date: 2020/9/4 15:23
 * @version: 1.0
 * Description: 操作者类型枚举
 */

@Getter
public enum OperatorType {

    /**
     * 操作者类型枚举
     */
    OTHER(0, "其它"),
    ADMIN(1, "后台用户"),
    WEB(2, "前台用户"),
    MOBILE(3, "手机端用户");

    /**
     * code 枚举码
     * msg 枚举信息
     */
    private Integer code;
    private String msg;

    OperatorType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
