package xyz.fusheng.model.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.core.dto.OrganizationDto;
import xyz.fusheng.model.security.oauth2.GithubDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @FileName: DebugController
 * @Author: code-fusheng
 * @Date: 2021/3/2 7:11 上午
 * @Version: 1.0
 * @Description:
 */

@Controller
@RequestMapping("/debug")
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
    public ResultVo<String> authorizeGithub() {
        String url = githubDetail.getAuthorizeUrl() +
                "?client_id=" + githubDetail.getClientId() +
                "&redirect_uri=" + githubDetail.getRedirectUrl();
        logger.info("授权URL:{}", url);
        return new ResultVo<>(url);
    }

    //    @PostMapping("/debugSyncUsers")
    public ResultVo<Object> debugSyncUsers(@RequestBody List<OrganizationDto> organizationDtoList) {
//        organizationDtoList.forEach(System.out::println);
        organizationDtoList.forEach(organizationDto -> {
            System.out.println(organizationDto.getId() + " - " + organizationDto.getCompanyName());
            organizationDto.getUsers().forEach(System.out::println);
        });

        return new ResultVo<>("成功");
    }

    @ResponseBody
    @RequestMapping("/testPrintWrite")
    public void testPrintWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json;charset=utf-8");
        pw.write(JSON.toJSONString(new ResultVo<>("Test")));
    }

}

