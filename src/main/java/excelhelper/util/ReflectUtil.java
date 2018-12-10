package excelhelper.util;

import excelhelper.annotations.DatePattern;
import excelhelper.annotations.ExcelColumn;
import excelhelper.enums.DefaultDatePattern;
import excelhelper.util.date.DateFormatFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 反射获取属性值工具类
 * @create 2018-12-06 10:20
 */
public class ReflectUtil {

    private static final Logger logger = Logger.getLogger("ReflectUtil");

    /**
     * @Description: 根据属性获取对象值
     * @Param: 属性 对象
     * @return:  String类型的属性值
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 10:37
     */
    public static String getValueFromField(Field field, Object bean){
        //设置可访问
        if( ! field.isAccessible() ){
            field.setAccessible(true);
        }
        try {
            //String类型
            if(String.class == field.getType()){
                return (String) field.get(bean);
            }
            //number类型
            if(Number.class.isAssignableFrom(field.getType())){
                return String.valueOf(field.get(bean));
            }
            //日期格式
            if (Date.class.isAssignableFrom(field.getType())){
                String pattern = DefaultDatePattern.DEFAULT_DATE_PATTERN.getPattern();
                if(field.isAnnotationPresent(DatePattern.class)){
                    pattern = field.getAnnotation(DatePattern.class).pattern();
                }
                String dateString = DateFormatFactory.createDateFormat(pattern).format((Date) field.get(bean));
                return dateString;
            }
            //其他类型暂不考虑
            return field.get(bean).toString();
        } catch (IllegalAccessException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return "";
    }

    /**
     * @Description: 根据get方法获取值
     * @Param: get方法  bean对象
     * @return:   String value
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 11:29
     */
    public static String getValueFromMethod(Method method, Object bean){
        if ( !method.isAccessible()){
            method.isAccessible();
        }
        try {
            //String类型
            if(String.class == method.getReturnType()){
                return (String) method.invoke(bean);
            }
            //number类型
            if (Number.class.isAssignableFrom(method.getReturnType())){
                return String.valueOf(method.invoke(bean));
            }
            //Date类型
            if(Date.class.isAssignableFrom(method.getReturnType())){
                String pattern = DefaultDatePattern.DEFAULT_DATE_PATTERN.getPattern();
                if(method.isAnnotationPresent(DatePattern.class)){
                    pattern = method.getAnnotation(DatePattern.class).pattern();
                }
                String dateString = DateFormatFactory.createDateFormat(pattern).format((Date) method.invoke(bean));
                return dateString;
            }
            return method.invoke(bean).toString();
        } catch (IllegalAccessException e) {
            logger.log(Level.WARNING, e.toString());
        } catch (InvocationTargetException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return "";
    }

    /**
     * @Description: 获取注解列表
     * @Param: 类对象
     * @return: Object[]{注解, field/method}
     * @Author: Javon Wang
     * @Date: 2018/12/6
     * @Time: 17:34
     */
    public static List<Object[]> getAnnotations(Class<?> cls){
        List<Object[]> annotationList = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        Arrays.stream(fields).filter(field -> {
            return field.isAnnotationPresent(ExcelColumn.class);
        }).forEach(field -> {
            annotationList.add(new Object[]{field.getAnnotation(ExcelColumn.class), field});
        });

        Method[] methods = cls.getDeclaredMethods();
        Arrays.stream(methods).filter(method -> {
            return method.isAnnotationPresent(ExcelColumn.class);
        }).forEach(method -> {
            annotationList.add(new Object[]{method.getAnnotation(ExcelColumn.class), method});
        });

        return  annotationList.stream().sorted(new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return ((ExcelColumn)o1[0]).sort() - ((ExcelColumn)o2[0]).sort();
            }
        }).collect(Collectors.toList());
    }

}
