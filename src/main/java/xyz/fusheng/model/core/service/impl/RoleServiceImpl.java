/**
 * @FileName: RoleServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:07
 * Description: RoleServiceImpl
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Role;
import xyz.fusheng.model.core.mapper.RoleMapper;
import xyz.fusheng.model.core.service.RoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
