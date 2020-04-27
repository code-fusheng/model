/**
 * @FileName: UserRoleServiceIMpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:11
 * Description: 用户角色业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.UserRole;
import xyz.fusheng.model.core.mapper.UserRoleMapper;
import xyz.fusheng.model.core.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{
}
