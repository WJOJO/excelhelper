package excelhelper.base.imp;

import java.io.File;
import java.io.InputStream;

/**
 * @author Javon Wang
 * @description 导入接口
 * @create 2019-01-09 17:33
 */
public interface ImpHandler {


    void readFile(String filePath);

    void readInputStream(InputStream inputStream);

}
