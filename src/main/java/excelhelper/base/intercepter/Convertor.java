package excelhelper.base.intercepter;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author Javon Wang
 * @description 对象翻译接口
 * @create 2019-01-04 15:45
 */
public interface Convertor {

    public <T> T convert(T bean);

}
