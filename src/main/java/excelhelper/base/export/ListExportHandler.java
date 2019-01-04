package excelhelper.base.export;

import java.util.List;

/**
 * @author Javon Wang
 * @description 导出BeanList接口
 * @create 2018-12-06 11:30
 */
public interface ListExportHandler<T> extends ExportHandler {

    /**
     * @Description: list转换
     * @Param: beanList
     * @return: beanList
     * @Author: Javon Wang
     * @Date: 2018/12/10
     * @Time: 11:04
     */
    //TODO list排序暂时未实现
    List<T> listSorted(List<T> beanList);
    /**
     * @Description: 写入文件
     * @Author: Javon Wang
     * @Date: 2019/1/4
     * @Time: 16:33
     */
    void export(List<T> beanList);


}