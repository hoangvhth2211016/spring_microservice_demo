package com.microservice.payment.services.payments;

import com.microservice.payment.entities.Payment;
import com.microservice.payment.exceptions.BadRequestException;
import com.microservice.payment.exceptions.NotFoundException;
import com.microservice.payment.models.orders.OrderRes;
import com.microservice.payment.models.payments.PaymentStatus;
import com.microservice.payment.models.tickets.TicketRes;
import com.microservice.payment.repositories.PaymentRepo;
import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Value("${server.gateway}")
    private String baseUrl;
    
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    
    @Value("${stripe.expiration-in-minutes}")
    private int expiration;
    
    @Value("${stripe.currency}")
    private String currency;
    
    @Autowired
    private PaymentRepo paymentRepo;
    
    @PostConstruct
    protected void setupStripe(){
        Stripe.apiKey = stripeApiKey;
    }
    
    @Override
    @SneakyThrows
    @Transactional
    public String createPaymentSession(OrderRes req) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (var ticket : req.getTickets()) {
            Price price = createPrice(ticket);
            lineItems.add(createLineItem(price));
        }
        
        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setExpiresAt(Instant.now().plus(expiration, ChronoUnit.MINUTES).getEpochSecond())
                .setCurrency(currency)
                .setSuccessUrl(baseUrl+"/payments/public/stripe/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(baseUrl+"/payments/public/stripe/cancel?order_id="+req.getId()+"&user_id="+req.getUserId())
                .setClientReferenceId(req.getId().toString())
                .build();
        
        Session newSession = Session.create(params);
        
        Payment payment = new Payment();
        payment.setOrderId(req.getId());
        payment.setUserId(req.getUserId());
        payment.setSessionId(newSession.getId());
        payment.setUrl(newSession.getUrl());
        payment.setStatus(PaymentStatus.OPEN.name());
        
        paymentRepo.save(payment);
        
        return newSession.getUrl();
    }
    
    @Override
    @SneakyThrows
    public Price createPrice(TicketRes ticket) {
        PriceCreateParams params = PriceCreateParams.builder()
                .setProductData(createProductData(ticket))
                .setCurrency(currency)
                .setUnitAmountDecimal(ticket.getPrice().multiply(new BigDecimal(100)))
                .build();
        return Price.create(params);
    }
    
    @Override
    public Page<Payment> getAll(Pageable pageable) {
        return paymentRepo.findAll(pageable);
    }
    
    @Override
    public Payment getByOrderId(Long oderId) {
        return paymentRepo.findByOrderId(oderId).orElseThrow(() -> new NotFoundException("Payment not found"));
    }
    
    @Override
    public Page<Payment> getOpenPayment(Pageable pageable) {
        return paymentRepo.findByStatusLike(PaymentStatus.OPEN.name(), pageable);
    }
    
    @Override
    public void refundPayment(Long orderId) {
        
        Payment payment = getByOrderId(orderId);
        
        try{
        
        Session session = Session.retrieve(payment.getSessionId());
        
        String paymentIntent = session.getPaymentIntent();
        
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntent)
                .build();
      
            Refund refund = Refund.create(params);
            updatePaymentStatus(payment, PaymentStatus.REFUNDED);
        }catch (Exception e){
            throw new BadRequestException("Unable to refund user order with id: "+orderId);
        }
      
    }
    
    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setStatus(status.name());
        paymentRepo.save(payment);
    }
    
    private SessionCreateParams.LineItem createLineItem(Price price){
        return SessionCreateParams.LineItem.builder()
                .setPrice(price.getId())
                .setQuantity(1L)
                .build();
    }
    
    private PriceCreateParams.ProductData createProductData(TicketRes ticket){
        return PriceCreateParams.ProductData.builder()
                .setName("Seat number: " + ticket.getSeatNumber())
                .build();
    }
    
    
    
}
