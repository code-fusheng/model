package xyz.fusheng.model.core.service;

import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.OperaLog;

/**
 * @author: code-fusheng
 * @Date: 2020/10/6 19:39
 */
public interface OperaLogService {

    /**
     * 添加操作日志
     *
     * @param operaLog
     */
    void saveOperaLog(OperaLog operaLog);

    /**
     * 分页查询操作日志
     *
     * @param page
     * @return
     */
    Page<OperaLog> getByPage(Page<OperaLog> page);
}
