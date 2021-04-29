import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
// these imports are used for the First JavaFX Activity
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends Application {
  private BorderPane mainPane;
  private final int WIDTH = 600, HEIGHT = 600;
  private Scene menuScreen, rulesScreen, gameScreen;

  private Scene createMenuScreen() {
    Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
    
    // Display the title of the game top and center
    Label title = new Label("Jack's Typing Game");
    title.setFont(Font.font(28));
    BorderPane.setAlignment(title, Pos.CENTER);
    mainPane.setTop(title);

    // create the start, rules, and quit buttons
    Button startBtn = new Button("Start");
    startBtn.setOnAction(event -> {
      //TODO add scene change
    });
    Button rulesBtn = new Button("Rules");
    rulesBtn.setOnAction(event -> {
      //TODO add scene change
    });
    Button quitBtn = new Button("Quit");
    quitBtn.setOnAction(event -> { Platform.exit(); });
    
    // add all buttons to a VBox
    VBox btnBox = new VBox();
    btnBox.setSpacing(20);
    btnBox.setAlignment(Pos.CENTER);
    btnBox.getChildren().add(startBtn);
    btnBox.getChildren().add(rulesBtn);
    btnBox.getChildren().add(quitBtn);
    // add the button styles
    scene.getStylesheets().add("Styles.css");
    
    BorderPane.setAlignment(btnBox, Pos.CENTER);
    mainPane.setCenter(btnBox);
    
    return scene;
  }

  // TODO make a rules scene
  private Scene createRulesScreen() {
    return null;
  }
  
  // TODO: make a game scene
  private Scene createGameScreen() {
    return null;
  }

  @Override
  public void start(final Stage stage) {
    mainPane = new BorderPane();
    
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
