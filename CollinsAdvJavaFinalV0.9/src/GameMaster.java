
import java.util.Random;

/******************************************************
***  GameMaster Class
***  Michael Collins
******************************************************
*** Purpose of the class
*** Handle core game coding.  Also contains all the AI
*** code for the computer player.  The computer player
*** is a 'simple' dumb AI that randomly selects valid
*** game pieces and moves them.  AI does follow the
*** stated game rules though.
******************************************************
*** Start Date: 12/2/2018
******************************************************
***Changes:
*** 12/2/2018 - moved and recoded game code from
*** CheckerBoardController to this class.
*** Then moved it back because issues...
*** 12/5/2018 - moved and recoded game code from
*** CheckerBoardController after removing the extention
*** of ImageView on Checker.  This solved the previous
*** issues.  Now we just build new ImageViews for every
*** tile on refresh after each game cycle.
*** 12/7/2018 - implemented player piece jumping.
******************************************************/
public class GameMaster {
    //holds game board
    public Checker[] tile;
    
    //player selected peice
    private Checker selected;
    
    //keeps track of player and AI pieces
    //this value only affects code when it is 0,
    //so setting it to 1 keeps that from happening.
    private int bPieces = 1;
    private int wPieces = 1;
    
    //game check flags
    private boolean canJump;
    
    //move and turn counters
    private int moves;
    public int turn;

    //random number generator for AI
    Random rnd = new Random();
    
    public GameMaster(){
        newBoard();
        selected = new Checker();
        turn = 1;
    }
    
    private void newBoard(){
        /******************************************************
        ***  newBoard
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Builds a new game board, and adds Mouse Listeners to
        *** every square.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 11/29/2018
        ******************************************************
        ***Change Log:
        ***12/1/2018 - moved game board loading code to the
        *** refresh method.  Restructured loading order, and
        *** pulled loading code into 2 methods:
        *** colBuildA and colBuildB
        *** This shortened the code by about 60 lines
        ******************************************************/
        
        //builds a new game board.
        tile = new Checker[64];
        
        //resets piece count
        Checker tmp = new Checker(false, -1);
        tmp.resetPieces();
        
        for(int i = 0; i < tile.length;){
            i = colBuildA(i);
            i = colBuildB(i);
        }
        canJump = false;
    }
    
    private int colBuildA(int i){
        /******************************************************
        ***  colBuildA
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Builds a game row starting at i.  passes back the
        *** next available game opening.
        *** Method Inputs: integer
        *** Return value: integer
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //builds a column starting with a black square
        int start = i;
        //adds invalid tiles
        for(; i < start + 8; i = i + 2){
            tile[i] = new Checker(false, i);
        }
        //adds valid tiles + pieces
        tile[start+1] = new BlackPiece(start+1);
        tile[start + 3] = new Checker(true, start + 3);
        tile[start + 5] = new WhitePiece(start + 5);
        tile[start + 7] = new WhitePiece(start + 7);
        return (start + 8);
    }
    
    private int colBuildB(int i){
        /******************************************************
        ***  colBuildB
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Builds a game row starting at i.  passes back the
        *** next available game opening.
        *** Method Inputs: integer
        *** Return value: integer
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //builds a column starting with a white square
        int start = i;
        //adds invalid tiles
        for(i = i + 1; i < start + 8; i = i + 2){
            tile[i] = new Checker(false, i);
        }
        //adds valid tiles + pieces
        tile[start] = new BlackPiece(start);
        tile[start + 2] = new BlackPiece(start + 2);
        tile[start + 4] = new Checker(true, start + 4);
        tile[start + 6] = new WhitePiece(start + 6);
        return (start + 8);
    }
    
    //selected stuff
    public int selected(int index){
        /******************************************************
        ***  selected
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** This is the main game controller.  It checks user
        *** selected grid squares to see if a change needs to
        *** be handled.  (Enforces game rules.)
        *** Method Inputs: Checker
        *** Return value: void
        ******************************************************
        *** Date: 12/1/2018
        ******************************************************
        ***Change Log:
        *** 12/2/2018 - added highlightMoves, and deselect
        ***
        *** 12/3/2018 - moved deselect to top of each section.
        *** It was not functioning correctly.
        ***
        *** 12/7/2018 - changed to return an integer. -1 for game
        *** still running, 0 for player lost, and 1 for player win.
        ******************************************************/
        int gameState = -1;
        Checker pc = tile[index];
        if(pc.isWhite()){
            if(pc.selected){
                deselect();
            }
            else{
                deselect();
                pc.isSelected(true);
                selected = pc;
                highlightMoves(selected.location, true);
            }
        }
        else if(pc.selected){
            deselect();
            Checker tmp = pc;
            int tmpLoc = pc.location;
            int tmpOG = selected.location;
            tmp.location = selected.location;
            selected.location = tmpLoc;
            tile[tmpLoc] = selected;
            tile[tmp.location] = pc;
            System.out.println("Whites Turn - Piece at " + tmpOG + " moved to " + tmpLoc + ".");
            //King Check
            if(tile[tmpLoc].location%8 == 0) tile[tmpLoc].kingMe();
            
            //if canJump, finds the piece that needs to die.
            if(canJump){
                jumpCheck(tmpLoc, tmpOG);
            }
            if(!canJump){
                    if(bPieces > 0){
                        //run AI
                        AIgo();
                        if(wPieces == 0) return 0;  //player loses
                        turn++;
                    }
                    else return 1;  //player wins
                }
                else{
                    gameState = selected(tmpLoc);
                }
        }
        return gameState;
    }
    
    private void deselect(){
        /******************************************************
        ***  deselect
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** deselects a selected piece and un-highlights all
        *** available moves.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        if(selected.selected){
            selected.isSelected(false);
            highlightMoves(selected.location, false);
        }
    }
    
    private void highlightMoves(int pos, boolean check){
        /******************************************************
        ***  highlightMoves
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** highlights moves that are available to a piece.
        *** Method Inputs: integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        if(canJump){
            jumpNorthH(pos, check);
            if(selected.King){
                jumpSouthH(pos, check);
            }
        }
        else{
            moveNorthH(pos, check);
            if(selected.King){
                moveSouthH(pos, check);
            }
        }
    }
    
    private void jumpNorthH(int pos,boolean check){
        /******************************************************
        ***  jumpNorthH
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** highlights possible jumps, if one is found moving
        *** north from selected piece.
        *** Method Inputs: integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 7].isBlack()){
                if(tile[pos + 14].isValid){
                    tile[pos + 14].isSelected(check);
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorthH");
        }
        //checks left jump
        try{
            if(tile[pos - 9].isBlack()){
                if(tile[pos - 18].isValid){
                    tile[pos - 18].isSelected(check);
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorthH");
        }
    }
    
    private void jumpSouthH(int pos, boolean check){
        /******************************************************
        ***  jumpSouthH
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** highlights possible jumps, if one is found moving
        *** north from selected piece.
        *** Method Inputs: integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 9].isBlack()){
                if(tile[pos + 18].isValid){
                    tile[pos + 18].isSelected(check);
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouthH");
        }
        //checks left jump
        try{
            if(tile[pos - 7].isBlack()){
                if(tile[pos - 14].isValid){
                    tile[pos - 14].isSelected(check);
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouthH");
        }
    }
    
    private void jumpNorth(int pos){
        /******************************************************
        ***  jumpNorth
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Counts possible jumps.  This method is to enforce
        *** game rule: If you can jump, you must.
        *** Method Inputs: integer
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 7].isBlack()){
                if(tile[pos + 14].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorth");
        }
        //checks left jump
        try{
            if(tile[pos - 9].isBlack()){
                if(tile[pos - 18].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorth");
        }
    }
    
    private void jumpNorthB(int pos){
        /******************************************************
        ***  jumpNorth
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Counts possible jumps.  This method is to enforce
        *** game rule: If you can jump, you must.
        *** Method Inputs: integer
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 7].isWhite()){
                if(tile[pos + 14].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorth");
        }
        //checks left jump
        try{
            if(tile[pos - 9].isWhite()){
                if(tile[pos - 18].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpNorth");
        }
    }
    
    private void jumpSouth(int pos){
        /******************************************************
        ***  jumpSouthW
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Counts possible jumps.  This method is to enforce
        *** game rule: If you can jump, you must. Added to fix a king bug.
        *** Method Inputs: integer
        *** Return value: void
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        *** Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 9].isWhite()){
                if(tile[pos + 18].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
        //checks left jump
        try{
            if(tile[pos - 7].isWhite()){
                if(tile[pos - 14].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
    }
    
    private void jumpSouthW(int pos){
        /******************************************************
        ***  jumpSouthW
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Counts possible jumps.  This method is to enforce
        *** game rule: If you can jump, you must. Added to fix a king bug.
        *** Method Inputs: integer
        *** Return value: void
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        *** Change Log:
        ******************************************************/
        
        //checks right jump.
        try{
            if(tile[pos + 9].isBlack()){
                if(tile[pos + 18].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
        //checks left jump
        try{
            if(tile[pos - 7].isBlack()){
                if(tile[pos - 14].isValid){
                    canJump = true;
                    moves++;
                }
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't jump that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
    }
    
    private void moveNorthH(int pos,boolean check){
        /******************************************************
        ***  moveNorthH
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** highlights possible moves, if one is found moving
        *** north from selected piece.
        *** Method Inputs: integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right move
        if(pos + 7 < 64){
            if(tile[pos + 7].isValid){
                tile[pos + 7].isSelected(check);
            }
        }

        //checks left move
        if(pos - 9 >= 0){
            if(tile[pos - 9].isValid){
                tile[pos - 9].isSelected(check);
            }
        }
    }
    
    private void moveSouthH(int pos, boolean check){
        /******************************************************
        ***  moveSouthH
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** highlights possible moves, if one is found moving
        *** south from selected piece.
        *** Method Inputs: integer, boolean
        *** Return value: void
        ******************************************************
        *** Date: 12/2/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        
        //checks right move.
        try{
            if(tile[pos + 9].isValid){
                tile[pos + 9].isSelected(check);
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't move that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
        
        //checks left move
        try{
            if(tile[pos - 7].isValid){
                tile[pos - 7].isSelected(check);
            }
        }
        catch(IndexOutOfBoundsException IOFBe){
            //just means you can't move that way.
        }
        catch(Exception e){
            System.out.println("Unknow error calculating jumpSouth");
        }
    }
    
    private void jumpCheck(int newPos, int ogPos){
        /******************************************************
        ***  jumpCheck
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Handles player made jumps.  Removes pieces, and checks
        *** for an additional jump.  returns true if no moves left,
        *** and false if the piece can jump again (Rules say you have
        *** to jump).
        *** Method Inputs: integer, integer
        *** Return value: boolean
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        int check = newPos - ogPos;
        bPieces = tile[ogPos + (check/2)].killMe();
        System.out.println("Whites Turn - piece at " + (ogPos + (check/2)) + " killed.");
        canJump = false; //resets the class level variable for checks.
        if(tile[newPos].King){
            jumpSouthW(newPos);
        }
        jumpNorth(newPos);
    }
 
    private void AIgo(){
        /******************************************************
        ***  AIgo
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Handles AI code when the AI gets to go.
        *** Method Inputs: void
        *** Return value: void
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        //builds an array of only black pieces.
        Checker[] pc = new Checker[selected.blackPieces];
        
        int j = 0;
        for(int i = 0; i < tile.length; i++){
            if(tile[i].isBlack()){
                pc[j] = tile[i];
                j++;
                if(j == pc.length){
                    //stops it from checking the whole array if all pieces are found
                    break;
                }
            }
        }
        //finds out if any of the black pieces can jump.
        for(int i = 0; i < pc.length; i++){
            if(pc[i].isKing()){
                jumpNorthB(pc[i].location);
            }
            jumpSouth(pc[i].location);
        }
        moveAI(pc);

        //out of place code?
        //finds out if white can jump.
        Checker[] whiteList = new Checker[selected.whitePieces];
        int jm = 0;
        for(int i = 0; i < tile.length; i++){
            if(tile[i].isWhite()){
                whiteList[jm] = tile[i];
                jm++;
                if(jm == whiteList.length){
                    //stops it from checking the whole array if all pieces are found
                    break;
                }
            }
        }
        //finds out if any of the white pieces can jump.
        for(int i = 0; i < whiteList.length; i++){
            if(whiteList[i].isKing()){
                jumpSouthW(whiteList[i].location);
            }
            jumpNorth(whiteList[i].location);
        }
    }
    
    private void moveAI(Checker[] pieceList){
        /******************************************************
        ***  moveAI
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Tries to move a pieces and returns false if the piece
        *** has no valid moves.
        *** Method Inputs: Checker[]
        *** Return value: void
        ******************************************************
        *** Date: 12/5/2018
        ******************************************************
        *** Change Log:
        *** 12/7/2018 - to handle jumps and kings, I moved the while
        *** loop into this method, and turned it from a boolean,
        *** to a void return.  The flag check is done inside the
        *** method now.
        ******************************************************/
        //gets possible moves.
        int pos;
        boolean hasMoved = false;
        int[] moves;
        int moveTo;
        while(!hasMoved){
            pos = pieceList[rnd.nextInt(pieceList.length)].location;
            moves = checkBounds(pos);  //returns jumps too.
            
            if(moves.length > 0){
                moveTo = rnd.nextInt(moves.length);
                Checker pc = tile[moves[moveTo]];
                Checker moveHere = tile[pos];
                Checker tmp = pc;
                int tmpLoc = pc.location;
                tmp.location = moveHere.location;
                moveHere.location = tmpLoc;
                tile[tmpLoc] = moveHere;
                tile[tmp.location] = pc;
                hasMoved = true;
                System.out.println("Blacks Turn - Piece at " + pos + " moved to " + tmpLoc + ".");
                //king check
                if(tile[tmpLoc].location%8 == 7) tile[tmpLoc].kingMe();
                
                if(canJump){
                    jumpCheckAI(tmpLoc, pos);
                    if(canJump){
                        pos = tmpLoc;
                        hasMoved = false;
                        moves = checkBounds(pos);
                    }
                }
            }
        }
    }
    
    private int[] checkBounds(int pos){
        /******************************************************
        ***  checkBounds
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** builds a list of all AI moves for a given piece.
        *** if canJump is true, only returns a list if the piece
        *** can jump.  Otherwise it returns a list the length
        *** of avaliable jumps.
        *** Method Inputs: integer, integer
        *** Return value: boolean
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        int[] bounds;
        bounds = moveBounds(pos);
        if(tile[pos].isKing()){
            bounds = moveBoundsKing(pos, bounds);
        }
        return bounds;
    }
    
    private int[] moveBounds(int pos){
        int move1;
        int move2;
        int[] i = new int[0];
        if(canJump){
            move1 = pos + 18;
            move2 = pos - 14;
            int check1 = pos + 9;
            int check2 = pos -7;
            if(move1 < 64 && move2 >= 0){
                if(tile[check1].isWhite() && tile[check2].isWhite()
                        && tile[move1].isValid && tile[move2].isValid){
                    i = new int[2];
                    i[0] = move1;
                    i[1] = move2;
                }
                else if(tile[check1].isWhite() && tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
                else if(tile[check2].isWhite() && tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
            else if(move1 < 64){
                if(tile[check1].isWhite() && tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
            }
            else if(move2 >= 0){
                if(tile[check2].isWhite() && tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
        }
        else{
            move1 = pos + 9;
            move2 = pos - 7;
            if(move1 < 64 && move2 >= 0){
                if(tile[move1].isValid && tile[move2].isValid){
                    i = new int[2];
                    i[0] = move1;
                    i[1] = move2;
                }
                else if(tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
                else if(tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
            else if(move1 < 64){
                if(tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
            }
            else if(move2 >= 0){
                if(tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
        }
        return i;
    }
    
    private int[] moveBoundsKing(int pos, int[] baseMoves){
        int move1;
        int move2;
        int[] i = new int[0];
        if(canJump){
            move1 = pos + 14;
            move2 = pos - 18;
            int check1 = pos + 7;
            int check2 = pos - 9;
            if(move1 < 64 && move2 >= 0){
                if(tile[check1].isWhite() && tile[check2].isWhite()
                        && tile[move1].isValid && tile[move2].isValid){
                    i = new int[2];
                    i[0] = move1;
                    i[1] = move2;
                }
                else if(tile[check1].isWhite() && tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
                else if(tile[check2].isWhite() && tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
            else if(move1 < 64){
                if(tile[check1].isWhite() && tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
            }
            else if(move2 >= 0){
                if(tile[check2].isWhite() && tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
        }
        else{
            move1 = pos + 7;
            move2 = pos - 9;
            if(move1 < 64 && move2 >= 0){
                if(tile[move1].isValid && tile[move2].isValid){
                    i = new int[2];
                    i[0] = move1;
                    i[1] = move2;
                }
                else if(tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
                else if(tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
            else if(move1 < 64){
                if(tile[move1].isValid){
                    i = new int[1];
                    i[0] = move1;
                }
            }
            else if(move2 >= 0){
                if(tile[move2].isValid){
                    i = new int[1];
                    i[0] = move2;
                }
            }
        }
        kingMovesShuffleAI(baseMoves, i);
        return i;
    }
    
    private void jumpCheckAI(int newPos, int ogPos){
        /******************************************************
        ***  jumpCheckAI
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** Handles computer made jumps.  Removes pieces, and checks
        *** for an additional jump.  resets canJump flag.
        *** Method Inputs: integer, integer
        *** Return value: void
        ******************************************************
        *** Date: 12/7/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        int check = newPos - ogPos;
        wPieces = tile[ogPos + (check/2)].killMe();
        System.out.println("Blacks Turn - piece at " + (ogPos + (check/2)) + " killed.");
        canJump = false; //resets the class level variable for checks.
        if(tile[newPos].King){
            jumpNorth(newPos);
        }
        jumpSouth(newPos);
    }
    
    private int[] kingMovesShuffleAI(int[] base, int[] additional){
        /******************************************************
        ***  kingMovesShuffleAI
        ***  Michael Collins
        ******************************************************
        *** Purpose of the Method 
        *** method combines standard moves with additional king
        *** moves in a way that the move selector doesn't need
        *** to change.
        *** Method Inputs: integer, integer
        *** Return value: integer
        ******************************************************
        *** Date: 12/8/2018
        ******************************************************
        ***Change Log:
        ******************************************************/
        int[] fullList = new int[base.length + additional.length];
        for(int i = 0; i < base.length; i++){
            fullList[i] = base[i];
        }
        if(additional.length != 0){
            int k = 0;
            for(int j = base.length; j < fullList.length; j++){
                fullList[j] = additional[k++];
            }
        }
        return fullList;
    }
}
