/**
 * @FileName: MenuMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:22
 * Description: 权限Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    List<Menu> getByPage(Page<Menu> page);

    /**
     * 分页查询统计总数
     * @param page
     * @return
     */
    int getCountByPage(Page<Menu> page);

    /**
     * 根据角色id查询权限列表
     *
     * @param id
     * @return
     */
    List<Menu> getMenuListByRoleId(Long id);

    /**
     * 查询所有权限 可用的
     *
     * @return
     */
    List<Menu> getAll();

    /**
     * 根据角色ID查询所有选中的权限菜单ID【只查子节点的】
     *
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdsByRoleId(Long roleId);

    /**
     * 根据权限id 删除角色权限中间表的对应数据
     *
     * @param id
     */
    void deleteRoleMenuByMenuId(Long id);
}
