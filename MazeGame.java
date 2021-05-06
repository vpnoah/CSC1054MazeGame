import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.io.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import java.util.*;
import javafx.collections.*;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

public class MazeGame extends Application{
   
   boolean win=false; //keeps track of whether the player has won or not
   
   public FlowPane root = new FlowPane(); //root flowpane for the scene
   
   public int [][] map = new int [21][21]; //2d array that keeps track of the map
     
   mazeCanvas mc; //canvas that will paint the maze
      
   int posX, posY;    //keeps track of the payer's position
      
   public void start (Stage stage){
   
      Scanner scan;
   
      try{
   
         File mapText = new File("maze0.txt"); //reads from the text file, and sets up the 2d array of values
   
         scan = new Scanner(mapText);
   
         for(int i=0; i<21; i++){
         
            for(int j=0; j<21; j++){
      
               map[i][j]=scan.nextInt();
                    
                  }
   
               }

            }
   
      catch(FileNotFoundException e){ //if the file is not there, lets the user know with a label
   
         Label l = new Label("maze0.txt was not found");
         root.getChildren().add(l);
   
         }
   
      for(int i=0; i<map.length; i++){ //goes through the 2d array, and finds where the starting point is at the top of the maze
   
         if(map[0][i]==0){
      
            posX=i; 
            posY=0;
      
         }
      }
      
      mc = new mazeCanvas(); //creates the canvas that will draw the maze
   
      root.setOnKeyPressed(new KeyPressedListener()); //sets key pressed
   
      root.getChildren().add(mc);
   
      Scene x = new Scene(root,525,525); //sets up the scene
      stage.setScene(x);
      stage.setTitle("Cool Maze Game");
      stage.show();
   
      root.requestFocus();
   
      }
     
   public static void main (String args[]){
   
      launch(args);
   
      }

   public class mazeCanvas extends Canvas{
   
      GraphicsContext gc = getGraphicsContext2D();
              
      public mazeCanvas(){ //initializes the size of the canvas and draws the map
      
         setWidth(525);
         setHeight(525);
      
         update(map);
           
     }
     
     public void win(){ //method if the player wins
     
         gc.clearRect(0,0,525,525);
         gc.setFill(Color.BLACK);
     
         gc.setFont( new Font("Verdana", 100)); //adds the text, "you win" to the screen
         gc.fillText("You Win", 40,100);
     
         try{
     
            FileInputStream catWin = new FileInputStream(new File("catWin.jfif")); //adds a funny victory picture to the screen
            Image catWin2 = new Image(catWin);
                  
            gc.drawImage(catWin2,100,200,300,300);  
        
         }
         
         catch(Exception e){ //catches any file not found error
     
         } 
        
     }
   
     public void update(int [][] x){ //takes in a 2d array of values
      
         for(int i=0; i<21; i++){ //goes through the 2d array, draws a white square on values of 0, black on values of 1
         
            for(int j=0; j<21; j++){
          
               if(x[j][i]==0){       
                  gc.setFill(Color.WHITE);
                  gc.fillRect(i*25, j*25, 25, 25);
               }
            
               if(x[j][i]==1){
                  gc.setFill(Color.BLACK);
                  gc.fillRect(i*25, j*25, 25, 25);
                  }
               }
            }
            
            gc.setFill(Color.CYAN); //draws a blue square at the players position
            gc.fillRect(posX*25,posY*25,25,25);
      }
   }

   public void moveUp(){
   
      try{
         if(map[posY-1][posX] == 0){ //if the space above the player is a 0 (not a wall), moves the player and updates the screen
            posY--;
            mc.update(map);
         }
      }
      
      catch(Exception e){}
   
   }
   
   public void moveDown(){
   
      try{
         if(map[posY+1][posX] == 0){ //if the space below the player is a 0 (not a wall), moves the player and updates the screen
            posY++;
            mc.update(map);
         }
      }
      
      catch(Exception e){}
   
      if(posY==20){ //if y=20, then the player got to the end of the maze
         mc.win(); //runs the win command
         win=true; //sets the win value to true
      }
   
   }
   
   public void moveLeft(){
   
      try{
         if(map[posY][posX-1] == 0){ //if the space to the left is a 0 (not a wall), moves the player and updates the screen
            posX--;
            mc.update(map);
         }
      }
      
      catch(Exception e){}
   
   }
   
   public void moveRight(){
   
      try{
         if(map[posY][posX+1] == 0){ //if the space to the right is a 0 (not a wall), moves the player and updates the screen
            posX++;
            mc.update(map);
         }
      }
      
      catch(Exception e){}
   
   }

   public class KeyPressedListener implements EventHandler<KeyEvent>{
   
      public void handle(KeyEvent ke){
   
            if(!win){ //if the player hasn't won, they should still be able to move
            
               if(ke.getCode()==KeyCode.UP){ //on keycode up, move up
                  moveUp();
               }
         
               if(ke.getCode()==KeyCode.DOWN){ //on keycode down, move down
                  moveDown();
                }
         
             if(ke.getCode()==KeyCode.LEFT){ //on keycode left, move left
                  moveLeft();
               }
         
             if(ke.getCode()==KeyCode.RIGHT){ //on keycode right, move right
                  moveRight();
               }
         }
      }
   }

   



}