package excelhelper.base.exp.core;

/**
 * @author Javon Wang
 * @description data拦截器 做字段翻译....
 * @create 2019-01-22 13:51
 */
public interface DataInterceptor {

    <T> T translate(T t);
}
