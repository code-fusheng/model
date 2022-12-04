package xyz.fusheng.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.core.entity.Conf;
import xyz.fusheng.model.core.service.ConfService;

import javax.annotation.Resource;

/**
 * @FileName: ConfController
 * @Author: code-fusheng
 * @Date: 2022/11/21 23:47
 * @Version: 1.0
 * @Description: Conf 配置接口
 */

@RestController
@RequestMapping("/conf")
@Api(tags = "配置", value = "配置接口")
public class ConfController {

    @Resource
    private ConfService confService;

    @GetMapping("/getAboutMe")
    @ApiOperation("查询关于我的配置")
    public ResultVo<JSONObject> getAboutMe() {
        Conf conf = confService.getAboutConf();
        JSONObject json = JSON.parseObject(conf.getConfValue(), JSONObject.class);
        return new ResultVo<>("操作成功: 查询关于我的配置!", json);
    }
}
