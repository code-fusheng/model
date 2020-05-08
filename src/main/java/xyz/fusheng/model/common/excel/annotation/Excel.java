package xyz.fusheng.model.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName: Excel
 * @Author: code-fusheng
 * @Date: 2020/5/8 15:15
 * @version: 1.0
 * Description: Excel 导入导出自定义注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

    /**
     * 判断该列是否需要格式化
     */
    boolean needFormat() default false;

    /**
     * 时间格式化
     */
    String format() default  "";

    /**
     * 导出时在 excel 表格中每个列的高度 单位为字母，一个汉字=2个字母
     */
    double height() default 10;

    /**
     * 导出时的列名，不可重复
     */
    String name();

    /**
     * 导出时在 excel 中每个列的宽，单位为字符，一个汉字=2字符
     * 如 以列名列内容中较合适的长度 例如姓名列6 【姓名一般三个字】
     * 性别列4【男女占1，但是列标题两个汉字】 限制1-255
     */
    double width() default 10;

    /**
     * 是否自动统计数据，如果是统计true的话，在最互殴追加一行统计，数据求和
     */
    boolean isStatistics() default  false;

    /**
     * 是否设置列宽自适应
     */
    boolean autoSize() default false;




}
