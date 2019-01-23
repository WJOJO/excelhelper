package excelhelper.base.configuration;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.base.constants.CellStyleType;
import excelhelper.base.constants.WorkbookType;
import excelhelper.base.exception.InitialException;
import excelhelper.base.exp.paging.NonPaging;
import excelhelper.base.exp.paging.Paging;
import excelhelper.base.exp.paging.PropertyPaging;
import excelhelper.base.intercepter.DataHandler;
import excelhelper.base.intercepter.Interceptor;
import excelhelper.util.comparator.AnnotationTreeMapComparator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * excel导出配置信息
 * @author Javon Wang
 * @since  2019-01-09 15:59
 */
public class ExcelConfiguration {

    /**
     * 类对象
     */
    private Class<?> cls;

    /**
     * 分页属性
     */
    private Field pagingField;

    /**
     * 分页处理器
     */
    private Paging pagingHandler;

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
    private DataHandler dataHandler;
    /**
     * bean的转换器，处理bean
     */
    private Interceptor beanIntercepter;

    /**
     * 存放所有类型的cellstyle
     */
    private Map<CellStyleType, CellStyle> cellStyleMap;




    public ExcelConfiguration(Class<?> cls){
        this(cls, -1);
    }

    public ExcelConfiguration(Class<?> cls, Integer group){
        this.cls = cls;
        if( ! cls.isAnnotationPresent(ExcelTable.class)){
            throw new RuntimeException("无注解，初始化失败");
        }
        this.excelTable = cls.getAnnotation(ExcelTable.class);
        this.group = group;

        initPaging();
        initWorkBook();
        initIntercepter();
        initAnnotationTreeMap();
        initColumnTitles();
        initCellStyleMap();
    }

    private void initCellStyleMap(){
        this.cellStyleMap = new HashMap<>();
        this.cellStyleMap.put(CellStyleType.CELL, this.workbook.createCellStyle());
        this.cellStyleMap.put(CellStyleType.COLUMNNAME, this.workbook.createCellStyle());
        this.cellStyleMap.put(CellStyleType.TITLE, this.workbook.createCellStyle());
    }

    /**
     * @Description: 初始化分sheet的属性
     * @Author: Javon Wang
     * @Date: 2019/1/23
     * @Time: 14:23
     */
    private void initPaging(){
        String[] pagingColumns = this.excelTable.pagingColumn();
        if(pagingColumns.length == 0){
            this.pagingField = null;
            this.pagingHandler = new NonPaging(this);
            return;
        } else{
            try {
                this.pagingField = this.cls.getDeclaredField(pagingColumns[group < 0?0:group]);
                this.pagingHandler = new PropertyPaging(this);
            } catch (NoSuchFieldException e) {
                throw new InitialException("分组第" + group + "组,分页属性" + pagingColumns[group]+"不存在！",e);
            }
        }

    }

    /**
     * @Description: 初始化list和bean的转换拦截器
     * @Author: Javon Wang
     * @Date: 2019/1/23
     * @Time: 13:48
     */    
    private void initIntercepter() throws InitialException{
        //初始化拦截器
        try {
            Constructor<? extends Interceptor> constructor = excelTable.beanIntercepter().getConstructor();
            this.beanIntercepter = constructor.newInstance();

            this.dataHandler = excelTable.listHandler().getConstructor().newInstance();
        } catch (Exception e) {
            throw new InitialException("构造beanlist拦截器失败", e);
        }
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
                if(this.group == -1){
                    annotationTreeMap.put(excelColumn, object);
                }else{
                    //已指定group
                    if(Arrays.asList(excelColumn.groups()).contains(this.group)){
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

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }


    public Field getPagingField() {
        return pagingField;
    }

    public void setPagingField(Field pagingField) {
        this.pagingField = pagingField;
    }

    public Paging getPagingHandler() {
        return pagingHandler;
    }

    public void setPagingHandler(Paging pagingHandler) {
        this.pagingHandler = pagingHandler;
    }

    public Interceptor getBeanIntercepter() {
        return beanIntercepter;
    }

    public void setBeanIntercepter(Interceptor beanIntercepter) {
        this.beanIntercepter = beanIntercepter;
    }
}
