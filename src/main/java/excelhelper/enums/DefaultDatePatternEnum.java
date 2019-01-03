package excelhelper.enums;

/**
 * @author Javon Wang
 * @description 日期格式泛型
 * @create 2018-12-06 10:41
 */
public enum DefaultDatePatternEnum {

    DEFAULT_DATE_PATTERN("YYYY-MM-DD HH:mm:ss");

    private String pattern;

    DefaultDatePatternEnum(String pattern){
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
