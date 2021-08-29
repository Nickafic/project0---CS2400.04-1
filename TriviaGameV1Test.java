public class TriviaGameV1Test
{
   public static void main(String[] args)
   {
      TriviaGameV1 game = new TriviaGameV1();
      while (game.askNextQuestion())
         game.showScore();
      System.out.println("Game over! Thanks for playing!");
   }
}
