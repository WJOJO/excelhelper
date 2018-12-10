package excelhelper.util.comparator;

import excelhelper.annotations.ExcelColumn;

import java.util.Comparator;

/**
 * @author Javon Wang
 * @description excel注解列表排序规则
 * @create 2018-12-10 11:32
 */
public class AnnotationTreeMapComparator implements Comparator<ExcelColumn> {

    @Override
    public int compare(ExcelColumn o1, ExcelColumn o2) {
        return o1.sort() - o2.sort();
    }
}
