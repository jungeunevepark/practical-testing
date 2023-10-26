package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

// @SpringBootTest
@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
	@Test
	void findAllBySellingStatusIn() {
		// given
		Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500);
		Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingType.SOLDOUT, "팥빙수", 7000);

		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllBySellingTypeIn(
			List.of(ProductSellingType.SELLING, ProductSellingType.HOLD));

		// then
		assertThat(products).hasSize(2)
			.extracting("productNumber", "name", "sellingType")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", ProductSellingType.SELLING),
				tuple("002", "카페라떼", ProductSellingType.HOLD)
			);
	}

	@DisplayName("상품번호 리스트로 상품들을 조회한다.")
	@Test
	void findAllByProductNumberIn() {
		Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500);
		Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingType.SOLDOUT, "팥빙수", 7000);
		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		List<Product> products = productRepository.findAllByProductNumberIn(
			List.of("001", "002"));

		// then
		assertThat(products).hasSize(2)
			.extracting("productNumber", "name", "sellingType")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", ProductSellingType.SELLING),
				tuple("002", "카페라떼", ProductSellingType.HOLD)
			);
	}

	@DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
	@Test
	void findLastestProduct() {
		// given
		String targetProductNumber = "003";
		Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500);
		Product product3 = createProduct(targetProductNumber, ProductType.HANDMADE, ProductSellingType.SOLDOUT, "팥빙수",
			7000);

		productRepository.saveAll(List.of(product1, product2, product3));

		// when
		String lastestProductNumber = productRepository.findLastestProduct();
		// then
		assertThat(lastestProductNumber).isEqualTo(targetProductNumber);
	}

	private static Product createProduct(String productNumber, ProductType type, ProductSellingType sellingType,
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