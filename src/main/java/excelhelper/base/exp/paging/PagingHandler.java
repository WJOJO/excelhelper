package excelhelper.base.exp.paging;

import java.util.List;
import java.util.Map;

/**
 * @author Javon Wang
 * @description 分页接口
 * @create 2019-01-23 09:44
 */
public interface PagingHandler<E> {

    /**
     * beanlist分页
     * @param beanList
     * @return
     */
    Map<String, List<E>> paging(List<E> beanList);

}
