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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearcherSQL extends Application  {
	
	public static int i = 19;
	public static Pair adder = new Pair("", 0);
	public static String color1 = "#606dbc";
	public static String color2 = "#465298";
	public static String temp = "";
	public static Connection conn;
	public static Statement statement;
	public static ResultSet result;
	
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
		String sqlHolder = new String();
		String url = "jdbc:mysql://localhost:3306/word_occurrences?autoReconnect=true&useSSL=false";//Change to user's localhost
		String uname = "root";
		String password = "Admin128";
		try {
			conn = DriverManager.getConnection(url, uname, password);
			statement = conn.createStatement();
			statement.executeUpdate("TRUNCATE words;");//Clear table at start to prevent duplicate entries
			//conn.commit(); //Only turn on if autocommit is off
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
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
				
				try {
					Statement statement = conn.createStatement();
					sqlHolder = "insert into words values ('"+ holder +"');";
					statement.executeUpdate(sqlHolder);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				

		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Statement statement = conn.createStatement();
			result = statement.executeQuery("select * from (select word, count(*) as c FROM words GROUP BY word order by c desc limit 20) t order by c asc;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				try {
					if(result.next()){
					words.setText((i+1)+ ". " + result.getString(1) + " (" + result.getInt(2) + " times)\n" + words.getText());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
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