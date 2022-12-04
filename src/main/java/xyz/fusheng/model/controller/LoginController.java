package xyz.fusheng.model.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.RedisUtils;
import xyz.fusheng.model.security.oauth2.GithubDetail;
import xyz.fusheng.model.security.sms.SendSms;
import xyz.fusheng.model.security.sms.SmsCode;
import xyz.fusheng.model.security.sms.SmsCodeGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @FileName: LoginController
 * @Author: code-fusheng
 * @Date: 2020/8/26 13:35
 * @version: 1.0
 * Description:
 */

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final GithubDetail githubDetail;

    /**
     * 注册标识
     */
    private static final String REGISTER_SIGN = "re";

    private static final int MAX_SMS_COUNT = 10;

    private static final String SMS_IP_COUNT = "ip:count";

    /**
     * redis 中存储的过期时间 180s
     */
    private static int ExpireTime = 180;

    @Autowired
    private SmsCodeGenerator smsCodeGenerator;

    @Resource
    private SendSms sendSms;

    @Resource
    private RedisUtils redisUtils;

    public LoginController(GithubDetail githubDetail) {
        this.githubDetail = githubDetail;
    }

    /**
     * 处理未登录
     *
     * @return
     */
    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> callbackLogin() {
        return new ResponseEntity<>(new ResultVo<>(ResultEnums.NOT_LOGIN), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/code/sms")
    public ResultVo<Object> createSmsCode(@RequestParam String mobile, HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (redisUtils.get(ip) == null) {
            redisUtils.set(ip, 1, 58);
            SmsCode smsCode = smsCodeGenerator.generate();
            if (redisUtils.set(mobile, smsCode, ExpireTime)) {
                if (REGISTER_SIGN.equals(mobile.substring(0, 2))) {
                    sendSms.send(mobile.substring(2), smsCode.getCode(), "SMS_204111151");
                } else {
                    sendSms.send(mobile, smsCode.getCode(), "SMS_204111151");
                }
            } else {
                return new ResultVo<>(401, "验证码未发送，请稍后再试！");
            }
        } else {
            redisUtils.set(ip, (Integer) redisUtils.get(ip) + 1, 58);
            if ((Integer) redisUtils.get(ip) > MAX_SMS_COUNT) {
                redisUtils.set(SMS_IP_COUNT + ip, 1, 60 * 60 * 24);
            }
            return new ResultVo<>(401, "请勿频繁操作");
        }
        return new ResultVo<>();
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


}
