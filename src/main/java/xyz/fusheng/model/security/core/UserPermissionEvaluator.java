package xyz.fusheng.model.security.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.service.UserService;
import xyz.fusheng.model.security.entity.SelfUser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @FileName: UserPermissionEvaluator
 * @Author: code-fusheng
 * @Date: 2020/4/26 23:14
 * Description: 自定义权限注解验证
 */

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private UserService userService;

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     * @Param  authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
        SelfUser selfUser = (SelfUser) authentication.getPrincipal();
        // 查询用户权限（这里可以将权限放入缓存提高效率）
        Set<String> permissions = new HashSet<>();
        List<Menu> menuList = userService.selectMenuByUserId(selfUser.getUserId());
        for (Menu menu : menuList) {
            permissions.add(menu.getPermission());
        }
        // 权限对比
        if (permissions.contains(permission.toString())){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
