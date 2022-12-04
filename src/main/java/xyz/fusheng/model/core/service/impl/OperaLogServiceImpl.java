package xyz.fusheng.model.core.service.impl;

import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.OperaLog;
import xyz.fusheng.model.core.mapper.OperaLogMapper;
import xyz.fusheng.model.core.service.OperaLogService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: code-fusheng
 * @Date: 2020/10/6 19:39
 */
@Service
public class OperaLogServiceImpl implements OperaLogService {

    @Resource
    private OperaLogMapper operaLogMapper;

    /**
     * 添加操作日志
     *
     * @param operaLog
     */
    @Override
    public void saveOperaLog(OperaLog operaLog) {
        operaLogMapper.insert(operaLog);
    }

    /**
     * 分页查询操作日志
     *
     * @param page
     * @return
     */
    @Override
    public Page<OperaLog> getByPage(Page<OperaLog> page) {
        // 查询数据
        List<OperaLog> operaLogList = operaLogMapper.getByPage(page);
        page.setList(operaLogList);
        // 统计总数
        int totalCount = operaLogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }
}
