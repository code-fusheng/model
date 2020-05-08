/**
 * @FileName: LogService
 * @Author: code-fusheng
 * @Date: 2020/4/16 10:27
 * Description: 日志业务逻辑接口
 */
package xyz.fusheng.model.core.service;

import org.apache.poi.ss.usermodel.Workbook;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Log;

import java.util.List;

public interface LogService {

    /**
     * 保存
     * @param logger
     */
    void save(Log logger);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<Log> getByPage(Page<Log> page);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 批量删除[根据id集合删除]
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询数据，构建成workbook用于导出
     * @return
     */
    Workbook export();
}
