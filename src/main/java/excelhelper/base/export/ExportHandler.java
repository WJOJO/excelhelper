package excelhelper.base.export;

import java.io.OutputStream;

/**
 * @author Javon Wang
 * @description 导出顶层接口
 * @create 2018-12-10 11:01
 */
public interface ExportHandler {

    public void writeFile(String path);

    public void writeStream(OutputStream os);

}
