/**
 * @FileName: ModelPlusController
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:31
 * Description:
 */
package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.service.ModelPlusService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/modelPlus")
public class ModelPlusController {

    @Autowired
    private ModelPlusService modelPlusService;

    /**
     * 添加模型 管理员
     * @param modelPlus
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody ModelPlus modelPlus){
        modelPlusService.save(modelPlus);
        return new Result<>("操作成功: 添加模型！");
    }

    /**
     * 根据id删除 物理删除 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id){
        modelPlusService.removeById(id);
        return new Result<>("操作成功: 删除模型！");
    }

    /**
     * 根据id更新模版
     * @param modelPlus
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    public Result<ModelPlus> update(@RequestBody ModelPlus modelPlus){
        modelPlusService.updateById(modelPlus);
        return new Result<>("操作成功: 更新模型！");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getById/{id}")
    public Result<ModelPlus> getById(@PathVariable("id") Long id){
        modelPlusService.getById(id);
        return new Result<>("操作成功: 查询模型！");
    }

    /**
     * 查询所有模型
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/list")
    public Result<List<ModelPlus>> list(){
        List<ModelPlus> modelPluses = modelPlusService.list();
        return new Result<>("操作成功: 模型列表！", modelPluses);
    }

    @PostMapping("/getByPage")
    public Result<Page<ModelPlus>> getByPage(@RequestBody Page<ModelPlus> page){
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 模型名称， 创建时间， 更新时间
            String[] sortColumns = {"model_plus_name", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"参数错误！");
            }
        }
        page = modelPlusService.getByPage(page);
        return new Result<>(page);
    }




}
