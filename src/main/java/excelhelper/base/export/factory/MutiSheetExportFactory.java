package excelhelper.base.export.factory;

import excelhelper.base.export.excel.DefaultMutiSheetExportHandler;
import excelhelper.base.export.ListExportHandler;
import excelhelper.base.intercepter.Convertor;

/**
 * @author Javon Wang
 * @description 多sheet导出工厂
 * @create 2019-01-04 17:16
 */
public class MutiSheetExportFactory extends AbstractExcelExportHandlerFactory{

    @Override
    public ListExportHandler createHandler(Class<?> beanClass) {
        return new DefaultMutiSheetExportHandler(beanClass);
    }

    @Override
    public ListExportHandler createHandler(Class<?> beanClass, Convertor convertor) {
        return new DefaultMutiSheetExportHandler(beanClass, convertor);
    }
}
