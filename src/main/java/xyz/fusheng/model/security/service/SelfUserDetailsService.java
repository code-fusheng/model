/**
 * @FileName: SelfUserDetailsService
 * @Author: code-fusheng
 * @Date: 2020/4/26 21:19
 * Description: SpringSecurity用户业务逻辑实现
 */
package xyz.fusheng.model.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.service.UserService;
import xyz.fusheng.model.security.entity.SelfUser;
import xyz.fusheng.model.security.oauth2.GithubBody;

@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     * @param username 用户名
     * @return UserDetails SpringSecurity用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public SelfUser loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userService.selectUserByName(username);
        if (user != null) {
            // 组装参数
            SelfUser selfUser = new SelfUser();
            BeanUtils.copyProperties(user, selfUser);
            return selfUser;
        }
        return null;
    }

    /**
     * 查询用户信息根据手机号码
     *
     * @param mobile
     * @return
     */
    public SelfUser loadUserByPhone(String mobile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getPhone, mobile);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            // 组装参数
            SelfUser selfUser = new SelfUser();
            BeanUtils.copyProperties(user, selfUser);
            return selfUser;
        }
        return null;
    }

    /**
     * 查询用户信息根据GithubId, 如果不存在就注册
     * @return
     */
    public SelfUser loadUserByGithubId(GithubBody githubBody) {
        User user = selectUserByGithubId(githubBody.getId());
        if (user == null) {
            addUserByGithub(githubBody);
            user = selectUserByGithubId(githubBody.getId());
            SelfUser selfUser = new SelfUser();
            BeanUtils.copyProperties(user, selfUser);
            return selfUser;
        }
        SelfUser selfUser = new SelfUser();
        BeanUtils.copyProperties(user, selfUser);
        return selfUser;
    }

    public User selectUserByGithubId(Long githubId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getGithubId, githubId);
        User user = userService.getOne(queryWrapper);
        return user;
    }

    public void addUserByGithub(GithubBody githubBody) {
        User user = new User();
        user.setGithubId(githubBody.getId());
        user.setUsername("Github-" + githubBody.getLogin());
        // 加密
        String encryptPass = new BCryptPasswordEncoder().encode(user.getUsername());
        user.setPassword(encryptPass);
        user.setAddress(githubBody.getLocation());
        user.setHeader(githubBody.getAvatarUrl());
        userService.save(user);
    }

}
