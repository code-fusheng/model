/**
 * @FileName: StateEnums
 * @Author: code-fusheng
 * @Date: 2020/4/15 14:00
 * Description: 状态码枚举
 */
package xyz.fusheng.model.common.enums;

import lombok.Getter;

@Getter
public enum StateEnums {

    /**
     * 逻辑删除状态
     */
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    /**
     * 启用状态
     */
    ENABLED(1, "启用"),
    NOT_ENABLE(0, "未启用"),

    /**
     * 登录状态
     */
    LOGIN_SUCCESS(0, "登录成功"),
    LOGIN_ERROR(1, "登录失败"),

    /**
     * 性别状态
     */
    SEX_MAN(1, "男"),
    SEX_WOMAN(2, "女"),
    SEX_PRIVACY(0, "私密"),

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(1, "请求正常"),
    REQUEST_ERROR(0, "请求异常"),

    DEFAULT_GOOD(0, "默认的点赞"),
    ARTICLE_GOOD(1, "文章的点赞"),
    COMMENT_GOOD(2, "评论的点赞"),

    DEFAULT_COLLECTION(0, "默认的收藏"),
    ARTICLE_COLLECTION(1, "文章的收藏"),

    ARTICLE_COMMENT(0, "文章的评论"),
    COMMENT_COMMENT(1, "文章的评论的评论");
    /**
     * code 状态码
     * msg 状态信息
     */
    private Integer code;
    private String msg;

    StateEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
