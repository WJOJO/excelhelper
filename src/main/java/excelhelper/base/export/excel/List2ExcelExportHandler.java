package excelhelper.base.export.excel;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.base.constants.WorkbookType;
import excelhelper.base.export.ListExportHandler;
import excelhelper.base.export.SheetWriter;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;
import excelhelper.util.comparator.AnnotationTreeMapComparator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.AccessibleObject;
import java.util.*;

/**
 * @author Javon Wang
 * @description list导出excel接口
 * @create 2018-12-10 10:54
 */
public abstract class List2ExcelExportHandler<T>  implements ListExportHandler<T> {

    /**
     * class对象
     */
    private Class<T> beanClass;
    /**
     * 导出bean的table注解
     */
    private ExcelTable excelTable;

    /**
     * excel工作簿
     */
    private Workbook workbook;

    /**
     * 列名列表
     */
    private List<String> columnNameList;

    /**
     * 单元格格式
     */
    private CellStyle cellStyle;

    /**
     * 存放排序好的所有要导出的field或者Method
     * 定义好排序规则
     */
    private TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap =
            new TreeMap<>(new AnnotationTreeMapComparator());

    /**
     * 导出组
     */
    private int group;

    /**
     * @Description: 创建sheetWriter对象
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:46
     */
    protected SheetWriter prepareSheetWriter(){
        return prepareSheetWriter(new DefaultConvertor(), excelTable.sheetName());
    }

    /**
     * @Description: 创建sheetWriter对象
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:46
     */
    protected SheetWriter prepareSheetWriter(Convertor convertor, String sheetName){
        SheetWriter sheetWriter = new SheetWriter(workbook, sheetName, annotationTreeMap);
        if(excelTable.enableTableName()){
            sheetWriter.createTableHeader(excelTable.tableName(),columnNameList.size(), cellStyle);
        }
        sheetWriter.createColumnTitle(columnNameList, cellStyle);
        sheetWriter.setConvertor(convertor);
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
    public void init(Class<T> cls) {
        this.beanClass = cls;
        if(cls.isAnnotationPresent(ExcelTable.class)){
            this.excelTable = cls.getAnnotation(ExcelTable.class);
        }else{
            throw new RuntimeException("对象无导出excel注解");
        }
        initWorkBook();
        initAnnotationTreeMap();
        initColumnTitles();
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
        WorkbookType workbookType = excelTable.workbookType();
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

    /**
     * @Description: 初始化列的标题
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:14
     */
    private void initColumnTitles(){
        columnNameList = new ArrayList<>();
        if (annotationTreeMap.size() == 0){
            return;
        }
        Iterator<ExcelColumn> iterator = annotationTreeMap.keySet().iterator();
        while(iterator.hasNext()){
            columnNameList.add(iterator.next().title());
        }
    }

    /**
     * @Description: 初始化字段在excel中的顺序
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:15
     */
    private boolean initAnnotationTreeMap(){
        AccessibleObject[] declaredFieldsOrMethods = beanClass.getDeclaredFields();
        add2AnnoTreeMap(declaredFieldsOrMethods);

        declaredFieldsOrMethods = beanClass.getDeclaredMethods();
        add2AnnoTreeMap(declaredFieldsOrMethods);
        return annotationTreeMap.size() > 0;
    }

    private void add2AnnoTreeMap(AccessibleObject[] accessibleObjects){

//        Arrays.stream(accessibleObjects).filter();

        for (AccessibleObject object :
                accessibleObjects) {
            if (object.isAnnotationPresent(ExcelColumn.class)) {
                annotationTreeMap.put(object.getAnnotation(ExcelColumn.class), object);
            }
        }
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public ExcelTable getExcelTable() {
        return excelTable;
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }
}
