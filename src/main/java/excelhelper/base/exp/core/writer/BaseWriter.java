package excelhelper.base.exp.core.writer;

import java.util.List;

/**
 * @author Javon Wang
 * @description sheet接口
 * @create 2019-01-22 14:38
 */
public interface BaseWriter<T> {

    void writeData(List<T> beanList);

    void writeRow(T bean);

}
