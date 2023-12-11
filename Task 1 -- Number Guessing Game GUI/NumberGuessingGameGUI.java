// Program Developed by : PIYUSH PATEL

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI {
    private JFrame frame;
    private JTextField nameTextField;
    private JTextPane outputTextPane;
    private JButton startButton;
    private JButton guessButton;
    private JTextField guessTextField;
    private JLabel attemptsLabel;

    private StyledDocument styledDocument;
    private Style regularStyle;
    private Style greenStyle;
    private Style redStyle;
    private Style blueStyle;

    private Random random;
    private int targetNumber;
    private int attemptsLimit;
    private int attempts;
    private int score;
    private int rounds;


    

    public NumberGuessingGameGUI() {
        frame = new JFrame("Number Guessing Game -- Developed By: PIYUSH PATEL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        random = new Random();
        targetNumber = 0;
        attemptsLimit = 10;
        attempts = 0;
        score = 0;
        rounds = 0;

        createUI();

        frame.setVisible(true);
    }

    private void createUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        ImageIcon icon = new ImageIcon("path/to/your/image.jpg");
        JLabel imageLabel = new JLabel(icon);
        topPanel.add(imageLabel);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameTextField = new JTextField(15);
        startButton = new JButton("Start Game");

        topPanel.add(nameLabel);
        topPanel.add(nameTextField);
        topPanel.add(startButton);

        outputTextPane = new JTextPane();
        outputTextPane.setEditable(false);
        outputTextPane.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(outputTextPane);

        JPanel guessPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel guessLabel = new JLabel("Your guess:");
        guessTextField = new JTextField(5);
        guessButton = new JButton("Guess");
        attemptsLabel = new JLabel("Attempts left: " + attemptsLimit);

        guessPanel.add(guessLabel);
        guessPanel.add(guessTextField);
        guessPanel.add(guessButton);
        guessPanel.add(attemptsLabel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(guessPanel, BorderLayout.SOUTH);

        styledDocument = outputTextPane.getStyledDocument();
        regularStyle = styledDocument.addStyle("RegularStyle", null);
        greenStyle = styledDocument.addStyle("GreenStyle", null);
        redStyle = styledDocument.addStyle("RedStyle", null);
        blueStyle = styledDocument.addStyle("BlueStyle", null);

        StyleConstants.setForeground(greenStyle, Color.GREEN);
        StyleConstants.setForeground(redStyle, Color.RED);
        StyleConstants.setForeground(blueStyle, Color.BLUE);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });
        
    }

    private void startGame() {
        String name = nameTextField.getText();
        styledDocument.setCharacterAttributes(0, styledDocument.getLength(), regularStyle, true);
        appendStyledText("Hi ", regularStyle);
        appendStyledText(name, blueStyle);
        appendStyledText("! Welcome to the Number Guessing Game!\n"
                + "Try to take fewer attempts to score more. You have only 10 attempts.\n", regularStyle);

        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        score = 0;

        appendStyledText("New round! Guess the number between 1 and 100\n", regularStyle);

        nameTextField.setEnabled(false);
        startButton.setEnabled(false);
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        attemptsLabel.setText("Attempts left: " + attemptsLimit);
    }

    private void makeGuess() {
        if (attempts < attemptsLimit) {
            int userGuess;
            try {
                userGuess = Integer.parseInt(guessTextField.getText());
            } catch (NumberFormatException ex) {
                appendStyledText("Please enter a valid number.\n", regularStyle);
                return;
            }

            attempts++;

            if (userGuess == targetNumber) {
                appendStyledText("Congratulations! You guessed the correct number!\n", greenStyle);
                score += attemptsLimit - attempts + 1;
                nextRound();
            } else if (userGuess < targetNumber) {
                appendStyledText("Too low! Try again.\n", redStyle);
            } else {
                appendStyledText("Too high! Try again.\n", blueStyle);
            }

            if (attempts == attemptsLimit) {
                appendStyledText("Sorry, you've reached the maximum number of attempts. The correct number was: "
                        + targetNumber + "\n", regularStyle);
                nextRound();
            }

            attemptsLabel.setText(" Attempts left: " + (attemptsLimit - attempts));
            guessTextField.setText("");
        }
    }

    private void appendStyledText(String text, Style style) {
        try {
            styledDocument.insertString(styledDocument.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void nextRound() {
        appendStyledText("Do you want to play another round? (yes/no)\n", regularStyle);
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);

        String playAgain = JOptionPane.showInputDialog(frame, "Do you want to play another round? (yes/no)");

        if (playAgain != null && playAgain.toLowerCase().equals("yes")) {
            rounds++;
            startGame();
        } else {
            endGame();
        }
    }

    private void endGame() {
        nameTextField.setEnabled(true);
        startButton.setEnabled(true);
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);

        appendStyledText("Game Over! Your total score is: " + score + "\n", regularStyle);
        appendStyledText("You played " + rounds + " rounds.\n", regularStyle);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGameGUI();
            }
        });
    }
}

