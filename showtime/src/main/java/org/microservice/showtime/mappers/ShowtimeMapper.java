package org.microservice.showtime.mappers;

import org.mapstruct.*;
import org.microservice.showtime.entities.Showtime;
import org.microservice.showtime.models.showtimes.ShowtimeDetailRes;
import org.microservice.showtime.models.showtimes.ShowtimeRes;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = ShowtimeSeatMapper.class )
public interface ShowtimeMapper {
    
    ShowtimeRes toRes(Showtime showtime);
    
    @Mapping(source = "showtimeSeats", target = "seats")
    ShowtimeDetailRes toDetailRes(Showtime showtime);
    
}