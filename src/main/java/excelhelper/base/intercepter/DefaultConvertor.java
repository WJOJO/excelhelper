package excelhelper.base.intercepter;

/**
 * @author Javon Wang
 * @description 默认的对象转换器
 * @create 2019-01-04 15:47
 */
public class DefaultConvertor implements Convertor{
    @Override
    public <T> T convert(T bean) {
        return bean;
    }
}
