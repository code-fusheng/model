package xyz.fusheng.model.core.dto;

import lombok.Data;

import java.util.List;

/**
 * @FileName: OrganizationDto
 * @Author: code-fusheng
 * @Date: 2021/3/25 9:28 上午
 * @Version: 1.0
 * @Description:
 */

@Data
public class OrganizationDto {

    private String organizationId;

    private String companyName;

    private String id;

    private List<UserDto> users;

}
