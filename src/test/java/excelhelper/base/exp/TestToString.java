package excelhelper.base.exp;

import org.junit.Test;

import java.lang.reflect.Method;

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

}
