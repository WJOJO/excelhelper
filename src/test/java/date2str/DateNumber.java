package date2str;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Javon Wang
 * @description 取出日期中的数字
 * @create 2019-01-08 10:59
 */
public class DateNumber {

    public void getName(){}

    public static void main(String[] args) {
//        String dateStr = "2018年1月19号12:20:20";
////        Pattern pattern = Pattern.compile("\\d+");
////        Matcher matcher = pattern.matcher(dateStr);
////        while (matcher.find()){
////            String s = matcher.group();
////            System.out.println(s);
////        }

        Method[] declaredMethods = DateNumber.class.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(method -> {
            System.out.print(method.getReturnType().getSimpleName() + "  ");
            System.out.println(method.getName());
        });
    }
}
