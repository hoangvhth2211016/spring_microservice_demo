package com.microservice.payment.services.asyncTasks;

import com.microservice.payment.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskListServiceImpl implements TaskListService {
    
    @Autowired
    private RedisTemplate<String, Payment> redisTemplate;
    
    @Value("${payment.task.key}")
    private String TASK_LIST_KEY;
    
    private final String TASK_LIST_KEY_PREFIX = "payments:";
    
    @Override
    public void addToTaskList(List<Payment> payments) {
        if(listCurrentSize() > 10000){
            return;
        }
        redisTemplate.opsForList().leftPushAll(TASK_LIST_KEY_PREFIX + TASK_LIST_KEY, payments);
    }
    
    @Override
    public Payment popOutOfTaskList() {
        return redisTemplate.opsForList().rightPop(TASK_LIST_KEY_PREFIX + TASK_LIST_KEY);
    }
    
    @Override
    public void removeKey() {
        redisTemplate.delete(TASK_LIST_KEY_PREFIX + TASK_LIST_KEY);
    }
    
    @Override
    public Boolean checkKeyExist() {
        return redisTemplate.hasKey(TASK_LIST_KEY_PREFIX + TASK_LIST_KEY);
    }
    
    private int listCurrentSize(){
        return Objects.requireNonNull(redisTemplate.opsForList().size(TASK_LIST_KEY_PREFIX + TASK_LIST_KEY)).intValue();
    }
}
