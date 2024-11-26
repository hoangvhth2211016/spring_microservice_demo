package org.microservice.screen.mappers;

import org.mapstruct.*;
import org.microservice.screen.entities.Screen;
import org.microservice.screen.models.screens.ScreenRes;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = SeatMapper.class)
public interface ScreenMapper {
    
    Screen toEntity(ScreenRes screenRes);
    
    //@AfterMapping
    //default void linkSeats(@MappingTarget Screen screen) {
    //    screen.getSeats().forEach(seat -> seat.setScreen(screen));
    //}
    
    ScreenRes toDto(Screen screen);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Screen partialUpdate(ScreenRes screenRes, @MappingTarget Screen screen);
}