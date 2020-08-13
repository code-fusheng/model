package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.service.GoodService;
import xyz.fusheng.model.core.vo.GoodVo;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: GoodController
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:35
 * @version: 1.0
 * Description: 点赞控制类
 */

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodService;

    /**
     * 点赞 - 增 - 登录用户
     *
     * @param good
     * @return
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> save(@RequestBody Good good) {
        // 点赞操作执行者的用户id
        long userId = SecurityUtil.getUserId();
        good.setGoodUserId(userId);
        if (goodService.getGoodCountByUserAndTargetAndType(userId, good.getGoodTarget(), good.getGoodType()) != 0) {
            return new Result<>(400, "操作失败: 重复点赞");
        }
        goodService.doGood(good);
        return new Result<>("操作提示: 点赞成功！");
    }

    /**
     * 多条件分页查询 - 查
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<GoodVo>> getByPage(@RequestBody Page<GoodVo> page) {
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if (StringUtils.isNotBlank(sortColumn)) {
            // 点赞时间
            String[] sortColumns = {"good_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = goodService.getByPage(page);
        return new Result<>("操作成功: 分页查询点赞！", page);
    }


}
