package com.microservice.payment.controllers;

import com.microservice.payment.entities.Payment;
import com.microservice.payment.models.orders.OrderRes;
import com.microservice.payment.models.payments.PaymentStatus;
import com.microservice.payment.models.pages.PageRes;
import com.microservice.payment.producers.PaymentEventHandler;
import com.microservice.payment.models.Event.PaymentEvent;
import com.microservice.payment.services.payments.PaymentService;
import com.microservice.payment.services.payments.PaymentServiceImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Value("${frontend.url}")
    private String frontendUrl;
    
    @Autowired
    private PaymentEventHandler paymentEventHandler;
    
    @GetMapping
    public PageRes<Payment> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return new PageRes<>(paymentService.getAll(pageable));
    }
    
    @GetMapping("public/orders/{oderId}")
    public Payment getByUserId(@PathVariable Long oderId){
        return paymentService.getByOrderId(oderId);
    }
    
    @PostMapping
    public String create(@RequestBody OrderRes order){
        return paymentService.createPaymentSession(order);
    }
    
    @PostMapping("refunds/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String refundPayment(@PathVariable Long orderId){
        paymentService.refundPayment(orderId);
        return "User payment refunded successfully!";
    }
    
    @GetMapping("public/stripe/success")
    public ResponseEntity<?> paymentResult(@RequestParam(value = "session_id", required = true) String sessionId) throws StripeException {
        
        Session session = Session.retrieve(sessionId);
        String status = session.getStatus();
        
        Long orderId = Long.parseLong(session.getClientReferenceId());
        Payment payment = paymentService.getByOrderId(orderId);
        
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(orderId);
        paymentEvent.setUserId(payment.getUserId());
        
        String redirectUrl = "";
        switch(status){
            case "open" -> {
                redirectUrl = frontendUrl+"/payment?status=open";
            }
            case "complete" -> {
                redirectUrl = frontendUrl+"/payment?status=complete";
                paymentService.updatePaymentStatus(payment, PaymentStatus.COMPLETE);
                paymentEvent.setPaymentStatus(PaymentStatus.COMPLETE.name());
                paymentEventHandler.updateSeatStatusOnPaymentSucceed(paymentEvent);
            }
            case "expired" -> {
                redirectUrl = frontendUrl+"/payment?status=expired";
                paymentService.updatePaymentStatus(payment, PaymentStatus.EXPIRED);
                paymentEventHandler.updateOrderOnPaymentExpiredOrCancelled(orderId);
                paymentEventHandler.clearUserReservationOnPaymentExpiredOrCancelled(payment.getUserId());
            }
            default -> {
                redirectUrl = frontendUrl+"/payment?status=error";
                paymentService.updatePaymentStatus(payment, PaymentStatus.ERROR);
                paymentEvent.setPaymentStatus(PaymentStatus.ERROR.name());
                paymentEventHandler.updateOrderOnPaymentError(orderId);
                paymentEventHandler.sendEmailOnPaymentError(paymentEvent);
            }
        }
        //return ResponseEntity
        //        .status(HttpStatus.SEE_OTHER)
        //        .location(URI.create(redirectUrl))
        //        .build();
        return ResponseEntity.ok(redirectUrl);
    }
    
    @GetMapping("public/stripe/cancel")
    public ResponseEntity<?> cancelPayment(
            @RequestParam(value = "order_id", required = true) Long orderId,
            @RequestParam(value = "user_id", required = true) Long userId
    ){
        Payment payment = paymentService.getByOrderId(orderId);
        
        paymentService.updatePaymentStatus(payment, PaymentStatus.CANCELLED);
        
        paymentEventHandler.updateOrderOnPaymentExpiredOrCancelled(orderId);
        
        paymentEventHandler.clearUserReservationOnPaymentExpiredOrCancelled(userId);
        //return ResponseEntity
        //        .status(HttpStatus.SEE_OTHER)
        //        .location(URI.create(frontendUrl+"/payment?status=cancel"))
        //        .build();
        return ResponseEntity.ok(orderId);
    }
    

}
