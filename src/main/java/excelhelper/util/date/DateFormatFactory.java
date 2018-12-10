package excelhelper.util.date;

import excelhelper.enums.DefaultDatePattern;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Javon Wang
 * @description 日期格式转换工厂
 * @create 2018-12-06 10:58
 */
public class DateFormatFactory {

    /**
     * @Description: 存放所有的日期格式转换器
     */
    public static final Map<String, SimpleDateFormat> dateFormaters
            = new ConcurrentHashMap<String, SimpleDateFormat>();

    /**
     * @Description: 根据日期格式创建dateFormat
     * @Param: 日期格式
     * @return: sdf
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 11:11
     */
    public static SimpleDateFormat createDateFormat(String pattern){

        try {
            if(dateFormaters.size() == 0){
                dateFormaters.put(DefaultDatePattern.DEFAULT_DATE_PATTERN.getPattern(),
                        new SimpleDateFormat(DefaultDatePattern.DEFAULT_DATE_PATTERN.getPattern()));
            }
            if(dateFormaters.get(pattern) == null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                dateFormaters.put(pattern, simpleDateFormat);
                return simpleDateFormat;
            }else{
                return dateFormaters.get(pattern);
            }
        } catch (Exception e) {
            //异常返回默认日期格式 dateformat
            return dateFormaters.get(DefaultDatePattern.DEFAULT_DATE_PATTERN.getPattern());
        }
    }
}
