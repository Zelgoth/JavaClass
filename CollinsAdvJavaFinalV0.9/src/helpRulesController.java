import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class helpRulesController {
    @FXML private TextArea ta_rules;
    @FXML private Button okButton;

    @FXML
    void closeButton(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        ta_rules.setEditable(false);
        ta_rules.setText("Setup and Object of Checkers\n"
                + "\n"
                + "Checkers is played on a standard 64 square board. Only the 32 light colored squares are used in play."
                + " Each player begins the game with 12 pieces, or checkers, placed in the three rows closest to him or her. \n"
                + "\n"
                + "The object of the game is to capture all of your opponent’s checkers or position your pieces so that your opponent has no available moves. \n"
                + "\n" +
                "\n" +
                "Movement\n" +
                "\n" +
                "Basic movement is to move a checker one space diagonally forward. You can not move a checker backwards until it becomes a King, as described below. If a jump is available, you must take the jump, as described in the next section. \n" +
                "\n" +
                "\n" +
                "Jumping\n" +
                "\n" +
                "If one of your opponent’s checkers is on a forward diagonal next to one of your checkers, and the next space beyond the opponent’s checker is empty, then your checker must jump the opponent’s checker and land in the space beyond. Your opponent’s checker is captured and removed from the board. \n" +
                "\n" +
                "After making one jump, your checker might have another jump available from its new position. Your checker must take that jump too. It must continue to jump until there are no more jumps available. Both men and kings are allowed to make multiple jumps. \n" +
                "\n" +
                "If, at the start of a turn, more than one of your checkers has a jump available, then you may decide which one you will move. But once you have chosen one, it must take all the jumps that it can."
                + "\n\nCrowning\n" +
                "\n" +
                "When one of your checkers reaches the opposite side of the board, it is crowned and becomes a King. Your turn ends there. \n" +
                "\n" +
                "A King can move backward as well as forward along the diagonals. It can only move a distance of one space. \n" +
                "\n" +
                "A King can also jump backward and forward. It must jump when possible, and it must take all jumps that are available to it. In each jump, the King can only jump over one opposing piece at a time, and it must land in the space just beyond the captured piece. The King can not move multiple spaces before or after jumping a piece. ");
    }
}
