package excelhelper.base.configuration;

/**
 * @author Javon Wang
 * @description 创建configuration对象
 * @create 2019-01-23 10:55
 */
public class ConfigurationBuilder {
    public static ExcelConfiguration build(Class<?> cls){
        return build(cls, -1);
    }

    public static ExcelConfiguration build(Class<?> cls, int group){
        return new ExcelConfiguration(cls, group);
    }
}
