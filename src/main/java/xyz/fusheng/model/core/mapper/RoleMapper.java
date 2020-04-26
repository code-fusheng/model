/**
 * @FileName: RoleMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:18
 * Description: 角色Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
