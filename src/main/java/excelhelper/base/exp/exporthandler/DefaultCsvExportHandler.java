package excelhelper.base.exp.exporthandler;

import excelhelper.annotations.MethodIngore;
import excelhelper.base.configuration.ExcelConfiguration;
import excelhelper.base.exception.InitialException;
import excelhelper.base.exp.ListExportHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Javon Wang
 * @description 默认的CSV导出
 * @create 2019-01-24 15:48
 */
@Slf4j
public class DefaultCsvExportHandler<T> implements ListExportHandler<T> {

    ExcelConfiguration configuration;

    BufferedWriter bufferedWriter;

    public DefaultCsvExportHandler(ExcelConfiguration configuration) {
        String tempPath = null;
         try {
            tempPath = this.getClass().getClassLoader().getResource("").getFile()
                    + System.currentTimeMillis() +  "_export.tmp";
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempPath)));
            this.configuration = configuration;
        } catch (FileNotFoundException e) {
            throw new InitialException("创建临时文件失败" + tempPath + "失败", e);
        }
    }

    @Override
    public List<T> listSorted(List<T> beanList) {
        return beanList;
    }

    @Override
    public void writeList(List<T> beanList) {
        writeColumnName();
        writeData(beanList);
    }

    private void writeColumnName(){
        StringBuilder sb = new StringBuilder();
        List<String> columnNameList = configuration.getColumnNameList();
        for (String columnName :
                columnNameList) {
            sb.append(columnName);
            sb.append(",");
        }
        try {
            bufferedWriter.write(sb.substring(0, sb.length() - 1));
            bufferedWriter.newLine();
        } catch (IOException e) {
            log.warn("初始化写入title失败", e);
        }
    }


    private void writeData(List<T> beanList){
        beanList = (List<T>)this.configuration.getDataHandler().handle(beanList);
        try {
            Method toString = this.configuration.getCls().getMethod("toString");
            if(toString.isAnnotationPresent(MethodIngore.class)
                    && ! toString.getAnnotation(MethodIngore.class).value()
                    && toString.getDeclaringClass() == this.configuration.getCls()){
                for (T bean :
                        beanList) {
                    bean = (T)this.configuration.getBeanIntercepter().translate(bean);
                    bufferedWriter.write((String) toString.invoke(bean));
                    bufferedWriter.newLine();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
