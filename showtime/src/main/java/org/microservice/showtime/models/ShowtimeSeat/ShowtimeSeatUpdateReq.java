package org.microservice.showtime.models.ShowtimeSeat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowtimeSeatUpdateReq {
    @NotNull
    private SeatStatus status;
}
