/**
 * @FileName: ModelController
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:21
 * Description: 模版控制类
 */
package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Model;
import xyz.fusheng.model.core.service.ModelService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * 模版类型的控制类 下面实现最基础的 层删改查 分页（模糊查询、条件查询、排序、时间区间删选）
     */

    /**
     * 保存 - 增
     * @param model
     * @return
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Model model){
        modelService.save(model);
        return new Result<>("操作成功: 添加模版！");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable("id") Integer id){
        modelService.deleteById(id);
        return new Result<>("操作成功: 删除模版!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PutMapping("/deleteByIds")
    public Result<Object> deleteByIds(@RequestBody List<Integer> ids){
        modelService.deleteByIds(ids);
        return new Result<>("操作成功: 批量删除模版！");
    }

    /**
     * 修改
     * @param model
     * @return
     */
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Model model){
        modelService.update(model);
        return new Result<>("操作成功: 修改模版!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result<Model> get(@PathVariable("id") Integer id){
        Model model = modelService.getById(id);
        return new Result<>("操作成功: 查询模版！",model);
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/list")
    public Result<List<Model>> list(){
        List<Model> modelList = modelService.getAll();
        return new Result<>("操作成功: 查询模版列表！", modelList);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<Model>> getByPage(@RequestBody Page<Model> page){
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 模型名称， 创建时间， 更新时间
            String[] sortColumns = {"model_name", "created_time", "update_time"};
            // Arrays.asList() 方法使用
            // 1. 该方法是将数组转换成list。 Json 数据格式中的 排序列为数组形式，此处需要转换成 List数据形式
            // 2. 该方法不适用于剧本数据类型（byte,short,int,long,float,double,boolean）
            // 3. 不支持add和remove方法
            List<String> sortList = Arrays.asList(sortColumns);
            if(!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(),"参数错误！");
            }
        }
        page = modelService.getByPage(page);
        return new Result<>("操作成功: 分页查询模版！", page);
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Integer id) {
        modelService.enableById(id);
        return new Result<>("操作成功: 启用模版！");
    }

    /**
     * 弃用
     * @param id
     * @return
     */
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Integer id) {
        modelService.disableById(id);
        return new Result<>("操作成功: 弃用模版！");
    }


}
