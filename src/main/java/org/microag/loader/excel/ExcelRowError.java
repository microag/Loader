package org.microag.loader.excel;

import lombok.Getter;

@Getter
public class ExcelRowError extends ExcelError{
    protected int row;

    public ExcelRowError(String sheetName, int sheet, String message, int row) {
        super(sheetName, sheet, message);
        this.row = row;
    }

    public String getLog() {
        return super.getLog()+" -> row:"+row;
    }

}
