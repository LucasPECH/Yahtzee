import javax.swing.*; //Needed for Swing classes
import java.awt.event.*; //Needed for ActionListener Interface
import java.awt.*; //Needed for BorderLayout class
import java.io.*; //Needed for the inputs and outputs of files
import java.util.*; //Needed for the Scanner class
import javax.swing.border.Border; //Needed for the creation of borders

/**
   The user inteface class display a jframe that allow the user to play a yahtzee game
*/
public class UserInterface extends JFrame
{           
  
   private final int WINDOW_WIDTH = 1500;  // Window width
   private final int WINDOW_HEIGHT = 800; // Window height
   
   private JToggleButton dice1; //ToggleButton representing a dice
   private JToggleButton dice2;
   private JToggleButton dice3;
   private JToggleButton dice4;
   private JToggleButton dice5;
   
   private JButton reroll; //Button which allows the player to reroll the dices he selected
   
   private JButton[][] scoreboard; //2D array of button used to display the scoreboard and which allow the player to choose the row he want to lock
   
   private JLabel message; //Message displaying the number of reroll the player still have
   private JLabel highscore; //Label displaying the highscore
   
   private Rolls myRoll; //Roll object of the player, this object contains the array with the value of each dice of the player
   private Scoreboard myScoreboard; //Scoreboard object which contains all the informations of each player scoreboard with score and already locked row
   
   private int nbReroll; //Variable that will count the nb of reroll for each player
   
   private int playerTurn; //Variable that store the nb of the player that is currently playing

   /**
   Constructor of the user interface
   */
   public UserInterface() throws IOException
   {
      Color light_blue = new Color(79, 205, 255);
      Color dark_blue = new Color(20, 69, 107);
      Color grey_blue = new Color(40,107, 140);
      
      UIManager.put("OptionPane.background",grey_blue);
      UIManager.put("Panel.background",grey_blue);
      
      String[] options = {"New game", "Load game"}; // Array of option that will be displayed on the show option dialog
      int choice = JOptionPane.showOptionDialog(null, "Load a game ?", "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // We ask the user if he wants to load a game
      
      setTitle("Yahtzee"); //Set the window title
      
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT); //Set the size of the window

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Specify what happens when the close button is clicked
      
      setLayout(new BorderLayout()); //Specify the layout we want for our JFrame
      
      setResizable(false); //Disable the possibility for the user to resize the window
     
      myRoll = new Rolls(); //Creation of Rolls object
      myRoll.InitalRoll(); //First roll of the dice     
      
      if(choice == 0) //Choice == 1 means the player wants to play a new game
      {
        myScoreboard = new Scoreboard(); //Creation of the scoreboard object
        playerTurn = 0; //When the interface is created is the player 1 turn
        nbReroll = 0; //The first player rerolled 0 times when he start the game
      }
      else
      {
        loadGame(); //We load a game
      }
            
      Font big = new Font("Segoe UI Black", 0, 75);
      Font playerFont = new Font("Segoe UI Black", 0, 30-2*myScoreboard.getNbPlayer()); //We decrease the size of the font depending on the nb of player
      Font medium = new Font("Segoe UI Black", 0, 30);
      Font small = new Font("Segoe UI Black", 0, 20);
 
      Border lb = BorderFactory.createLineBorder(light_blue);
      Border db = BorderFactory.createLineBorder(dark_blue);
      Border gb = BorderFactory.createLineBorder(grey_blue);
      
      UIManager.put("ToggleButton.select", grey_blue);
      UIManager.put("ToggleButton.background", grey_blue);
      UIManager.put("Button.disabledText", light_blue);
      
      JPanel centerPanel = new JPanel(); //Creation of the centerPanel where we will put all the thing we want to display in the center of the JFrame
      
      scoreboard = new JButton[16][myScoreboard.getNbPlayer()]; //Creation of the 2D array of button depending on the number of player, the number of player is the number of column
      
      centerPanel.setLayout(new GridLayout(16, myScoreboard.getNbPlayer())); //We want the center panel to display the 2D in a gridlayout so that it looks like a table
       
      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < myScoreboard.getNbPlayer(); j++)
         {

           scoreboard[i][j] = new JButton(); //Creation of a button for the table
           scoreboard[i][j].setFont(small);
           scoreboard[i][j].setForeground(Color.WHITE);
           scoreboard[i][j].setBackground(dark_blue);
           scoreboard[i][j].setBorder(lb);
           scoreboard[i][j].setFocusPainted(false);
           scoreboard[i][j].setActionCommand(Integer.toString(i)); //We give a name to each button so that we can detect which one the user clicked on
           scoreboard[i][j].addActionListener(new ScoreboardButtonListener()); //We have an event to each button
           
           centerPanel.add(scoreboard[i][j], myScoreboard.getNbPlayer() * i + j); //We position each button at the good position in the panel so that button 1 is the first line of each column etc..
          }
       }
      
      add(centerPanel, BorderLayout.CENTER); //We had the center panel in the center part of the frame panel
      
      JPanel westPanel = new JPanel(); //Creation of the centerPanel where we will put all the thing we want to display in the west of the JFrame
      
      JLabel[] westHeader = new JLabel[16]; //Creation of an array of label that will allow us to display the name of each row
      
      westPanel.setLayout(new GridLayout(16, 1)); //We create a grid layout so that each label of the west header will be printed in column
      
      westPanel.setBackground(light_blue);
      
      String [] numbers = {" Ones           "," Twos           "," Threes         "," Fours          "," Fives          "," Sixes          ", " Sum            ", " Bonus          ",  " Three of a kind ", " Four of a kind ", " Full House     ", " Small straight ", " Large straight ", " Chance         ", " Yahtzee        ", " Total          "};
      
      for (int j = 0; j <= 15; j++) 
      {
           westHeader[j] = new JLabel(numbers[j]); //We put the good name in the good label
           if ((j==6) || (j==7) || (j==15))
           {
           westHeader[j].setFont(medium);
           westHeader[j].setForeground(dark_blue);
           }
           else
           {
           westHeader[j].setFont(small);
           westHeader[j].setForeground(grey_blue);
           }
           westPanel.add(westHeader[j], j); //We had the Label in the west panel so that they are put in column
      }
      add(westPanel, BorderLayout.WEST); //We had the west panel in the west part of the frame panel
        
      JPanel northPanel = new JPanel(); //Creation of the centerPanel where we will put all the thing we want to display in the north of the JFrame

      northPanel.setLayout(new GridLayout(1, myScoreboard.getNbPlayer()+2)); //We create a layout that will allow to display the player number in line 
      
      if(myScoreboard.getNbPlayer() < 8) //Depending on the number of player, we must change the header so that the players a above their column
      {
        JLabel[] northHeader = new JLabel[myScoreboard.getNbPlayer()+2]; //We create an array of label with a size equal to the number of player
        
        northHeader[0] = new JLabel(""); //The first label is the space above the row names so we don't put anything in it
        
        northPanel.add(northHeader[0], 0); //We add the label to the north panel at first position
        
        northPanel.setBackground(light_blue);
        
        for(int j = 1 ; j < myScoreboard.getNbPlayer()+1 ; j++)
        {
          northHeader[j] = new JLabel("Player "+j); //We create a new label filled will the player nb so that we identify which column is at which player
          northHeader[j].setFont(playerFont);
          northHeader[j].setForeground(dark_blue);
          northPanel.add(northHeader[j], j); //We add each new label to the north panel
        }
        
        northHeader[myScoreboard.getNbPlayer()+1] = new JLabel(""); //The first label is the space above the row names so we don't put anything in it
        
        northPanel.add(northHeader[myScoreboard.getNbPlayer()+1], myScoreboard.getNbPlayer()+1); //We add the label to the north panel at first position
        add(northPanel, BorderLayout.NORTH);  
      }
      else
      {
        JLabel[] northHeader = new JLabel[myScoreboard.getNbPlayer()+4]; //We create an array of label with a size equal to the number of player
        
        northHeader[0] = new JLabel(""); //The first label is the space above the row names so we don't put anything in it
        
        northPanel.add(northHeader[0], 0); //We add the label to the north panel at first position
        
        northHeader[1] = new JLabel(""); //The first label is the space above the row names so we don't put anything in it
        
        northPanel.add(northHeader[1], 1); //We add the label to the north panel at first position
        
        northPanel.setBackground(light_blue);
        
        for(int j = 2 ; j < myScoreboard.getNbPlayer()+2 ; j++)
        {
          northHeader[j] = new JLabel("Player "+(j-1)); //We create a new label filled will the player nb so that we identify which column is at which player
          northHeader[j].setFont(playerFont);
          northHeader[j].setForeground(dark_blue);
          northPanel.add(northHeader[j], j); //We add each new label to the north panel
        }
        
        northHeader[myScoreboard.getNbPlayer()+2] = new JLabel("");
        northPanel.add(northHeader[myScoreboard.getNbPlayer()+2], myScoreboard.getNbPlayer()+2);
        
        northHeader[myScoreboard.getNbPlayer()+3] = new JLabel("");
        northPanel.add(northHeader[myScoreboard.getNbPlayer()+3], myScoreboard.getNbPlayer()+3);
        add(northPanel, BorderLayout.NORTH);     
        
      }
      
      message = new JLabel((2-nbReroll)+" Reroll left "); //We set the text to put in the message label showing the number of reroll left

      message.setFont(medium);
      message.setForeground(Color.WHITE);
      
      JPanel buttonPanel = new JPanel();
      
      dice1 = new JToggleButton(); //Creation of a toggle button
      dice2 = new JToggleButton();
      dice3 = new JToggleButton();
      dice4 = new JToggleButton();
      dice5 = new JToggleButton();
      reroll = new JButton(" Reroll "); //Creation of the reroll button
      
      reroll.setFont(big);
      reroll.setBackground(grey_blue);
      reroll.setForeground(Color.WHITE);
      reroll.setBorder(gb);
      
      dice1.setBorder(gb);
      dice2.setBorder(gb);
      dice3.setBorder(gb);
      dice4.setBorder(gb);
      dice5.setBorder(gb);
      
      dice1.addActionListener(new DiceButtonListener());
      dice2.addActionListener(new DiceButtonListener());
      dice3.addActionListener(new DiceButtonListener());
      dice4.addActionListener(new DiceButtonListener());
      dice5.addActionListener(new DiceButtonListener());
      
      reroll.setEnabled(false); //Before selecting any dices the user can't reroll
      
      buttonPanel.add(message); //We add the message button panel
      buttonPanel.add(dice1); //We add each dice to the button panel
      buttonPanel.add(dice2);
      buttonPanel.add(dice3);
      buttonPanel.add(dice4);
      buttonPanel.add(dice5);
      SetDisplayDices(); //We set the display of the dices depending on the roll of the first player
      
      buttonPanel.add(reroll); //We add the reroll button to the button panel
      reroll.addActionListener(new RerollButtonListener()); //We had the reroll event to the reroll button
      
      buttonPanel.setBackground(grey_blue);
                     
      add(buttonPanel, BorderLayout.SOUTH); //We add the button panel to the south part of the JFrame panel
      
      JPanel eastPanel = new JPanel();
      eastPanel.setLayout(new GridLayout(4, 1));
      
      eastPanel.setBackground(light_blue);
      
      highscore = new JLabel(" Highscore : "+myScoreboard.getHighscore()+ " ");
      JButton save = new JButton("Save"); //Creation of the save button
      JButton hint = new JButton("Hint");
      JButton restart = new JButton("Restart");
      
      save.setFont(medium);
      hint.setFont(medium);
      restart.setFont(medium);
      save.setBackground(dark_blue);
      save.setForeground(Color.WHITE);
      hint.setBackground(dark_blue);
      hint.setForeground(Color.WHITE);
      restart.setBackground(dark_blue);
      restart.setForeground(Color.WHITE);
      save.setFocusPainted(false);
      hint.setFocusPainted(false);
      restart.setFocusPainted(false);
      save.setBorder(db);
      hint.setBorder(db);
      restart.setBorder(db);

      highscore.setFont(medium);
      highscore.setOpaque(true);
      highscore.setBackground(light_blue);
      highscore.setForeground(dark_blue);
      highscore.setBorder(lb);
      
      eastPanel.add(highscore);
      eastPanel.add(hint);
      eastPanel.add(save);
      eastPanel.add(restart);
      hint.addActionListener(new HintButtonListener());
      save.addActionListener(new SaveButtonListener());
      restart.addActionListener(new RestartButtonListener());
      
      add(eastPanel, BorderLayout.EAST);
      
      SetScoreboard(playerTurn); //We set the score on the scoreboard represented by the 2D array of button      
      
      setVisible(true); //Once all the panel have been created an added to the frame, we display the window.  
   }
   
   /**
      The SetDisplayDices function allows to display the good image of dice depending on the roll values of this dice
   */
   public void SetDisplayDices()
   {
      dice1.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(0)+".png")); // Set the good image to display on the button depending on the values of the roll
      dice2.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(1)+".png"));
      dice3.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(2)+".png"));
      dice4.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(3)+".png"));
      dice5.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(4)+".png"));
   }
   
   /**
      The LoadGame function allows to load a game from the score saved in a .txt file
      This function fill the scoresheet and alreadyfill array but also update the nb of reroll and the player turn
   */
   public void loadGame() throws IOException
   {
       String content = new Scanner(new File("textfiles\\save.txt")).useDelimiter("\\Z").next();
       String[] tokens = content.split(",");
        
       int nbPlayer = Integer.parseInt(tokens[0]);
       playerTurn = Integer.parseInt(tokens[1]);
       nbReroll = Integer.parseInt(tokens[2]);
        
       for(int i = 3 ; i < 8 ; i++)
       {
         myRoll.setRollValue(i-3, Integer.parseInt(tokens[i]));
       }
        
       myScoreboard = new Scoreboard(nbPlayer); //Creation of a new scoreboard depending on the nb of player
        
       for (int i = 0; i < nbPlayer; i++) //Filling the scoresheet
       {
           int x = 0;
           for (int j = 8+i*16; j < 8+16+(i*16); j++)
           {
               myScoreboard.setScore(x,i, Integer.parseInt(tokens[j]));
               x++;
           }
       }
       for (int i = 0; i < nbPlayer; i++) //Filling the alreadyFill array
       {
           int x = 0;
           for (int j = (nbPlayer*16)+8+i*16; j < (nbPlayer*16)+8+16+(i*16); j++)
           {
               myScoreboard.setFill(x, i, Boolean.parseBoolean(tokens[j]));
               x++;
           }
       }
   }
   
   /**
      The RerollButtonListener action listener is load when a player click on the button reroll
      This function allow to change dices the user have decided to reroll
      It also permit to disable the dices and reroll button when a player have already used 2 reroll
   */
   private class SaveButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
        try //Used to handle exception
        {
          myScoreboard.saveGame(playerTurn, nbReroll, myRoll.getRollArray());
        }
        catch (IOException ex) //Handle the exception
        {
          System.out.println("An IO error occured: ");
          ex.printStackTrace(System.out);
        }
      }
   }
   
   /**
       The LoadButtonListener is loaded when a player click the load button
       his function loads a previously saved game
   */
   private class RestartButtonListener implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           try //Used to handle exception
           {
              dispose(); //We destroy the current JFrame object
              new UserInterface();
           }
           catch (IOException ex)
           {
               //Handle the exception in some elegant way...
               System.out.println("An IO error occured: ");
               ex.printStackTrace(System.out);
           }
       }
   }
    
   /**
      The DiceButtonListener action listener is load when a player click a dice button
      This function allow to enable or not the reroll button so that a player cannot reroll with no dice select
   */
   private class DiceButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
        if(dice1.isSelected() == true || dice2.isSelected() == true || dice3.isSelected() == true || dice4.isSelected() == true || dice5.isSelected() == true && reroll.isEnabled() == false)
        {
           reroll.setEnabled(true); //Their is one dice selected so the user can reroll
        }
        else if(dice1.isSelected() == false || dice2.isSelected() == false || dice3.isSelected() == false || dice4.isSelected() == false || dice5.isSelected() == false && reroll.isEnabled() == true)
        {
           reroll.setEnabled(false); //No dices selected, the user can't reroll
        }
        
        if(dice1.isSelected())
        {
          dice1.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(0) +"selected.png")); // We load a different image when the dices are selected so that the user can see the difference between select and not selected dices
        }
        if(!dice1.isSelected())
        {
          dice1.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(0) +".png"));
        }
        if(dice2.isSelected())
        {
          dice2.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(1) +"selected.png"));
        }
        if(!dice2.isSelected())
        {
          dice2.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(1) +".png"));
        }
        if(dice3.isSelected())
        {
          dice3.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(2) +"selected.png"));
        }
        if(!dice3.isSelected())
        {
          dice3.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(2) +".png"));
        }
        if(dice4.isSelected())
        {
          dice4.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(3) +"selected.png"));
        }
        if(!dice4.isSelected())
        {
          dice4.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(3) +".png"));
        }
        if(dice5.isSelected())
        {
          dice5.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(4) +"selected.png"));
        }
        if(!dice5.isSelected())
        {
          dice5.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(4) +".png"));
        }
      }
   }
   
   /**
      The HintButtonListener action listener is load when a player click the hint button
      This function allow to display the rules of the yahtzee game in a JPanel window
   */
   private class HintButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
        try //Used to handle exception
        {
          JTextArea textArea = new JTextArea(getHint());
          textArea.setEditable(false);
          JScrollPane scrollPane = new JScrollPane(textArea);  
          textArea.setLineWrap(true);  
          textArea.setWrapStyleWord(true); 
          scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
          JOptionPane.showMessageDialog(null, scrollPane, "Hint on the game", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException ex) //Handle the exception 
        {
          System.out.println("An IO error occured: ");
          ex.printStackTrace(System.out);
        }
      }
   }
   
   
   
   
   /**
      The getHint method allow to create a string filled with all the yahtzee rules 
      It read a .txt file to get the rules
      @return a string that is filled with all the rules of the yahtzee game
   */
   
   public String getHint() throws IOException
   {
       File hint = new File("textfiles\\hint.txt");
       Scanner hScan = new Scanner(hint);
       
       String hintString = "";
       
       while(hScan.hasNext() == true)
       {
         hintString += hScan.nextLine()+"\n";
       }
       hScan.close();
       return hintString;
   }
   
   
   
   
   /**
      The RerollButtonListener action listener is load when a player click the reroll button
      It allows to change the icon of the dices the user want to reroll
      it also permit to increase the nb of reroll of the current player and to check if we have to disabled all the dice so that the user is force to select a row
   */
   
   private class RerollButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
        if(dice1.isSelected()) //If dice 1 is selected, we reroll which means we may have to load a new image
        {
          myRoll.setRoll(0); //Generating a new random value for dice 0
          dice1.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(0)+".png")); //Change the image of dice 0 depending of its value
        }
        if(dice2.isSelected())
        {
          myRoll.setRoll(1);
          dice2.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(1)+".png"));
        }
        if(dice3.isSelected())
        {
          myRoll.setRoll(2);
          dice3.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(2)+".png"));
        }
        if(dice4.isSelected())
        {
          myRoll.setRoll(3);
          dice4.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(3)+".png"));
        }
        if(dice5.isSelected())
        {
          myRoll.setRoll(4);
          dice5.setIcon(new ImageIcon("dices_illustrations\\dice"+ myRoll.getRoll(4)+".png"));
        }
        
        nbReroll++; //We increment the nb of reroll of the player
        
        myScoreboard.ReinitializeScoreSheet(); //We reintialize the scoresheet so that it can be load with the new roll
        SetScoreboard(playerTurn); //We set the scoreboard with the new roll values
        
        message.setText((2-nbReroll)+" Reroll left"); //We change the message label so that it show the new number of reroll the player still have
        
        if(nbReroll == 2) //If nb reroll is equal to 2 then we disable each dice and the reroll button so that the user is forced to choose a row in the scoreboard
        {
          dice1.setEnabled(false);
          dice2.setEnabled(false);
          dice3.setEnabled(false);
          dice4.setEnabled(false);
          dice5.setEnabled(false);
          reroll.setEnabled(false);
          message.setText("<html>You can't reroll more.<br>Please choose a row.<html>"); //We change the message label to explain the player what he have to do
        }
        
        dice1.setSelected(false);
        dice2.setSelected(false);
        dice3.setSelected(false);
        dice4.setSelected(false);
        dice5.setSelected(false);
        reroll.setEnabled(false);
        
      }
   }
   
   /**
      The SetScoreboard function receive a player number as argument and then load its scoreboard on the user interface
      It will change the value printed on each button of the 2D array of button that we use as scoreboard
      It also disabled each row already locked for a given player so that he can't lock 2 times the same row
      @param playerNb integer representing the number of the player currently playing
   */
   public void SetScoreboard(int playerNb)
   {
      myScoreboard.Score(myRoll, playerNb);
      for (int i = 0; i < 16; i++) 
      {
         for (int j = 0; j < myScoreboard.getNbPlayer(); j++) 
         {

           scoreboard[i][j].setText(Integer.toString(myScoreboard.getScore(i,j))); //We set the text on the button depending on the score of this row
           
           if(j != playerNb || myScoreboard.getFill(i,j) == true) //Disable the column of the player not playing and also disable the already filled row of the player
              scoreboard[i][j].setEnabled(false);
          }
       }
   }
   
   /**
      The ScoreboardButtonListener action listener is load when a player click on a button of the 2D array button that we use as scoreboard
      This function allow to disable the button the user clicked on so that the user can't lock a second time this row
      It also allows go to the next player turn and to load its score
      This function also allow to enable all the dices again for the next player
      This function checks if the game is finished as the game will be finished when the last player will lock its last row
      If the game is finished this function tell which player won and ask the user if he wants to play again or not
   */
   
   private class ScoreboardButtonListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent e)
      {
        try //Used to handle exception
        {
          String actionCommand = e.getActionCommand(); //Allow to have the name of the button pressed
          
          int rowNb = Integer.parseInt(actionCommand); //As we decided to give number to each row in the 2D array of button we put this number as an int in the row nb variable
          
          for(int i = 0 ; i < 16 ; i++)
          {
            scoreboard[i][playerTurn].setEnabled(false); //We disable all the player scoreboard button because if he selected a row it means this is the end of its turn
          }
          
          scoreboard[rowNb][playerTurn].setEnabled(false); //We put the button the player selected in disable so that he can't select it again
          myScoreboard.setFill(rowNb,playerTurn, true); //We fill the alreadyFill array of the scoreboard class as true so that it save the player have already lock this row
          
          myScoreboard.setScore(15, playerTurn, myScoreboard.TotalGame(playerTurn)); //We set the total value of the player scoreboard
          myScoreboard.setScore(6, playerTurn, myScoreboard.TotalUpperSection(playerTurn)); //We set the upper section total value of the player 
          
          if(myScoreboard.TotalUpperSection(playerTurn) > 63) //If upper section total is higher than 63 then we give the player its bonus
          {
            myScoreboard.setScore(7, playerTurn, 35);
          }
          
          myScoreboard.ReinitializeScoreSheet(); //We reinitialize the not filled row of the player
          
          if(playerTurn == myScoreboard.getNbPlayer()-1) //If it is the last player it means that next player is player number 0
          {
            playerTurn = 0;
          }
          else
          {
            playerTurn++; //Else it means that next player is played number + 1
          }
          
          nbReroll = 0; //New player so number of reroll set to 0
          myRoll.InitalRoll(); //New player, new roll
          
          for(int i = 0 ; i < 16 ; i++)
          {
            scoreboard[i][playerTurn].setEnabled(true); //New player means we can enable its scoreboard button so that he can make a choice
          }
          
          SetScoreboard(playerTurn); //We set the next player scoreboard value
          SetDisplayDices(); //We display the next player roll with the good images
          
          dice1.setEnabled(true); //We enable the next player to click on the dices 
          dice2.setEnabled(true);
          dice3.setEnabled(true);
          dice4.setEnabled(true);
          dice5.setEnabled(true); 
          
          message.setText((2-nbReroll)+" Reroll left"); //We reinitialize the text in the message label so that the next player know how many reroll he have left
          
          if(myScoreboard.End() == true) //We check if the game is not finished
          {
            myScoreboard.findWinner(myScoreboard.getNbPlayer()); //It is finished so we display who is the winner 
            String[] options = {"YES!", "NO!"}; //Array of option that will be displayed on the show option dialog
            myScoreboard.highscore(); //Calculate the highscore and save it in a file
            highscore.setText("Highscore : "+myScoreboard.getHighscore());
            int choice = JOptionPane.showOptionDialog(null, "Another game ?", "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // We ask the user if he wants to play again
            
            dispose(); //We destroy the current JFrame object
            
            if(choice == 0) //If he wants to play then we create a new user interface
            {
              new UserInterface();
            }
          }
      }
      catch (IOException ex) //Handle the exception
      {
          
        System.out.println("An IO error occured: ");
        ex.printStackTrace(System.out);
       }
     }
   }
   
   /**
      Main method
   */
   public static void main(String[] args) throws IOException
   {     
     new UserInterface();   //We call the constructor of the user interface
   }
   
}