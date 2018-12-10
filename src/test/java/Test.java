import excelhelper.base.export.BaseList2ExcelExporthandler;
import excelhelper.base.export.BaseListExportHandler;
import excelhelper.base.export.factory.SSEExportHandlerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javon Wang
 * @description 测试
 * @create 2018-12-06 17:49
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        List<User> users = new ArrayList<>();
        User user = new User("王建伟", 24);
        long start = System.currentTimeMillis();
        System.out.printf("开始时间:%d %s", start, "\n");
        for (int i = 0; i < 10000; i++){
            users.add((User)user.clone());
        }


        BaseList2ExcelExporthandler handler =
                new SSEExportHandlerFactory().createHandler();
        handler.init(User.class);
        handler.writeToWorkBook(users);

        handler.writeFile("D://test.xlsx");
        long end = System.currentTimeMillis();
        System.out.printf("结束时间:%d %s", end, "\n");
        System.out.println("耗时:"+ (end - start));
    }
}
