/**
 * @FileName: MenuServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:09
 * Description: 权限业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.mapper.MenuMapper;
import xyz.fusheng.model.core.mapper.ModelPlusMapper;
import xyz.fusheng.model.core.service.MenuService;

import javax.annotation.Resource;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Override
    public Page<Menu> getByPage(Page<Menu> page) {
        // 查询数据
        List<Menu> menuList = menuMapper.getByPage(page);
        page.setList(menuList);
        // 统计总数
        int totalCount = menuMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }
}
