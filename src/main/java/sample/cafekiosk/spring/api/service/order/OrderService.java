package sample.cafekiosk.spring.api.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	public OrderResponse createOrder(OrderCreateRequest request) {
		return null;
	}
}
