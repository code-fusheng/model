package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: Music
 * @Author: code-fusheng
 * @Date: 2020/8/16 12:34
 * @version: 1.0
 * Description: 歌曲实体类
 */

@Data
@TableName("m_music")
public class Music implements Serializable {

    private static final long serialVersionUID = -1919281941085751390L;

    /**
     * 歌曲id
     */
    @TableId
    private Long musicId;

    /**
     * 歌曲名
     */
    private String musicName;

    /**
     * 歌手
     */
    private String musicArtist;

    /**
     * 歌曲链接
     */
    private String musicUrl;

    /**
     * 歌曲封面
     */
    private String musicCover;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 是否启用，0否1是 默认1
     */
    private Integer isEnabled;

    /**
     * 是否删除，0否1是 默认0
     */
    @TableLogic
    private Integer isDeleted;

}
