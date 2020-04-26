/**
 * @FileName: UserRoleMapper
 * @Author: code-fusheng
 * @Date: 2020/4/26 19:24
 * Description: 用户角色Mapper
 */
package xyz.fusheng.model.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.fusheng.model.core.entity.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
