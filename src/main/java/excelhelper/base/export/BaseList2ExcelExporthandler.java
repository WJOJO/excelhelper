package excelhelper.base.export;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author Javon Wang
 * @description list导出excel接口
 * @create 2018-12-10 10:54
 */
public interface BaseList2ExcelExporthandler<T> extends BaseListExportHandler<T> {
    /**
     * @Description: 写入excel工作集
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 11:37
     */
    void writeToWorkBook(List<T> beanList);
    /**
     * @Description: 初始化准备工作空间 包含生成workbook  生成sheet 生成row cell rownum columnnum
     * @Author: Javon Wang
     * @Date: 2018/12/10
     * @Time: 11:06
     */
    void init(Class<T> cls);
}
