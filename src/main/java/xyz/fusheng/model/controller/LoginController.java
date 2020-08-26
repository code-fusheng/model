package xyz.fusheng.model.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Result;

/**
 * @FileName: LoginController
 * @Author: code-fusheng
 * @Date: 2020/8/26 13:35
 * @version: 1.0
 * Description:
 */

@RestController
public class LoginController {

    /**
     * 处理未登录
     *
     * @return
     */
    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> callbackLogin() {
        return new ResponseEntity<>(new Result<>(ResultEnums.NOT_LOGIN), HttpStatus.FORBIDDEN);
    }

}
