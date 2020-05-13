/**
 * @FileName: MenuMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:22
 * Description: 权限Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<Menu> getByPage(Page<Menu> page);

    /**
     * 分页查询统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<Menu> page);
}
