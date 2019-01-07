package excelhelper.base.export.excel;


import excelhelper.base.export.SheetWriter;
import excelhelper.base.intercepter.Convertor;
import excelhelper.base.intercepter.DefaultConvertor;
import excelhelper.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Javon Wang
 * @description 多个sheet导出excel
 * @create 2019-01-04 10:36
 */
@Slf4j
public class DefaultMutiSheetExportHandler<T> extends List2ExcelExportHandler<T> {

    private Map<String, SheetWriter<T>> sheetWriterMap = new HashMap<>();

    private Convertor convertor;

    public DefaultMutiSheetExportHandler(Class<T> cls){
        this(cls, new DefaultConvertor());
    }


    public DefaultMutiSheetExportHandler(Class<T> cls, Convertor convertor){
        super();
        super.init(cls);
        this.convertor = convertor;
    }


    @Override
    public void export(List<T> beanList){
        beanList = listSorted(beanList);
        String pagingColumn = getExcelTable().pagingColumn()[0];
        try {
            Field field = getBeanClass().getDeclaredField(pagingColumn);
            Consumer<T> consumer = (t) -> {
                String sheetName = pagingColumn + "-" + ReflectUtil.getValueFromField(field, t);
                SheetWriter<T> sheetWriter = sheetWriterMap.get(sheetName);
                if(sheetWriter == null){
                    sheetWriter = prepareSheetWriter(convertor, sheetName);
                    sheetWriterMap.put(sheetName, sheetWriter);
                }
                sheetWriter.writeRow(t, getCellStyle());
            };
            beanList.forEach(consumer);

        } catch (NoSuchFieldException e) {
            log.error("找不到用来分页的属性");
        }
    }

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }
}
