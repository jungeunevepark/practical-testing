package sample.cafekiosk.spring.api.controller.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

@RestController
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping("/api/v1/product/new")
	public ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
		return ApiResponse.ok(HttpStatus.OK, productService.createProduct(request.toServiceRequest()));
	}

	@GetMapping("/api/v1/product/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts() {
		return ApiResponse.ok(HttpStatus.OK, productService.getSellingProducts());
	}
}
