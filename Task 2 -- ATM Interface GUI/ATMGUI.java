// Program Developed by : PIYUSH PATEL

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


class BankAccount {
    private double balance;
    private String accountHolderName;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds. Withdrawal failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        balance -= amount;
        return true;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
    public interface TransactionCallback {
        void onComplete();
    }
    public double getBalance() {
        return bankAccount.getBalance();
    }

    public void withdraw(double amount, TransactionCallback callback) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // Simulating some processing time
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (bankAccount.withdraw(amount)) {
                    callback.onComplete();
                }

                return null;
            }
        };

        worker.execute();
    }

    public void deposit(double amount, TransactionCallback callback) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // Simulating some processing time
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                bankAccount.deposit(amount);
                callback.onComplete();

                return null;
            }
        };

        worker.execute();
    }
    public void checkBalance() {
        JOptionPane.showMessageDialog(null, "Current balance: " + bankAccount.getBalance(), "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showInfo() {
        JOptionPane.showMessageDialog(null, "Account Holder: " + bankAccount.getAccountHolderName() + "\nCurrent balance: " + bankAccount.getBalance(), "Account Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

class ATMGUI extends JFrame {
    private ATM atm;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        setTitle("Codsoft Bank ATM -- Developed By: PIYUSH PATEL");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton withdrawButton = createButton("Withdraw", Color.RED);
        JButton depositButton = createButton("Deposit", Color.GREEN);
        JButton checkBalanceButton = createButton("Check Balance", Color.BLUE);
        JButton showInfoButton = createButton("Show Info", Color.ORANGE);
        JButton exitButton = createButton("Exit", Color.GRAY);

        withdrawButton.addActionListener(e -> handleWithdraw());
        depositButton.addActionListener(e -> handleDeposit());
        checkBalanceButton.addActionListener(e -> handleCheckBalance());
        showInfoButton.addActionListener(e -> handleShowInfo());
        exitButton.addActionListener(e -> handleExit());

        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(checkBalanceButton);
        panel.add(showInfoButton);
        panel.add(exitButton);

        add(panel);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 30));
        return button;
    }

    private void handleWithdraw() {
        String amountStr = JOptionPane.showInputDialog(createIcon(), "Enter withdrawal amount:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);

                showLoadingScreen("Withdrawing...");

                atm.withdraw(amount, () -> {
                    hideLoadingScreen();
                    JOptionPane.showMessageDialog(null, "Withdrawal successful. Remaining balance: " + atm.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDeposit() {
        String amountStr = JOptionPane.showInputDialog(createIcon(), "Enter deposit amount:");
        if (amountStr != null) {
            try {
                double amount = Double.parseDouble(amountStr);

                showLoadingScreen("Depositing...");

                atm.deposit(amount, () -> {
                    hideLoadingScreen();
                    JOptionPane.showMessageDialog(null, "Deposit successful. New balance: " + atm.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleCheckBalance() {
        showLoadingScreen("Checking Balance...");
        atm.checkBalance();
        hideLoadingScreen();
    }

    private void handleShowInfo() {
        showLoadingScreen("Loading Info...");
        atm.showInfo();
        hideLoadingScreen();
    }

    private void handleExit() {
        showLoadingScreen("Exiting...");
        JOptionPane.showMessageDialog(null, "Exiting Codsoft Bank ATM. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE, createIcon());
        System.exit(0);
    }

    private void showLoadingScreen(String message) {
        JDialog loadingDialog = new JDialog(this, "Loading", true);
        loadingDialog.setLayout(new FlowLayout());
        loadingDialog.setSize(300, 120);
        loadingDialog.setLocationRelativeTo(this);

        JLabel loadingLabel = new JLabel(message);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // Use an indeterminate progress bar for animation

        loadingDialog.add(loadingLabel);
        loadingDialog.add(progressBar);

        // Schedule a task to close the loading dialog after 2000 milliseconds (2 seconds)
        Timer timer = new Timer(2000, e -> {
            loadingDialog.dispose();
            // Optionally, you can display a success message here
            // JOptionPane.showMessageDialog(null, "Transaction successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        timer.setRepeats(false); // Only execute the task once
        timer.start();

        loadingDialog.setVisible(true);
    }

    private void hideLoadingScreen() {
        SwingUtilities.invokeLater(() -> {
            for (Window window : Window.getWindows()) {
                if (window instanceof JDialog) {
                    JDialog dialog = (JDialog) window;
                    if ("Loading".equals(dialog.getTitle())) {
                        dialog.dispose();
                    }
                }
            }
        });
    }

    private Icon createIcon() {
        // Replace "path/to/your/icon.png" with the actual path to your image file
        try {
            BufferedImage originalImage = ImageIO.read(new File("2.png")); // Replace with your image path
            Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // return icon;
    }

    public static void main(String[] args) {
        String accountHolderName = JOptionPane.showInputDialog("Enter your name:");
        String initialBalanceStr = JOptionPane.showInputDialog("Enter initial balance:");

        try {
            double initialBalance = Double.parseDouble(initialBalanceStr);
            BankAccount userAccount = new BankAccount(accountHolderName, initialBalance);
            ATM atm = new ATM(userAccount);

            SwingUtilities.invokeLater(() -> {
                new ATMGUI(atm).setVisible(true);
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for initial balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}