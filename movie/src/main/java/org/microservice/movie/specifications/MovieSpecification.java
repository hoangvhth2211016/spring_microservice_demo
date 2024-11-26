package org.microservice.movie.specifications;

import org.microservice.movie.entities.Movie;
import org.microservice.movie.models.movies.MovieStatus;
import org.microservice.movie.models.movies.MovieSchedule;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {
    
    public static Specification<Movie> movieStatusEquals(MovieStatus status) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("status"), status.name());
        };
    }
    
    public static Specification<Movie> movieSchedule(MovieSchedule type) {
        return (root, query, criteriaBuilder) -> {
            if (type.equals(MovieSchedule.UPCOMING)){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("released"), java.time.LocalDate.now());
            }
            return criteriaBuilder.lessThan(root.get("released"), java.time.LocalDate.now());
        };
    }
}
