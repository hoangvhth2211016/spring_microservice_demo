package com.microservice.payment.services.asyncTasks;

import com.microservice.payment.producers.PaymentEventHandler;
import com.microservice.payment.services.paymentChecks.PaymentCheckProcess;
import com.microservice.payment.services.payments.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class TaskManager {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private TaskListService taskListService;
    
    @Autowired
    private PaymentEventHandler paymentEventHandler;
    
    private static final Long MAX_TIMEOUT = 10000L;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    
    @PostConstruct
    public void init(){
        for(int i = 0; i < 3; i++){
            PaymentCheckProcess paymentCheckProcess = new PaymentCheckProcess(paymentService, taskListService, paymentEventHandler);
            executorService.execute(paymentCheckProcess);
            log.info("Thread {} is ready", i+1);
        }
    }
 
}
