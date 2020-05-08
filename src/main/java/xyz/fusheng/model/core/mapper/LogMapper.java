/**
 * @FileName: LogMapper
 * @Author: fusheng
 * @Date: 2020/3/11 13:43
 * Description: 日志Mapper
 */
package xyz.fusheng.model.core.mapper;

import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Log;

import java.util.List;

@Component
public interface LogMapper {

    /**
     * 保存
     * @param logger
     */
    void save(Log logger);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<Log> getByPage(Page<Log> page);

    /**
     * 分页查询是统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<Log> page);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id集合删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询全部
     * @return
     */
    List<Log> getAll();
}
