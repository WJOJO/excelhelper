package excelhelper.util.date;

import excelhelper.enums.DefaultDatePatternEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Javon Wang
 * @description 日期格式转换工厂
 * @create 2018-12-06 10:58
 */
public class DateFormatFactory {

    private static ThreadLocal<DateFormat> dateFormatThreadLocal;


    /**
     * @Description: 根据日期格式创建dateFormat
     * @Param: 日期格式
     * @return: sdf
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 11:11
     */
    public static DateFormat createDateFormat(final String pattern){
        if (dateFormatThreadLocal == null){
            synchronized (dateFormatThreadLocal){
                if (dateFormatThreadLocal == null){
                    dateFormatThreadLocal = new ThreadLocal<DateFormat>(){
                        @Override
                        protected DateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                }
            }
        }
        return dateFormatThreadLocal.get();
    }

}
