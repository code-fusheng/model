/**
 * @FileName: SecurityUtil
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:06
 * Description: Security工具类
 */
package xyz.fusheng.model.common.utils;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.security.entity.SelfUser;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil() {
    }

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
    public static String getUserName() {
        return getUserInfo().getUsername();
    }

    /**
     * 构造登录信息
     *
     * @param request
     * @return
     */
    public static LoginLog createLoginLog(HttpServletRequest request) {
        LoginLog loginLog = new LoginLog();
        // UserAgent 对象
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取IP地址
        String ipAddress = IpUtils.getIpAddr(request);
        log.info("用户登陆获取IP地址信息:{}", ipAddress);
        loginLog.setIpAddress(ipAddress);
        // 获取操作系统
        String osType = userAgent.getOperatingSystem().getName();
        loginLog.setOsType(osType);
        // 获取浏览器类型
        String browserType = userAgent.getBrowser().getName();
        loginLog.setBrowserType(browserType);
        // 获取登录地址
        String loginLocation = AddressUtils.getIpAddressInfo(ipAddress);
        log.info("用户登陆获取真实地址信息:{}", loginLocation);
        loginLog.setLoginLocation(loginLocation);
        loginLog.setLoginType(0);

        return loginLog;
    }


}
