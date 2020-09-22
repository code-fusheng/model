package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.vo.GoodVo;

/**
 * @FileName: GoodService
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:37
 * @version: 1.0
 * Description: 点赞业务逻辑接口
 */

public interface GoodService extends IService<Good> {
    /**
     * 根据用户id ，目标id，点赞类型统计点赞数
     *
     * @param userId
     * @param goodTarget
     * @param goodType
     * @return
     */
    int getGoodCountByUserAndTargetAndType(long userId, Long goodTarget, Integer goodType);

    /**
     * 点赞
     *
     * @param good
     */
    void doGood(Good good);

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    Page<GoodVo> getByPage(Page<GoodVo> page);

    /**
     * 查询点赞视图
     *
     * @param goodId
     * @return
     */
    GoodVo getGoodVoById(Long goodId);
}
