package excelhelper.base.exp.paging;

import excelhelper.base.configuration.ExcelConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javon Wang
 * @description bean属性分页
 * @create 2019-01-23 09:45
 */
public class PropertyPagingHandler<E> implements PagingHandler<E> {

    ExcelConfiguration excelConfiguration;

    public PropertyPagingHandler(ExcelConfiguration excelConfiguration){
        this.excelConfiguration = excelConfiguration;
    }

    @Override
    public Map<String, List<E>> paging(List<E> beanList) {
        Map<String, List<E>> map = new HashMap<>();
        Field pagingField = excelConfiguration.getPagingField();
        String fieldName = pagingField.getName();
        if(!pagingField.isAccessible()){
            pagingField.setAccessible(true);
        }
        for (E e :
                beanList) {
            try {
                String value = String.valueOf(pagingField.get(e));
                value = fieldName + "_" + value;
                if (map.containsKey(value)) {
                    map.get(value).add(e);
                } else {
                    ArrayList<E> list = new ArrayList<>();
                    list.add(e);
                    map.put(value, list);
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return map;
    }
}
