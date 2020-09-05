package xyz.fusheng.model.common.utils; /**
 * @author: code-fusheng
 * @Date: 2020/9/4 15:29
 */

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName: ServletUtils
 * @Author: code-fusheng
 * @Date: 2020/9/4 15:29
 * @version: 1.0
 * Description: ServletUtils 工具类
 */

public class ServletUtils {

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }


    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

}
