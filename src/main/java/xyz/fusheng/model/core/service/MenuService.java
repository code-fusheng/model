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

public interface MenuService extends IService<Menu> {
    /**
     * 多条件分页查询
     * @param page
     * @return
     */
    Page<Menu> getByPage(Page<Menu> page);
}
