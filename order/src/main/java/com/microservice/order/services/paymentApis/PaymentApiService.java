package com.microservice.order.services.paymentApis;


import com.microservice.order.entities.Order;
import com.microservice.order.models.payments.PaymentRes;
import reactor.core.publisher.Mono;

public interface PaymentApiService {
    
    Mono<String> createPaymentSession(Order order);
    
    Mono<PaymentRes> getPaymentByOrderId(Long orderId);
    
}
