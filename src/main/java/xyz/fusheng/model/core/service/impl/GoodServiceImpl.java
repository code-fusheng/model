package xyz.fusheng.model.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.WebSocketServer;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.mapper.ArticleMapper;
import xyz.fusheng.model.core.mapper.CommentMapper;
import xyz.fusheng.model.core.mapper.GoodMapper;
import xyz.fusheng.model.core.mapper.MessageMapper;
import xyz.fusheng.model.core.service.GoodService;
import xyz.fusheng.model.core.vo.ArticleVo;
import xyz.fusheng.model.core.vo.CommentVo;
import xyz.fusheng.model.core.vo.GoodVo;
import xyz.fusheng.model.core.vo.MessageVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: GoodServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:44
 * @version: 1.0
 * Description: 点赞业务逻辑接口实现类
 */

@Service("goodService")
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public int getGoodCountByUserAndTargetAndType(long userId, Long goodTarget, Integer goodType) {
        int count = 0;
        List<Good> goods = goodMapper.getGoodsByUserAndTargetAndType(userId, goodTarget, goodType);
        count = (goods.size() == 0) ? 0 : 1;
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doGood(Good good) {
        // 先执行操作，再查询真实数据
        goodMapper.insert(good);
        // 获取点赞类型
        int goodType = good.getGoodType();
        long targetId = good.getGoodTarget();
        long userId = good.getGoodUserId();
        // 创建消息对象 - 初步封装消息
        MessageVo messageVo = new MessageVo();
        messageVo.setSendUserId(SecurityUtil.getUserId());
        messageVo.setSendUserName(SecurityUtil.getUserName());
        messageVo.setMessageState(StateEnums.MESSAGE_NO_READ.getCode());
        switch (goodType) {
            //    文章的点赞
            case 1:
                // 更新文章点赞数
                goodMapper.updateArticleGoodCountByTargetAndUserAndType(targetId, userId, StateEnums.ARTICLE_GOOD.getCode());
                // 查询点赞对象信息
                ArticleVo articleVo = articleMapper.getById(good.getGoodTarget());
                // 完善消息对象
                messageVo.setMessageTargetId(articleVo.getArticleId());
                messageVo.setMessageTargetDesc(articleVo.getArticleTitle());
                messageVo.setReceiveUserId(articleVo.getAuthorId());
                messageVo.setReceiveUserName(articleVo.getAuthorName());
                messageVo.setMessageType(StateEnums.ARTICLE_GOOD_MESSAGE.getCode());
                break;
            //    评论的点赞
            case 2:
                // 更新评论点赞数
                goodMapper.updateCommentGoodCountByTargetAndUserAndType(targetId, userId, StateEnums.COMMENT_GOOD.getCode());
                // 查询点赞对象信息
                CommentVo commentVo = commentMapper.getCommentVoById(good.getGoodTarget());
                // 完善消息对象
                messageVo.setMessageTargetId(commentVo.getCommentId());
                messageVo.setMessageTargetDesc(commentVo.getCommentContent());
                messageVo.setReceiveUserId(commentVo.getCommentUserId());
                messageVo.setReceiveUserName(commentVo.getUsername());
                messageVo.setMessageType(StateEnums.COMMENT_GOOD_MESSAGE.getCode());
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
    public Page<GoodVo> getByPage(Page<GoodVo> page) {
        // 查询数据
        List<GoodVo> goodList = goodMapper.getByPage(page);
        goodList.forEach(goodVo -> {
            if (goodVo.getGoodType().equals(StateEnums.ARTICLE_GOOD.getCode())) {
                // 为文章的点赞添加文章标题的值
                goodVo.setTargetDesc(articleMapper.getArticleTitleById(goodVo.getGoodTarget()));
            } else if (goodVo.getGoodType().equals(StateEnums.COMMENT_GOOD.getCode())) {
                // 为评论的点赞添加评论内容
                goodVo.setTargetDesc(commentMapper.getCommentContentById(goodVo.getGoodTarget()));
            }
        });
        page.setList(goodList);
        // 统计总数
        int totalCount = goodMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public GoodVo getGoodVoById(Long goodId) {
        return goodMapper.getGoodVoById(goodId);
    }
}
