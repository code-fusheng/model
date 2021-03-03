package xyz.fusheng.model.security.oauth2;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @FileName: GithubConfig
 * @Author: code-fusheng
 * @Date: 2021/3/1 4:42 下午
 * @Version: 1.0
 * @Description:
 */

@Data
@Component
public class GithubDetail {

    private String clientId = "a2b0213889d7e9b6c234";

    private String clientSecret = "5c44c2e3deb7e1af789a96ffc02ddf2d1e4680da";

    private String authorizeUrl = "https://github.com/login/oauth/authorize";

    // private String redirectUrl ="http://www.fusheng.xyz/login/github";
    private String redirectUrl ="http://localhost:9999/login/github";

    private String accessTokenUrl = "https://github.com/login/oauth/access_token";

    private String userInfoUrl="https://api.github.com/user";

}
