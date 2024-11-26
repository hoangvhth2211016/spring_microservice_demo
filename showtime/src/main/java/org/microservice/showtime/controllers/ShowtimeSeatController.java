package org.microservice.showtime.controllers;

import jakarta.validation.Valid;
import org.microservice.showtime.mappers.ShowtimeSeatMapper;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatCreateReq;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatRes;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatUpdateReq;
import org.microservice.showtime.services.showtimeSeats.ShowtimeSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("showtime_seats")
public class ShowtimeSeatController {
    
    @Autowired
    private ShowtimeSeatService showtimeSeatService;
    
    @Autowired
    private ShowtimeSeatMapper showtimeSeatMapper;
    
    @GetMapping("public")
    public List<ShowtimeSeatRes> getSeatInList(@RequestParam(name = "ids") Set<Long> showtimeSeatIds){
        return showtimeSeatMapper.toDtoList(showtimeSeatService.getSeatInList(showtimeSeatIds)).stream().toList();
    }
    
    @GetMapping("public/{id}")
    public ShowtimeSeatRes getById(@PathVariable Long id){
        return showtimeSeatMapper.toDto(showtimeSeatService.getById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ShowtimeSeatRes create(@RequestBody @Valid ShowtimeSeatCreateReq req){
        return showtimeSeatMapper.toDto(showtimeSeatService.create(req));
    }
    
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ShowtimeSeatRes update(@PathVariable Long id, @RequestBody @Valid ShowtimeSeatUpdateReq req){
        return showtimeSeatMapper.toDto(showtimeSeatService.update(id, req));
    }
    
}
