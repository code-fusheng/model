package xyz.fusheng.model.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.ModelPlus;
import xyz.fusheng.model.core.entity.Music;
import xyz.fusheng.model.core.mapper.MusicMapper;
import xyz.fusheng.model.core.service.MusicService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @FileName: MusicServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/8/16 13:03
 * @version: 1.0
 * Description: 歌曲业务逻辑接口实现类
 */

@Service("musicService")
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Resource
    private MusicMapper musicMapper;

    @Override
    public Page<Music> getByPage(Page<Music> page) {
        // 查询数据
        List<Music> musicList = musicMapper.getByPage(page);
        page.setList(musicList);
        // 统计总数
        int totalCount = musicMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void enableById(Long id) {
        UpdateWrapper<Music> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Music::getMusicId, id);
        updateWrapper.lambda().set(Music::getIsEnabled, StateEnums.ENABLED.getCode());
        musicMapper.update(null, updateWrapper);
    }

    @Override
    public void disableById(Long id) {
        UpdateWrapper<Music> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Music::getMusicId, id);
        updateWrapper.lambda().set(Music::getIsEnabled, StateEnums.NOT_ENABLE.getCode());
        musicMapper.update(null, updateWrapper);
    }

    @Override
    public void deleteById(Long id) {
        musicMapper.deleteById(id);
    }
}
