/**
 * @FileName: RoleMenuServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:14
 * Description: 角色权限业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.RoleMenu;
import xyz.fusheng.model.core.mapper.RoleMenuMapper;
import xyz.fusheng.model.core.service.RoleMenuService;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
