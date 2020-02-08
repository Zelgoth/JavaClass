import javafx.scene.image.Image;

/******************************************************
***  WhitePiece Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Subclass of Checker.  Used to handle white game
*** pieces.  The black and white game pieces are examples
*** of polymorphism.
******************************************************
*** Start Date: 12/1/2018
******************************************************
*** Changes:
*** 12/5/2018 - changed all "setImage" lines to currentImage =
*** See code notes in the superclass Checker for more information.
******************************************************/
public class WhitePiece extends Checker{
    private Image white = new Image("white.png");
    private Image king = new Image("whiteKing.png");
    private Image moveable = new Image("whiteSelected.png");
    private boolean White;
       
    public WhitePiece(int location){
        /******************************************************
        ***  WhitePiece
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
        White = true;
        whitePieces++;
        currentImage = white;
    }
    
    @Override
    public boolean isWhite(){
        /******************************************************
        ***  isWhite
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Here to answer the question "Are you a white piece?"
        *** Method Inputs: void
        *** Return value: boolean
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        ******************************************************/
        return White;
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
    public void isSelected(boolean selected){
        /******************************************************
        ***  isSelected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Highlights the tile or un-highlights the tile as a
        *** user selects or deselects it.
        *** Method Inputs: void
        *** Return value: integer
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/5/2018 - setImage changed to "currentImage =".
        *** See class notes for reason.
        ******************************************************/
        this.selected = selected;
        if(selected && White){
            currentImage = moveable;
        }
        else if(selected){
            currentImage = moveOption;
        }
        else if(White){
            if(King){
                currentImage = king;
            }
            else{
                currentImage = white;
            }
        }
        else{
            setEmpty();
        }
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
        *** 12/7/2018 - made into an Override
        ******************************************************/
        setEmpty();
        White = false;
        whitePieces--;
        return whitePieces;
    }
}
