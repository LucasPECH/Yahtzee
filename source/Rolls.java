public class Rolls 
{
  
    private Dice [] roll = new Dice [5]; //Array of integers filled with the 5 dices

    /**
    This function use the Dice constructorto fill the 5 dices value in an array 
    */
    public void InitalRoll() 
    { 
        for (int i = 0; i < 5; i++) 
        {
          roll[i] = new Dice();
        }
    }
    
    /** 
    This function creates a new dice for a specific occurence in an array
    @param diceNb the occurence of the dice 
    */
    public void setRoll(int diceNb)
    {
      roll[diceNb] = new Dice();
    }
   
    /**
    This function set the value of a given dice at a given value
    @param diceNb is the nb of the dice to fill
    @param value is the value to put in the dice
    */
    public void setRollValue(int diceNb, int value)
    {
      roll[diceNb].SetDice(value);
    }

    /**
    This function show the value of the 5 dices value stored in an array
    */
    public void ShowRoll()
    { 
        for (int i = 0; i < 5; i++) 
        {
            System.out.println("Dice "+ (i+1) + " : " + roll [i]);
        }
        System.out.println("\n");
    }
    
    /**
    This function is an accessor
    @param diceNb the occurence of the dice 
    @return it returns one of the dices of the roll
    */
    public int getRoll(int diceNb)
    {
        return roll[diceNb].getDice();
    }
    
    /**
    This function is an accessor
    @return it returns the array contening the dices
    */
    public Dice[] getRollArray()
    {
        return roll;
    }
    
    /**
    This function checks if there are n similar dices in a roll
    @param n the number of similar dices
    @return it returns true if there are n similar dices, else it returns false
    */
    public boolean IsThere_n_SameDice(int n)
    {
      for (int i = 1; i < 7 ; i++)
      { 
        int nbSameDice = 0;
        for (int j = 0 ; j < 5 ; j++)
        {
          if(roll[j].getDice() == i)
          {
            nbSameDice++;
          }
        }
        if(nbSameDice == n)
        {
          return true;
        }
      }
      return false;
    }
    
    /**
    This function calculates the sum of all the dices in a roll
    @return it returns the sum 
    */
    public int RollSum()
    {
      int sum = 0;
      for (int i = 0 ; i < 5 ; i++)
      {
        sum += roll[i].getDice();
      }
      return sum;
    }
    
    /**
    This function checks if the dices in the roll make a small straight
    @return it returns true if the dice make a small straight, else it returns false
    */
    public boolean SmallStraight()
    {
      int nbPair = 0;
      for (int i = 1; i < 7 ; i++)
      { 
        int nbSameDice = 0;
        for (int j = 0 ; j < 5 ; j++)
        {
          if(roll[j].getDice() == i)
          {
            nbSameDice++;
          }
        }
        if(nbSameDice == 2)
        {
          nbPair++;
        }
        else if(nbSameDice >= 3)
        {
          return false;
        }
      }
      if ((nbPair == 1 && ((min() == 1 && max() == 4) || (min() == 2 && max() == 5) || (min() == 3 && max() == 6) )) || (nbPair == 0 && ThereIsAThree() == true && ThereIsAFour() == true))
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    
    /**
    This function checks if the dices in the roll make a large straight
    @return it returns true if the dice make a large straight, else it returns false
    */
    public boolean LargeStraight()
    {
      for (int i = 1; i < 7 ; i++)
      { 
        int nbSameDice = 0;
        for (int j = 0 ; j < 5 ; j++)
        {
          if(roll[j].getDice() == i)
          {
            nbSameDice++;
          }
        }
        if(nbSameDice >= 2)
        {
          return false;
        }
      }
      if ((min() == 1 && max() == 5) || (min() == 2 && max() == 6))
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    
    /**
    This function finds the highest dice in a roll
    @return it returns the highest dice value
    */
    public int max()
    {
      int max = 0;
      for(int i = 0 ; i < 5 ; i++)
      {
        if(roll[i].getDice() > max)
        {
          max = roll[i].getDice();
        }
      }
      return max;
    }
    
    /**
    This function finds the lowest dice in a roll
    @return it returns the lowest dice value
    */
    public int min()
    {
      int min = 6;
      for(int i = 0 ; i < 5 ; i++)
      {
        if(roll[i].getDice() < min)
        {
          min = roll[i].getDice();
        }
      }
      return min;
    }
    
    /**
    This function checks if there is a three in the roll
    @return it returns true if there is a three in the roll, else it returns false
    */
    public boolean ThereIsAThree()
    {
      for(int i = 0 ; i < 5 ; i++)
      {
        if(roll[i].getDice() == 3)
        {
          return true;
        }
      }
      return false;
    }
    
    /**
    This function checks if there is a four in the roll
    @return it returns true if there is a four in the roll, else it returns false
    */
    public boolean ThereIsAFour()
    {
      for(int i = 0 ; i < 5 ; i++)
      {
        if(roll[i].getDice() == 4)
        {
          return true;
        }
      }
      return false;
    }
    
}
