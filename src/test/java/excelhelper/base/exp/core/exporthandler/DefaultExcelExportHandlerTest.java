package excelhelper.base.exp.core.exporthandler;

import excelhelper.base.configuration.ConfigurationBuilder;
import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.exp.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class DefaultExcelExportHandlerTest {

    List<User> users;

    @Before
    public void initUser(){
        this.users = new ArrayList<>();
        long start = System.currentTimeMillis();
        Random random = new Random();
//        System.out.printf("开始时间:%d %s", start, "\n");
        for (int i = 0; i < 10000; i++){
            User user = new User("张让" + i, random.nextInt(10));
            users.add(user);
        }
    }

    @Test
    public void writeList() {
        long start = System.currentTimeMillis();
        ExcelConfiguration excelConfiguration = ConfigurationBuilder.build(User.class);
        DefaultExcelExportHandler<User> handler = new DefaultExcelExportHandler<>(excelConfiguration);
        handler.writeList(users);
        handler.writeFile("D://temp//DefaultExcelExportHandler.xlsx");
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");

    }
}