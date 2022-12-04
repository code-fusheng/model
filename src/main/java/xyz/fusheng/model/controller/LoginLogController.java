package xyz.fusheng.model.controller; /**
 * @author: code-fusheng
 * @Date: 2020/9/5 20:14
 */

import org.springframework.web.bind.annotation.*;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.service.LoginLogService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: LoginLogController
 * @Author: code-fusheng
 * @Date: 2020/9/5 20:14
 * @version: 1.0
 * Description: 登录日志控制类
 */

@RestController
@RequestMapping("/loginLog")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    /**
     * 分页查询 + 排序
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public ResultVo<Page<LoginLog>> getByPage(@RequestBody Page<LoginLog> page) {
        String sortColumn = page.getSortColumn();
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        if (StringUtils.isNotBlank(sortColumn)) {
            // 根据 请求时间 排序
            String[] sortColumns = {"login_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new ResultVo<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = loginLogService.getByPage(page);
        return new ResultVo<>(page);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @Log(title = "删除登录日志", businessType = BusinessType.DELETE)
    public ResultVo<Object> delete(@PathVariable("id") Integer id) {
        loginLogService.deleteById(id);
        return new ResultVo<>("删除成功: 删除日志！");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PutMapping("/deleteByIds")
    @Log(title = "批量删除登录日志", businessType = BusinessType.DELETE)
    public ResultVo<Object> deleteByIds(@RequestBody List<Integer> ids) {
        loginLogService.deleteByIds(ids);
        return new ResultVo<>("操作成功: 批量删除日志！");
    }

}
