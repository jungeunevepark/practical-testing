package sample.cafekiosk.spring.api.service.product;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	void tearDown() {
		productRepository.deleteAllInBatch();
	}

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

		List<Product> products = productRepository.findAll();
		Assertions.assertThat(products).hasSize(2)
			.extracting("productNumber", "type", "sellingType", "name", "price")
			.contains(
				Tuple.tuple("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000),
				Tuple.tuple("002", ProductType.HANDMADE, ProductSellingType.SELLING, "카푸치노", 5000)
			);
	}

	@DisplayName("신규 상품이 하나도 없는 경우 신규 상품을 등록하면 상품 번호는 001이다.")
	@Test
	void createProductWhenProductsIsEmpty() {
		// given
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
			.contains("001", ProductType.HANDMADE, ProductSellingType.SELLING, "카푸치노", 5000);
		List<Product> products = productRepository.findAll();
		Assertions.assertThat(products).hasSize(1)
			.extracting("productNumber", "type", "sellingType", "name", "price")
			.contains(
				Tuple.tuple("001", ProductType.HANDMADE, ProductSellingType.SELLING, "카푸치노", 5000)
			);
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