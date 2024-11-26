package org.microservice.showtime.controllers;


import jakarta.validation.Valid;
import org.microservice.showtime.entities.Showtime;
import org.microservice.showtime.mappers.ShowtimeMapper;
import org.microservice.showtime.mappers.ShowtimeSeatMapper;
import org.microservice.showtime.models.ShowtimeSeat.ShowtimeSeatRes;
import org.microservice.showtime.models.pages.PageRes;
import org.microservice.showtime.models.showtimes.ShowtimeCreateReq;
import org.microservice.showtime.models.showtimes.ShowtimeDetailRes;
import org.microservice.showtime.models.showtimes.ShowtimeRes;
import org.microservice.showtime.services.showtimes.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("showtimes")
public class ShowtimeController {
    
    @Autowired
    private ShowtimeService showtimeService;
    
    @Autowired
    private ShowtimeMapper showtimeMapper;

    @GetMapping("public")
    public PageRes<ShowtimeRes> getAll(
            @PageableDefault(page = 0, size = 20, sort = "fromTime", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "movie_id", required = false) Long movieId,
            @RequestParam(name = "on", required = false) LocalDate on
            ){
        return new PageRes<ShowtimeRes>(showtimeService.getAll(pageable, on, movieId).map(showtimeMapper::toRes));
    }
    
    @GetMapping("public/{id}")
    public Showtime getById(@PathVariable Long id){
        return showtimeService.getById(id);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Showtime create(@RequestBody @Valid ShowtimeCreateReq req){
        return showtimeService.create(req);
    }
    
}
