package org.microservice.movie.services.movies;

import jakarta.transaction.Transactional;
import org.microservice.movie.entities.Movie;
import org.microservice.movie.exceptions.NotFoundException;
import org.microservice.movie.mappers.MovieMapper;
import org.microservice.movie.models.movies.*;
import org.microservice.movie.repositories.MovieRepo;
import org.microservice.movie.services.omdbs.OmdbService;
import org.microservice.movie.specifications.MovieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    
    @Autowired
    private MovieRepo movieRepo;
    
    @Autowired
    private OmdbService omdbService;
    
    @Autowired
    private MovieMapper movieMapper;
    
    @Override
    public Page<Movie> getAll(Pageable pageable, MovieStatus status, MovieSchedule schedule) {
        Specification<Movie> spec = Specification.where(null);
        if (status != null) {
            spec = spec.and(MovieSpecification.movieStatusEquals(status));
        }
        if (schedule != null) {
            spec = spec.and(MovieSpecification.movieSchedule(schedule));
        }
        return movieRepo.findAll(spec, pageable);
    }
    
    @Override
    public Movie getById(Long id) {
        return movieRepo.findById(id).orElseThrow(()-> new NotFoundException("Movie Not Found"));
    }
    
    @Override
    public Movie create(MovieCreateReq req) {
        MovieOmdbRes omdbRes = omdbService.getMovieFromOmdb(req.getTitle()).block();
        Movie newMovie = movieMapper.toEntity(omdbRes);
        newMovie.setReleased(req.getReleased());
        newMovie.setStatus(req.getStatus() != null ? req.getStatus().name() : MovieStatus.INACTIVE.name());
        if(req.getTrailer() != null ) newMovie.setTrailer(req.getTrailer());
        return movieRepo.save(newMovie);
    }
    
    @Override
    public Movie updateInternalInformation(Long id, MovieUpdateReq req) {
        Movie movie = getById(id);
        movie.setStatus(req.getStatus().name());
        movie.setTrailer(req.getTrailer());
        return movieRepo.save(movie);
    }
    
    @Override
    public Movie getByIdAvailable(Long id) {
        return movieRepo.findByIdAndStatus(id, MovieStatus.ACTIVE.name()).orElseThrow(()-> new NotFoundException("Movie Not Found"));
    }
    
    @Override
    public Movie getByIdAvailableFrom(Long id, LocalDateTime fromTime) {
        return movieRepo.findByIdAndStatusAndReleasedLessThanEqual(id,MovieStatus.ACTIVE.name(), LocalDate.from(fromTime)).orElseThrow(()-> new NotFoundException("Movie Not Found"));
    }
}
