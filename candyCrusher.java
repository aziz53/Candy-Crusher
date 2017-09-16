/* 
 * [Candycrasher.java]
 * Ali Meshkat 
 * finds the swap that results in the most number of points 
 * finds the number of valid number of swaps
 * April 2, 2017
 */
import java.io.File;
import java.util.Scanner;
class CandyCrusher{
  public static void main(String args[])throws Exception{
    
    //creates scanners
    Scanner input = new Scanner(System.in);
    File gameBoard = new File("board.txt");
    Scanner file = new Scanner(gameBoard);

    //gets dimentions of board from file
    int dimention1 =  file.nextInt();  //rows
    int dimention2 = file.nextInt();   //columns
    file.nextLine();  //avoids next line blues
    
    char[][] board = new char[dimention1][dimention2];  // board array
 
    //creates the array 
    String line;
     for (int i = 0; i <= dimention1-1; i++){
      line = file.nextLine();
      for (int j = 0; j<= dimention2-1; j++){
        board[i][j] = line.charAt(j);
      }
    }
       
       
    ///checks all possible swaps, counts the points, stores the best and its coordinates
    ///and counts the number of valid moves(+3 points)
     char temp; 
     int points;
     int validMovesNum = 0;
     int maxPoints = 0;
     //coordinates of best swap
     int maxRow1 = 0;
     int maxColumn1 = 0 ;
     int maxRow2 = 0;
     int maxColumn2 = 0;
     //
     for (int i = 0; i <= dimention1-1; i++){
       for (int j = 0 ; j <= dimention2-1; j++){
         
         if (j != dimention2-1){  // if a horizontal swap to right is possible 

           temp = board[i][j];  //swaps the letters
           board[i][j] = board[i][j+1];
           board[i][j+1] = temp;
           
           points = pointsChecker(board, i, j, i, j+1, dimention1, dimention2); // counts points
           
           if (points > maxPoints){ //stores best move
             maxPoints = points;
             maxRow1 = i;
             maxColumn1 = j;
             maxRow2 = i;
             maxColumn2 = j+1;
           }
           
           if (points > 2){   //counts valid moves 
             validMovesNum++;
           }
           
           temp = board[i][j];   // undos swap 
           board[i][j] = board[i][j+1];
           board[i][j+1] = temp;
         }
         
         if (i != dimention1-1){ // if a downwards swap is possible 
           temp = board[i][j];
           board[i][j] = board[i+1][j];
           board[i+1][j] = temp;
           points = pointsChecker(board, i, j, i+1, j, dimention1, dimention2);
           if (points > maxPoints){
             maxPoints = points;
             maxRow1 = i;
             maxColumn1 = j;
             maxRow2 = i+1;
             maxColumn2 = j;
           }
           if (points > 2){
             validMovesNum++;
           }
           temp = board[i][j];
           board[i][j] = board[i+1][j];
           board[i+1][j] = temp;
         }
       }
     }
     

     //Printing results to console
     System.out.println("Max number of points from a single swap: " + maxPoints);
     System.out.println("Best move(" + maxPoints + " points): ("  + maxRow1 +", "+ maxColumn1 +") and ("+ maxRow2 +", "+ maxColumn2+ ")");
     System.out.println("Number of valid swaps: "  +  validMovesNum);


     // asks to display board
     System.out.print("Would you like to see the board??(1.Yes     2.No)");
     int answer = input.nextInt();
     while(answer != 1 && answer !=2){
       System.out.print("Please enter '1' or '2' only");
       answer = input.nextInt();
     }
     int count = 0;
     if (answer == 1){
       //displays board
       for (int i = 0; i <= dimention1-1; i++){
         for (int j = 0 ; j <= dimention2-1; j++){
           count++;
           if (count % dimention2 == 0){
             System.out.println(board[i][j]);
           }else{
             System.out.print(board[i][j]);
           }
         }
       }
     }
     //closes scanners
     input.close();
     file.close();
  }//end of main
  
  
  
  /**
    * pointsChecker
    * This method accepts the coordiantes of the swap, 
    * the board and its dimentions and determines the two letters
    * that need to be checked. Runs the pointCounter to count
    * both letters separately and returns the full points of the swap
    */
  public static int pointsChecker(char[][] board, int rowNumM1,int columnNumM1, int rowNumM2,int columnNumM2, int dimention1, int dimention2){   
    
    //makes a copy of the board
     char[][] board2 = new char[dimention1][dimention2];
     for (int i = 0; i <= dimention1-1; i++){
       for (int j = 0 ; j <= dimention2-1; j++){
         board2[i][j] = board[i][j]; 
       }
     }

     //the two letters of the swap
    char letter1 = board[rowNumM1][columnNumM1];
    char letter2 = board[rowNumM2][columnNumM2];

    //counts points for the first letter of the move 
    int match = 1 + pointCounter( board2, rowNumM1, columnNumM1, letter1, dimention1, dimention2); //adds one 
                                                       //to result as pointCounter does not count the starting letter
    if (match < 3) {   // eliminates points gained if less than 3(invalid move)
      match = 0; 
    }
    
    //counts points for the second letter of the move 
    int match2 = 1 + pointCounter( board2, rowNumM2, columnNumM2, letter2, dimention1, dimention2);
    if (match2 < 3) { 
      match2 = 0; 
    }
    
    match += match2; // adds the two moves 
    return match;
  }
  



   /**
    * pointsCounter
    * This method accepts the letter of the swap, coordiantes of the swap,
    * the board and its dimentions and recursively counts the number
    * of same letters next to each other(horizontally or vertically)
    * it returns an int showing the number of matches of the letter
    */
  public static int pointCounter(char[][] board,int rowNum,int columnNum,char letter, int dimention1, int dimention2){
    int match = 0;
    
    board[rowNum][columnNum] = '*'; // changes letter to a symbol so it is counted once only 
    
    if (columnNum + 1 < dimention2){     // checks for one to right 
      if (board [rowNum][columnNum+1] == letter){  
         match += 1 + pointCounter( board, rowNum, columnNum+1, letter,dimention1 , dimention2);//adds one and runs
                                                                         // the method for the new letter
      }
    }
    
    if (columnNum - 1 > -1){            // checks for one to left 
      if (board [rowNum][columnNum-1] == letter){    
        match += 1 + pointCounter( board, rowNum, columnNum-1, letter,dimention1 , dimention2);        
     }
    }
    
    if (rowNum+ 1 < dimention1){        //   checks for one below 
      if (board [rowNum+ 1][columnNum] == letter){
        match += 1 + pointCounter( board, rowNum+1, columnNum, letter,dimention1 , dimention2);   
      }
    } 
    
    if (rowNum- 1 > -1){                 //   checks for one above 
      if (board [rowNum- 1][columnNum] == letter){ 
        match += 1 + pointCounter( board, rowNum-1, columnNum, letter,dimention1 , dimention2);
      }
    }

    return match;
  }
}