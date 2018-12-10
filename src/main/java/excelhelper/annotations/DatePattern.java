package excelhelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Javon Wang
 * @description 日期格式 仅用于日期类型属性值上
 * @create 2018-12-06 10:45
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatePattern {

    /**
     * 日期格式转换
     */
    String pattern() default "YYYY-MM-DD hh:mm:ss";
}
