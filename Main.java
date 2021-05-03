import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
  private Stage stage;
  public final static int WIDTH = 700;
  public final static int HEIGHT = 500;
  private Scene menuScreen, rulesScreen, gameScreen;
  
  private String [] acceptableChars = new String [] {"a", "b"};  // TODO remove this stub when possible
  private List<String> allWords = new ArrayList<String>();
  private List<Word> wordsOnScreen = new ArrayList<Word>();
  
  ImageView JUNGLE = new ImageView("jungle.jpg");
  
  private Scene createMenuScreen() {
    BorderPane mainPane = new BorderPane();
    mainPane.setStyle("-fx-background-color: blue;");
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    scene.getStylesheets().add("menuStyles.css");//scene.getStylesheets().add("Styles" + System.lineSeparator() + "menuStyles.css");
    
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
    scene.getStylesheets().add("ruleStyles.css");//scene.getStylesheets().add("Styles" + System.lineSeparator() + "ruleStyles.css");
    
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
    scene.getStylesheets().add("gameStyles.css");//scene.getStylesheets().add("Styles" + System.lineSeparator() + "gameStyles.css");
    
    // create canvas and graphics context
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    // fit the background image to the screen size
    JUNGLE.fitWidthProperty().bind(scene.widthProperty()); 
    JUNGLE.setPreserveRatio(true);
    
    // add background image and canvas to the screen
    group.getChildren().add(JUNGLE);
    group.getChildren().add(canvas);
    
    // handle key presses (when user is typing words onscreen)
    scene.setOnKeyPressed(event -> {
      String enteredChar = event.getText();
      if (Arrays.binarySearch(acceptableChars, enteredChar) >= 0)
        gc.fillText((String) enteredChar, 50, 50);
    });

    
    // start the main game loop
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // clear canvas
      Utils.addWordToScreen(allWords, wordsOnScreen);
      
      for (Word word : wordsOnScreen) {  // display all current words and update their position
        gc.fillText(word.getContents(), word.getX(), word.getY());
        word.move();
      }
      
      
    }));
    
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();//TODO make this loop not start until the play button is clicked for the first time



    return scene;
  }

  @Override
  public void start(final Stage stage) {
    this.stage = stage;
    
    
    allWords = Utils.setUpWords(5);
    
    
    menuScreen = createMenuScreen();
    rulesScreen = createRulesScreen();
    gameScreen = createGameScreen();

    stage.setTitle("Typing Game");
    stage.setScene(menuScreen);
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch();
  }
}
