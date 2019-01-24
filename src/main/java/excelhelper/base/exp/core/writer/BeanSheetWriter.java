package excelhelper.base.exp.core.writer;

import excelhelper.annotations.ExcelColumn;
import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.constants.CellStyleType;
import excelhelper.util.ReflectUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Javon Wang
 * @description bean导出excel
 * @create 2019-01-22 13:54
 */
public class BeanSheetWriter<T> implements BaseWriter<T> {

    private final Sheet sheet;

    private final ExcelConfiguration configuration;

    private int rowNum = 0;

    private int columnNum = 0;


    public BeanSheetWriter(String sheetName, ExcelConfiguration configuration){
        super();
        this.configuration = configuration;
        this.sheet = configuration.getWorkbook().createSheet(sheetName);
        initSheet();
    }


    private void initSheet(){
        createTableHeader();
        createColumnTitle();
    }

    /**
     * @Description: 创建表格头
     * @Param: 表名, 列size, 单元格样式非空
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:28
     */
    private void createTableHeader(){
        Row header = sheet.createRow(rowNum++);
        header.setHeightInPoints(20);
        Cell cell = header.createCell(0);
        cell.setCellValue(configuration.getExcelTable().tableName());
        cell.setCellStyle(configuration.getCellStyleMap().get(CellStyleType.TITLE));
        sheet.addMergedRegion(
                new CellRangeAddress(0, 0,
                        0, configuration.getColumnNameList().size() - 1 ));
    }

    /**
     * @Description: 创建表格列头
     * @Param: 列名， 单元格样式
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:36
     */
    private void createColumnTitle(){
        Row row = sheet.createRow(rowNum++);
        List<String> columnNameList = configuration.getColumnNameList();
        for (String title :
                columnNameList) {
            Cell cell = row.createCell(columnNum++);
            cell.setCellStyle(configuration.getCellStyleMap().get(CellStyleType.COLUMNNAME));
            cell.setCellValue(title);
        }
        columnNum = 0;
    }


    @Override
    public void writeData(List<T> beanList){
        beanList = (List<T>)configuration.getDataHandler().handle(beanList);
        for (T bean :
                beanList) {
            bean = (T)configuration.getBeanIntercepter().translate(bean);
            writeRow(bean);
        }
    }

    @Override
    public void writeRow(T bean){
        Row row = sheet.createRow(rowNum++);
        Iterator<Map.Entry<ExcelColumn, AccessibleObject>> iterator =
                configuration.getAnnotationTreeMap().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<ExcelColumn, AccessibleObject> annoKey = iterator.next();
            Cell cell = row.createCell(columnNum ++);
            cell.setCellStyle(configuration.getCellStyleMap().get(CellStyleType.CELL));
            String value = "";
            AccessibleObject fieldOrMethod = annoKey.getValue();
            if (fieldOrMethod instanceof Field) {
                value = ReflectUtil.getValueFromField((Field) fieldOrMethod, bean);
                cell.setCellValue(value);
            }else{
                value = ReflectUtil.getValueFromMethod((Method)fieldOrMethod, bean);
                cell.setCellValue(value);
            }
        }
        columnNum = 0;
    }


}
