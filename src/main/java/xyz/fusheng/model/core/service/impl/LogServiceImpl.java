/**
 * @FileName: LogServiceImpl
 * @Author: fusheng
 * @Date: 2020/3/11 15:25
 * Description: 日志业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Log;
import xyz.fusheng.model.core.mapper.LogMapper;
import xyz.fusheng.model.core.service.LogService;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 保存
     * @param logger
     */
    @Override
    public void save(Log logger) {
        logMapper.save(logger);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<Log> getByPage(Page<Log> page) {
        // 查询数据
        List<Log> logList = logMapper.getByPage(page);
        page.setList(logList);
        // 查询总数
        int totalCount = logMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        logMapper.deleteById(id);
    }

    /**
     * 根据id集合删除
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        logMapper.deleteByIds(ids);
    }
}
