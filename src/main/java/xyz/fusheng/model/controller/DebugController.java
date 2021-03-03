package xyz.fusheng.model.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.security.oauth2.GithubDetail;

/**
 * @FileName: DebugController
 * @Author: code-fusheng
 * @Date: 2021/3/2 7:11 上午
 * @Version: 1.0
 * @Description:
 */

//@Controller
public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    private final GithubDetail githubDetail;

    public DebugController(GithubDetail githubDetail) {
        this.githubDetail = githubDetail;
    }

    /**
     * 让用户跳转到 GitHub
     * 这里不能加@ResponseBody，因为这里是要跳转而不是返回响应
     * 另外LoginController也不能用@RestController修饰
     * @return 跳转url
     */
    @GetMapping("/github/login")
    public Result<String> authorizeGithub() {
        String url = githubDetail.getAuthorizeUrl() +
                "?client_id=" + githubDetail.getClientId() +
                "&redirect_uri=" + githubDetail.getRedirectUrl();
        logger.info("授权URL:{}", url);
        return new Result<>(url);
    }

}

