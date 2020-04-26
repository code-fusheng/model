/**
 * @FileName: SecurityUtil
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:06
 * Description: Security工具类
 */
package xyz.fusheng.model.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import xyz.fusheng.model.security.entity.SelfUser;

public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     * @return userDetails 用户信息
     */
    public static SelfUser getUserInfo(){
        SelfUser userDetails = (SelfUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getUserId();
    }

    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }



}
