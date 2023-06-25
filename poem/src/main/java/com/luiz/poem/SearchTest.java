package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SearchTest {
	/**
	 * Test to see if method to count and sort text functions correctly
	 */
	@Test
	void test() {
		Searcher search = new Searcher();
		File file = new File("output.txt");
		Pair holder = new Pair("a", 1);
		ArrayList<Pair> testStore = new ArrayList<Pair>();
		testStore.add(holder);
		Pair holder2 = new Pair("b", 1);
		testStore.add(holder2);
		search.countNSort("output.txt", "removetest.html", "a", ".");
		assertEquals(search.wordStore, testStore);
	}

}
