package sample.cafekiosk.spring.api.service.order.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderServiceRequest {
	private List<String> productNumbers;

	@Builder
	public OrderServiceRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}
}
