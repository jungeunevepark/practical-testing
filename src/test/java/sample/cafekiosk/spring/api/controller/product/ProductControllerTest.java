package sample.cafekiosk.spring.api.controller.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());

	}
}