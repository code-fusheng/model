/**
 * @FileName: ModelPlusMapper
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:37
 * Description:
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<Category> getByPage(Page<Category> page);

    /**
     * 分页查询统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<Category> page);

}
