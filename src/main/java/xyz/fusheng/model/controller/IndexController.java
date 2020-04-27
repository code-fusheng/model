package xyz.fusheng.model.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.utils.Result;

/**
 * 初始页面
 */
@RestController
@RequestMapping("/index")
public class IndexController {


    /**
     * 首页
     * @Return
     */
    @GetMapping("/info")
    public Result<Object> userLogin(){
        return new Result<>("操作成功: 这里是首页！");
    }

}
