package org.microag.loader.excel;

import lombok.Getter;

@Getter
public class ExcelError {
    protected String sheetName;
    protected int sheet;
    protected String message;

    public ExcelError(String sheetName, int sheet, String message) {
        this.sheetName = sheetName;
        this.sheet = sheet;
        this.message = message;
    }

    public String getLog() {
        return "Sheet(" + sheet + "):" + sheetName + "->" + message;
    }
}
