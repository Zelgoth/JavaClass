import javafx.scene.image.Image;

/******************************************************
***  Checker Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Subclass of ImageView.  Super class of game pieces.
*** This method handles all shared data between all board
*** tile types, movable and un-movable.
******************************************************
*** Start Date: 11/30/2018
******************************************************
***Changes:
*** 12/1/2018 - added location and a new constructor.
*** 12/2/2018 - added isSelected option to handle moving
*** and highlighting pieces easier.
*** 12/5/2018 - changed this class to no longer extend
*** image view.  The idea worked fine until I got to
*** more complex stages of changing variables.  This
*** object will still handle all the images and code
*** related to what image should be shown, but the
*** actual ImageViews will now be built fresh on refresh
*** every game cycle. Added currentImage and getImage()
*** method to handle this change.
******************************************************/

public class Checker{
    //double inharetaince, yay.
    //basically this is all here to be overriden
    public static int blackPieces = 0;
    public static int whitePieces = 0;
    //Base Image
    private Image empty = new Image("whiteCheckersGrid.png");
    protected Image moveOption = new Image("moveable.png");
    
    //current set image to use.
    protected Image currentImage;
    
    //selected boolean
    public boolean selected;

    //king boolean
    protected boolean King;
    //moveable location boolean
    protected boolean isValid; //answer to "can a piece move here NOW?"
    public boolean isMoveable; //answer to "can a piece EVER move here?"
    //saves location, needs to be updated on move
    public int location;
    
    public Checker(){
        /******************************************************
        ***  Checker
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Constructor for Checker.  This is to be used to build
        *** a blank piece with no identity, used as a check to
        *** prevent null exceptions.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 11/30/2018
        ******************************************************
        ***Change Log:
        *** 12/3/2018 - added selected initializer to resolve
        *** more null pointer exceptions.
        *** 12/5/2018 - added isMovable and now use this to handle
        *** building game pieces.
        ******************************************************/
        King = false;
        isValid = false;
        selected = false;
        isMoveable = true;
    }
    
    public Checker(boolean isValid, int location){
        /******************************************************
        ***  Checker
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Used to construct a Checker with a location and type
        *** identity.
        *** Method Inputs: boolean, integer
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        *** 12/2/2018 - added selected initializer.
        *** 12/5/2018 - changed setImage to "currentImage =" since
        *** this no longer extends ImageView.
        ******************************************************/
        this.location = location;
        this.isValid = isValid;
        selected = false;
        if(isValid){
            setEmpty();
            isMoveable = true;
        }
        else{
            currentImage = new Image("blackCheckersGrid.png");
            isMoveable = false;
        }
    }
    
    public void resetPieces(){
        /******************************************************
        ***  resetPieces
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** used to reset static variables to 0 when game is
        *** being restarted.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 11/30/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        blackPieces = 0;
        whitePieces = 0;
    }
    
    protected void setEmpty(){
        /******************************************************
        ***  setEmpty
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Used to reset image to the white tile image.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        *** Change Log:
        *** 12/5/2018 - setImage changed to "currentImage =".
        *** See class notes for reason.
        ******************************************************/
        //sets moveable to now.
        isValid = true;
        //sets to defalt image (white square)
        currentImage = empty;
    }
    
    public void isSelected(boolean selected){
        /******************************************************
        ***  isSelected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** used to handle moving and highlighting moves.
        *** Method Inputs: boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        *** 12/5/2018 - setImage changed to "currentImage =".
        *** See class notes for reason.
        ******************************************************/
        this.selected = selected;
        if(selected){
            currentImage = moveOption;
        }
        else{
            currentImage = empty;
        }
    }
    
    public void kingMe(){
        /******************************************************
        ***  kingMe
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** makes a piece a king.  Does nothing in Checkers
        *** though.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //just here to be Overriden.
        King = false;
    }
    
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
        ***Change Log:
        ******************************************************/
        return false;
    }
    
    public boolean isBlack(){
        /******************************************************
        ***  isBlack
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Here to answer the question "Are you a black piece?"
        *** Method Inputs: void
        *** Return value: boolean
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return false;
    }
    
    public boolean isKing(){
        /******************************************************
        ***  isKing
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Here to answer the question "Are you a king piece?"
        *** Method Inputs: void
        *** Return value: boolean
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return false;
    }
    
    public Image getImage(){
        /******************************************************
        ***  getImage
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Returns the image in currentImage
        *** Method Inputs: void
        *** Return value: Image
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return currentImage;
    }
    
    public int killMe(){
        /******************************************************
        ***  killMe
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Here as an Override.  Returns -1 as a dead value.
        *** The return will brake the game if something else
        *** is coded wrong.  Acts as a check as well, where
        *** the player will win early if something goes wrong.
        *** Method Inputs: void
        *** Return value: Image
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return -1;
    }
    
    public boolean cantMove(){
        return true;
    }
}
