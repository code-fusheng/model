/**
 * @FileName: ModelPlusServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/4/29 16:35
 * Description:
 */
package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.utils.Page;
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
        page.setTotalCount(modelPlusList.size());
        return page;
    }
}
