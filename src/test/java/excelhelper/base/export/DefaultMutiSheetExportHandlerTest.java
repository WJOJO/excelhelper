package excelhelper.base.export;

import excelhelper.base.export.factory.MutiSheetExportFactory;
import excelhelper.base.export.factory.SSEExportHandlerFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultMutiSheetExportHandlerTest {

    @Test
    public void testmuti() {
        List<User> users = new ArrayList<>();
        long start = System.currentTimeMillis();
        Random random = new Random();
        System.out.printf("开始时间:%d %s", start, "\n");
        for (int i = 0; i < 10000; i++){
            User user = new User("张让" + i, random.nextInt(10));
            users.add(user);
        }


        ListExportHandler handler = new MutiSheetExportFactory().createHandler(User.class);

        handler.export(users);
        handler.writeFile("D://temp//testMutiSheet.xlsx");
        long end = System.currentTimeMillis();
        System.out.printf("结束时间:%d %s", end, "\n");
        System.out.println("耗时:"+ (end - start));

    }
}