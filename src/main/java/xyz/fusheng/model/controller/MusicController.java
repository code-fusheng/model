package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.Music;
import xyz.fusheng.model.core.service.MusicService;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: MusicController
 * @Author: code-fusheng
 * @Date: 2020/8/16 12:51
 * @version: 1.0
 * Description: 歌曲控制类
 */

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 添加歌曲 - 增 - 管理员
     *
     * @param music
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Music music) {
        musicService.save(music);
        return new Result<>("操作成功: 添加歌曲！");
    }

    /**
     * 根据id删除歌曲 - 删【逻辑删除】- 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id) {
        musicService.deleteById(id);
        return new Result<>("操作成功: 删除歌曲！");
    }

    /**
     * 修改歌曲 - 改 - 管理员
     *
     * @param music
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    public Result<Object> update(@RequestBody Music music) {
        music.setVersion(musicService.getById(music.getMusicId()).getVersion());
        musicService.updateById(music);
        return new Result<>("操作成功: 修改歌曲!");
    }

    /**
     * 根据id查询 - 查 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getById/{id}")
    public Result<Music> getById(@PathVariable("id") Long id) {
        Music music = musicService.getById(id);
        return new Result<>("操作成功: 查询歌曲！", music);
    }

    /**
     * 查询所有歌曲 - 查
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<Music>> list() {
        List<Music> musics = musicService.list();
        return new Result<>("操作成功: 歌曲列表！", musics);
    }

    /**
     * 多条件分页查询 - 查
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<Music>> getByPage(@RequestBody Page<Music> page) {
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if (StringUtils.isNotBlank(sortColumn)) {
            // 歌曲名称， 创建时间， 更新时间
            String[] sortColumns = {"music_name", "created_time", "update_time"};
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "操作失败: 参数错误！");
            }
        }
        page = musicService.getByPage(page);
        return new Result<>("操作成功: 分页查询歌曲！", page);
    }

    /**
     * 启用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Long id) {
        musicService.enableById(id);
        return new Result<>("操作成功: 启用歌曲！");
    }

    /**
     * 弃用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Long id) {
        musicService.disableById(id);
        return new Result<>("操作成功: 弃用歌曲！");
    }

}
