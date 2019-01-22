package excelhelper.base.config;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.base.exp.core.DataInterceptor;
import excelhelper.base.intercepter.Convertor;
import excelhelper.util.comparator.AnnotationTreeMapComparator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.AccessibleObject;
import java.util.*;

/**
 * @author Javon Wang
 * @description excel导出配置信息
 * @create 2019-01-09 15:59
 */
public class ExcelConfiguration {

    /**
     * 类对象
     */
    private Class<?> cls;
    /**
     *  类的表格注解
     */
    private ExcelTable excelTable;

    /**
     * 该对象的属性方法注解及属性方法——排过序的
     */
    private TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap =
            new TreeMap<>(new AnnotationTreeMapComparator());

    /**
     * 列名列表
     */
    private List<String> columnNameList;

    /**
     * 导出组
     */
    private Integer group;
    /**
     * 合并表格名称的单元格样式
     */
    private CellStyle tableNameCellStyle;
    /**
     * 列头样式
     */
    private CellStyle columnTitleCellStyle;
    /**
     * 单元格样式
     */
    private CellStyle cellStyle;


    /**
     * 工作空间
     */
    private Workbook workbook;

    /**
     * 数据转换拦截器，处理list
     */
    private DataInterceptor dataInterceptor;
    /**
     * bean的转换器，处理bean
     */
    private Convertor convertor;


    public ExcelConfiguration(Class<?> cls){
        this(cls, null);
    }

    public ExcelConfiguration(Class<?> cls, Integer group){
        this.cls = cls;
        if( ! cls.isAnnotationPresent(ExcelTable.class)){
            throw new RuntimeException("无注解，初始化失败");
        }
        this.excelTable = cls.getAnnotation(ExcelTable.class);
        this.group = group;
        initAnnotationTreeMap();
        initColumnTitles();
    }


    /**
     * @Description: 初始化字段在excel中的顺序
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 14:15
     */
    private boolean initAnnotationTreeMap(){
        AccessibleObject[] declaredFieldsOrMethods = this.cls.getDeclaredFields();
        add2AnnoTreeMap(declaredFieldsOrMethods);

        declaredFieldsOrMethods = this.cls.getDeclaredMethods();
        add2AnnoTreeMap(declaredFieldsOrMethods);
        return annotationTreeMap.size() > 0;
    }

    /**
     * @Description: 所有的属性及方法放入可排序的treemap
     * @Author: Javon Wang
     * @Date: 2019/1/9
     * @Time: 16:31
     */
    private void add2AnnoTreeMap(AccessibleObject[] accessibleObjects){

        for (AccessibleObject object :
                accessibleObjects) {
            if (object.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = object.getAnnotation(ExcelColumn.class);
                //未指定group
                if(this.group == null){
                    annotationTreeMap.put(excelColumn, object);
                }else{
                    //已指定group
                    if(Arrays.stream(excelColumn.groups()).anyMatch((e) -> e == this.group)){
                        annotationTreeMap.put(excelColumn, object);
                    }
                }
            }
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

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public ExcelTable getExcelTable() {
        return excelTable;
    }

    public void setExcelTable(ExcelTable excelTable) {
        this.excelTable = excelTable;
    }

    public TreeMap<ExcelColumn, AccessibleObject> getAnnotationTreeMap() {
        return annotationTreeMap;
    }

    public void setAnnotationTreeMap(TreeMap<ExcelColumn, AccessibleObject> annotationTreeMap) {
        this.annotationTreeMap = annotationTreeMap;
    }

    public List<String> getColumnNameList() {
        return columnNameList;
    }

    public void setColumnNameList(List<String> columnNameList) {
        this.columnNameList = columnNameList;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public CellStyle getTableNameCellStyle() {
        return tableNameCellStyle;
    }

    public void setTableNameCellStyle(CellStyle tableNameCellStyle) {
        this.tableNameCellStyle = tableNameCellStyle;
    }

    public CellStyle getColumnTitleCellStyle() {
        return columnTitleCellStyle;
    }

    public void setColumnTitleCellStyle(CellStyle columnTitleCellStyle) {
        this.columnTitleCellStyle = columnTitleCellStyle;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public DataInterceptor getDataInterceptor() {
        return dataInterceptor;
    }

    public void setDataInterceptor(DataInterceptor dataInterceptor) {
        this.dataInterceptor = dataInterceptor;
    }

    public Convertor getConvertor() {
        return convertor;
    }

    public void setConvertor(Convertor convertor) {
        this.convertor = convertor;
    }
}
