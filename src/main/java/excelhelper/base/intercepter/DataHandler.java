package excelhelper.base.intercepter;

/**
 * @author Javon Wang
 * @description data拦截器 做字段翻译....
 * @create 2019-01-22 13:51
 */
public interface DataHandler<T> {

    T handle(T t);
}
