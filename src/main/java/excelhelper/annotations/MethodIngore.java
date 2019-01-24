package excelhelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Javon Wang
 * @description 是否忽略当前toString方法
 * @create 2019-01-24 15:38
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodIngore {

    boolean value() default false;

}
