package excelhelper.base;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.OutputStream;

/**
 * @author Javon Wang
 * @description 抽象的excel工作空间
 * @create 2018-12-06 13:21
 */
public class AbstractExcelWorkSpace implements WorkSpace{

    /**
     * poi excel工作簿
     */
    protected Workbook workbook;


    public void write2File(String filePath) {

    }

    public void write2Stream(OutputStream outputStream) {

    }
}
