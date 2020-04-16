/**
 * @FileName: ModelMapper
 * @Author: code-fusheng
 * @Date: 2020/4/14 23:18
 * Description: 模版数据访问层
 */
package xyz.fusheng.model.core.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import xyz.fusheng.model.common.utils.Page;
import xyz.fusheng.model.core.entity.Model;

import java.util.List;

/**
 * @author code-fusheng
 *  1. @Mapper 使用的是 MyBatis 的 Bean
 *  2. @Repository 数据访问层组件,使用的是 Spring 的 Bean 注入，解决 ServiceImpl 注入报错的提示,
 *  3. @Repository 是 @Component 的衍生品
 *  4. @Component 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 */

@Mapper
@Repository
public interface ModelMapper {

    /**
     * 保存
     * @param model
     */
    @Insert("insert into sys_model(model_name) values (#{modelName})")
    void save(Model model);

    /**
     * 根据id删除(逻辑删除，对于重要的信息一律采用逻辑删除)
     * @param id
     */
    @Update("update sys_model set is_deleted = 1 where model_id = #{id}")
    void deleteById(Integer id);

    /**
     * 批量删除
     * @param ids
     */
    @Update("<script>" +
            "        update sys_model\n" +
            "        set is_deleted = 1" +
            "        where model_id in\n" +
            "        <foreach collection=\"list\" separator=\",\" item=\"id\" open=\"(\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    void deleteByIds(List<Integer> ids);

    /**
     * 修改
     * @param model
     */
    @Update("<script>" +
            "        update sys_model set\n" +
            "        version = version + 1\n" +
            "        <if test=\"modelName!=null and modelName!=''\">\n" +
            "            ,model_name = #{modelName}\n" +
            "        </if>\n" +
            "        where model_id = #{modelId}\n" +
            "        and version = #{version}" +
            "</script>")
    void update(Model model);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from sys_model where model_id = #{id} and is_deleted = 0")
    Model getById(Integer id);

    /**
     * 查询所有[后台查询]
     * @return
     */
    @Select("select * from sys_model")
    List<Model> getAll();

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Select("<script>" +
            "        select model_id, model_name, created_time, update_time, is_enabled from sys_model\n" +
            "        where is_deleted = 0 \n" +
            "        <if test=\"params.modelName!=null and params.modelName!=''\">\n" +
            "            and model_name like CONCAT('%', #{params.modelName}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.modelTime!=null\">\n" +
            "            and created_time between #{params.modelTime[0],jdbcType=TIMESTAMP} and #{params.modelTime[1],jdbcType=TIMESTAMP}\n" +
            "        </if>\n" +
            "        <if test=\"sortColumn!=null and sortColumn!=''\">\n" +
            "            order by ${sortColumn} ${sortMethod}\n" +
            "        </if>\n" +
            "        limit #{index}, #{pageSize}" +
            "</script>")
    List<Model> getByPage(Page<Model> page);

    /**
     * 统计总数
     * @param page
     * @return
     */
    @Select("<script>" +
            "        select count(*) from sys_model\n" +
            "        where is_deleted = 0\n" +
            "        <if test=\"params.modelName!=null and params.modelName!=''\">\n" +
            "            and model_name like CONCAT('%', #{params.modelName}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.modelTime!=null\">\n" +
            "            and created_time between #{params.modelTime[0],jdbcType=TIMESTAMP} and #{params.modelTime[1],jdbcType=TIMESTAMP}\n" +
            "        </if>\n" +
            "</script>")
    int getCountByPage(Page<Model> page);

    /**
     * 改变启用状态
     * @param model
     */
    @Update("update sys_model set version = version + 1, is_enabled = #{isEnabled} where model_id = #{modelId} and version = #{version}")
    void updateEnable(Model model);

}
