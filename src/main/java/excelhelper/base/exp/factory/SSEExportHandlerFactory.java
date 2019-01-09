package excelhelper.base.exp.factory;

import excelhelper.base.exp.excel.DefaultSingleSheetExportHandler;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;

/**
 * @author Javon Wang
 * @description 单sheet excel导出工厂
 * @create 2018-12-06 15:36
 */
public class SSEExportHandlerFactory extends AbstractExcelExportHandlerFactory {

    @Override
    public ListExportHandler createHandler(Class<?> beanClass) {
        return createHandler(beanClass, new DefaultConvertor(), null);
    }

    @Override
    public ListExportHandler createHandler(Class<?> beanClass, Convertor convertor) {
        return createHandler(beanClass, convertor, null);
    }

    @Override
    public ListExportHandler createHandler(Class<?> beanClass, Convertor convertor, Integer group) {
        return new DefaultSingleSheetExportHandler(beanClass, convertor, group);
    }
}
