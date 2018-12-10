package excelhelper.base.export.factory;

import excelhelper.base.constants.WorkbookType;
import excelhelper.base.export.AbstractExcelExportHandler;
import excelhelper.base.export.BaseListExportHandler;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Javon Wang
 * @description excel导出类工厂
 * @create 2018-12-06 14:53
 */
public abstract class AbstractExcelExportHandlerFactory {

    public abstract BaseListExportHandler createHandler();

}
