package excelhelper.base.export;

import java.util.List;

/**
 * @author Javon Wang
 * @description single sheet Excel 导出类
 * @create 2018-12-06 15:19
 */
public class DefaultSingleSheetExportHandler<T> extends List2ExcelExportHandler<T> {

    @Override
    public void writeToWorkBook(List<T> beanList) {
        for (T bean :
                beanList) {
            System.out.println("开始写入第" + rowNum + "行:" + bean);
            wirteRow(bean);
        }
    }

    @Override
    public <T> List<T> beanListConvert(List<T> beanList) {
        return beanList;
    }
}
