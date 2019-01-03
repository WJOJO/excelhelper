package excelhelper.base.export;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.util.ReflectUtil;
import excelhelper.util.comparator.AnnotationTreeMapComparator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Javon Wang
 * @description list导出excel接口
 * @create 2018-12-10 10:54
 */
public abstract class List2ExcelExportHandler<T>  implements ListExportHandler {


    /**
     * excel工作簿
     */
    protected Workbook workbook;
    /**
     * 列名列表
     */
    protected List<String> columnNameList;
    /**
     * 单元格格式
     */
    protected CellStyle cellStyle;

    /**
     * 存放排序好的所有要导出的field或者Method
     * 定义好排序规则
     */
    protected TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap =
            new TreeMap<>(new AnnotationTreeMapComparator());

    /**
     * 当前操作sheet
     */
    protected Sheet sheet;

    /**
     * 当前行号
     */
    protected int rowNum;
    /**
     * 当前操作行
     */
    protected Row row;

    /**
     * 当前列号
     */
    protected int columnNum;


    /**
     * 抽象List写入工作空间的方法
     * @param beanList
     */
    public abstract void writeToWorkBook(List<T> beanList);



    /**
     * @Description: 写一行excel
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 14:40
     */
    protected void wirteRow(T bean){
        System.out.printf("第%d行开始写入:", rowNum);
        createRow();
        Iterator<Map.Entry<ExcelColumn, AccessibleObject>> iterator =
                annotationTreeMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<ExcelColumn, AccessibleObject> annoKey = iterator.next();
            Cell cell = createCell();
            if(cellStyle != null){
                cell.setCellStyle(cellStyle);
            }
            AccessibleObject fieldOrMethod = annoKey.getValue();
            String value = "";
            if (fieldOrMethod instanceof Field) {
                value = ReflectUtil.getValueFromField((Field) fieldOrMethod, bean);
                cell.setCellValue(value);
            }else{
                value = ReflectUtil.getValueFromMethod((Method)fieldOrMethod, bean);
                cell.setCellValue(value);
            }
            System.out.print(value + " ");
        }
        System.out.print("\n");
    }

    /**
     * @Description: 创建行 行数指针加1
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 14:50
     */
    private void createRow(){
        //新的一行初始化列指针
        row = sheet.createRow(rowNum ++);
        columnNum = 0;
    }

    /**
     * @Description: 创建列 列指针加1
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 14:50
     */
    private Cell createCell(){
        return row.createCell(columnNum ++);
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


    public void initWorkbook(Class<T> cls) {
        initWorkBook();
        initSheet(cls);
        initAnnotationTreeMap(cls);
    }

    private void initWorkBook(){
        System.out.println("初始化workbook");
        //workboo为空初始化workbook
        if(workbook == null){
            workbook = new SXSSFWorkbook(100);
        }
    }
    private boolean initSheet(Class<T> cls){
        if(! cls.isAnnotationPresent(ExcelTable.class)){
            return false;
        }
        ExcelTable excelTable = cls.getAnnotation(ExcelTable.class);
        sheet = workbook.createSheet( excelTable.sheetName());


        //初始化注解列表
        System.out.println("初始化注解列表");
        boolean isInitTreeMap = initAnnotationTreeMap(cls);
        if (!isInitTreeMap){
            return false;
        }
        //初始化列名
        initColumnNameList();
        //创建表名及列头
        System.out.println("创建表名及列头");
        createTableHeader(excelTable);

        return true;
    }

    private void createTableHeader(ExcelTable excelTable){
        if(excelTable.enableTableName()){
            createRow();
            row.setHeightInPoints(20);
            Cell cell = createCell();
            cell.setCellValue(excelTable.tableName());
            sheet.addMergedRegion(
                    new CellRangeAddress(0, 0, 0, columnNameList.size() - 1 ));
        }
        createRow();
        for(int i = 0 ; i < columnNameList.size(); i++){
            Cell columnNameCell = createCell();
            columnNameCell.setCellValue(columnNameList.get(i));
        }
    }

    private void initColumnNameList(){
        columnNameList = new ArrayList<>();
        if (annotationTreeMap.size() == 0){
            return;
        }
        Iterator<ExcelColumn> iterator = annotationTreeMap.keySet().iterator();
        while(iterator.hasNext()){
            columnNameList.add(iterator.next().title());
        }
    }

    private boolean initAnnotationTreeMap(Class<T> cls){
        AccessibleObject[] declaredFieldsOrMethods = cls.getDeclaredFields();
        add2AnnoTreeMap(declaredFieldsOrMethods);

        declaredFieldsOrMethods = cls.getDeclaredMethods();
        add2AnnoTreeMap(declaredFieldsOrMethods);
        return annotationTreeMap.size() > 0;
    }

    private void add2AnnoTreeMap(AccessibleObject[] accessibleObjects){
        for (AccessibleObject object :
                accessibleObjects) {
            if (object.isAnnotationPresent(ExcelColumn.class)) {
                annotationTreeMap.put(object.getAnnotation(ExcelColumn.class), object);
            }
        }
    }

}
