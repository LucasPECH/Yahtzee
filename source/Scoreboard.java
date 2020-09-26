import javax.swing.*; //Needed for the Swing classes
import java.util.*; //Needed for Scanner class
import java.io.*; //Needed for the inputs and outputs of files
  
public class Scoreboard 
{

    private int highscore; //Integer that stores the highest score ever recorded while playing our game
    private int int_nbPlayer; //Integer that stores the number of players playing this game of yahtzee
    private int [][] scoreSheet; //Array of integers filled with the uppersection scores
    private boolean [][] alreadyFill; //Array of booleans filled with true value if the uppersection has already been blocked for this value

    /**
      Default Scoreboard constructor asks the user how many players does he wants
      And create the scoresheet and the alreadyFill array according to the number of player
    */
    public Scoreboard() throws IOException
    {
      File hs = new File("textfiles\\highscore.txt");
      Scanner hScan = new Scanner(hs);
      
      highscore = hScan.nextInt();
      
      String nbPlayer = JOptionPane.showInputDialog("How many players for this game ? (10 players maximum)"); 
      
      if(nbPlayer != null)
      {
        if(!nbPlayer.equals("1") && !nbPlayer.equals("2") && !nbPlayer.equals("3") && !nbPlayer.equals("4") && !nbPlayer.equals("5") && !nbPlayer.equals("6") && !nbPlayer.equals("7") && !nbPlayer.equals("8") && !nbPlayer.equals("9") && !nbPlayer.equals("10")); //The user must specify how many players will be playing (within a maximum of 10 players)
        {
          while(!nbPlayer.equals("1") && !nbPlayer.equals("2") && !nbPlayer.equals("3") && !nbPlayer.equals("4") && !nbPlayer.equals("5") && !nbPlayer.equals("6") && !nbPlayer.equals("7") && !nbPlayer.equals("8") && !nbPlayer.equals("9") && !nbPlayer.equals("10"))
          {
            nbPlayer = JOptionPane.showInputDialog("How many players for this game ?"); 
          }
        }
      
        int_nbPlayer = Integer.parseInt(nbPlayer);
        
        scoreSheet = new int[16][int_nbPlayer];
        alreadyFill = new boolean[16][int_nbPlayer];
        
        for(int i = 0 ; i < 16 ; i++)
        {
          for(int j = 0 ; j < int_nbPlayer ; j++)
          {
            if(i == 6 || i == 7 || i == 15) //These row are the row showing the total and bonus, the user don't have to fill them so we put them as already filled
            {
              alreadyFill[i][j] = true;
            }
            else
            {
              alreadyFill[i][j] = false;
            }
          }
        }
      }
      else
      {
        int_nbPlayer = 0;
      }
    }
    
    /**
      This scoreboard constructor receives as paramter the nb of player playing the game and creates the scoresheet and already fill array depending on this nb
      @param nbPlayer is the number of player playing the game
    */
    public Scoreboard(int nbPlayer) throws IOException
    {
      File hs = new File("textfiles\\highscore.txt");
      Scanner hScan = new Scanner(hs);
      
      int_nbPlayer = nbPlayer;
      highscore = hScan.nextInt();
       
      scoreSheet = new int[16][nbPlayer];
      alreadyFill = new boolean[16][nbPlayer];  
     }
    
    /**
      This function calculates the potential values in the scoresheet in fucntion of the values of the dices and of the already filled rows
      @param roll the array filled with the dices values
      @param playerNb integer representing the number of the player that we are currently filling the scoresheet
    */
    public void Score(Rolls roll, int playerNb)
    { 
        for (int i = 0; i < 5; i++) 
        {
            int n = roll.getRoll(i);
            if(n == 1 && alreadyFill[0][playerNb] == false) //If the dice have a value of 1 and if the 1 row in the scoresheet is not already filled then we modify the value of the scoreboard so that the user can choose the value he want to fill
            {  
              scoreSheet[0][playerNb]++;
            }
            else if(n == 2 && alreadyFill[1][playerNb] == false)
            {
                scoreSheet[1][playerNb]+=2;
            }
            else if(n == 3 && alreadyFill[2][playerNb] == false)
            {
                scoreSheet[2][playerNb]+=3;
            }
            else if(n == 4 && alreadyFill[3][playerNb] == false)
            {
                scoreSheet[3][playerNb]+=4;
            }
            else if(n == 5 && alreadyFill[4][playerNb] == false)
            {
                scoreSheet[4][playerNb]+=5;
            }
            else if(n == 6 && alreadyFill[5][playerNb] == false)
            {
                scoreSheet[5][playerNb]+=6;            
            }
        }
        
        if(roll.IsThere_n_SameDice(3) == true) 
        {
          if(roll.IsThere_n_SameDice(2) == true && alreadyFill[10][playerNb] == false) //If there is a pair and 3 same dice we fill the full house row
          {
            scoreSheet[10][playerNb] = 25;
          }
          else if(alreadyFill[8][playerNb] == false) //If there is 3 similar dice and the row "Three same dice is not filled" then we fill it with the potential score
          {
            scoreSheet[8][playerNb] = roll.RollSum();
          }
        }
        
        if(roll.IsThere_n_SameDice(4) == true)
        {
          if(alreadyFill[8][playerNb] == false) //4 same dices means there is 3 same dice so we fill this row
          {
            scoreSheet[8][playerNb] = roll.RollSum();
          } 
          if(alreadyFill[9][playerNb] == false) //We fill the 4 same dices row
          {
            scoreSheet[9][playerNb] = roll.RollSum();
          }
        }
        
        if(roll.SmallStraight() == true && alreadyFill[11][playerNb] == false) //If there is a SmallStraight and it has not been filled yet, then we fill it
        {
          scoreSheet[11][playerNb] = 30;
        }
        
        if(roll.LargeStraight() == true) 
        {
          if(alreadyFill[11][playerNb] == false) //If there is a large straight it means there is also a small straight
          {
            scoreSheet[11][playerNb] = 30;
          }
          if(alreadyFill[12][playerNb] == false) //We fill the row large straight if it is not filled yet
          {
            scoreSheet[12][playerNb]= 40;
          }
        }
        
        if(alreadyFill[13][playerNb] == false) //If chance row is not filled then we fill it
          scoreSheet[13][playerNb] = roll.RollSum();
        
        if(roll.IsThere_n_SameDice(5) == true) 
        {
          if(alreadyFill[8][playerNb] == false) //If there is 5 similar dice then it means there is 3 same dice
          {
            scoreSheet[8][playerNb] = roll.RollSum();
          }
          if(alreadyFill[9][playerNb] == false) //If there is 5 similar dice then it means there is 4 same dice
          {
            scoreSheet[9][playerNb] = roll.RollSum();
          }
          if(alreadyFill[14][playerNb] == false) //Yahtzee column not filled so we fill it
          {
            scoreSheet[14][playerNb] = 50;
          }
        }
        
        if(roll.IsThere_n_SameDice(5) == true && alreadyFill[14][playerNb] == true  && scoreSheet[14][playerNb] != 0) // Detection of multiple yahtzee
        {
          if(End() == false)
          {
            JOptionPane.showMessageDialog(null, "Multiple YAHTZEE !! +100 point !!");
          }
          
          scoreSheet[14][playerNb] += 100; //Bonus point for multiple yahtzee
          scoreSheet[15][playerNb] += 100;
          
          if(alreadyFill[roll.getRoll(0)-1][playerNb] == true)
          {
            if(alreadyFill[8][playerNb] == false) //When a player get multiple yahtzee he can fill any column he hasn't filled yet so we show it what are the potential score for each column not filled yet
            {
              scoreSheet[8][playerNb] = roll.RollSum();
            }
            if(alreadyFill[9][playerNb] == false)  
            {
              scoreSheet[9][playerNb] = roll.RollSum();
            }
            if(alreadyFill[10][playerNb] == false)
            {
              scoreSheet[10][playerNb] = 25;
            }
            if(alreadyFill[11][playerNb] == false)
            {
              scoreSheet[11][playerNb] = 30;
            }
            if(alreadyFill[12][playerNb] == false)
            {
              scoreSheet[12][playerNb] = 40;
            }
            if(alreadyFill[13][playerNb] == false)
            {
              scoreSheet[13][playerNb] = roll.RollSum();
            }
          }
        }
        
        scoreSheet[6][playerNb] = TotalUpperSection(playerNb);
        
        if(scoreSheet[6][playerNb] > 63)
        {
          scoreSheet[7][playerNb] = 35;
        }
        
        scoreSheet[15][playerNb] = TotalGame(playerNb);
    }
    
    /**
      This function allows to obtain the highscore evermade on the scoreboard
      @return return the value of the highscore as an integer
    */
    public int getHighscore()
    {
      return highscore;
    }
     
    /**
      This function allows to reinitialize all the value of the scoresheet that the user didn't filled yet
    */
    public void ReinitializeScoreSheet()
    {
        for (int i = 0; i <= 14; i++)
        {
          for (int j = 0; j < int_nbPlayer; j++)
          {
            if(alreadyFill[i][j] == false)
              scoreSheet[i][j] = 0;
          }
        }
    }
    
    /**
     This function checks if all the rows are filled, if they all are filled then this is the end of the game
     @return we return true if the scoresheet if totally filled else we return false
    */
    public boolean End()
    { 
        for (int i = 0; i <= 14; i++) 
        {
          for (int j = 0; j < int_nbPlayer; j++)
          {
            if(alreadyFill[i][j] == false)
            {
                return false;
            }
          }
        }
        return true;
    }
    
    /**
     This function goes through all the uppersection, calculate the total of the value in this section
     @return return the total value by adding the value of each row
    */
    public int TotalUpperSection(int playerNb)
    { 
        int sum = 0;
        for (int i = 0; i <= 5; i++) 
        {
          if(alreadyFill[i][playerNb] == true)
          {
            sum += scoreSheet[i][playerNb];
          }
        }
        return sum;
    }
    
    /**
     This function goes through all the scoresheet, calculate the total of the value
     @return return the total value by adding the value of each row
    */
    public int TotalGame(int playerNb)
    {
        int sum = TotalUpperSection(playerNb);
        for (int i = 8; i < 15; i++) 
        {
          if(alreadyFill[i][playerNb] == true)
          {
            sum += scoreSheet[i][playerNb];
          }
        }
        if(TotalUpperSection(playerNb) > 63) //We check if the player got the bonus of the uppersection
        {
          sum += 35;
        }
        return sum;
    }
   
    /**
     This function permits to obtain the alreadyFill array
     @return return the alreadyFill array
    */
    public boolean getFill(int i, int j)
    {
        return alreadyFill[i][j];
    }
    
    /**
     This function permits to set value in the alreadyFill array
     @param i the row number in the alreadyfill array
     @param j the column number in the alreadyfill array
     @bool the value to put in alreadyfill[i][j]
    */
    public void setFill(int i, int j, boolean bool)
    {
      alreadyFill[i][j] = bool;
    }
    
    /**
     This function permits to obtain the value of a give part of the scoresheet
     @param i the row where is the score we want to obtain
     @param j the column where is the score we want to obtain
    */
    public int getScore(int i, int j)
    {
      return scoreSheet[i][j];
    }
    
    /**
     This function permits to set value in the scoresheet array
     @param i the row number in the scoresheet array
     @param j the column number in the scoresheet array
     @score the value to put in scoresheet[i][j]
    */
    public void setScore(int i, int j, int score)
    {
      scoreSheet[i][j] = score;
    }
    
    /**
     This function permits to obtain the number of player 
     @return return the number of player playing the game
    */
    public int getNbPlayer()
    {
      return int_nbPlayer;
    }
    
    /**
    This function permits to find the winner of the game, it also handles equalities
    @param int_nbPlayer the number of players of the game
    */
    public void findWinner(int int_nbPlayer)
    {
      if(int_nbPlayer > 1)
      {   
        int[] winner = new int[int_nbPlayer];
        winner[0] = 0;
        int nbWinner = 0;
        for (int i = 1; i < int_nbPlayer; i++)
        {
          if (scoreSheet[15][winner[0]] < scoreSheet[15][i])
          {
            nbWinner = 0;
            winner[nbWinner]=i;
          }
          else if(scoreSheet[15][winner[0]] == scoreSheet[15][i])
          {
            nbWinner++;
            winner[nbWinner] = i;
          }
        }
        if(nbWinner == 0)
        {
          JOptionPane.showMessageDialog(null, "Player " + (winner[0]+1) + " is the winner with a total of "+TotalGame(winner[0])+ " point this game !");
        }
        else
        {
          String finalMessage = "Equality between :"; 
          for(int i = 0 ; i < nbWinner+1 ; i++)
          {
            if(i == nbWinner-1)
            {
              finalMessage += " player "+(winner[i]+1)+ " and ";
            }
            else if(i == nbWinner)
            {
              finalMessage += " player "+(winner[i]+1);
            }
            else
            {
              finalMessage += " player "+(winner[i]+1)+ ", ";
            }
          }
          finalMessage += "with a total score of "+TotalGame(winner[0])+"!";
          JOptionPane.showMessageDialog(null, finalMessage);
        }
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Well played you scored "+TotalGame(0)+" points this game !");
      } 
    }
    
    /**
    This function allows the player to save the game by writing all the useful data in a file
    @param playerTurn the number of the player whose it is turn
    @param the number of reroll the player whose it is the turn still has
    @param myRoll the roll of the player whose it is the turn
    */
    public void saveGame(int playerTurn, int nbReroll, Dice[] myRoll) throws IOException
    {
      PrintWriter sPrint = new PrintWriter("textfiles\\save.txt");
      sPrint.print(getNbPlayer() +",");
      sPrint.print(playerTurn +",");
      sPrint.print(nbReroll+",");
      for(int i =0 ; i < 5 ; i++)
      {
        sPrint.print(myRoll[i].getDice()+",");
      }
      for (int i = 0; i < getNbPlayer(); i++)
      {
        for (int j = 0; j < scoreSheet.length; j++)
        {
          sPrint.print(getScore(j,i)+",");
        }
      }
      for (int i = 0; i < getNbPlayer(); i++)
      {
        for (int j = 0; j < alreadyFill.length; j++)
        {
          sPrint.print(getFill(j,i)+",");
        }
      }
      sPrint.close();
    }
    
    /**
    This function checks if a new higscore has been set during the game
    */
    public void highscore() throws IOException
    {
      
      File hs = new File("textfiles\\highscore.txt");
      Scanner hScan = new Scanner(hs);
            
      int high = hScan.nextInt();
      hScan.close();

      for (int i = 0; i < getNbPlayer(); i++)
      {
        int total = scoreSheet[15][i];
        if (high<=total)
        {
           PrintWriter hPrint = new PrintWriter("textfiles\\highscore.txt");
           hPrint.println(total);
           hPrint.close();
           JOptionPane.showMessageDialog(null, "Player " + (i+1) + " has set a new highscore of  " + total + " !");
           highscore = total;
        }
     }
   }
    
 }     
      
