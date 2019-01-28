package excelhelper.base.exp.paging;

import excelhelper.base.configuration.ExcelConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javon Wang
 * @description 不分页的做法
 * @create 2019-01-23 10:32
 */
public class NonPagingHandler<E> implements PagingHandler<E> {

    ExcelConfiguration configuration;

    public NonPagingHandler(ExcelConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public Map<String, List<E>> paging(List<E> beanList) {
        Map<String, List<E>> map = new HashMap<>();
        map.put(configuration.getExcelTable().sheetName(), beanList);
        return map;
    }
}
