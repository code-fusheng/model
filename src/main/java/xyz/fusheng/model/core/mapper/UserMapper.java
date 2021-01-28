/**
 * @FileName: UserMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:16
 * Description:
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.User;

import java.math.BigDecimal;
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

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<User> getByPage(Page<User> page);

    /**
     * 分页查询统计总数
     *
     * @param page
     * @return
     */
    int getCountByPage(Page<User> page);

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    User getUserInfoById(Long userId);

    /**
     * 地址排序
     * @param lat
     * @param lng
     * @return
     */
    List<User> getUserListOrderDistance(BigDecimal lat, BigDecimal lng);
}
