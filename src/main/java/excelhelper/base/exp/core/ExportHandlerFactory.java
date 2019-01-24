package excelhelper.base.exp.core;

import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.exp.core.exporthandler.DefaultCsvExportHandler;
import excelhelper.base.exp.core.exporthandler.DefaultExcelExportHandler;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author Javon Wang
 * @description 导出处理工厂
 * @create 2019-01-24 15:40
 */
public class ExportHandlerFactory {

    public static  <T> ListExportHandler<T> buildExcelHandler(Class<T> cls){
        return buildExcelHandler(cls, -1);
    }

    public static  <T> ListExportHandler<T> buildExcelHandler(Class<T> cls, int group){
        ExcelConfiguration configuration = new ExcelConfiguration(cls, group);
        return new DefaultExcelExportHandler<>(configuration);
    }

    public static <T> ListExportHandler<T> buildCsvHandler(Class<T> cls){
        return buildCsvHandler(cls, -1);
    }

    public static <T> ListExportHandler<T> buildCsvHandler(Class<T> cls, int group){
        ExcelConfiguration configuration = new ExcelConfiguration(cls, group);
        return new DefaultExcelExportHandler<>(configuration);
    }
}
