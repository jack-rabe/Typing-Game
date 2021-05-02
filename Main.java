
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
  private Stage stage;
  private final int WIDTH = 600, HEIGHT = 600;
  private Scene menuScreen, rulesScreen, gameScreen;
  
  private String [] acceptableChars = new String [] {"a", "b"};  // TODO remove this stub when possible
  private List<String> allWords = new ArrayList<String>();
  private List<Word> wordsOnScreen = new ArrayList<Word>();
  
  
  private Scene createMenuScreen() {
    BorderPane mainPane = new BorderPane();
    mainPane.setStyle("-fx-background-color: blue;");
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    scene.getStylesheets().add("Styles" + System.lineSeparator() + "menuStyles.css");
    
    // Display the title of the game top and center
    Label title = new Label("Home");
    title.setFont(Font.font(28));
    BorderPane.setAlignment(title, Pos.CENTER);
    mainPane.setTop(title);

    // create the start, rules, and quit buttons
    Button startBtn = new Button("Start");
    startBtn.setOnAction(event -> { stage.setScene(gameScreen); });
    Button rulesBtn = new Button("Rules");
    rulesBtn.setOnAction(event -> { stage.setScene(rulesScreen); });
    Button quitBtn = new Button("Quit");
    quitBtn.setOnAction(event -> { Platform.exit(); });
    
    // add all buttons to a VBox
    VBox btnBox = new VBox();
    btnBox.setSpacing(20);
    btnBox.setAlignment(Pos.CENTER);
    btnBox.getChildren().add(startBtn);
    btnBox.getChildren().add(rulesBtn);
    btnBox.getChildren().add(quitBtn);
    
    BorderPane.setAlignment(btnBox, Pos.CENTER);
    mainPane.setCenter(btnBox);
    return scene;
  }

  private Scene createRulesScreen() {
    BorderPane mainPane = new BorderPane();
    mainPane.setStyle("-fx-background-color: red;");
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    scene.getStylesheets().add("Styles" + System.lineSeparator() + "ruleStyles.css");
    
    // Display the title of the game top and center
    Label title = new Label("Rules");
    title.setFont(Font.font(28));
    BorderPane.setAlignment(title, Pos.CENTER);
    mainPane.setTop(title);

    // create the start, rules, and quit buttons
    Button menuBtn = new Button("Menu");
    menuBtn.setOnAction(event -> { stage.setScene(menuScreen); });
    Button quitBtn = new Button("Quit");
    quitBtn.setOnAction(event -> { Platform.exit(); });
    
    // add all buttons to VBoxes
    VBox menuBtnBox = new VBox();
    menuBtnBox.setAlignment(Pos.BOTTOM_LEFT);
    menuBtnBox.getChildren().add(menuBtn);
    VBox quitBtnBox = new VBox();
    quitBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
    quitBtnBox.getChildren().add(quitBtn);
   
    mainPane.setLeft(menuBtnBox);
    mainPane.setRight(quitBtnBox);
    return scene;
  }
  
  private Scene createGameScreen() {
    Group group = new Group();
    Scene scene = new Scene(group);
    scene.getStylesheets().add("Styles" + System.lineSeparator() + "gameStyles.css");
    
    // create canvas and graphics context
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    group.getChildren().add(canvas);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    scene.setOnKeyPressed(event -> {
      String enteredChar = event.getText();
      if (Arrays.binarySearch(acceptableChars, enteredChar) >= 0)
          gc.fillText((String) enteredChar, 50, 50);
    });
    
         
    
    
    return scene;
  }

  @Override
  public void start(final Stage stage) {
    this.stage = stage;
    
    menuScreen = createMenuScreen();
    rulesScreen = createRulesScreen();
    gameScreen = createGameScreen();
    
    allWords = Utils.setUpWords(5);
    
    

    stage.setTitle("Typing Game");
    stage.setScene(menuScreen);
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch();
  }
}
