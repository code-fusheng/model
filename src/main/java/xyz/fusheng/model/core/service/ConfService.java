package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.core.entity.Conf;

/**
 * @FileName: ConfService
 * @Author: code-fusheng
 * @Date: 2022/11/21 23:55
 * @Version: 1.0
 * @Description:
 */

public interface ConfService extends IService<Conf> {

    /**
     * 获取关于我的配置
     *
     * @return
     */
    Conf getAboutConf();
}
