package com.microservice.payment.services.paymentChecks;

import com.microservice.payment.entities.Payment;
import com.microservice.payment.models.Event.PaymentEvent;
import com.microservice.payment.models.payments.PaymentStatus;
import com.microservice.payment.producers.PaymentEventHandler;
import com.microservice.payment.services.asyncTasks.TaskListService;
import com.microservice.payment.services.payments.PaymentService;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PaymentCheckProcess implements Runnable{
    
    private PaymentService paymentService;
    
    private TaskListService taskListService;
    
    private PaymentEventHandler paymentEventHandler;
    
    @Override
    public void run() {
        try{
            while (true) {
                if(taskListService.checkKeyExist()){
                    Payment payment = taskListService.popOutOfTaskList();
                    if(payment == null){
                        taskListService.removeKey();
                    }else{
                        Session session = paymentService.getPaymentSession(payment.getSessionId());
                        String status = session.getStatus();
                        
                        Long orderId = Long.parseLong(session.getClientReferenceId());
                        
                        PaymentEvent paymentEvent = new PaymentEvent();
                        paymentEvent.setOrderId(orderId);
                        paymentEvent.setUserId(payment.getUserId());
                        
                        switch(status){
                            case "open" -> {}
                            case "complete" -> {
                                paymentService.updatePaymentStatus(payment, PaymentStatus.COMPLETE);
                                paymentEvent.setPaymentStatus(PaymentStatus.COMPLETE.name());
                                paymentEventHandler.updateSeatStatusOnPaymentSucceed(paymentEvent);
                            }
                            case "expired" -> {
                                paymentService.updatePaymentStatus(payment, PaymentStatus.EXPIRED);
                                paymentEventHandler.updateOrderOnPaymentExpiredOrCancelled(orderId);
                                paymentEventHandler.clearUserReservationOnPaymentExpiredOrCancelled(payment.getUserId());
                            }
                            default -> {
                                paymentService.updatePaymentStatus(payment, PaymentStatus.ERROR);
                                paymentEvent.setPaymentStatus(PaymentStatus.ERROR.name());
                                paymentEventHandler.updateOrderOnPaymentError(orderId);
                                paymentEventHandler.sendEmailOnPaymentError(paymentEvent);
                            }
                        }
                    }
                }else{
                Thread.sleep(100);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
