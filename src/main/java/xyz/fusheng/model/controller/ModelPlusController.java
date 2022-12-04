/**
 * @FileName: ModelPlusController
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:31
 * Description:
 */
package xyz.fusheng.model.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessType;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.service.ModelPlusService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/modelPlus")
@Api(tags = "高级模版", value = "高级模版操作管理接口")
public class ModelPlusController {

    @Resource
    private ModelPlusService modelPlusService;

    /**
     * 添加模型 - 增 - 管理员
     * @param modelPlus
     * @return
     */
    @ApiOperation(value = "添加高级模版", notes = "添加高级模版")
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/save','modelplus:list:add')")
    @PostMapping("/save")
    @Log(title = "添加高级模版", businessType = BusinessType.INSERT)
    public ResultVo<Object> save(@RequestBody ModelPlus modelPlus){
        modelPlusService.save(modelPlus);
        return new ResultVo<>("操作成功: 添加模型！");
    }

    /**
     * 根据id删除模型 - 删【逻辑删除】- 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/deleteById','modelplus:list:delete')")
    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "逻辑删除单个高级模版", notes = "根据id逻辑删除高级模版")
    @Log(title = "删除高级模版", businessType = BusinessType.DELETE)
    public ResultVo<Object> deleteById(@ApiParam(value = "高级模版id", required = true) @PathVariable("id") Long id) {
        modelPlusService.deleteById(id);
        return new ResultVo<>("操作成功: 删除模型！");
    }

    /**
     * 修改模版 - 改 - 管理员
     * @param modelPlus
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/update','modelplus:list:update')")
    @PutMapping("/update")
    @ApiOperation(value = "修改高级模版", notes = "修改高级模版")
    @Log(title = "修改高级模版", businessType = BusinessType.UPDATE)
    public ResultVo<Object> update(@RequestBody ModelPlus modelPlus){
        modelPlus.setVersion(modelPlusService.getById(modelPlus.getModelPlusId()).getVersion());
        modelPlusService.updateById(modelPlus);
        return new ResultVo<>("操作成功: 修改模版!");
    }

    /**
     * 根据id查询 - 查 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/getById','modelplus:list:info')")
    @GetMapping("getById/{id}")
    @ApiOperation(value = "查询单个高级模版", notes = "根据id查询高级模版")
    @Log(title = "查询单个高级模版", businessType = BusinessType.SELECT)
    public ResultVo<ModelPlus> getById(@ApiParam(value = "高级模版id", required = true) @PathVariable("id") Long id) {
        ModelPlus modelPlus = modelPlusService.getById(id);
        return new ResultVo<>("操作成功: 查询模型！", modelPlus);
    }

    /**
     * 查询所有模型 - 查
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/list','modelplus:list')")
    @GetMapping("/list")
    @Log(title = "查询高级模版列表", businessType = BusinessType.SELECT)
    public ResultVo<List<ModelPlus>> list(){
        List<ModelPlus> modelPluses = modelPlusService.list();
        return new ResultVo<>("操作成功: 模型列表！", modelPluses);
    }

    /**
     * 多条件分页查询 - 查
     * @param page
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/getByPage','modelplus:list')")
    @ApiOperation(value = "分页查询高级模版", notes = "分页查询高级模版")
    @PostMapping("/getByPage")
    public ResultVo<Page<ModelPlus>> getByPage(@RequestBody Page<ModelPlus> page){
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
                return new ResultVo<>(ResultEnums.ERROR.getCode(),"操作失败: 参数错误！");
            }
        }
        page = modelPlusService.getByPage(page);
        return new ResultVo<>("操作成功: 分页查询模型！", page);
    }

    /**
     * 启用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/enable','modelplus:list:enable')")
    @PutMapping("/enable/{id}")
    @ApiOperation(value = "启用高级模版", notes = "根据id启用查询高级模版")
    @Log(title = "启用高级模版", businessType = BusinessType.ENABLE)
    public ResultVo<Object> enable(@ApiParam(value = "高级模版id", required = true) @PathVariable("id") Long id) {
        modelPlusService.enableById(id);
        return new ResultVo<>("操作成功: 启用模版！");
    }

    /**
     * 弃用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN') or hasPermission('/modelplus/disable','modelplus:list:disable')")
    @PutMapping("/disable/{id}")
    @ApiOperation(value = "弃用高级模版", notes = "根据id弃用查询高级模版")
    @Log(title = "弃用高级模版", businessType = BusinessType.DISABLE)
    public ResultVo<Object> disable(@ApiParam(value = "高级模版id", required = true) @PathVariable("id") Long id) {
        modelPlusService.disableById(id);
        return new ResultVo<>("操作成功: 弃用模版！");
    }

}
