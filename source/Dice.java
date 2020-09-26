import java.util.Random; //Needed for the random class

public class Dice 
{
    
    private int value; //Value of the dice
    
    /**
      This constructor allows to put a random number betwen 1 and 6 in a given dice
    */
    public Dice()
    { 
        Random rand = new Random(); //Allows us to generate random numbers
        value = rand.nextInt(6)+1; //Generates a random number between 1 and 6 (both included)
    }
    
    /**
     This function the the value of a dice to a given value
     @param val the value to put in the dice
    */
    public void SetDice(int val)
    {
      value = val;
    }
    
    /** 
     This function return the value of a given dice
     @return the value of the dice
    */
    public int getDice()
    {
      return value;
    }
    
}
