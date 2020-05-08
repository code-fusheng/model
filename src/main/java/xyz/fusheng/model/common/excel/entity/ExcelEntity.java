package xyz.fusheng.model.common.excel.entity;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @FileName: ExcelEntity
 * @Author: code-fusheng
 * @Date: 2020/5/8 15:39
 * @version: 1.0
 * Description: 注解对应的实体类
 */
@Data
public class ExcelEntity {

    private double width = 10;

    private double height = 10;

    /**
     * 统计
     */
    private boolean isStatistics;

    /**
     * 对应name
     */
    protected String name;
    /**
     * 导出日期格式
     */
    private String format;

    /**
     * 数据的字段
     */
    private Field dataField;

    /**
     * 自适应列宽
     */
    private boolean autoSize;

}
