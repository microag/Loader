package org.microag.loader.excel;

import lombok.Getter;

@Getter
public class ExcelColError extends ExcelRowError {
    private int col;
    private String value;

    public ExcelColError(String sheetName, int sheet, String message, int row, int col, String value) {
        super(sheetName, sheet, message, row);
        this.col = col;
        this.value = value;
    }

    public String getLog() {
        return super.getLog() + " -> col:" + col + " -> Value:'" + value + "'";
    }

}
