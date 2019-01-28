package excelhelper.base.exp;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author Javon Wang
 * @description 测试user的tostring方法
 * @create 2019-01-24 15:36
 */
public class TestToString {

    @Test
    public void test2String() throws Exception{
//        Class<User> userClass = User.class;
//        Method toString = userClass.getMethod("toString");
//        Class<?> aClass = toString.getDeclaringClass();
//        System.out.println(aClass.getSimpleName());

        String file = this.getClass().getClassLoader().getResource("").getFile();
        System.out.println(file);

    }


    @Test
    public void testArraysAsList(){
        int[] array = {1,2,3,4,5};
        List<int[]> ints = Arrays.asList(array);

        Integer[] array1 = {1,2,3,4,5};
        List<Integer> integers = Arrays.asList(array1);
    }

}
