/**
 * @FileName: MenuService
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:08
 * Description: 权限业务逻辑接口
 */
package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<Menu> getByPage(Page<Menu> page);

    /**
     * 根据角色id查询权限列表
     *
     * @param id
     * @return
     */
    List<Menu> getMenuListByRoleId(Long id);

    /**
     * 格式化权限
     *
     * @param id
     * @return
     */
    List<Menu> getFormatMenuListByRoleId(Long id);

    /**
     * 获取权限树
     *
     * @return
     */
    List<Menu> getFormatMenuTree();

    /**
     * 查询可用权限列表
     *
     * @return
     */
    List<Menu> getMenuTree();

    /**
     * 根据角色ID查询菜单权限ID数据
     *
     * @param roleId
     * @return
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据id删除权限
     *
     * @param id
     */
    void deleteById(Long id);
}
