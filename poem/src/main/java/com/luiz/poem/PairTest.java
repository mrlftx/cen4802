package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PairTest {
	/**
	 * Test to see if pair string comparison method runs correctly
	 */
	@Test
	void test() {
		Pair test = new Pair("test", 0);
		boolean result = test.leftEquals("test");
		assertTrue(result);
	}

}
