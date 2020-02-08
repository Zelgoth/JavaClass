import javafx.scene.image.Image;

/******************************************************
***  BlackPiece Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Subclass of Checker.  Used to handle black game
*** pieces.  The AI will us these pieces.
******************************************************
*** Start Date: 12/1/2018
******************************************************
*** Changes:
*** 12/5/2018 - changed all "setImage" lines to currentImage =
*** See code notes in the superclass Checker for more information.
******************************************************/
public class BlackPiece extends Checker{
    private Image black = new Image("black.png");
    private Image king = new Image("blackKing.png");
    private boolean Black;
    private boolean cantMove;
    
    public BlackPiece(int location){
        /******************************************************
        ***  BlackPiece
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Constructor.  Sets up the object with the correct image
        *** and flags.  Also hands location to super.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/5/2018 - setImage changed to "currentImage =".
        *** See class notes for reason.  Also changed Constructor
        *** in super being called.
        ******************************************************/
        super();
        this.location = location;
        Black = true;
        blackPieces++;
        currentImage = black;
    }
    
    @Override
    public boolean isBlack(){
        /******************************************************
        ***  isBlack
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Here to answer the question "Are you a black piece?"
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return Black;
    }
    
    @Override
    public boolean isKing(){
        return King;
    }
    
    @Override
    public void kingMe(){
        /******************************************************
        ***  kingMe
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Makes this piece a king piece.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/5/2018 - setImage changed to "currentImage =".
        *** See class notes for reason.
        ******************************************************/
        King = true;
        currentImage = king;
    }
    
    @Override
    public int killMe(){
        /******************************************************
        ***  killMe
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Makes this piece a basic game tile.
        *** Method Inputs: void
        *** Return value: integer
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/7/2018 - Made into an Override
        ******************************************************/
        setEmpty();
        Black = false;
        blackPieces--;
        return blackPieces;
    }
    
    @Override
    public boolean cantMove(){
        return cantMove;
    }
}