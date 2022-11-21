package xyz.fusheng.model.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @FileName: conf
 * @Author: code-fusheng
 * @Date: 2022/11/21 17:45
 * @Version: 1.0
 * @Description:
 */

@Data
@TableName("sys_conf")
@ApiModel(value = "conf", description = "元素")
public class Conf implements Serializable {

    private String id;

    private String confLabel;

    private String confValue;

}
