package org.microag.loader;

import org.microag.loader.data.Record;
import org.microag.loader.data.RecordFinder;
import org.microag.loader.excel.ExcelError;
import org.microag.loader.excel.ExcelLoader;
import org.microag.loader.excel.ExcelValidationResult;
import org.microag.loader.exception.FileException;

import java.io.File;

public class Test {
    public void checkMethod() {
        System.out.println("MicroAG Loader module (Ver 0.6)");

        ExcelLoader loader = new ExcelLoader("col1", "col2");
        try {
            loader.loadFile(new File("D:\\LoaderTestFile.xls"));
            System.out.println("Row\t\tColumn 1\t\tColumn 2");
            ExcelValidationResult result = loader.parseFile(new RecordFinder() {
                @Override
                public void read(Record record) {
                    if (record.get("col1").equals("error"))
                        throw new RuntimeException("This row contain Error!");
                    System.out.println(record.getIndex() + "\t\t" + record.get("col1") + "\t\t" + record.get("col2"));
                }
            });
            for (ExcelError err : result.getErrors()) {
                System.out.println(err.getLog());
            }

        } catch (FileException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Test t = new Test();
        t.checkMethod();
    }
}
