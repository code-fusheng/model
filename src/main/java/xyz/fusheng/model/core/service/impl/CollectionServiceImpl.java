package xyz.fusheng.model.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.WebSocketServer;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.mapper.ArticleMapper;
import xyz.fusheng.model.core.mapper.CollectionMapper;
import xyz.fusheng.model.core.mapper.MessageMapper;
import xyz.fusheng.model.core.service.CollectionService;
import xyz.fusheng.model.core.vo.ArticleVo;
import xyz.fusheng.model.core.vo.CollectionVo;
import xyz.fusheng.model.core.vo.MessageVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: CollectionServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:38
 * @version: 1.0
 * Description: 点赞业务逻辑接口实现类
 */

@Service("collectionService")
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public int getCollectionCountByUserAndTargetAndType(long userId, Long collectionTarget, Integer collectionType) {
        int count = 0;
        List<Collection> collections = collectionMapper.getCollectionsByUserAndTargetAndType(userId, collectionTarget, collectionType);
        count = (collections.size() == 0) ? 0 : 1;
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doCollection(Collection collection) {
        // 先执行操作，再更新实时数据
        collectionMapper.insert(collection);
        // 获取收藏类型
        int collectionType = collection.getCollectionType();
        long targetId = collection.getCollectionTarget();
        long userId = collection.getCollectionUserId();
        // 创建消息对象 - 初步封装消息
        MessageVo messageVo = new MessageVo();
        messageVo.setSendUserId(SecurityUtil.getUserId());
        messageVo.setSendUserName(SecurityUtil.getUserName());
        messageVo.setMessageState(StateEnums.MESSAGE_NO_READ.getCode());
        switch (collectionType) {
            // 文章的收藏
            case 1:
                // 更新文章收藏量
                collectionMapper.updateArticleCollectionCountByTargetAndUserAndType(targetId, userId, StateEnums.ARTICLE_COLLECTION.getCode());
                // 查询收藏对象
                ArticleVo articleVo = articleMapper.getById(collection.getCollectionTarget());
                // 完善消息对象
                messageVo.setMessageTargetId(articleVo.getArticleId());
                messageVo.setMessageTargetDesc(articleVo.getArticleTitle());
                messageVo.setReceiveUserId(articleVo.getAuthorId());
                messageVo.setReceiveUserName(articleVo.getAuthorName());
                messageVo.setMessageType(StateEnums.ARTICLE_COLLECTION_MESSAGE.getCode());
                break;
            default:
                break;
        }
        messageMapper.insert(messageVo);
        try {
            WebSocketServer.sendInfo(JSON.toJSONString(messageVo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<CollectionVo> getByPage(Page<CollectionVo> page) {
        // 查询数据
        List<CollectionVo> collectionList = collectionMapper.getByPage(page);
        collectionList.forEach(collectionVo -> {
            if (collectionVo.getCollectionType().equals(StateEnums.ARTICLE_COLLECTION.getCode())) {
                // 为文章的手残添加文章标题属性值
                collectionVo.setTargetDesc(articleMapper.getArticleTitleById(collectionVo.getCollectionTarget()));
            }
        });
        page.setList(collectionList);
        // 统计总数
        int totalCount = collectionMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }
}
