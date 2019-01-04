package excelhelper.base.export.excel;

import excelhelper.base.export.SheetWriter;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;

import java.util.List;

/**
 * @author Javon Wang
 * @description single sheet Excel 导出类
 * @create 2018-12-06 15:19
 */
public class DefaultSingleSheetExportHandler<T> extends List2ExcelExportHandler<T> {

    private SheetWriter sheetWriter;

    public DefaultSingleSheetExportHandler(Class<T> cls){
        this(cls, new DefaultConvertor());
    }

    public DefaultSingleSheetExportHandler(Class<T> cls, Convertor convertor){
        super();
        super.init(cls);
        this.sheetWriter = super.prepareSheetWriter(convertor, getExcelTable().sheetName());
    }


    @Override
    public void export(List<T> beanList) {
        beanList = listSorted(beanList);
        sheetWriter.write(beanList, getCellStyle());
    }

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }
}
