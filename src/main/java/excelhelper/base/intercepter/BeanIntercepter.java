package excelhelper.base.intercepter;


/**
 * @author Javon Wang
 * @description 对象bean的转换
 * @create 2019-01-23 11:08
 */
public class BeanIntercepter<T> implements Interceptor<T>{

    @Override
    public T translate(T t) {
        return t;
    }
}
