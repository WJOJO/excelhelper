package excelhelper.base.intercepter;

import java.util.List;

/**
 * @author Javon Wang
 * @description 数组处理类
 * @create 2019-01-22 11:18
 */
public class ListDataHandler implements DataHandler<List> {

    @Override
    public List handle(List list) {
        return list;
    }
}
