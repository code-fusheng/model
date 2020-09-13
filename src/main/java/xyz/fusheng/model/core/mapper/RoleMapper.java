/**
 * @FileName: RoleMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:18
 * Description: 角色Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.fusheng.model.core.entity.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色id删除 sys_role_menu 中间表的数据
     *
     * @param roleId
     */
    void deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存角色和权限的关系
     *
     * @param roleId
     * @param menuId
     */
    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}
