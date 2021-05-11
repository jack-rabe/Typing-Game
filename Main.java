import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
  private Stage stage;
  public final static int WIDTH = 700;
  public final static int HEIGHT = 500;
  private final static int FPS = 10;
  private Scene menuScreen, rulesScreen, gameScreen, gameOverScreen;
  
  private List<String> allWords = new ArrayList<String>();
  private List<Word> wordsOnScreen = new ArrayList<Word>();
  private int score = 0;
  private int health = 5;
  private int timeForNewWord = 10;
  private Word selectedWord = null;
  
  ImageView JUNGLE = new ImageView("jungle.jpg");
  Timeline timeline;
  
  Font GAMEFONT = new Font("arial", 24);
  Font SCOREFONT = new Font("arial", 26);
  Font RULESFONT = new Font("arial", 22);
  Font TITLEFONT = new Font("arial", 42);
  
  private Scene createGameOverScreen() {
    BorderPane mainPane = new BorderPane();
    mainPane.setStyle("-fx-background-color: black;");
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    scene.getStylesheets().add("Styles" + System.lineSeparator() + "gameOverStyles.css");
    
    // Display the title of the game top and center
    Label gameOver = new Label("Game Over!");
    BorderPane.setAlignment(gameOver, Pos.CENTER);
    mainPane.setCenter(gameOver);

    // create the return to menu and quit buttons
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
  
  private Scene createMenuScreen() {
    BorderPane mainPane = new BorderPane();
    mainPane.setStyle("-fx-background-color: blue;");
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    scene.getStylesheets().add("Styles" + System.lineSeparator() + "menuStyles.css");
    
    // Display the title of the game top and center
    Label title = new Label("Home");
    BorderPane.setAlignment(title, Pos.CENTER);
    mainPane.setTop(title);

    // create the start, rules, and quit buttons
    Button startBtn = new Button("Start");
    startBtn.setOnAction(event -> { stage.setScene(gameScreen); timeline.play(); });
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
    title.setFont(TITLEFONT);
    BorderPane.setAlignment(title, Pos.CENTER);
    mainPane.setTop(title);
    
    // display the explanatory rules text
    Label rules = new Label("This is a game for beginner typists to improve their"
        + " skills. Words will appear on the left side of the screen. You will be "
        + "able to type a single word at a time, and when you are finished, it will"
        + " disappear. If a word makes it all the way to the left side of the screen,"
        + " you will lose a life; once you lose five lives, the game is over.");
    rules.setFont(RULESFONT);
    rules.setWrapText(true);
    rules.setPadding(new Insets(14));
    mainPane.setCenter(rules);

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
    StackPane stackPane = new StackPane();
    Scene scene = new Scene(stackPane, WIDTH, HEIGHT);
    
    // create canvas and graphics context
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    // fit the background image to the screen size
    JUNGLE.fitWidthProperty().bind(scene.widthProperty()); 
    JUNGLE.setPreserveRatio(true);
    
    // add background image and canvas to the screen
    stackPane.getChildren().add(JUNGLE);
    stackPane.getChildren().add(canvas);
    
    // handle key presses (when user is typing words onscreen)
    scene.setOnKeyPressed(event -> {
      if (event.getText() == null) { return; }  // ensure some text has been entered
      char enteredChar = event.getText().charAt(0);
      List<Character> acceptableLetters = Utils.getAcceptableLetters(wordsOnScreen, selectedWord);

      if (!acceptableLetters.contains(enteredChar)) { return; }  // exit method if the character is invalid
      
      if (selectedWord != null) { // a word has already been selected
        if (enteredChar == selectedWord.getCurrentChar()) {
          selectedWord.charTyped();
        }
      }
      else { // no words are selected yet
        for (Word word : wordsOnScreen) {
          if (enteredChar == word.getCurrentChar()) {
            word.charTyped();
            selectedWord = word;
            break;
          }
        }
      }

      // remove a word that has been completely typed
      int wordLength = selectedWord.getContents().length();
      if (selectedWord != null && (selectedWord.getCurrentIndex() >= wordLength)) {
        wordsOnScreen.remove(selectedWord);
        selectedWord = null;
        score ++;
      }
    });

    // create the main game loop
    timeline = createTimeline(gc, canvas);

    return scene;
  }
  
  private Timeline createTimeline(GraphicsContext gc, Canvas canvas) {
    score = 0;
    health = 5;
    
    timeline = new Timeline(new KeyFrame(Duration.seconds(1.0 / FPS), event -> {
      if (health <= 0) {  // check to see if the player has already lost
        timeline.stop();
        timeline = createTimeline(gc, canvas);
        stage.setScene(gameOverScreen);
        wordsOnScreen.clear();
        selectedWord = null;
      }
      
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // clear canvas
      if (timeForNewWord == 0) { Utils.addWordToScreen(allWords, wordsOnScreen); }

      // display all current words and update their position, removing words that go offscreen
      gc.setFont(GAMEFONT);
      List<Word> wordsToRemove = new ArrayList<Word>();
      for (Word word : wordsOnScreen) {
        displayWord(gc, word);
        word.move();
        if (word.getX() <= 0) {  // remove words that go off the screen
          if (word.equals(selectedWord)) { selectedWord = null; }
          wordsToRemove.add(word);
        }
      }
      // removes words that go off the screen
      for (Word word : wordsToRemove){ 
        wordsOnScreen.remove(word); 
        health--;
        }

      // display the score and health remaining
      String scoreAsStr = "Score: " + score;
      String healthAsStr = "Health: " + health;
      gc.setFont(SCOREFONT);
      gc.setFill(Color.WHEAT);
      gc.fillRect(20, 0, 115, 30);
      gc.fillRect( WIDTH - 130, 0, 120, 30);
      gc.setFill(Color.BLACK);
      gc.fillText(scoreAsStr, WIDTH - 125, 25);
      gc.fillText(healthAsStr,  25,  25);

      updateTime(); // determines when new words will be added
    }));
    
    timeline.setCycleCount(Animation.INDEFINITE);
    
    return timeline;
  }
  
  private void updateTime() {
    if (timeForNewWord <= 0)
      timeForNewWord = 10;
    else
      timeForNewWord--;
  }
  
  private void displayWord(GraphicsContext gc, Word word) {
    if (word.equals(selectedWord)) {
      // calculate the offset between the typed and untyped parts of the word
      String typed = word.getContents().substring(0, word.getCurrentIndex());
      String untyped = word.getContents().substring(word.getCurrentIndex());
      Text typedText = new Text(typed);
      typedText.setFont(GAMEFONT);
      double typedLength = typedText.getLayoutBounds().getWidth();
      
      gc.setFill(Color.BLUE);
      gc.fillText(typed, word.getX(), word.getY());
      gc.setFill(Color.BLACK);
      gc.fillText(untyped, word.getX() + typedLength, word.getY());
    }
    
    else {  // this is not the selected word
      gc.setFill(Color.BLACK);
      gc.fillText(word.getContents(), word.getX(), word.getY());
    }
  }

  @Override
  public void start(final Stage stage) {
    this.stage = stage;
    allWords = Utils.setUpWords(6);
    
    menuScreen = createMenuScreen();
    rulesScreen = createRulesScreen();
    gameScreen = createGameScreen();
    gameOverScreen = createGameOverScreen();

    stage.setTitle("Typing Game");
    stage.setScene(menuScreen);
    stage.show();
  }

  public static void main(String[] args) {
    Application.launch();
  }
}
