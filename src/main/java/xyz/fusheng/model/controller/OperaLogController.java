package xyz.fusheng.model.controller; /**
 * @author: code-fusheng
 * @Date: 2020/10/6 21:40
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.OperaLog;
import xyz.fusheng.model.core.service.OperaLogService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: OperaLogController
 * @Author: code-fusheng
 * @Date: 2020/10/6 21:40
 * @version: 1.0
 * Description: 操作日志控制类
 */

@RestController
@RequestMapping("/operaLog")
@Api(tags = "操作日志", value = "操作日志管理接口")
public class OperaLogController {

    @Resource
    private OperaLogService operaLogService;

    /**
     * 分页查询操作日志
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "分页查询操作日志", notes = "分页查询操作日志")
    @PostMapping("/getByPage")
    public Result<Page<OperaLog>> getByPage(@RequestBody Page<OperaLog> page) {
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if (StringUtils.isNotBlank(sortColumn)) {
            // 创建时间
            String[] sortColumns = {"opera_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = operaLogService.getByPage(page);
        return new Result<>("操作成功: 分页查询操作日志!", page);
    }

}
