package excelhelper.base.constants;

/**
 * @author Javon Wang
 * @description 导出类型 Excel版本
 * @create 2018-12-06 15:49
 */
public enum WorkbookType {
    /**
     * excel <2007
     */
    HSSF,
    /**
     * excel >2007
     */
    XSSF,
    /**
     * 缓存式
     */
    SXSSF;
}
