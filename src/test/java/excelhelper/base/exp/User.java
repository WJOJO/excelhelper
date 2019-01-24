package excelhelper.base.exp;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;

/**
 * @author Javon Wang
 * @description 测试类
 * @create 2018-12-06 17:47
 */
@ExcelTable(enableTableName = true, tableName = "用户信息", sheetName = "用户的sheet", pagingColumn = "age")
public class User implements Cloneable{


    @ExcelColumn(title = "名字", sort = 1)
    private String name;

    @ExcelColumn(title = "年龄", sort = 2)
    private int age;
    @ExcelColumn(title = "年龄", sort = 3)
    private int age1;
    @ExcelColumn(title = "年龄", sort = 4)
    private int age2;
    @ExcelColumn(title = "年龄", sort = 5)
    private int age3;
    @ExcelColumn(title = "年龄", sort = 6)
    private int age4;
    @ExcelColumn(title = "年龄", sort = 7)
    private int age5;
    @ExcelColumn(title = "年龄", sort = 8)
    private int age6;
    @ExcelColumn(title = "年龄", sort = 9)
    private int age7;
    @ExcelColumn(title = "年龄", sort = 10)
    private int age8;
    @ExcelColumn(title = "年龄", sort = 11)
    private int age9;
    @ExcelColumn(title = "年龄", sort = 12)
    private int age10;
    @ExcelColumn(title = "年龄", sort = 13)
    private int age11;
    @ExcelColumn(title = "年龄", sort = 14)
    private int age12;
    @ExcelColumn(title = "年龄", sort = 15)
    private int age13;
    @ExcelColumn(title = "年龄", sort = 16)
    private int age14;
    @ExcelColumn(title = "年龄", sort = 17)
    private int age15;
    @ExcelColumn(title = "年龄", sort = 18)
    private int age16;
    @ExcelColumn(title = "年龄", sort = 19)
    private int age17;
    @ExcelColumn(title = "年龄", sort = 20)
    private int age18;
    @ExcelColumn(title = "年龄", sort = 21)
    private int age19;
    @ExcelColumn(title = "年龄", sort = 22)
    private int age20;
    @ExcelColumn(title = "年龄", sort = 23)
    private int age21;
    @ExcelColumn(title = "年龄", sort = 24)
    private int age22;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new User(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", age1=" + age1 +
                ", age2=" + age2 +
                ", age3=" + age3 +
                ", age4=" + age4 +
                ", age5=" + age5 +
                ", age6=" + age6 +
                ", age7=" + age7 +
                ", age8=" + age8 +
                ", age9=" + age9 +
                ", age10=" + age10 +
                ", age11=" + age11 +
                ", age12=" + age12 +
                ", age13=" + age13 +
                ", age14=" + age14 +
                ", age15=" + age15 +
                ", age16=" + age16 +
                ", age17=" + age17 +
                ", age18=" + age18 +
                ", age19=" + age19 +
                ", age20=" + age20 +
                ", age21=" + age21 +
                ", age22=" + age22 +
                '}';
    }
}
