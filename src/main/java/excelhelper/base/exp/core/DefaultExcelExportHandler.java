package excelhelper.base.exp.core;

import excelhelper.base.config.ExcelConfiguration;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Javon Wang
 * @description 默认实现的excel导出
 * @create 2019-01-22 14:40
 */
@Slf4j
public class DefaultExcelExportHandler<T> implements ListExportHandler<T> {

    private ConcurrentHashMap<String, BaseSheetWriter> sheetWriterMap;

    private ExcelConfiguration configuration;

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }

    @Override
    public void writeList(List<T> beanList) {
        beanList = listSorted(beanList);
        String pagingColumn = configuration.getExcelTable().pagingColumn()[configuration.getGroup()-1];
        try {
            Field field = configuration.getCls().getDeclaredField(pagingColumn);
            Consumer<T> consumer = (t) -> {
                String sheetName = pagingColumn + "-" + ReflectUtil.getValueFromField(field, t);
                BaseSheetWriter sheetWriter = sheetWriterMap.get(sheetName);
                if(sheetWriter == null){
                    sheetWriter = new BeanSheetWriter(sheetName, configuration);
                    sheetWriterMap.put(sheetName, sheetWriter);
                }
                sheetWriter.writeRow(t);
            };
            beanList.forEach(consumer);

        } catch (NoSuchFieldException e) {
            log.error("找不到用来分页的属性");
        }
    }

    @Override
    public void writeFile(String path) {
        try {
            FileOutputStream stream = new FileOutputStream(path);
            writeStream(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeStream(OutputStream os) {
        try {
            configuration.getWorkbook().write(os);
            configuration.getWorkbook().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
