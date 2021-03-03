package xyz.fusheng.model.security.oauth2;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @FileName: GithubBody
 * @Author: code-fusheng
 * @Date: 2021/3/1 5:07 下午
 * @Version: 1.0
 * @Description: Github 返回用户业务实体
 */

@Getter
@Setter
public class GithubBody implements Serializable {

    private Long id;

    private Long userId;

    private String login;

    private String avatarUrl;

    private String name;

    private String blog;

    private String location;

    private String bio;

}
