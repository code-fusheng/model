package xyz.fusheng.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import xyz.fusheng.model.common.enums.ResultEnums;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * 用户
 * @author code-fusheng
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户 Security 开放接口 - 增
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user){
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return new Result<>(400, "操作错误: 缺少必须表单字段！");
        }
        if(userService.selectUserByName(user.getUsername())!=null){
            return new Result<>(400, "操作错误: 该用户名已被注册！");
        }
        // 加密
        String encryptPass = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptPass);
        // 设置为启用状态
        user.setIsDeleted(0);
        boolean ret = userService.save(user);
        if(!ret){
            return new Result<>("注册失败！");
        }
        // 默认角色 User 普通用户
        return new Result<>("注册成功！");
    }

    /**
     * 添加用户 管理员
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody User user){
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return new Result<>(400, "操作错误: 缺少必须表单字段！");
        }
        if(userService.selectUserByName(user.getUsername())!=null){
            return new Result<>(400, "操作错误: 该用户名已被注册！");
        }
        // 加密
        String encryptPass = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptPass);
        // 设置为启用状态
        user.setIsDeleted(0);
        boolean ret = userService.save(user);
        if(!ret){
            return new Result<>("添加失败！");
        }
        // 默认角色 User 普通用户
        return new Result<>("添加成功！");
    }

    /**
     * 根据id删除 物理删除 管理员
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public Result<Object> deleteById(@PathVariable("id") Long id){
        userService.removeById(id);
        return new Result<>("操作成功: 删除用户！");
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update")
    public Result<Object> update(@RequestBody User user){
        userService.updateById(user);
        return new Result<>("操作成功: 修改用户!");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("getById/{id}")
    public Result<User> getById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return new Result<>("操作成功: 查询用户！", user);
    }

    /**
     * 获取用户列表 - 查
     * @Return Result<List<User>> 用户列表
     */
    @GetMapping("/list")
    public Result<List<User>> list(){
        List<User> userList = userService.list();
        return new Result<>("操作成功: 用户列表！", userList);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<User>> getByPage(@RequestBody Page<User> page){
        // 获取排序方式  page对象中 封装了 sortColumn 排序列
        String sortColumn = page.getSortColumn();
        // 驼峰转下划线
        String newSortColumn = StringUtils.upperCharToUnderLine(sortColumn);
        page.setSortColumn(newSortColumn);
        // 判断排序列不为空
        if(StringUtils.isNotBlank(sortColumn)){
            // 用户名称， 创建时间， 更新时间
            String[] sortColumns = {"username", "created_time", "update_time"};
            // Arrays.asList() 方法使用
            // 1. 该方法是将数组转换成list。 Json 数据格式中的 排序列为数组形式，此处需要转换成 List数据形式
            // 2. 该方法不适用于剧本数据类型（byte,short,int,long,float,double,boolean）
            // 3. 不支持add和remove方法
            List<String> sortList = Arrays.asList(sortColumns);
            if (!sortList.contains(newSortColumn.toLowerCase())) {
                return new Result<>(ResultEnums.ERROR.getCode(), "参数错误！");
            }
        }
        page = userService.getByPage(page);
        return new Result<>("操作成功: 分页查询用户！", page);
    }

    /**
     * 启用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/enable/{id}")
    public Result<Object> enable(@PathVariable("id") Long id) {
        userService.enableById(id);
        return new Result<>("操作成功: 启用用户！");
    }

    /**
     * 弃用 - 改 - 管理员
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/disable/{id}")
    public Result<Object> disable(@PathVariable("id") Long id) {
        userService.disableById(id);
        return new Result<>("操作成功: 弃用用户！");
    }
}
