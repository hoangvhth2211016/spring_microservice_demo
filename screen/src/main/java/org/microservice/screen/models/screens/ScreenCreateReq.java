package org.microservice.screen.models.screens;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.microservice.screen.validators.numbersOfCoupleSeat.NumbersOfCoupleSeat;
import org.microservice.screen.validators.vipFromRow.VipFromRow;
import org.microservice.screen.validators.vipToRow.VipToRow;

@Getter
@Setter
@NumbersOfCoupleSeat
@VipToRow
public class ScreenCreateReq {
    
    @NotNull
    @Min(0)
    private Integer number;
    
    @NotNull
    @Min(4)
    @Max(24)
    private Integer numbersOfRow;
    
    @NotNull
    @Min(6)
    @Max(15)
    private Integer numbersOfColumn;
    
    @NotNull
    @Min(1)
    private Integer vipFromColumn;
    
    @NotNull
    @VipFromRow
    private Character vipFromRow;
    
    @NotNull
    private Character vipToRow;
    
    @NotNull
    private Integer numberOfCouple;
    
}
