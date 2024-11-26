package com.microservice.mail.consumers;

import com.microservice.mail.models.MailTemplate;
import com.microservice.mail.models.events.OrderCompleteMailEvent;
import com.microservice.mail.models.events.PaymentEvent;
import com.microservice.mail.models.tickets.TicketRes;
import com.microservice.mail.models.users.UserRes;
import com.microservice.mail.services.MailService;
import com.microservice.mail.services.userApis.UserApiService;
import com.microservice.mail.utils.FileUtil;
import com.microservice.mail.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class UserOrderEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(UserOrderEventListener.class);
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private UserApiService userApiService;
    
    @RabbitListener(queues = "order-mail-complete-queue")
    public void sendMailOnUserOrderComplete(OrderCompleteMailEvent message){
        logger.info("Send mail on order complete to user -> {}", message.getUserId());
        
        UserRes user = userApiService.getUserById(message.getUserId())
                .block();
        
        StringBuilder template = new StringBuilder(FileUtil.readTemplate("orderCompleteMail.html"));
        BigDecimal total = BigDecimal.ZERO;
        StringBuilder items = new StringBuilder();
        for (TicketRes ticket : message.getTickets()) {
            String item = "  <li>\n" +
                    "                <p><span>Seat: "+ticket.getSeatNumber()+"</span> - <span> " +ticket.getPrice()+ "$</span></p>\n" +
                    "            </li>";
            items.append(item);
            total = total.add(ticket.getPrice());
        }
        
        StringUtil.replace(template, Map.of(
                "${firstName}", user.getFirstName(),
                "${lastName}", user.getLastName(),
                "${username}", user.getUsername(),
                "${email}", user.getEmail(),
                "${items}", items.toString(),
                "${total}", total.toString()
        ));
        
        MailTemplate mailTemplate = MailTemplate.builder()
                .to(user.getEmail())
                .subject("User Order Tickets")
                .body(template.toString())
                .build();
        mailService.send(mailTemplate);
    }
    
    public void sendMailOnUserOrderError(PaymentEvent message){
        logger.info("Send mail on order error to user -> {}", message.getUserId());
        
        UserRes user = userApiService.getUserById(message.getUserId())
                .block();
        
        StringBuilder template = new StringBuilder(FileUtil.readTemplate("userInformOrderErrorMail.html"));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm a");
        
        String orderDate = message.getTimeline().format(formatter);
        
        StringUtil.replace(template, Map.of(
                "${firstName}", user.getFirstName(),
                "${lastName}", user.getLastName(),
                "${orderId}", message.getOrderId().toString(),
                "${orderDate}", orderDate
        ));
        
        MailTemplate mailTemplate = MailTemplate.builder()
                .to(user.getEmail())
                .subject("Error on payment process")
                .body(template.toString())
                .build();
        mailService.send(mailTemplate);
    }
    
}
