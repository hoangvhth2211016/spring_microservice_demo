package com.microservice.order.models.showtimeSeats;

import com.microservice.order.exceptions.BadRequestException;

import java.math.BigDecimal;

public enum SeatType {
    NORMAL,
    VIP,
    COUPLE;
    
    public static BigDecimal getPrice(SeatType seatType){
        return switch (seatType) {
            case NORMAL -> new BigDecimal(10);
            case VIP -> new BigDecimal(8);
            case COUPLE -> new BigDecimal(18);
            default -> throw new BadRequestException("Unknown seat type: " + seatType);
        };
    }
    
}
