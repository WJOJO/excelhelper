package excelhelper.base.imp.callback;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Javon Wang
 * @description 数据导入回调
 * @create 2019-01-09 17:43
 */
public abstract class ImpData<T> implements Callable {

    private List<T> list;


    @Override
    public Object call() throws Exception {
        return null;
    }
}
