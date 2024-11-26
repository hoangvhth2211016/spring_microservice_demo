package org.microservice.showtime.mappers;

import org.mapstruct.*;
import org.microservice.showtime.entities.ShowtimeSeat;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatRes;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShowtimeSeatMapper {
    ShowtimeSeat toEntity(ShowtimeSeatRes showtimeSeatRes);
    
    ShowtimeSeatRes toDto(ShowtimeSeat showtimeSeat);
    
    Collection<ShowtimeSeatRes> toDtoList(Collection<ShowtimeSeat> showtimeSeats);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShowtimeSeat partialUpdate(ShowtimeSeatRes showtimeSeatRes, @MappingTarget ShowtimeSeat showtimeSeat);
}