package xyz.fusheng.model.core.service; /**
 * @author: code-fusheng
 * @Date: 2020/9/5 20:15
 */

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Log;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.entity.ModelPlus;

import java.util.List;

/**
 * @FileName: LoginLogService
 * @Author: code-fusheng
 * @Date: 2020/9/5 20:15
 * @version: 1.0
 * Description: 登录日志业务逻辑接口
 */

public interface LoginLogService extends IService<LoginLog> {

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    Page<LoginLog> getByPage(Page<LoginLog> page);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id集合删除
     *
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

}
