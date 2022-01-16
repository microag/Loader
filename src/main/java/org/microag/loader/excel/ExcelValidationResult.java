package org.microag.loader.excel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExcelValidationResult {

    private List<ExcelError> errors = new ArrayList<>();

    public void setErrors(List<ExcelError> errors) {
        this.errors = errors;
    }

}
