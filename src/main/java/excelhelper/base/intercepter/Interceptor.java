package excelhelper.base.intercepter;

/**
 * @author Javon Wang
 * @description bean属性翻译拦截器
 * @create 2019-01-23 11:13
 */
public interface Interceptor<T> {
    T translate(T t);
}
