package sample.cafekiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	public ProductResponse createProduct(ProductCreateRequest request) {
		String nextProductNumber = createNextProductNumber();
		Product product = request.toEntity(nextProductNumber);
		Product savedProduct = productRepository.save(product);
		return ProductResponse.builder()
			.id(savedProduct.getId())
			.productNumber(nextProductNumber)
			.type(request.getType())
			.sellingType(request.getSellingType())
			.name(request.getName())
			.price(5000)
			.build();
	}

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplay());

		return products.stream()
			.map(ProductResponse::from)
			.collect(Collectors.toList());
	}

	private String createNextProductNumber() {
		String latestProductNumber = productRepository.findLastestProduct();
		if (latestProductNumber == null) {
			return "001";
		}
		int latestProductNumberInt = Integer.parseInt(latestProductNumber);
		int nextProductNumberInt = latestProductNumberInt + 1;

		// 9 -> 009 10 -> 010
		return String.format("%03d", nextProductNumberInt);
	}
}
