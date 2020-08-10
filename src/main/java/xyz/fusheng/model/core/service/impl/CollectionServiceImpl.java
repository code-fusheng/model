package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.mapper.CollectionMapper;
import xyz.fusheng.model.core.service.CollectionService;

/**
 * @FileName: CollectionServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:38
 * @version: 1.0
 * Description: 点赞业务逻辑接口实现类
 */

@Service("collectionService")
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

}
