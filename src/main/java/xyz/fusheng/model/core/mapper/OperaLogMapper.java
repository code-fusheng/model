package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.OperaLog;

import java.util.List;

/**
 * @author: code-fusheng
 * @Date: 2020/10/6 19:39
 */
public interface OperaLogMapper extends BaseMapper<OperaLog> {
    /**
     * 分页查询操作日志
     *
     * @param page
     * @return
     */
    List<OperaLog> getByPage(Page<OperaLog> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<OperaLog> page);
}
