package excelhelper.base.export.factory;

import excelhelper.base.export.excel.DefaultSingleSheetExportHandler;
import excelhelper.base.export.ListExportHandler;
import excelhelper.base.intercepter.Convertor;

/**
 * @author Javon Wang
 * @description 单sheet excel导出工厂
 * @create 2018-12-06 15:36
 */
public class SSEExportHandlerFactory extends AbstractExcelExportHandlerFactory {

    @Override
    public ListExportHandler createHandler(Class<?> beanClass) {
        return new DefaultSingleSheetExportHandler(beanClass);
    }

    @Override
    public ListExportHandler createHandler(Class<?> beanClass, Convertor convertor) {
        return new DefaultSingleSheetExportHandler(beanClass, convertor);
    }
}
