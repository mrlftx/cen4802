package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class RemoveTest {
	/**
	 * Test to see if method to remove html tags functions correctly
	 */
	@Test
	void test() {
		Remover remove = new Remover();
		File file = new File("output.txt");
		Path filename = Path.of("removetest.html");
		String holder = "";
		try {
			remove.removeHTML(filename, "a", ".", "testoutput.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try(Scanner sc = new Scanner(new FileInputStream(file))){
			holder = sc.nextLine();
			assertEquals(holder, "a  b");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
