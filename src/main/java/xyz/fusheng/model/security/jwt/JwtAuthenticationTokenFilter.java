/**
 * @FileName: JwtAuthenticationTokenFilter
 * @Author: code-fusheng
 * @Date: 2020/4/26 23:28
 * Description: Jwt接口请求校验拦截器
 */
package xyz.fusheng.model.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import xyz.fusheng.model.common.config.JwtConfig;
import xyz.fusheng.model.security.entity.SelfUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JwtConfig.tokenHeader);
        if (null!=tokenHeader && tokenHeader.startsWith(JwtConfig.tokenPrefix)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JwtConfig.tokenPrefix, "");
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(JwtConfig.secret)
                        .parseClaimsJws(token)
                        .getBody();
                // 获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(userId)) {
                    // 获取角色
                    //List<GrantedAuthority> authorities = new ArrayList<>();
                    //String authority = claims.get("authorities").toString();
                    //if(!StringUtils.isEmpty(authority)){
                    //    List<Map<String,String>> authorityMap = JSONObject.parseObject(authority, List.class);
                    //    for(Map<String,String> role : authorityMap){
                    //        if(!StringUtils.isEmpty(role)) {
                    //            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                    //        }
                    //    }
                    //}
                    //组装参数
                    SelfUser selfUser = new SelfUser();
                    selfUser.setUsername(claims.getSubject());
                    selfUser.setUserId(Long.parseLong(claims.getId()));
                    // selfUser.setAuthorities(authorities);
                    selfUser.setAuthorities(null);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUser, userId, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e){
                log.info("Token过期");
            } catch (Exception e) {
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
