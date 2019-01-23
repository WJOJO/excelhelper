package excelhelper.base.exp.core.sheet;

import java.util.List;

/**
 * @author Javon Wang
 * @description sheet接口
 * @create 2019-01-22 14:38
 */
public interface BaseSheetWriter<T> {

    void writeData(List<T> beanList);

    void writeRow(T bean);

}
