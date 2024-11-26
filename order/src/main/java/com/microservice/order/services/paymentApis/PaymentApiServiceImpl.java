package com.microservice.order.services.paymentApis;

import com.microservice.order.entities.Order;
import com.microservice.order.models.payments.PaymentCreateReq;
import com.microservice.order.models.payments.PaymentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentApiServiceImpl implements PaymentApiService {
    
    @Autowired
    private WebClient webClient;
    
    @Override
    public Mono<String> createPaymentSession(Order order) {
        return webClient
                .post()
                .uri("payments")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(String.class);
    }
    
    @Override
    public Mono<PaymentRes> getPaymentByOrderId(Long orderId) {
        return webClient
                .get()
                .uri("payments/public/orders/" + orderId)
                .retrieve()
                .bodyToMono(PaymentRes.class);
    }
}
