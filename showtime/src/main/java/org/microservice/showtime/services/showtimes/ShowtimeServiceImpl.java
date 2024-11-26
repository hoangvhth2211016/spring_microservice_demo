package org.microservice.showtime.services.showtimes;

import jakarta.transaction.Transactional;
import org.microservice.showtime.entities.Showtime;
import org.microservice.showtime.entities.ShowtimeSeat;
import org.microservice.showtime.exceptions.BadRequestException;
import org.microservice.showtime.exceptions.NotFoundException;
import org.microservice.showtime.models.ShowtimeSeat.SeatStatus;
import org.microservice.showtime.models.movies.MovieRes;
import org.microservice.showtime.models.pages.PageRes;
import org.microservice.showtime.models.screens.ScreenRes;
import org.microservice.showtime.models.screens.SeatRes;
import org.microservice.showtime.models.showtimes.ShowtimeCreateReq;
import org.microservice.showtime.models.showtimes.ShowtimeDetailRes;
import org.microservice.showtime.repositories.ShowtimeRepo;
import org.microservice.showtime.repositories.ShowtimeSeatRepo;
import org.microservice.showtime.services.movieApis.MovieApiService;
import org.microservice.showtime.services.screenApis.ScreenApiService;
import org.microservice.showtime.specifications.ShowtimeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ShowtimeServiceImpl implements ShowtimeService {
    
    @Autowired
    private ShowtimeRepo showtimeRepo;
    
    @Autowired
    private ScreenApiService screenApiService;
    
    @Autowired
    private MovieApiService movieApiService;
    
    @Override
    public Page<Showtime> getAll(Pageable pageable, LocalDate on, Long movieId) {
        Specification<Showtime> spec = Specification.where(null);
        if(on != null){
            spec = spec.and(ShowtimeSpecification.showtimeOn(on));
        }
        if(movieId != null){
            spec = spec.and(ShowtimeSpecification.showtimeForMovie(movieId));
        }
        return showtimeRepo.findAll(spec, pageable);
    }
    
    @Override
    public Showtime create(ShowtimeCreateReq req) {
        if(req.getFromTime().isBefore(LocalDate.now().atStartOfDay())){
            throw new BadRequestException("Showtime cannot be created for past date");
        }
        if(showtimeRepo.existsByScreenIdAndToTimeAfter(req.getScreenId(), req.getFromTime())){
            throw new BadRequestException("Screen already booked for this time");
        }
        MovieRes movie = movieApiService.findAvailableById(req.getMovieId(), req.getFromTime()).block();
        ScreenRes screen = screenApiService.findScreenById(req.getScreenId()).block();
        if (movie == null || screen == null) {
            throw new BadRequestException("Invalid movie or screen");
        }
        Showtime newShowTime = new Showtime();
        newShowTime.setMovieId(movie.getId());
        newShowTime.setMovieTitle(movie.getTitle());
        newShowTime.setScreenId(screen.getId());
        newShowTime.setScreenNumber(screen.getNumber());
        newShowTime.setFromTime(req.getFromTime());
        newShowTime.setToTime(req.getFromTime().plusMinutes(movie.getRuntime()));
        for(SeatRes seat: screen.getSeats()){
            ShowtimeSeat showtimeSeat = new ShowtimeSeat();
            showtimeSeat.setSeatId(seat.getId());
            showtimeSeat.setShowtime(newShowTime);
            showtimeSeat.setSeatType(seat.getType());
            showtimeSeat.setStatus(SeatStatus.AVAILABLE.name());
            showtimeSeat.setSeatNumber(seat.getRowNr()+seat.getColumnNr().toString());
            newShowTime.getShowtimeSeats().add(showtimeSeat);
        }
        return showtimeRepo.save(newShowTime);
    }
    
    @Override
    public Showtime getById(Long id) {
        return showtimeRepo.findById(id).orElseThrow(() -> new NotFoundException("Showtime not found"));
    }
    
    
}
