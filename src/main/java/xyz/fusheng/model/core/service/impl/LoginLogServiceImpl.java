package xyz.fusheng.model.core.service.impl; /**
 * @author: code-fusheng
 * @Date: 2020/9/5 20:16
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.mapper.LoginLogMapper;
import xyz.fusheng.model.core.service.LoginLogService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: LoginLogServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/9/5 20:16
 * @version: 1.0
 * Description: 登录日志业务逻辑接口实现类
 */

@Service("loginLogService")
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public Page<LoginLog> getByPage(Page<LoginLog> page) {
        // 查询数据
        List<LoginLog> loginLogList = loginLogMapper.getByPage(page);
        page.setList(loginLogList);
        // 统计总数
        int totalCount = loginLogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        loginLogMapper.deleteById(id);
    }

    /**
     * 根据id集合删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        loginLogMapper.deleteByIds(ids);
    }

}
