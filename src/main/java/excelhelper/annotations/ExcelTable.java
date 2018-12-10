package excelhelper.annotations;

import javax.xml.bind.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Javon Wang
 * @description 表格配置信息
 * @create 2018-12-06 13:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTable {
    /**
     * 是否启用表格标题
     */
    boolean enableTableName() default false;

    /**
     * 表格标题
     */
    String tableName() default "";

    /**
     * sheet名字
     */
    String sheetName() default "Export";
}
