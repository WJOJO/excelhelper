package excelhelper.util;

import excelhelper.annotations.DatePattern;
import excelhelper.annotations.ExcelColumn;
import excelhelper.enums.DefaultDatePatternEnum;
import excelhelper.util.date.DateFormatFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String datePattern = annotation.datePattern();
                String dateString = DateFormatFactory.createDateFormat(datePattern).format((Date) field.get(bean));
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
                ExcelColumn annotation = method.getAnnotation(ExcelColumn.class);
                String datePattern = annotation.datePattern();
                String dateString = DateFormatFactory.createDateFormat(datePattern).format((Date) method.invoke(bean));
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
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(ExcelColumn.class)){
                annotationList.add(new Object[]{fields[i].getAnnotation(ExcelColumn.class), fields[i]});
            }
        }

        Method[] methods = cls.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(ExcelColumn.class)){
                annotationList.add(new Object[]{methods[i].getAnnotation(ExcelColumn.class), methods[i]});
            }
        }

        Collections.sort(annotationList, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return ((ExcelColumn)o1[0]).sort() - ((ExcelColumn)o2[0]).sort();
            }
        });
        return annotationList;
    }

}
