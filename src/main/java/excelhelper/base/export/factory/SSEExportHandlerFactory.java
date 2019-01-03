package excelhelper.base.export.factory;

import excelhelper.base.export.List2ExcelExportHandler;
import excelhelper.base.export.DefaultSingleSheetExportHandler;

/**
 * @author Javon Wang
 * @description 单sheet excel导出工厂
 * @create 2018-12-06 15:36
 */
public class SSEExportHandlerFactory extends AbstractExcelExportHandlerFactory {

    @Override
    public List2ExcelExportHandler createHandler() {
        return new DefaultSingleSheetExportHandler();
    }


}
