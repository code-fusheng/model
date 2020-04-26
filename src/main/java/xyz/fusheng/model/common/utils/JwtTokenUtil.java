/**
 * @FileName: JWTTokenUtils
 * @Author: code-fusheng
 * @Date: 2020/4/26 21:30
 * Description: JTW工具类
 */
package xyz.fusheng.model.common.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import xyz.fusheng.model.common.config.JwtConfig;
import xyz.fusheng.model.security.entity.SelfUser;

import java.util.Date;

@Slf4j
public class JwtTokenUtil {

    /**
     * 私有化构造器
     */
    private JwtTokenUtil(){}

    /**
     * 生成 Token
     * @param selfUser 用户安全实体
     * @return Token
     */
    public static String createAccessToken(SelfUser selfUser){
        // 登录成功生成 JWT
        String token = Jwts.builder()
                // 放入用户名和用户id
                .setId(selfUser.getUserId()+"")
                // 主题
                .setSubject(selfUser.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("fusheng")
                // 自定义属性，放入用户拥有的权限
                .claim("authorities", JSON.toJSONString(selfUser.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
        return token;
    }


}
