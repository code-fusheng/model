/**
 * @FileName: ThreadLocalContext
 * @Author: fusheng
 * @Date: 2020/3/11 22:13
 * Description:* 本地线程上下文，单例模式 ,用来存储在同一个线程中可能会用到的全局变量
 */
package xyz.fusheng.model.common.utils;

import lombok.Data;
import xyz.fusheng.model.core.entity.Log;

@Data
public class ThreadLocalContext {

    /**
     * 日志实体
     */
    private Log logger = new Log();

    /**
     * 是否记录日志
     */
    private boolean isLog = false;

    /**
     * 线程本地内存中的变量
     */
    private static ThreadLocal<ThreadLocalContext> threadLocal = new ThreadLocal<>();

    public static ThreadLocalContext get() {
        if (threadLocal.get() == null) {
            ThreadLocalContext threadLocalContext = new ThreadLocalContext();
            threadLocal.set(threadLocalContext);
        }
        ThreadLocalContext threadLocalContext = threadLocal.get();
        return threadLocalContext;
    }

    public void remove() {
        this.logger = null;
        this.isLog = false;
        threadLocal.remove();
    }


}
