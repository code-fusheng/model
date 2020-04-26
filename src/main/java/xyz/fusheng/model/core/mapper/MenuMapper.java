/**
 * @FileName: MenuMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:22
 * Description: 权限Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.Menu;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
