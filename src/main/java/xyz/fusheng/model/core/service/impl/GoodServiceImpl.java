package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.mapper.GoodMapper;
import xyz.fusheng.model.core.service.GoodService;

/**
 * @FileName: GoodServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:44
 * @version: 1.0
 * Description: 点赞业务逻辑接口实现类
 */

@Service("goodService")
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

}
