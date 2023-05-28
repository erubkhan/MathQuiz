/*imports*/
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;

class Main {

  /* Declaring and initializing variables needed for data processing */
  static int answerNumber = 1;
  static int questionNumber = 0;
  static int userAnswer = 0;
  static double mark = 0;
  static final int totalQuestions = 5;
  static Timer timer = new Timer();
  int correctAnswerIndex;

  /* Initializing arrays and their holding values */
  static boolean[] questionsAnswered = new boolean[5];
  static String[] questions = {
      "What is 2+4+6+8+10?",
      "What is the sum of angles in a triangle?",
      "How many sides does an octagon have?",
      "What is 2 x 2 x 2 x 2 x 2?",
      "What shape has 4 equal sides?"
  };
  
  /* Use of parallel arrays  */
  static String[][] answers = {
      { "30", "12", "40", "24" },
      { "90 degrees", "120 degrees", "180 degrees", "360 degrees" },
      { "4", "6", "10", "8" },
      { "2", "10", "16", "32" },
      { "Rectangle", "Pentagon", "Square", "Octagon" }
  };
  static String[] possibleAnswers = {"a", "b", "c", "d"};
  static int[] correctAnswerIndexes = {0, 2, 3, 3, 2};
  //static String[] correctAnswers = { "a", "c", "d", "d", "c" };

  /* Initialize and create GUI elements */
  static JFrame frmMain = new JFrame("Quiz");
  static JPanel pnlMain = new JPanel();
  static JLabel lblTitle = new JLabel("Math Quiz!");
  static JTextField question = new JTextField("What is 2+4+6+8+10?");
  static JTextField choice1 = new JTextField("a) 30");
  static JTextField choice2 = new JTextField("b) 12");
  static JTextField choice3 = new JTextField("c) 40");
  static JTextField choice4 = new JTextField("d) 24");
  static JLabel lblAnswer = new JLabel("<html>Answer:</html>");
  static JTextArea feedback = new JTextArea("");
  static JTextField txtUserInput = new JTextField();
  static JRadioButton[] answerButtons = new JRadioButton[4];
  static JLabel lblTimer = new JLabel("");
  static int timeLeft = 100;
  
  /* Creating a method with parameters for setting the correct array element to each question */
  public static void setQuestion(int i) {
    question.setText(questions[i]);
    choice1.setText("a) " + answers[i][0]);
    choice2.setText("b) " + answers[i][1]);
    choice3.setText("c) " + answers[i][2]);
    choice4.setText("d) " + answers[i][3]);
    feedback.setText("");
    txtUserInput.setText("");
  }

  /* Creating a method with return of the calculations of an average mark */
  public static double calculateMark(int correct, int total) {
    double mark = (double) correct / total * 100;
    return Math.round(mark);
  }

  public static void main(String[] args) {

    /* Setting bounds for initilized GUI elements */
    frmMain.setBounds(5, 5, 386, 500);
    frmMain.setLayout(null);
    frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /* Adding panel elements to the Main frame */
    frmMain.add(pnlMain);
    pnlMain.setBounds(0, 0, 569, 682);
    pnlMain.setLayout(null);
    pnlMain.setBackground(Color.PINK);

    /* Add title and label elements */
    lblTitle.setForeground(Color.BLACK);
    lblTitle.setFont(new Font("Monospace", Font.BOLD, 16));
    lblTitle.setBounds(152, 22, 200, 50);
    lblAnswer.setBounds(30, 270, 150, 50);
    lblTimer.setBounds(30, 70, 100, 20);
   
    /* formatting the bounds of textfields */
    question.setBounds(30, 90, 268, 50);
    choice1.setBounds(30, 150, 150, 50);
    choice2.setBounds(30, 210, 150, 50);
    choice3.setBounds(200, 150, 150, 50);
    choice4.setBounds(200, 210, 150, 50);
    txtUserInput.setBounds(98, 280, 30, 30);

    /* Stops the user from editing textfields */
    question.setEditable(false);
    choice1.setEditable(false);
    choice2.setEditable(false);
    choice3.setEditable(false);
    choice4.setEditable(false);

    /* Adding feedback text field */
    feedback.setBounds(30, 330, 319, 70);
    feedback.setLineWrap(true);
    feedback.setEditable(false);

    /* add buttons to the main fraim */
    JButton btnNext = new JButton("Next Question");
    JButton btnCheck = new JButton("Enter");
    JButton btnResult = new JButton("Results");
    JButton btnHint = new JButton("<html>?<html>");

    /* set bounds fo buttons */
    btnNext.setBounds(30, 415, 318, 40);
    btnCheck.setBounds(142, 280, 90, 30);
    btnResult.setBounds(242, 280, 108, 30);
    btnHint.setBounds(306, 92, 40, 46);
    
    /* Adding scroll bar to feedback textfield */
    JScrollPane scroll;
    scroll = new JScrollPane(feedback);
    scroll.setVerticalScrollBarPolicy(
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    frmMain.add(scroll, pnlMain);

    /* adding all GUI elements to panel frame */
    pnlMain.add(btnNext);
    pnlMain.add(btnCheck);
    pnlMain.add(btnResult);
    pnlMain.add(txtUserInput);
    pnlMain.add(feedback);
    pnlMain.add(choice4);
    pnlMain.add(choice3);
    pnlMain.add(choice2);
    pnlMain.add(choice1);
    pnlMain.add(question);
    pnlMain.add(lblTitle);
    pnlMain.add(lblAnswer);
    pnlMain.add(btnHint);
    pnlMain.add(lblTimer);

    /* allows the Next Question button to perform its task */
    btnNext.addActionListener(ActionEvent -> {
      if (questionNumber >= questions.length)
        return;
      questionNumber++;

      setQuestion(questionNumber);
    });

    /* allows the Check Answer button to perform its task */
    btnCheck.addActionListener((ActionEvent e) -> {
      String answer = txtUserInput.getText();
      String correctAnswer = possibleAnswers[correctAnswerIndexes[questionNumber]];
      /* check answer */
      if (answer.equalsIgnoreCase(correctAnswer)) {
        feedback.setText("Your answer is correct! Your math skills are strong! Keep up the good work!");
        questionsAnswered[questionNumber] = true;
        pnlMain.setBackground(Color.GREEN);
      } else {
        feedback.setText("Your answer is incorrect. The correct answer was " + correctAnswer + ". Keep practicing your addition skills.");
        pnlMain.setBackground(Color.RED);
      }
    });

    /* allows the Final Result button to perform its task */
    btnResult.addActionListener(ActionEvent -> {
      if (questionNumber < totalQuestions - 1)
        return;
      
      /* allows the timer end at 0 seconds */
      timer.cancel();
      
      int correctAnswers = 0;
      int incorrectAnswers = 0;

      for (int i = 0; i < questionsAnswered.length; ++i) {
        if (questionsAnswered[i])
          ++correctAnswers;
        else
          ++incorrectAnswers;
      }
      
      /* calling created method to calculate mark */
      mark = calculateMark(correctAnswers, totalQuestions);

      /* initializing random value for a money prize for the user */
      Random rnd = new Random();
      int outcome = rnd.nextInt(500) + 1;

      /* Printing out statistics for the user */
      String QuizStatsMessage = "Results: ";
      QuizStatsMessage += "Number of incorrect answers: " + incorrectAnswers;
      QuizStatsMessage += "Number of correct answers: " + correctAnswers;
      QuizStatsMessage += "Mark: " + mark + "%";
      QuizStatsMessage += (mark >= 50) ? "You passed" : "You failed. Try again.";
      QuizStatsMessage += "Prize: You have won $" + outcome + " for completing this Quiz!";
      feedback.setText(QuizStatsMessage);
    });

    /* time for user to complete entire quiz */
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        --timeLeft;
        
        lblTimer.setText(String.format("Time Left: %d", timeLeft));

        if (timeLeft <= 0) {
          System.exit(0);
        }
      }
    }, 0, 1000);

    /* setting the main frame visible to the user */
    frmMain.setVisible(true);
  }// End of Main Class
}// End of Main Method