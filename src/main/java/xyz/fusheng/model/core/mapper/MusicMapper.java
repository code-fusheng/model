package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.entity.Music;

import java.util.List;

/**
 * @FileName: MusicMapper
 * @Author: code-fusheng
 * @Date: 2020/8/16 13:07
 * @version: 1.0
 * Description:
 */

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    List<Music> getByPage(Page<Music> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<Music> page);
}
