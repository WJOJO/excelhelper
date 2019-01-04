package excelhelper.base.export;

import java.io.OutputStream;

/**
 * @author Javon Wang
 * @description 导出顶层接口
 * @create 2018-12-10 11:01
 */
public interface ExportHandler {
    
    /**
     * @Description: 写入文件
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 17:53
     */
    public void writeFile(String path);

    /**
     * @Description: 写入流
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 17:54
     */
    public void writeStream(OutputStream os);

}
