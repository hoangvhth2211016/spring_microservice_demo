package com.microservice.order.consumers;

import com.microservice.order.models.orders.OrderStatus;
import com.microservice.order.services.orders.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdateEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusUpdateEventListener.class);
    
    @Autowired
    private OrderService orderService;
    
    
    @RabbitListener(queues = "order-payment-succeed-queue")
    public void updateOrderOnPaymentSucceed(Long orderId){
        logger.info("Processing order update on payment succeed for order -> {}", orderId);
        orderService.updateOrderStatus(orderId, OrderStatus.COMPLETE);
    }
    
    @RabbitListener(queues = "order-payment-expired-queue")
    public void updateOrderOnPaymentExpiredOrCancelled(Long orderId){
        logger.info("Processing order update on payment expired or cancelled for order -> {}", orderId);
        orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
    }
    
    @RabbitListener(queues = "order-payment-error-queue")
    public void updateOrderOnPaymentError(Long orderId){
        logger.info("Processing order update on payment error for order -> {}", orderId);
        orderService.updateOrderStatus(orderId, OrderStatus.ERROR);
    }
    
}
