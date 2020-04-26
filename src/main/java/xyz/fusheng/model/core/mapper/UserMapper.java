/**
 * @FileName: UserMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:16
 * Description:
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户id查询角色集合
     * @param userId 用户id
     * @return List<Role> 角色集合
     */
    List<Role> selectRoleByUserId(Long userId);


    /**
     * 通过用户id查询权限集合
     * @param userId 用户id
     * @return List<Menu> 权限集合
     */
    List<Menu> selectMenuByUserId(Long userId);
}
