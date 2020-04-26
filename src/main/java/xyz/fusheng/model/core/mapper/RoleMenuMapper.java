/**
 * @FileName: RoleMenuMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:25
 * Description: 角色权限Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.RoleMenu;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
