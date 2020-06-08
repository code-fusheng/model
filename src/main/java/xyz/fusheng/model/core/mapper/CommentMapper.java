package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.Comment;

/**
 * @FileName: CommentMapper
 * @Author: code-fusheng
 * @Date: 2020/6/3 0:15
 * @version: 1.0
 * Description: 评论Mapper
 */

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
