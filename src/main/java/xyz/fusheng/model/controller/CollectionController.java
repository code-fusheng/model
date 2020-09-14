package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.service.CollectionService;
import xyz.fusheng.model.core.vo.CollectionVo;
import xyz.fusheng.model.core.vo.GoodVo;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: CollectionController
 * @Author: code-fusheng
 * @Date: 2020/8/10 22:34
 * @version: 1.0
 * Description: 收藏前端控制类
 */

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 收藏 - 增 - 登录用户
     *
     * @param collection
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('collection/save','collection:list:save')")
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> save(@RequestBody Collection collection) {
        // 收藏操作执行者的用户id
        long userId = SecurityUtil.getUserId();
        collection.setCollectionUserId(userId);
        if (collectionService.getCollectionCountByUserAndTargetAndType(userId, collection.getCollectionTarget(), collection.getCollectionType()) != 0) {
            return new Result<>(400, "操作失败: 重复点赞！");
        }
        collectionService.doCollection(collection);
        return new Result<>("操作提示: 收藏成功！");
    }

    /**
     * 多条件分页查询 - 查
     *
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('collection/getByPage','collection:list')")
    @PostMapping("/getByPage")
    public Result<Page<CollectionVo>> getByPage(@RequestBody Page<CollectionVo> page) {
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if (StringUtils.isNotBlank(sortColumn)) {
            // 收藏时间
            String[] sortColumns = {"collection_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = collectionService.getByPage(page);
        return new Result<>("操作成功: 分页查询收藏！", page);
    }

}
