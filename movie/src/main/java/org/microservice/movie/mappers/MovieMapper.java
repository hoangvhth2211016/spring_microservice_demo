package org.microservice.movie.mappers;

import org.mapstruct.*;
import org.microservice.movie.entities.Movie;
import org.microservice.movie.models.movies.MovieCreateReq;
import org.microservice.movie.models.movies.MovieOmdbRes;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {
    
    @Mapping(target = "runtime", source = "runtime", qualifiedByName = "convertRuntime")
    Movie toEntity(MovieOmdbRes omdbRes);
    
    @Named("convertRuntime")
    default Integer convertRuntime(String runtime) {
        return Integer.parseInt(runtime.split(" ")[0]);
    }
    
}