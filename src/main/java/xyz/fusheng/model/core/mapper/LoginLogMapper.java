package xyz.fusheng.model.core.mapper; /**
 * @author: code-fusheng
 * @Date: 2020/9/5 20:16
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.LoginLog;

import java.util.List;

/**
 * @FileName: LoginLogMapper
 * @Author: code-fusheng
 * @Date: 2020/9/5 20:16
 * @version: 1.0
 * Description:
 */

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    List<LoginLog> getByPage(Page<LoginLog> page);

    /**
     * 统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<LoginLog> page);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 批量删除[根据id集合删除]
     *
     * @param ids
     */
    void deleteByIds(List<Integer> ids);
}
