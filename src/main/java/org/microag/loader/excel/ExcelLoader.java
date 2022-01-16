package org.microag.loader.excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.microag.loader.data.Record;
import org.microag.loader.data.RecordFinder;
import org.microag.loader.exception.FileException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

/*
This Excel loader just read first sheet of your excel file
 */
public class ExcelLoader {
    private String[] columnNames;
    private Hashtable<String, Integer> columnIndex = new Hashtable<>();
    private int sh_index = 0;
    private int headerRow = 0;
    private Workbook workbook = null;

    public ExcelLoader(String... columnNames) {
        this.columnNames = columnNames;
    }

    public void loadFile(File excelFilePath) throws FileException {

        try {
            workbook = Workbook.getWorkbook(excelFilePath);
            fillColumnIndex(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("Read file error, File not found.");
        } catch (BiffException e) {
            e.printStackTrace();
            throw new FileException("Read excel file error.");
        }
    }

    public ExcelValidationResult parseFile(RecordFinder finder) {
        if (workbook == null) throw new RuntimeException("File not loaded, Please call 'loadFile(File)' method.");

        ExcelValidationResult result = new ExcelValidationResult();
        List<ExcelError> errors = result.getErrors();

        Sheet sheet = workbook.getSheet(sh_index);

        for (int i = headerRow + 1; i < sheet.getRows(); i++) {
            Record rec = new Record("Row:" + i, i);
            for (String columnName : columnIndex.keySet()) {
                int colIndex = getColumnIndex(columnName);

                String k = sheet.getCell(colIndex, i).getContents();
                if (StringUtils.isNotEmpty(k)) {
                    rec.fillData(columnName, k.trim());
//                    String input = "Thu Jun 18 20:56:02 EDT 2009";
//                    SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
//                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                    try {
//                        LocalDateTime.parse(k, formatter);
//                    } catch (Exception e) {
//                        errors.add(new ExcelError(sheet.getName(), sh_index, i, 0, k, "Incompatible date format"));
//                    }
                }
            }
            try {
                finder.read(rec);
            } catch (Exception e) {
                errors.add(new ExcelRowError(sheet.getName(), sh_index, e.getMessage(), i));
            }
        }

        workbook.close();
        return result;
    }

    private void fillColumnIndex(Workbook workbook) {
        Sheet sheet = workbook.getSheet(sh_index);
        for (int c = 0; c < sheet.getColumns(); c++) {
            String k = sheet.getCell(c, headerRow).getContents();
            if (StringUtils.isNotEmpty(k))
                columnIndex.put(k.trim(), c);
        }
    }

    private int getColumnIndex(String columnName) {
        if (columnIndex.containsKey(columnName)) {
            return columnIndex.get(columnName);
        } else
            return -1;  //Means column not found
    }
}
