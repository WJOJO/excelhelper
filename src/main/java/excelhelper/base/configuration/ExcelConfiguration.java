package excelhelper.base.configuration;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;
import excelhelper.base.constants.CellStyleType;
import excelhelper.base.constants.WorkbookTypeEnum;
import excelhelper.base.exception.InitialException;
import excelhelper.base.exp.paging.NonPagingHandler;
import excelhelper.base.exp.paging.PagingHandler;
import excelhelper.base.exp.paging.PropertyPagingHandler;
import excelhelper.base.intercepter.DataHandler;
import excelhelper.base.intercepter.Interceptor;
import excelhelper.util.comparator.AnnotationTreeMapComparator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
@Slf4j
public class ExcelConfiguration {

    /**
     * 类对象
     */
    private Class<?> cls;

    /**
     * 是否导出 true为导出  false为导入
     */
    private boolean isExport;

    /**
     * 分页属性
     */
    private Field pagingField;

    /**
     * 分页处理器
     */
    private PagingHandler pagingHandler;

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


    public ExcelConfiguration(Class<?> cls, Integer group, boolean isExport){
        this.cls = cls;
        if( ! cls.isAnnotationPresent(ExcelTable.class)){
            throw new RuntimeException("无注解，初始化失败");
        }
        this.excelTable = cls.getAnnotation(ExcelTable.class);
        this.group = group;
        this.isExport = isExport;
        initPaging();
        initWorkBook();
        initIntercepter();
        initAnnotationTreeMap();
        initColumnTitles();
        initCellStyleMap();
    }

    private void initCellStyleMap(){
        this.cellStyleMap = new HashMap<>();
        CellStyle tableNameStyle = this.workbook.createCellStyle();
        tableNameStyle.setAlignment(HorizontalAlignment.CENTER);
        Font tableFont = this.workbook.createFont();
        tableFont.setFontName("Arial");
        tableFont.setFontHeight((short)16);
        tableFont.setBold(true);
        tableNameStyle.setFont(tableFont);
        this.cellStyleMap.put(CellStyleType.TITLE, tableNameStyle);

        CellStyle columnNameStyle = this.workbook.createCellStyle();

        this.cellStyleMap.put(CellStyleType.CELL, this.workbook.createCellStyle());
        this.cellStyleMap.put(CellStyleType.COLUMNNAME, this.workbook.createCellStyle());
    }

    /**
     * @Description: 初始化分sheet的属性
     * @Author: Javon Wang
     * @Date: 2019/1/23
     * @Time: 14:23
     */
    private void initPaging(){
        int index = 0;
        String[] pagingColumns = this.excelTable.pagingColumn();
        try {
            if(pagingColumns.length == 0){
                this.pagingField = null;
                this.pagingHandler = new NonPagingHandler(this);
                return; // 直接返回
            } else if(this.group > pagingColumns.length){
                log.info("分页导出的字段 与 分组不一致, 使用第一个分页的属性值进行分页");
                index = 0;
            } else{
                index = this.group <= 0?0:this.group - 1;
            }
            this.pagingField = this.cls.getDeclaredField(pagingColumns[index]);
            this.pagingHandler = new PropertyPagingHandler(this);
        } catch (NoSuchFieldException e) {
            throw new InitialException("分组第" + group + "组,分页属性" + pagingColumns[group]+"不存在！",e);
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
    private void initAnnotationTreeMap(){
        AccessibleObject[] declaredFieldsOrMethods = this.cls.getDeclaredFields();
        add2AnnoTreeMap(declaredFieldsOrMethods);

        declaredFieldsOrMethods = this.cls.getDeclaredMethods();
        add2AnnoTreeMap(declaredFieldsOrMethods);
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
            //未注解直接跳过
            if ( ! object.isAnnotationPresent(ExcelColumn.class)) {
                continue;
            }
            ExcelColumn excelColumn = object.getAnnotation(ExcelColumn.class);
            if(excelColumn.type() == 0){
                add2AnnoTreeMap(excelColumn, object);
            }
            if(this.isExport && excelColumn.type() == 2){
                add2AnnoTreeMap(excelColumn, object);
            }else {
                add2AnnoTreeMap(excelColumn, object);
            }
        }
    }

    /**
     * @Description: 添加入annotationMap中
     * @Author: Javon Wang
     * @Date: 2019/1/28
     * @Time: 13:49
     */
    private void add2AnnoTreeMap(ExcelColumn excelColumn, AccessibleObject object){
        //未指定group 或者 指定group
        if(this.group == -1 || Arrays.asList(excelColumn.groups()).contains(this.group)){
            this.annotationTreeMap.put(excelColumn, object);
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
            throw new InitialException("导出group不存在，请检查配置");
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
        WorkbookTypeEnum workbookTypeEnum = excelTable.workbookType();
        switch (workbookTypeEnum){
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

    public PagingHandler getPagingHandler() {
        return pagingHandler;
    }

    public void setPagingHandler(PagingHandler pagingHandler) {
        this.pagingHandler = pagingHandler;
    }

    public Interceptor getBeanIntercepter() {
        return beanIntercepter;
    }

    public void setBeanIntercepter(Interceptor beanIntercepter) {
        this.beanIntercepter = beanIntercepter;
    }

    public Map<CellStyleType, CellStyle> getCellStyleMap() {
        return cellStyleMap;
    }

    public void setCellStyleMap(Map<CellStyleType, CellStyle> cellStyleMap) {
        this.cellStyleMap = cellStyleMap;
    }
}
