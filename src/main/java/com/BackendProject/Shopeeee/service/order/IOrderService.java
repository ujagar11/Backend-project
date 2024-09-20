package com.BackendProject.Shopeeee.service.order;

import com.BackendProject.Shopeeee.dto.OrderDto;
import com.BackendProject.Shopeeee.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
