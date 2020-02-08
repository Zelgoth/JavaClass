import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************
***  Main Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Starts the program and GUI
******************************************************
*** Start Date: 11/25/2018
******************************************************
***Changes:
******************************************************/
public class Main extends Application{
    
    public static void main(String[] args) {
        /******************************************************
        ***  main
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Quits and hands args to launch.  launch calls start.
        *** Method Inputs: args
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        /******************************************************
        ***  Start
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** start is a method in the Application class.  It builds
        *** the FX GUI from the FXML file.  Due to how the FXML file
        *** is referenced, it has to be in the default folder.  There
        *** is a way around this, but it takes some doing.
        ***
        *** Once the GUI is built, it makes and calls LetterCountController to
        *** handle its events and coding.
        *** Method Inputs: stage
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        Parent root = FXMLLoader.load(getClass().getResource("CheckersBoard.fxml"));
        
        Scene scene = new Scene(root); //attach scene graph to scene
        stage.setTitle("Checkers"); //Displayed in window's title bar
        stage.setScene(scene); //attach scene to stage
        stage.resizableProperty().setValue(Boolean.FALSE); //kills maximize button.
        stage.show(); //display the stage
    }
}
