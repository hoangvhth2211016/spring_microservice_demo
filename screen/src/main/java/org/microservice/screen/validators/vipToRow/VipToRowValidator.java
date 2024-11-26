package org.microservice.screen.validators.vipToRow;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.microservice.screen.models.screens.ScreenCreateReq;

public class VipToRowValidator implements ConstraintValidator<VipToRow, ScreenCreateReq> {
    
    @Override
    public boolean isValid(ScreenCreateReq req, ConstraintValidatorContext context) {
        if (req.getVipToRow() == null || req.getNumbersOfRow() == null) {
            return true;
        }
        
        char vipToRow = req.getVipToRow();
        int maxRow = req.getNumbersOfRow() - 1;
        char maxRowChar = (char) ('A' + maxRow);
        
        return vipToRow <= maxRowChar;
    }
}