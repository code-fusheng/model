package xyz.fusheng.model.common.enums; /**
 * @author: code-fusheng
 * @Date: 2020/10/30 15:37
 */

import lombok.Getter;

/**
 * @FileName: TypeEnums
 * @Author: code-fusheng
 * @Date: 2020/10/30 15:37
 * @version: 1.0
 * Description: 类型枚举
 */

@Getter
public enum TypeEnums {

    /**
     * 请求类型
     */
    GET(0, "get"),
    POST(1, "post"),
    PUT(2, "put"),
    DELETE(3, "delete");

    /**
     * 值与标签
     */
    private Integer value;
    private String label;

    TypeEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
