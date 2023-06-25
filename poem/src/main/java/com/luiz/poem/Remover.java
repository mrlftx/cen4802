package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class Remover {
	
	/**
	 * Method to remove html tags from a text
	 * @param path The filepath of the text to be read
	 * @param s1 Where in the text to start (i.e. all text before will be discarded)
	 * @param s2 Where in the text to end (i.e. all text after will be discarded)
	 * @param output The name of the output text the html tag free text will be placed in
	 * @throws IOException
	 */
	public void removeHTML(Path path, String s1, String s2, String output) throws IOException {
		String holder = new String();
		File out = new File(output);
		FileWriter writer = new FileWriter ("output.txt", false);
		try {
			holder = Files.readString(path);
			holder = holder.replaceAll("<[^>]*>", "");
			//holder = holder.substring(holder.indexOf("The Raven\n\nby Edgar Allan Poe"), holder.indexOf("\n\n*** END"));
			holder = holder.substring(holder.indexOf(s1), holder.indexOf(s2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(holder);
		writer.flush();
	}
}
