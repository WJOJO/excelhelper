package excelhelper.base.exp;

import excelhelper.annotations.ExcelColumn;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;
import excelhelper.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Javon Wang
 * @description sheet页写入
 * @create 2019-01-04 11:08
 */
@Slf4j
public class SheetWriter<T> {

    private final Sheet sheet;

    private int rowNum;

    private int columnNum;

    private final Convertor convertor;

    /**
     * 存放排序好的所有要导出的field或者Method
     * 定义好排序规则
     */
    private TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap;

    public SheetWriter(Workbook workbook, String sheetName,
                       TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap){
        this(workbook, sheetName, annotationTreeMap, new DefaultConvertor());
    }

    public SheetWriter(Workbook workbook, String sheetName,
                       TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap, Convertor convertor){
        this.convertor = convertor;
        this.sheet = workbook.createSheet(sheetName);
        this.annotationTreeMap = annotationTreeMap;
    }

    /**
     * @Description: sheet写入bean列表
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:38
     */
    public void write(List<T> beanList, CellStyle cellStyle){
        for (T t :
                beanList) {
            writeRow(convertor.convert(t), cellStyle);
        }

    }
    /**
     * @Description: sheet中写入bean
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:38
     */
    public void writeRow(T bean, CellStyle cellStyle){
        Row row = sheet.createRow(rowNum++);
        Iterator<Map.Entry<ExcelColumn, AccessibleObject>> iterator =
                annotationTreeMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<ExcelColumn, AccessibleObject> annoKey = iterator.next();
            Cell cell = row.createCell(columnNum ++);
            if(cellStyle != null){
                cell.setCellStyle(cellStyle);
            }
            String value = "";
            AccessibleObject fieldOrMethod = annoKey.getValue();
            if (fieldOrMethod instanceof Field) {
                value = ReflectUtil.getValueFromField((Field) fieldOrMethod, bean);
                writeCellByType(cell, value);
            }else{
                value = ReflectUtil.getValueFromMethod((Method)fieldOrMethod, bean);
                writeCellByType(cell, value);
            }
        }
        columnNum = 0;
    }

    /**
     * @Description: 按格式写入单元格
     * @Author: Javon Wang
     * @Date: 2019/1/9
     * @Time: 17:28
     */
    private void writeCellByType(Cell cell, String value){
        cell.setCellValue(value);
        log.debug("写入单元格：" + String.valueOf(value));
    }



    /**
     * @Description: 创建表格头
     * @Param: 表名, 列size, 单元格样式非空
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:28
     */
    public void createTableHeader(String tableName, int columnSize, CellStyle cellStyle){
        Row header = sheet.createRow(rowNum++);
        header.setHeightInPoints(20);
        Cell cell = header.createCell(0);
        cell.setCellValue(tableName);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegion(
                new CellRangeAddress(0, 0, 0, columnSize - 1 ));
    }

    /**
     * @Description: 创建表格列头
     * @Param: 列名， 单元格样式
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:36
     */
    public void createColumnTitle(List<String> columnTitles, CellStyle cellStyle){
        Row row = sheet.createRow(rowNum++);
        for (String title :
                columnTitles) {
            Cell cell = row.createCell(columnNum++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title);
        }
        columnNum = 0;
    }

}
