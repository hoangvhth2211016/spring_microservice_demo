package org.microservice.movie.repositories;

import org.microservice.movie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
   
    Optional<Movie> findByIdAndStatus(Long id, String name);
    
    Optional<Movie> findByIdAndStatusAndReleasedLessThanEqual(Long id, String status, LocalDate released);
}
