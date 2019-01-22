package excelhelper.base.imp;

import java.util.List;

/**
 * @author Javon Wang
 * @description 动态数组导入
 * @create 2019-01-09 17:34
 */
public interface ListImpHandler<T> extends ImpHandler {

    List<T> readList();

}
