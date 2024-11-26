package org.microservice.screen.models.seats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.microservice.screen.entities.Screen;

@Getter
@Setter
public class SeatUpdateReq {
    
    private Long id;
    
    @NotNull
    private Long screenId;
    
    @NotNull
    private Integer columnNr;
    
    @NotBlank
    private Character rowNr;
    
    @NotBlank
    private String type;
    
}
