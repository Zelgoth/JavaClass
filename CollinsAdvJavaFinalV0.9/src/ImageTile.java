import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/******************************************************
***  Main Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Still need a class to handle events with.  This
*** class handles the things that Checkers used to
*** handle on the event side.
*** It saves its location to tell the program when it
*** is selected by the user, but is still used like an
*** ImageView as well.
******************************************************
*** Start Date: 12/5/2018
******************************************************
***Changes:
******************************************************/
public class ImageTile extends ImageView{
    //class variable location tells the GUI where the
    //corrisponding Checker piece is in the tile array.
    private int location;
    //this is not the same as isValid for pieces.  This
    //will only be false when the tile is a black square.
    private boolean isValid;
    
    public ImageTile(Image image, int location, boolean isValid){
        /******************************************************
        ***  ImageTile
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Constructor. Loads image and sets size automatically.
        *** Method Inputs: Image, integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        super(image);
        setFitHeight(100);
        setFitWidth(100);
        this.location = location;
        this.isValid = isValid;
    }
    
    public boolean isValid(){
        /******************************************************
        ***  isValid
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** tells the GUI if this is a valid board location.
        *** black squares will return false.
        *** Method Inputs: void
        *** Return value: boolean
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return isValid;
    }
    
    public int getLocation(){
        /******************************************************
        ***  getLocation
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** tells the GUI where to find the piece in GameMaster.
        *** Method Inputs: void
        *** Return value: integer
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        return location;
    }
}
