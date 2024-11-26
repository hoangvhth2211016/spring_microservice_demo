package org.microservice.showtime.specifications;

import org.microservice.showtime.entities.Showtime;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ShowtimeSpecification {
    
    public static Specification<Showtime> showtimeOn(LocalDate on) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.function("DATE", LocalDate.class, root.get("fromTime")), on);
        };
    }
    
    public static Specification<Showtime> showtimeForMovie(Long movieId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("movieId"), movieId);
        };
    }
    
}
