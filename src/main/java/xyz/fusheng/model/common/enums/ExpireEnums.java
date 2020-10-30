package xyz.fusheng.model.common.enums;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @FileName: ExpireEnum
 * @Author: code-fusheng
 * @Date: 2020/10/30 15:01
 * @version: 1.0
 * Description:
 */

@Getter
public enum ExpireEnums {

    /**
     * 未读消息
     */
    UNREAD_MSG(30L, TimeUnit.DAYS);

    /**
     * 过期时间
     */
    private Long time;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    ExpireEnums(Long time, TimeUnit timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }
}
