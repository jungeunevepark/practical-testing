package sample.cafekiosk.unit.beverage;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AmericanoTest {

	@Test
	void getName(){
		Americano americano = new Americano();
		// assertEquals(americano.getName(), "아메리카노");
		assertThat(americano.getName()).isEqualTo("아메리카노"); // 조금 더 풍부한 검증이 가능해 많이 사용됨
	}

	@Test
	void getPrice(){
		Americano americano = new Americano();

		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}