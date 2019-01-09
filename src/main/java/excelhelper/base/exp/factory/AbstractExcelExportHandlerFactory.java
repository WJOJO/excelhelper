package excelhelper.base.exp.factory;

import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.intercepter.Convertor;

/**
 * @author Javon Wang
 * @description excel导出类工厂
 * @create 2018-12-06 14:53
 */
public abstract class AbstractExcelExportHandlerFactory {

    public abstract ListExportHandler createHandler(Class<?> beanClass);
    public abstract ListExportHandler createHandler(Class<?> beanClass, Convertor convertor);
    public abstract ListExportHandler createHandler(Class<?> beanClass, Convertor convertor, Integer group);

}
