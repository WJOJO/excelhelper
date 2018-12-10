package excelhelper.base.export.factory;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.base.export.BaseList2ExcelExporthandler;
import excelhelper.base.export.BaseListExportHandler;
import excelhelper.base.export.DefaultSSEExportHandler;
import excelhelper.util.ReflectUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javon Wang
 * @description 单sheet excel导出工厂
 * @create 2018-12-06 15:36
 */
public class SSEExportHandlerFactory extends AbstractExcelExportHandlerFactory {

    @Override
    public BaseList2ExcelExporthandler createHandler() {
        return new DefaultSSEExportHandler();
    }


}
