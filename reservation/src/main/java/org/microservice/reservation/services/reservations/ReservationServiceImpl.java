package org.microservice.reservation.services.reservations;


import org.microservice.reservation.entities.Reservation;
import org.microservice.reservation.exceptions.AlreadyReservedException;
import org.microservice.reservation.exceptions.NotFoundException;
import org.microservice.reservation.models.reservations.ReservationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationServiceImpl implements ReservationService {
    
    private static final String PREFIX = "reservations:";
    
    @Value("${spring.data.redis.timeout}")
    private Long TIMEOUT;
    
    @Value("${spring.data.redis.payment-timeout}")
    private Long PAYMENT_TIMEOUT;
    
    @Autowired
    private RedisTemplate<String, Reservation> redisTemplate;
    
    @Override
    public Reservation getById(Long showtimeSeatId) {
        return redisTemplate.opsForValue().get(PREFIX + showtimeSeatId);
    }
    
    @Override
    public Reservation create(Long userId, ReservationReq req) {
        String key = PREFIX + req.getShowtimeSeatId();
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setShowtimeSeatId(req.getShowtimeSeatId());
        reservation.setShowtimeId(req.getShowtimeId());
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, reservation, TIMEOUT, TimeUnit.SECONDS);
        if (success != null && success){
            return reservation;
        }
        throw new AlreadyReservedException();
    }
    
    @Override
    public void removeFromReservation(Long userId, Long showtimeSeatId) {
        Reservation Reservation = getById(showtimeSeatId);
        if (Reservation == null || !Reservation.getUserId().equals(userId)){
            throw new NotFoundException("User reservation not found");
        }
        redisTemplate.delete(PREFIX + showtimeSeatId);
    }
    
    @Override
    public void clearUserReservation(Long userId) {
        List<String> ids = getByUserId(userId).stream().map(Reservation -> (PREFIX+Reservation.getShowtimeSeatId())).toList();
        if(ids.isEmpty()){
            throw new NotFoundException("User reservation list not found");
        }
        redisTemplate.delete(ids);
    }
    
    @Override
    public List<Reservation> getByUserId(Long userId) {
        List<Reservation> allReservations =  redisTemplate.opsForValue()
                .multiGet(Objects.requireNonNull(redisTemplate.keys(PREFIX + "*")));
        if(allReservations == null){
            return List.of();
            
        }
        return allReservations.stream()
                .filter(Reservation -> Reservation.getUserId().equals(userId))
                .toList();
    }
    
    @Override
    public void extendUserReservation(Long userId) {
        List<Reservation> Reservations = getByUserId(userId);
        for (Reservation Reservation : Reservations) {
            redisTemplate.expire(PREFIX + Reservation.getShowtimeSeatId(), PAYMENT_TIMEOUT, TimeUnit.SECONDS);
        }
    }
    
    
}
