package sample.cafekiosk.spring.api.controller.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {
	@NotNull
	private ProductType type;
	@NotNull
	private ProductSellingType sellingType;
	@NotBlank
	private String name;
	@Positive
	private int price;

	@Builder
	private ProductCreateRequest(ProductType type, ProductSellingType sellingType, String name, int price) {
		this.type = type;
		this.sellingType = sellingType;
		this.name = name;
		this.price = price;
	}

	public Product toEntity(String nextProductNumber) {
		return Product.builder()
			.productNumber(nextProductNumber)
			.type(type)
			.sellingType(sellingType)
			.name(name)
			.price(price)
			.build();
	}
}
