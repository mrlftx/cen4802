package com.luiz.poem;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.application.*;
import javafx.event.*;
public class Searcher extends Application  {
	
	public static ArrayList<Pair> wordStore = new ArrayList<Pair>();
	public static int i = 19;
	public static Pair adder = new Pair("", 0);
	public static String color1 = "#606dbc";
	public static String color2 = "#465298";
	public static String temp = "";
	
	/**
	 * Method to count the number of words in a text, then sort them in descending order from highest to lowest
	 * @param outname The name of the output file
	 * @param pathname The filepath to the text that will be word counted
	 * @param txtstart Where in the text to start (i.e. all text before will be discarded)
	 * @param txtend Where in the text to end (i.e. all text after will be discarded)
	 */
	public static void countNSort(String outname, String pathname, String txtstart, String txtend) {
		String holder = new String();
		holder.equalsIgnoreCase(holder);

		File file = new File(outname);
		int store = 0;
		boolean isIn = false;
		Path filename = Path.of(pathname);
		Remover strip = new Remover();
		try {
			strip.removeHTML(filename, txtstart, txtend, outname);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try(Scanner sc = new Scanner(new FileInputStream(file))){
			while(sc.hasNext()){
				isIn = false;
				holder = sc.next();
				holder = holder.replaceAll("[,.!?;—$\\\"“”‘’]", "");
				adder = new Pair("", 0);
				adder.setLeft(holder);
					for(int i = 0; i < wordStore.size(); i++) {
						if(wordStore.get(i).leftEquals(holder)){
							store = wordStore.get(i).getRight();
							store++;
							wordStore.get(i).setRight(store);
							isIn= true;
						}
					}
					if(!isIn) {
						adder.setRight(1);
						wordStore.add(adder);
					}
				

		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Collections.sort(wordStore, Comparator.comparing(pair -> -pair.getRight()));
	}
	
	/**
	 * The method that creates the java fx graphics
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			root.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 10px, repeat,  "+ color1 + " 50%, " + color2 + " 50%)");
			Timeline palette = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
				temp = color1;
				color1 = color2;
				color2 = temp;
				root.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 10px, repeat,  "+ color1 + " 50%, " + color2 + " 50%)");
			}));
			palette.setCycleCount(Timeline.INDEFINITE);
			palette.play();
			Scene scene = new Scene(root, 400, 450);
			Rectangle rect = new Rectangle(380, 430, Color.ALICEBLUE);
			rect.setLayoutX(10);
			rect.setLayoutY(10);
			Text title = new Text(30, 35, "What are the 20 most reoccurring words in \n\t\t\t\"The Raven\"?");
			title.setStyle("-fx-font-size: 18;");
			title.wrappingWidthProperty().bind(scene.widthProperty());
			Button press = new Button("Click here to find out!");
			press.setLayoutX(135);
			press.setLayoutY(235);
			Label words = new Label();
			words.setLayoutX(150);
			words.setLayoutY(80);
			words.setStyle("-fx-font-weight: bold;");
			root.getChildren().addAll(rect, title, press, words);
			primaryStage.setScene(scene);
			primaryStage.show();
			Timeline cycle = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
				adder = wordStore.get(i);
				words.setText((i+1)+ ". " + adder.getLeft() + " (" + adder.getRight() + " times)\n" + words.getText());
				i--;
			}));
			cycle.setCycleCount(20);
			EventHandler<ActionEvent> display = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					Button source = (Button) arg0.getSource();
					source.setVisible(false);
					cycle.play();
				}
			};
			press.setOnAction(display);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		countNSort("output.txt", "poem.html", "The Raven\n\nby Edgar Allan Poe", "\n\n*** END");
		launch(args);
	}
}
