package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.core.entity.Conf;
import xyz.fusheng.model.core.mapper.ConfMapper;
import xyz.fusheng.model.core.service.ConfService;

import javax.annotation.Resource;

/**
 * @FileName: ConfServiceImpl
 * @Author: code-fusheng
 * @Date: 2022/11/22 00:01
 * @Version: 1.0
 * @Description:
 */

@Service
public class ConfServiceImpl extends ServiceImpl<ConfMapper, Conf> implements ConfService {

    @Resource
    private ConfMapper confMapper;

    @Override
    public Conf getAboutConf() {
        Conf conf = confMapper.selectOne(new QueryWrapper<Conf>().lambda().eq(Conf::getId, "1"));
        return conf;
    }

}
