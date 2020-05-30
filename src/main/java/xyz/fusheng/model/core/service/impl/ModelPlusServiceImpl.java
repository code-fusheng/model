/**
 * @FileName: ModelPlusServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:35
 * Description:
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Article;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.mapper.ModelPlusMapper;
import xyz.fusheng.model.core.service.ModelPlusService;

import javax.annotation.Resource;
import java.util.List;

@Service("modelPlusService")
public class ModelPlusServiceImpl extends ServiceImpl<ModelPlusMapper, ModelPlus> implements ModelPlusService {

    @Resource
    private ModelPlusMapper modelPlusMapper;

    @Override
    public Page<ModelPlus> getByPage(Page<ModelPlus> page) {
        // 查询数据
        List<ModelPlus> modelPlusList = modelPlusMapper.getByPage(page);
        page.setList(modelPlusList);
        // 统计总数
        int totalCount = modelPlusMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<ModelPlus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(ModelPlus::getModelPlusId, id);
        updateWrapper.lambda().set(ModelPlus::getIsEnabled, StateEnums.ENABLED.getCode());
        modelPlusMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Long id) {
        UpdateWrapper<ModelPlus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(ModelPlus::getModelPlusId, id);
        updateWrapper.lambda().set(ModelPlus::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        modelPlusMapper.update(null, updateWrapper);
    }

    @Override
    public void deleteById(Long id) {
        modelPlusMapper.deleteById(id);
    }
}
