package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredTime) {
		List<String> productNumbers = request.getProductNumbers();
		// product
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		Order order = Order.create(products, registeredTime);
		Order save = orderRepository.save(order);

		return OrderResponse.from(save);
	}
}
