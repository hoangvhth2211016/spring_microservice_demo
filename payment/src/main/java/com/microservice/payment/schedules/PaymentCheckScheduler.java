package com.microservice.payment.schedules;

import com.microservice.payment.entities.Payment;
import com.microservice.payment.services.asyncTasks.TaskListService;
import com.microservice.payment.services.payments.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentCheckScheduler {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private TaskListService taskListService;
    
    @Value("${payment.check.is-running}")
    private Boolean isRunning;
    
    private Boolean isScanning = false;
    
    @Scheduled(fixedRate = 12000)
    public void startPaymentCheckScheduler() {
        if(isRunning == null || !isRunning){
            log.info("schedule off");
            return;
        }
        
        log.info("schedule on");
        if (!isScanning) {
            return;
        }
        
        isScanning = true;
        
        int page = 0;
        int size = 10;
        
        while (true) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Payment> payments = paymentService.getOpenPayment(pageable);
            if(payments.getContent().isEmpty()){
                break;
            }
            taskListService.addToTaskList(payments.getContent());
            page++;
        }
        isScanning = false;
    }

}
