package com.microservice.order.models.payments;

import com.microservice.order.models.tickets.TicketView;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentCreateReq {
    
    private Long orderId;
    
    private Long userId;
    
    private String status;

    List<TicketView> tickets = new ArrayList<>();
    
}
