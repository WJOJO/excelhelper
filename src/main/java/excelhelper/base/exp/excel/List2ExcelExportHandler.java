package excelhelper.base.exp.excel;

import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.constants.WorkbookType;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.exp.SheetWriter;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Javon Wang
 * @description list导出excel接口
 * @create 2018-12-10 10:54
 */
public abstract class List2ExcelExportHandler<T>  implements ListExportHandler<T> {

    /**
     * excel导入导出配置
     */
    protected ExcelConfiguration excelConfig;

    /**
     * excel工作簿
     */
    private Workbook workbook;


    /**
     * 单元格格式
     */
    private CellStyle cellStyle;


    /**
     * @Description: 创建sheetWriter对象
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:46
     */
    protected SheetWriter prepareSheetWriter(){
        return prepareSheetWriter(new DefaultConvertor(), excelConfig.getExcelTable().sheetName());
    }

    /**
     * @Description: 创建sheetWriter对象
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:46
     */
    protected SheetWriter prepareSheetWriter(Convertor convertor, String sheetName){
        SheetWriter sheetWriter = new SheetWriter(workbook, sheetName, excelConfig.getAnnotationTreeMap());
        if(excelConfig.getExcelTable().enableTableName()){
            sheetWriter.createTableHeader(excelConfig.getExcelTable().tableName(),
                    excelConfig.getColumnNameList().size(), cellStyle);
        }
        sheetWriter.createColumnTitle(excelConfig.getColumnNameList(), cellStyle);
        return sheetWriter;
    }



    @Override
    public void writeFile(String path) {
        try {
            FileOutputStream stream = new FileOutputStream(path);
            writeStream(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeStream(OutputStream os) {
        try {
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 初始化workbook
     * @Param: beanclass
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 10:56
     */
    public void init(ExcelConfiguration excelConfig) {
        this.excelConfig = excelConfig;
        initWorkBook();
        initCellStyle();
    }

    /**
     * @Description: 创建单元格样式
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:54
     */
    private void initCellStyle(){
        this.cellStyle = workbook.createCellStyle();
    }

    /**
     * @Description: 根据注解的workbooktype初始化workbook
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:13
     */
    private void initWorkBook(){
        System.out.println("初始化workbook");
        WorkbookType workbookType = excelConfig.getExcelTable().workbookType();
        switch (workbookType){
            case XSSF:
                this.workbook = new XSSFWorkbook();
                break;
            case HSSF:
                this.workbook = new HSSFWorkbook();
                break;
            case SXSSF:
                this.workbook = new SXSSFWorkbook(1000);
                break;
        }

    }

    protected CellStyle getCellStyle(){
        return workbook.createCellStyle();
    }

}
