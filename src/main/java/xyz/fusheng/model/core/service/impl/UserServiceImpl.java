/**
 * @FileName: UserServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:05
 * Description: 用户业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.mapper.UserMapper;
import xyz.fusheng.model.core.service.UserService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户实体
     * @param username 用户名
     * @return
     */
    @Override
    public User selectUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 通过用户id查询角色集合
     * @param userId 用户id
     * @return List<Role> 角色名集合
     */
    @Override
    public List<Role> selectRoleByUserId(Long userId) {
        return this.baseMapper.selectRoleByUserId(userId);
    }

    /**
     * 根据用户id查询权限集合
     * @param userId 用户id
     * @return List<Menu> 权限名集合
     */
    @Override
    public List<Menu> selectMenuByUserId(Long userId) {
        return this.baseMapper.selectMenuByUserId(userId);
    }

    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    @Override
    public Page<User> getByPage(Page<User> page) {
        // 查询数据
        List<User> userList = userMapper.getByPage(page);
        page.setList(userList);
        // 查询总数
        int totalCount = userMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(User::getUserId, id);
        updateWrapper.lambda().set(User::getIsEnabled, StateEnums.ENABLED.getCode());
        userMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Long id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(User::getUserId, id);
        updateWrapper.lambda().set(User::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        userMapper.update(null, updateWrapper);
    }

    @Override
    public User getUserInfoById(Long userId) {
        User userInfo = userMapper.getUserInfoById(userId);
        return userInfo;
    }

    @Override
    public List<User> getUserListOrderDistance(BigDecimal lat, BigDecimal lng) {
        List<User> userList = userMapper.getUserListOrderDistance(lat, lng);
        return userList;
    }
}
