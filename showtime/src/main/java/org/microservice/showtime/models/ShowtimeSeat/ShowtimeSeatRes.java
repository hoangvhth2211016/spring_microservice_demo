package org.microservice.showtime.models.ShowtimeSeat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class ShowtimeSeatRes {

    private Long id;

    private Long seatId;

    private String seatNumber;

    private String seatType;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
}
