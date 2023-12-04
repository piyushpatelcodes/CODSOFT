import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int attemptsLimit = 10;
        int rounds = 0;
        int score = 0;


        System.out.println("\n");
        for (int i = 0; i < 100; i++) {
            if(i==25){
                System.out.print(" Program Developed By : PIYUSH PATEL ");
            }
            System.out.print("-");
        }

        
        System.out.println("\n Enter your name : ");

        String name = scanner.nextLine();
        
        System.out.println("\n Hiii!!! "+name+" Welcome to the Number Guessing Game!");
        System.out.println(name+" . Try To Take LESS Attempts to Sore MORE.  You have ONLY 10 Attempts." );
    
        

        // Main game loop
        while (true) {
            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            System.out.println("\nNew round! Guess the number between " + lowerBound + " and " + upperBound);

            // Guessing loop
            for (int attempts = 1; attempts <= attemptsLimit; attempts++) {
                System.out.print("Enter your guess (Attempt " + attempts + "): ");
                int userGuess = scanner.nextInt();

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number!");
                    score += attemptsLimit - attempts + 1;
                    break;
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

                // Check if attempts limit is reached
                if (attempts == attemptsLimit) {
                    System.out.println("Sorry, you've reached the maximum number of attempts. The correct number was: " + targetNumber);
                }
            }

            // Ask if the user wants to play another round
            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgain = scanner.next().toLowerCase();

            if (!playAgain.equals("yes")) {
                rounds++;
                break;
            }

            
        }

        // Display final score and rounds played
        System.out.println("\nGame Over! Your total score is: " + score);
        System.out.println("You played " + rounds + " rounds.");
    }
}
