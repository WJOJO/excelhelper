package excelhelper.base.exp.core.sheet;

import excelhelper.annotations.ExcelColumn;
import excelhelper.base.configuration.ExcelConfiguration;
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
public class BeanSheetWriter<T> implements BaseSheetWriter<T>{

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
        cell.setCellStyle(configuration.getTableNameCellStyle());
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
        configuration.getColumnNameList().forEach(title -> {
            Cell cell = row.createCell(columnNum++);
            cell.setCellStyle(configuration.getColumnTitleCellStyle());
            cell.setCellValue(title);
        });
        columnNum = 0;
    }


    @Override
    public void writeData(List<T> beanList){
        ((List<T>)configuration.getDataHandler().handle(beanList))
                .stream()
                .peek(bean -> configuration.getBeanIntercepter().translate(bean))
                .forEach(this::writeRow);
    }

    @Override
    public void writeRow(T bean){
        Row row = sheet.createRow(rowNum++);
        Iterator<Map.Entry<ExcelColumn, AccessibleObject>> iterator =
                configuration.getAnnotationTreeMap().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<ExcelColumn, AccessibleObject> annoKey = iterator.next();
            Cell cell = row.createCell(columnNum ++);
            if(configuration.getCellStyle() != null){
                cell.setCellStyle(configuration.getCellStyle());
            }
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
