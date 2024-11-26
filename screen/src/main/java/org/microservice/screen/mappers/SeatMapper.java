package org.microservice.screen.mappers;

import org.mapstruct.*;
import org.microservice.screen.entities.Seat;
import org.microservice.screen.models.seats.SeatRes;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeatMapper {
    
    Seat toEntity(SeatRes seatRes);
    
    SeatRes toDto(Seat seat);
    
    Collection<SeatRes> toDtos(Collection<Seat> seats);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Seat partialUpdate(SeatRes seatRes, @MappingTarget Seat seat);
}