package com.microservice.payment.services.payments;

import com.microservice.payment.entities.Payment;
import com.microservice.payment.models.orders.OrderRes;
import com.microservice.payment.models.payments.PaymentStatus;
import com.microservice.payment.models.tickets.TicketRes;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    
    String createPaymentSession(OrderRes order);
    
    Price createPrice(TicketRes ticket);
    
    Page<Payment> getAll(Pageable pageable);
    
    Payment getByOrderId(Long oderId);
    
    Page<Payment> getOpenPayment(Pageable pageable);
    
    void refundPayment(Long orderId);
    
    void updatePaymentStatus(Payment payment, PaymentStatus status);
    
    default Session getPaymentSession(String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }
    
}
