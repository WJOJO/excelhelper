package excelhelper.base.exp;

import excelhelper.annotations.ExcelColumn;
import excelhelper.annotations.ExcelTable;

/**
 * @author Javon Wang
 * @description 测试类
 * @create 2018-12-06 17:47
 */
@ExcelTable(enableTableName = true, tableName = "用户信息", sheetName = "用户的sheet", pagingColumn = {"age"})
public class User implements Cloneable{


    @ExcelColumn(title = "名字", sort = 1)
    private String name;

    @ExcelColumn(title = "年龄", sort = 2)
    private int age;

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
}
