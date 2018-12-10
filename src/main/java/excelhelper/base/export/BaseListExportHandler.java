package excelhelper.base.export;

import org.apache.poi.ss.formula.functions.T;

import java.io.OutputStream;
import java.util.List;

/**
 * @author Javon Wang
 * @description 导出BeanList接口
 * @create 2018-12-06 11:30
 */
public interface BaseListExportHandler<T> extends BaseExportHandler{

    /**
     * @Description: list转换 可做翻译
     * @Param: beanList
     * @return: beanList
     * @Author: Javon Wang
     * @Date: 2018/12/10
     * @Time: 11:04
     */
    List<T> transformBeanList(List<T> beanList);



}