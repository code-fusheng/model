package xyz.fusheng.model.security.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.record.DVALRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.security.entity.SelfUser;
import xyz.fusheng.model.security.handler.UserLoginFailureHandler;
import xyz.fusheng.model.security.handler.UserLoginSuccessHandler;
import xyz.fusheng.model.security.service.SelfUserDetailsService;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName: GithubFilter
 * @Author: code-fusheng
 * @Date: 2021/3/1 5:13 下午
 * @Version: 1.0
 * @Description: Github 认证器
 */

public class GithubAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(GithubAuthenticationFilter.class);

    @Resource
    private GithubDetail githubDetail;

    private UserLoginFailureHandler userLoginFailureHandler;

    private UserLoginSuccessHandler userLoginSuccessHandler;

    private SelfUserDetailsService selfUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        boolean result = StringUtils.equals("/login/github", httpServletRequest.getRequestURI());
        if (result && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "get")) {
            try {
                String code = httpServletRequest.getParameter("code");
                String accessToken = validateGithubCode(code);
                String userInfo = getGithubUserInfo(accessToken);
                GithubBody githubBody = JSON.parseObject(userInfo, GithubBody.class);
                SelfUser selfUser = selfUserDetailsService.loadUserByGithubId(githubBody);
                if (selfUser == null) {
                    throw new BadCredentialsException("用户不存在!");
                }
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(selfUser, selfUser.getPassword(), null);
//                Oauth2AuthenticationToken oauth2AuthenticationToken = new Oauth2AuthenticationToken(selfUser, selfUser.getAuthorities());
                userLoginSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, usernamePasswordAuthenticationToken);
                return;
            } catch (Exception e) {
                logger.info("GithubAuthenticationFilter Error:{}", e.getMessage());
                userLoginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, new BadCredentialsException(e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String validateGithubCode(String code) {
        String url = githubDetail.getAccessTokenUrl() +
                "?client_id=" + githubDetail.getClientId() +
                "&client_secret=" + githubDetail.getClientSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        logger.info("getAccessToken Url :{}", url);
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回JSON格式
        requestHeaders.add("accept", "application/json");
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // post 请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseStr = response.getBody();
        logger.info("responseStr : {}", responseStr);
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        String accessToken = jsonObject.getString("access_token");
        logger.info("accessToken : {}", accessToken);
        return accessToken;
    }

    private String getGithubUserInfo(String accessToken) {
        String url = githubDetail.getUserInfoUrl();
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回JSON
        requestHeaders.add("accept", "application/json");
        // AccessToken放在请求头中
        requestHeaders.add("Authorization", "token " + accessToken);
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // get请求方式
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String userInfo = response.getBody();
        logger.info("userInfo={}", userInfo);
        return userInfo;
    }

    public void setSelfUserDetailsService(SelfUserDetailsService selfUserDetailsService) {
        this.selfUserDetailsService = selfUserDetailsService;
    }

    public void setUserLoginSuccessHandler(UserLoginSuccessHandler userLoginSuccessHandler) {
        this.userLoginSuccessHandler = userLoginSuccessHandler;
    }

    public void setUserLoginFailureHandler(UserLoginFailureHandler userLoginFailureHandler) {
        this.userLoginFailureHandler = userLoginFailureHandler;
    }
}
