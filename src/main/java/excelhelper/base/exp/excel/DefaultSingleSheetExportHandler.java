package excelhelper.base.exp.excel;

import excelhelper.base.config.ExcelConfig;
import excelhelper.base.exp.SheetWriter;
import excelhelper.base.intercepter.Convertor;

import java.util.List;

/**
 * @author Javon Wang
 * @description single sheet Excel 导出类
 * @create 2018-12-06 15:19
 */
public class DefaultSingleSheetExportHandler<T> extends List2ExcelExportHandler<T> {

    private SheetWriter sheetWriter;


    public DefaultSingleSheetExportHandler(Class<T> cls, Convertor convertor, Integer group){
        super();
        ExcelConfig excelConfig = new ExcelConfig(cls, group);
        super.init(excelConfig);
        this.sheetWriter = super.prepareSheetWriter(convertor, excelConfig.getExcelTable().sheetName());
    }


    @Override
    public void writeList(List<T> beanList) {
        beanList = listSorted(beanList);
        sheetWriter.write(beanList, getCellStyle());
    }

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }
}
