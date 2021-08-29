import java.util.Scanner;
public class TriviaGameV1
{
   final int ARRAY_SIZE = 5;
   private String[] questions = new String[ARRAY_SIZE];
   private String[] answers = new String[ARRAY_SIZE];
   private int[] values = new int[ARRAY_SIZE];
   private int score = 0;
   private int questionNum = 0;
   public TriviaGameV1()
   {
      questions[0] = "The first Pokemon that Ash receives from Professor Oak";
      questions[1] = "Erling Kagge skiied into here alone on January 7, 1993";
      questions[2] = "1997 British band that produced 'Tub Thumper'";
      questions[3] = "Who is the tallest person on record (8 ft. 11 in) that haslived?";
      questions[4] = "PT Barnum said \"This way to the _______\"to attract people to the exit.";

      answers[0] = "pikachu";
      answers[1] = "south pole";
      answers[2] = "chumbawumba";
      answers[3] = "robert wadlow";
      answers[4] = "egress";

      values[0] = 1;
      values[1] = 2;
      values[2] = 2;
      values[3] = 3;
      values[4] = 1;


   }
   public boolean askNextQuestion()
   {
      Scanner kb = new Scanner(System.in);

      if(questionNum == 5)
         return false;
      else
      {
         System.out.println("\nQuestion " + (questionNum + 1));
         System.out.println(questions[questionNum]);
         String ans = kb.nextLine();
         if(ans.equalsIgnoreCase(answers[questionNum]))
         {
            score += values[questionNum];
            System.out.println("That is correct!");
            questionNum++;
         }
        else
        {
            System.out.println("Wrong. The correct answer is " + answers[questionNum]);
            questionNum++;
        }
        return true;
      }
   }
   public void showScore()
   {
      System.out.println("Your Score is " + score);
   }
}