package com.adiwave.reactorexercises;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ReactorExercisesApplicationTests {

	@Test
	void contextLoads() {
		assertThat(23).isEqualTo(23);
	}

}
