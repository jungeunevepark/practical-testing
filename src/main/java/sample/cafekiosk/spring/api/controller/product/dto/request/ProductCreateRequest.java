package sample.cafekiosk.spring.api.controller.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductServiceRequest;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

	@NotNull(message = "상품 타입은 필수입니다.")
	private ProductType type;

	@NotNull(message = "상품 판매상태는 필수입니다.")
	private ProductSellingType sellingType;

	@NotBlank(message = "상품 이름은 필수입니다.")
	private String name;

	@Positive(message = "상품 가격은 양수이어야 합니다.")
	private int price;

	@Builder
	private ProductCreateRequest(ProductType type, ProductSellingType sellingType, String name, int price) {
		this.type = type;
		this.sellingType = sellingType;
		this.name = name;
		this.price = price;
	}

	public ProductServiceRequest toServiceRequest() {
		return ProductServiceRequest.builder()
			.type(type)
			.sellingType(sellingType)
			.name(name)
			.price(price)
			.build();
	}
}
