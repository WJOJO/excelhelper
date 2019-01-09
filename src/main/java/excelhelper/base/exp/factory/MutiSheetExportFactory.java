package excelhelper.base.exp.factory;

import excelhelper.base.exp.excel.DefaultMutiSheetExportHandler;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;

/**
 * @author Javon Wang
 * @description 多sheet导出工厂
 * @create 2019-01-04 17:16
 */
public class MutiSheetExportFactory extends AbstractExcelExportHandlerFactory{

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
        return new DefaultMutiSheetExportHandler(beanClass, convertor, group);
    }
}
