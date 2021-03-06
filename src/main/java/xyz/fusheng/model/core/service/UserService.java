/**
 * @FileName: UserService
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:03
 * Description: 用户业务逻辑接口
 */
package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Model;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 根据用户名查询实体
     *
     * @param username 用户名
     * @return User 用户实体
     */
    User selectUserByName(String username);

    /**
     * 根据用户id查询用户角色集合
     *
     * @param userId 用户id
     * @return List<Role> 角色名集合
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据用户id查询权限集合
     *
     * @param userId 用户id
     * @return List<Menu> 权限集合
     */
    List<Menu> selectMenuByUserId(Long userId);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<User> getByPage(Page<User> page);

    /**
     * 根据id启用
     *
     * @param id
     */
    void enableById(Long id);

    /**
     * 根据id弃用
     *
     * @param id
     */
    void disableById(Long id);

    /**
     * 用户信息
     *
     * @param userId
     * @return
     */
    User getUserInfoById(Long userId);

    /**
     *
     * @param lat
     * @param lng
     * @return
     */
    List<User> getUserListOrderDistance(BigDecimal lat, BigDecimal lng);
}

