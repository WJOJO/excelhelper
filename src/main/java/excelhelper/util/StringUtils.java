package excelhelper.util;

/**
 * @author Javon Wang
 * @description 字符串处理工具
 * @create 2019-01-28 10:57
 */
public class StringUtils {

    public static boolean isEmpty(String str){
        return str == null | "".equals(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
