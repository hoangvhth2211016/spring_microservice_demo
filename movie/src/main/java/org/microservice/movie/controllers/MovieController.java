package org.microservice.movie.controllers;

import jakarta.validation.Valid;
import org.microservice.movie.entities.Movie;
import org.microservice.movie.models.movies.*;
import org.microservice.movie.models.pages.PageRes;
import org.microservice.movie.services.movies.MovieService;
import org.microservice.movie.services.omdbs.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("movies")
public class MovieController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private OmdbService omdbService;
    
    @GetMapping("public")
    public PageRes<Movie> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "status", required = false) MovieStatus status,
            @RequestParam(name = "schedule", required = false) MovieSchedule schedule
            ){
        return new PageRes<Movie>(movieService.getAll(pageable, status, schedule));
    }
    
    @GetMapping("public/{id}")
    public Movie getById(@PathVariable Long id){
        return movieService.getById(id);
    }
    
    @GetMapping("public/{id}/available")
    public MovieApiRes getByIdAvailable(@PathVariable Long id, @RequestParam(name = "from") LocalDateTime from){
        Movie movie = movieService.getByIdAvailableFrom(id, from);
        MovieApiRes res = new MovieApiRes();
        res.setId(movie.getId());
        res.setTitle(movie.getTitle());
        res.setRuntime(movie.getRuntime());
        return res;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Movie create(@RequestBody @Valid MovieCreateReq req){
        return movieService.create(req);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody @Valid MovieUpdateReq req){
        return movieService.updateInternalInformation(id, req);
    }
    
    @GetMapping("public/omdb")
    public MovieOmdbRes getMovieFromOmdb(@RequestParam String title) {
        return omdbService.getMovieFromOmdb(title).block();
    }
    
}
