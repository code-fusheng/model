package xyz.fusheng.model.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Music;

/**
 * @FileName: MusicService
 * @Author: code-fusheng
 * @Date: 2020/8/16 13:00
 * @version: 1.0
 * Description: 歌曲业务逻辑接口
 */

public interface MusicService extends IService<Music> {

    /**
     * 多条件分页查询
     *
     * @param page
     * @return
     */
    Page<Music> getByPage(Page<Music> page);

    /**
     * 根据id启用
     *
     * @param id
     */
    void enableById(Long id);

    /**
     * 根据id弃用
     *
     * @param id
     */
    void disableById(Long id);

    /**
     * 根据id逻辑删除
     *
     * @param id
     */
    void deleteById(Long id);

}
