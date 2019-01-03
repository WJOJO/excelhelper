package excelhelper.base.export;

import java.util.List;

/**
 * @author Javon Wang
 * @description 导出BeanList接口
 * @create 2018-12-06 11:30
 */
public interface ListExportHandler extends ExportHandler {

    /**
     * @Description: list转换
     * @Param: beanList
     * @return: beanList
     * @Author: Javon Wang
     * @Date: 2018/12/10
     * @Time: 11:04
     */
    <T> List<T> beanListConvert(List<T> beanList);



}