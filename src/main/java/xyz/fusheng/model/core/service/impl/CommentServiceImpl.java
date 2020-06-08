package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Comment;
import xyz.fusheng.model.core.mapper.CommentMapper;
import xyz.fusheng.model.core.service.CommentService;

/**
 * @FileName: CommentServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:11
 * @version: 1.0
 * Description: 评论业务逻辑接口实现类
 */

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
