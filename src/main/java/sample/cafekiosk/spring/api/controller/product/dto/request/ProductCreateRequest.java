package sample.cafekiosk.spring.api.controller.product.dto.request;

import lombok.Getter;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductCreateRequest {
	private ProductType type;
	private ProductSellingType sellingType;
	private String name;
	private int price;
}
