/**
 * @FileName: MenuServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:09
 * Description: 权限业务逻辑接口实现类
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.mapper.MenuMapper;
import xyz.fusheng.model.core.mapper.ModelPlusMapper;
import xyz.fusheng.model.core.service.MenuService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


    @Override
    public List<Menu> getMenuListByRoleId(Long id) {
        List<Menu> menuList = menuMapper.getMenuListByRoleId(id);
        return menuList;
    }

    @Override
    public List<Menu> getFormatMenuListByRoleId(Long id) {
        List<Menu> menuList = menuMapper.getMenuListByRoleId(id);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        // 获取一级权限
        queryWrapper.lambda().eq(Menu::getLevel, 1);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        for (Menu menu : menus) {
            List<Menu> child = getChild(menu.getMenuId(), menuList);
            menu.setChildList(child);
        }
        return menus;
    }

    @Override
    public List<Menu> getFormatMenuTree() {
        List<Menu> menuList = menuMapper.getAll();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        // 获取一级权限
        queryWrapper.lambda().eq(Menu::getLevel, 1);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        for (Menu menu : menus) {
            List<Menu> child = getChild(menu.getMenuId(), menuList);
            menu.setChildList(child);
        }
        return menuList;
    }

    @Override
    public List<Menu> getMenuTree() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Menu::getIsEnabled, StateEnums.ENABLED.getCode());
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        return menuList;
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return menuMapper.queryMenuIdsByRoleId(roleId);
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Menu> getChild(Long id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getPid().equals(id)) {
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildList(getChild(nav.getMenuId(), allMenu));
        }
        //排序
        Collections.sort(childList, order());
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Menu>();
        }
        return childList;
    }


    /**
     * 根据菜单id属性排序
     *
     * @return
     */
    public Comparator<Menu> order() {
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if (!o1.getMenuId().equals(o2.getMenuId())) {
                    return (int) (o1.getMenuId() - o2.getMenuId());
                }
                return 0;
            }
        };
        return comparator;
    }
}
