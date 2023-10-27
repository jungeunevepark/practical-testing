package sample.cafekiosk.spring.api.controller.product;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.domain.product.ProductSellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // 직렬화, 역직렬화

	@MockBean
	private ProductService productService;

	@DisplayName("신규 상품을 등록한다.")
	@Test
	void createProduct() throws Exception {
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingType(ProductSellingType.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());

	}

	@DisplayName("신규 상품을 등록할 때, 상품 타입은 필수값이다.")
	@Test
	void createProductWithoutType() throws Exception {
		ProductCreateRequest request = ProductCreateRequest.builder()
			.sellingType(ProductSellingType.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());

	}

	@DisplayName("신규 상품을 등록할 때, 상품 판매상태은 필수값이다.")
	@Test
	void createProductWithoutSellingType() throws Exception {
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.name("아메리카노")
			.price(4000)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());

	}

	@DisplayName("신규 상품을 등록할 때, 상품 이름은 필수값이다.")
	@Test
	void createProductWithoutName() throws Exception {
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingType(ProductSellingType.SELLING)
			.price(4000)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty());

	}

	@DisplayName("신규 상품을 등록할 때, 상품 가격은 양수값이다.")
	@Test
	void createProductWithoutZeroPrice() throws Exception {
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingType(ProductSellingType.SELLING)
			.name("아메리카노")
			.price(0)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/new")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 가격은 양수이어야 합니다."))
			.andExpect(jsonPath("$.data").isEmpty());

	}
}