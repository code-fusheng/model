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
import xyz.fusheng.model.core.entity.ModelPlus;

import java.util.List;

@Mapper
public interface ModelPlusMapper extends BaseMapper<ModelPlus> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<ModelPlus> getByPage(Page<ModelPlus> page);

    /**
     * 分页查询统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<ModelPlus> page);
}
