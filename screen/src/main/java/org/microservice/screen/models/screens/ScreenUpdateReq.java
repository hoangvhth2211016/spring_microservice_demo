package org.microservice.screen.models.screens;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.microservice.screen.models.seats.SeatUpdateReq;
import org.microservice.screen.validators.vipFromRow.VipFromRow;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScreenUpdateReq {
    
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
    
    private List<SeatUpdateReq> seatUpdateReqs = new ArrayList<>();
    
    
}
