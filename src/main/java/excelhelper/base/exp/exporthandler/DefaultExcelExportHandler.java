package excelhelper.base.exp.exporthandler;

import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.exp.ListExportHandler;
import excelhelper.base.exp.writer.BaseWriter;
import excelhelper.base.exp.writer.BeanSheetWriter;
import excelhelper.base.exp.paging.PagingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Javon Wang
 * @description 默认实现的excel导出
 * @create 2019-01-22 14:40
 */
@Slf4j
public class DefaultExcelExportHandler<T> implements ListExportHandler<T> {

    private ConcurrentHashMap<String, BaseWriter> sheetWriterMap = new ConcurrentHashMap<>();

    private ExcelConfiguration configuration;


    public DefaultExcelExportHandler(ExcelConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }

    @Override
    public void writeList(List<T> beanList) {
        beanList = listSorted(beanList);
        PagingHandler pagingHandler = configuration.getPagingHandler();
        Map<String, List<T>> map = pagingHandler.paging(beanList);

        Iterator<Map.Entry<String, List<T>>> iterator = map.entrySet().iterator();
        BaseWriter<T> beanSheetWriter;
        while(iterator.hasNext()){
            Map.Entry<String, List<T>> stringListEntry = iterator.next();
            String key = stringListEntry.getKey();
            List<T> value = stringListEntry.getValue();

            if (null == sheetWriterMap.get(key)) {
                beanSheetWriter = new BeanSheetWriter<>(key, configuration);
                sheetWriterMap.put(key, beanSheetWriter);
            } else {
                beanSheetWriter = sheetWriterMap.get(key);
            }
            beanSheetWriter.writeData(value);
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