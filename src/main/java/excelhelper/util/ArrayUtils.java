package excelhelper.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;

/**
 * @author Javon Wang
 * @description 数组方法
 * @create 2019-01-09 16:18
 */
public class ArrayUtils {

    /**
     * 判断数组是否包含元素
     * @param array
     * @param t
     * @return
     */
    public static <T>  boolean arrayContains(T[] array, T t){
        if(isEmpty(array)){
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == t || array[i].equals(t))
                return true;
        }
        return false;
    }

    /**
     * @Description: 数组是否为空或者空数组
     * @Author: Javon Wang
     * @Date: 2019/1/9
     * @Time: 16:20
     */
    public static <T>  boolean isEmpty(T[] array){
        return array == null || array.length == 0;
    }
}
