package sample.cafekiosk.spring.api.service.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품 번호에서 1 증가한 값이다.")
	@Test
	void createProduct() {
		// given
		Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000);
		productRepository.save(product1);

		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingType(ProductSellingType.SELLING)
			.name("카푸치노")
			.price(5000)
			.build();

		// when
		ProductResponse product = productService.createProduct(request);

		// then
		Assertions.assertThat(product)
			.extracting("productNumber", "type", "sellingType", "name", "price")
			.contains("002", ProductType.HANDMADE, ProductSellingType.SELLING, "카푸치노", 5000);
	}

	private Product createProduct(String productNumber, ProductType type, ProductSellingType sellingType,
		String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(type)
			.sellingType(sellingType)
			.name(name)
			.price(price)
			.build();
	}
}