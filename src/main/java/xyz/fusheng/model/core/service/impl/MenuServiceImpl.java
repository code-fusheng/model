/**
 * @FileName: MenuServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:09
 * Description: 权限业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.mapper.MenuMapper;
import xyz.fusheng.model.core.service.MenuService;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
