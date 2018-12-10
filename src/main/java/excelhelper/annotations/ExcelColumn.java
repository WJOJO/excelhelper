package excelhelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义表格列的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExcelColumn {
    /**
     * 列标题
     */
    String title();

    /**
     * 顺序
     */
    int sort();

    /**
     * 导入导出组
     */
    int[] groups() default {};



}
