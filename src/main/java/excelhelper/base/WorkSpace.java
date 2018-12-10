package excelhelper.base;

import java.io.OutputStream;

/**
 * @author Javon Wang
 * @description 工作空间
 * @create 2018-12-06 11:40
 */
public interface WorkSpace {


    public void write2File(String filePath);


    public void write2Stream(OutputStream outputStream);

}
