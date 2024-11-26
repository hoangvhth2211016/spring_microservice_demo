package org.microservice.screen.controllers;

import jakarta.validation.Valid;
import org.microservice.screen.entities.Screen;
import org.microservice.screen.entities.Seat;
import org.microservice.screen.mappers.ScreenMapper;
import org.microservice.screen.models.pages.PageRes;
import org.microservice.screen.models.screens.ScreenCreateReq;
import org.microservice.screen.models.screens.ScreenRes;
import org.microservice.screen.models.screens.ScreenUpdateReq;
import org.microservice.screen.models.seats.SeatUpdateReq;
import org.microservice.screen.services.screens.ScreenService;
import org.microservice.screen.services.seats.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;
    
    @Autowired
    private SeatService seatService;
    
    @Autowired
    private ScreenMapper screenMapper;
    
    @GetMapping("public")
    public PageRes<Screen> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<>(screenService.getAll(pageable));
    }
    
    @GetMapping("public/{id}")
    public ScreenRes getById(@PathVariable Long id){
        return screenMapper.toDto(screenService.getById(id));
    }
    
    @GetMapping("public/{screenId}/seats")
    public List<Seat> getScreenSeats(@PathVariable Long screenId){
        return seatService.getAllByScreenId(screenId);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Screen createScreenAndSeats(@RequestBody @Valid ScreenCreateReq req){
        return screenService.createScreenAndSeats(req);
    }
    
    @PutMapping("{id}")
    public Screen updateScreen(@PathVariable Long id, @RequestBody @Valid ScreenUpdateReq req){
        return screenService.updateScreen(id, req);
    }
    
    @PutMapping("{screenId}/seats/{seatId}")
    public Seat updateSeat(@PathVariable(value = "screenId") Long screenId, @PathVariable(value = "seatId") Long seatId, @RequestBody @Valid SeatUpdateReq req){
        return seatService.updateSeat(screenId, seatId, req);
    }

}
