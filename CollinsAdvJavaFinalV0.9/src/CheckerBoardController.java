import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/******************************************************
***  Main Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Runs the main GUI.  Handles Game board rendering
******************************************************
*** Start Date: 11/25/2018
******************************************************
*** Changes:
*** 12/2/2018 - moved game code to GameMaster for cleaner
*** coding.
*** 12/2/2018 - moved game code back because FX doesn't like
*** called classes to change more then one GUI item at a time.
*** Still planing to pull out game code, but have to rewrite
*** other classes and the "refresh" method first.
*** 12/5/2018 - Pulled game code out to GameMaster.  Restructured
*** game board to be refreshed from scratch every game cycle.
*** This way I can have less non-GUI code in this class.
*** Change was made possible by removing the extention of
*** ImageView from Checker.
******************************************************/

public class CheckerBoardController {
    //built into GUI items
    @FXML private GridPane gpBoard;
    @FXML private Label lbScore;
    //hard coded GUI componets.
    ImageTile[] newTile;
    
    //game master holds all the game code.
    GameMaster gm;
    
    //game state.  -1 means still going. 0 is lost, 1 is won.
    int gameState;

    EventHandler boardEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                /******************************************************
                ***  handle
                ***  Michael Collins
                ******************************************************
                *** Purpose of the Method 
                *** handle is a method in EventHandler.  This will handle
                *** all the board squares in this case when ever a mouse
                *** click is released on a square.
                *** Method Inputs: MouseEvent (mouse released)
                *** Return value: void
                ******************************************************
                *** Date: 11/30/2018
                ******************************************************
                *** Change Log:
                *** 12/1/2018 - removed testing code, and added a pass
                *** of event source to selected.
                *** 12/3/2018 - added refresh call to update game board.
                *** 12/5/2018 - changed this to check for ImageTile, instead
                *** of Checker, and to hand results to GameMaster.
                ******************************************************/
                ImageTile tile;
                if(event.getSource() instanceof ImageTile){
                    tile = (ImageTile)event.getSource();
                    if(tile.isValid()){
                        gameState = gm.selected(tile.getLocation());
                        refresh();
                    }
                }
            }
        };
    
    @FXML
    void closeSelected(ActionEvent event) {
        /******************************************************
        ***  closeSelected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Allows the user to close the program via file menu.
        *** Method Inputs: ActionEvent
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        Platform.exit();
    }

    @FXML
    void newGameSelected(ActionEvent event) {
        /******************************************************
        ***  newGameSelected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Allows the user to start a new game via file menu.
        *** Method Inputs: ActionEvent
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        *** Change Log:
        *** 11/29/2018 - Added newBoard method code
        *** 12/5/2018 - Changes to code made GameMaster build
        *** the boards in its Constructor.  This way all I need
        *** to do here is make a new GameMaster and refresh.
        *** Change was important for handling the AI.
        ******************************************************/
        gm = new GameMaster();
        gpBoard.setDisable(false);
        gameState = -1;
        refresh();
    }

    @FXML
    void rulesSelected(ActionEvent event) throws IOException {
        /******************************************************
        ***  rulesSelected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Allows the user to open a rules window via the help
        *** menu.
        *** Method Inputs: ActionEvent
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        Parent root = FXMLLoader.load(getClass().getResource("ruleWindow.fxml"));
        
        Stage stage = new Stage(); //makes a new stage
        stage.setTitle("Checkers Game Rules"); //Displayed in window's title bar
        stage.setScene(new Scene(root)); //attach scene to stage
        stage.initStyle(StageStyle.UTILITY);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show(); //display the stage
    }
    
    @FXML
    void initialize() {
        /******************************************************
        ***  initialize
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** FX GUI constructor will call this on start up.
        *** This method needs to set up the game board, and
        *** all scores.
        *** Method Inputs: ActionEvent
        *** Return value: void
        ******************************************************
        *** Date: 11/25/2018
        ******************************************************
        *** Change Log:
        *** 11/29/2018 - Added newBoard method code
        *** 12/3/2018 - Added a initiator for selected with a blank
        *** game piece.
        *** 12/5/2018 - Moved game code to GameMaster.  This just
        *** starts a new GameMaster and refreshes now.
        ******************************************************/
        gm = new GameMaster();
        gameState = -1;
        refresh();
    }
    
    private void refresh(){
        /******************************************************
        ***  refresh
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** clears and reloads the game board.
        *** Method Inputs: ActionEvent
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/3/2018 - added clear to method to resolve some bugs
        *** that come from resetting the grid.
        *** 12/5/2018 - a lot changed here.  It still builds the
        *** same board, just out of ImageTile now instead of Checker.
        *** Some additional checks where added to make Event handling
        *** more efficiently.
        *** also resets Event Handlers.  This may be a bad idea if
        *** Java doesn't clean up all the unused ImageTiles...
        *** Or I could just go back to all the code in one Class...
        ******************************************************/
        newTile = new ImageTile[64];
        
        //IMPORTANT!!  This adds action event handlers.
        for(int i = 0; i < 64; i++){
            newTile[i] = new ImageTile(gm.tile[i].getImage(), i, gm.tile[i].isMoveable);
            newTile[i].setOnMouseReleased(boardEvent);
        }
        
        gpBoard.getChildren().clear();
        int j = -1;
        for(int i = 0; i < 64; i++){
            if(i%8 == 0) j++;
            gpBoard.add(newTile[i], j, i%8);
        }
        if(gameState == -1) lbScore.setText("Turn: " + gm.turn);
        else if(gameState == 1){
            lbScore.setText("You have won after " + gm.turn + " turns!");
            gpBoard.setDisable(true);
        }
        else{
            lbScore.setText("You have Lost!");
            gpBoard.setDisable(true);
        }
    }
}