package com.microservice.order.services.orders;

import com.microservice.order.entities.Order;
import com.microservice.order.entities.Ticket;
import com.microservice.order.exceptions.BadRequestException;
import com.microservice.order.exceptions.NotFoundException;
import com.microservice.order.models.payments.PaymentRes;
import com.microservice.order.models.reservations.ReservationRes;
import com.microservice.order.models.events.OrderCreatedEvent;
import com.microservice.order.models.orders.OrderStatus;
import com.microservice.order.models.orders.OrderUpdateMessage;
import com.microservice.order.models.orders.PaymentStatus;
import com.microservice.order.models.payments.PaymentCreateReq;
import com.microservice.order.models.showtimeSeats.SeatType;
import com.microservice.order.models.showtimeSeats.ShowtimeSeatRes;
import com.microservice.order.models.tickets.TicketView;
import com.microservice.order.models.users.UserInformation;
import com.microservice.order.producers.OrderCreatedEventHandler;
import com.microservice.order.repositories.OrderRepo;
import com.microservice.order.repositories.TicketRepo;
import com.microservice.order.services.reservationApis.ReservationApiService;
import com.microservice.order.services.paymentApis.PaymentApiService;
import com.microservice.order.services.showtimeApis.ShowtimeApiService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepo orderRepo;
    
    @Autowired
    private TicketRepo ticketRepo;
    
    @Autowired
    private PaymentApiService paymentApiService;
    
    @Autowired
    private ReservationApiService reservationApiService;
    
    @Autowired
    private ShowtimeApiService showtimeApiService;
    
    @Autowired
    private OrderCreatedEventHandler orderCreatedEventHandler;
    
    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }
    
    @Override
    public String create(UserInformation userInfo) {
        
        List<ReservationRes> reservations = reservationApiService.getCartById(userInfo.getId()).collectList().block();
        
        if(reservations.isEmpty() || reservations == null){
            throw new NotFoundException("User reservation is empty");
        }
        
        Order currentOrder = getByShowtimeIdAndUserId(reservations.getFirst().getShowtimeId(), userInfo.getId());
        
        if(currentOrder != null){
            PaymentRes payment = paymentApiService.getPaymentByOrderId(currentOrder.getId()).block();
            if(payment.getStatus().equals(PaymentStatus.OPEN.name())){
                return payment.getUrl();
            }
        }
        
        try {
            List<ShowtimeSeatRes> showtimeSeats = showtimeApiService
                    .getSeatList(reservations.stream()
                            .map(ReservationRes::getShowtimeSeatId).toList())
                    .collectList()
                    .block();
    
            if(showtimeSeats.isEmpty() || showtimeSeats == null){
                throw new NotFoundException("Showtime seat list might be empty or already booked by other user");
            }
            
            Order order = new Order();
            order.setUserId(userInfo.getId());
            order.setShowtimeId(reservations.getFirst().getShowtimeId());
            order.setStatus(OrderStatus.PENDING.name());
            for(ShowtimeSeatRes seat : showtimeSeats){
                Ticket ticket = new Ticket();
                ticket.setOrder(order);
                ticket.setShowtimeSeatId(seat.getId());
                ticket.setSeatNumber(seat.getSeatNumber());
                if(Objects.equals(seat.getSeatType(), SeatType.VIP.name())){
                    ticket.setPrice(new BigDecimal(10));
                } else if(Objects.equals(seat.getSeatType(), SeatType.NORMAL.name())){
                    ticket.setPrice(new BigDecimal(8));
                } else {
                    ticket.setPrice(new BigDecimal(18));
                }
                order.getTickets().add(ticket);
            }
            
            Order newOrder = orderRepo.save(order);
            
       
       
            String paymentSessionUrl = paymentApiService
                    .createPaymentSession(order)
                    .block();
            
            orderCreatedEventHandler.handleUserReservationOrderCreated(new OrderCreatedEvent(userInfo.getId(), true));
            return paymentSessionUrl;
            
        } catch (Exception e) {
            orderCreatedEventHandler.handleUserReservationOrderCreated(new OrderCreatedEvent(userInfo.getId(), false));
            throw e;
        }
    }
    
    @Override
    public Order getById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }
    
    @Override
    public Order getByShowtimeIdAndUserId(Long showtimeId, Long userId) {
        return orderRepo.findByUserIdAndShowtimeId(userId, showtimeId).orElse(null);
    }
    
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getById(orderId);
        if(!order.getStatus().equals(OrderStatus.PENDING.name())){
            return order;
        }
        order.setStatus(status.name());
        return orderRepo.save(order);
    }
    
    @Override
    public Page<Order> getByUserId(Long id, Pageable pageable) {
        return orderRepo.findByUserId(id, pageable);
    }
    
    @Override
    public List<Ticket> getTicketsByOrderId(Long id) {
        return ticketRepo.findByOrder_Id(id);
    }
    
}
