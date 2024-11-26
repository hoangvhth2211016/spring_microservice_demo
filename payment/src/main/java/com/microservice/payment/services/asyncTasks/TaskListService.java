package com.microservice.payment.services.asyncTasks;

import com.microservice.payment.entities.Payment;

import java.util.List;

public interface TaskListService {

    void addToTaskList(List<Payment> payments);

    Payment popOutOfTaskList();

    void removeKey();

    Boolean checkKeyExist();
    
}
