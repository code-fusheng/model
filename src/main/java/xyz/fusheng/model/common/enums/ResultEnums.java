/**
 * @FileName: ResultEnums
 * @Author: code-fusheng
 * @Date: 2020/4/15 13:48
 * Description: HTTP 返回结果状态码枚举
 */
package xyz.fusheng.model.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {

    /**
     * 返回枚举类型，每一个枚举类型代表一个返回状态
     * 1** 信息，服务器收到请求，需要请求这继续执行操作
     * 2** 成功，操作被成功执行并接受处理
     * 3** 重定向，需要进一步的操作已完成请求
     * 4** 客户端错误，请求包汉语发错误或无法完成请求
     * 5** 服务器错误，服务在处理请求过程中发生了错误
     */

    SUCCESS(200, "操作成功！"),
    ERROR(400, "请求错误！"),
    NOT_LOGIN(401, "未登录！"),
    NOT_AUTHORIZED(403, "未授权！"),
    NOT_FOUND(404,"访问连接不存在！"),
    INTERNAL_SERVER_ERROR(500,"服务器故障！")
    ;

    /**
     * code 响应码
     * msg 响应信息
     */
    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
