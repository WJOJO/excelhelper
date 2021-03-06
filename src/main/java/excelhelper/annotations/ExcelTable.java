package excelhelper.annotations;

import excelhelper.base.constants.WorkbookType;
import excelhelper.base.intercepter.BeanIntercepter;
import excelhelper.base.intercepter.DataHandler;
import excelhelper.base.intercepter.Interceptor;
import excelhelper.base.intercepter.ListDataHandler;

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

    /**
     * excel工作空间类型
     */
    WorkbookType workbookType() default WorkbookType.SXSSF;

    /**
     * 分页字段 对应顺序 group1 group2 group3
     */
    String[] pagingColumn() default {};

    /**
     * beanlist处理器
     * @return
     */
    Class<? extends DataHandler> listHandler() default ListDataHandler.class;

    /**
     * bean翻译的拦截器
     * @return
     */
    Class<? extends Interceptor> beanIntercepter() default BeanIntercepter.class;

}
