package GUI;
import Operatii.Operation;
import Operatii.Pair;
import Polinom.Polynomial;

import javax.swing.*;
import java.awt.*;

public class GUI {
// Se definesc campurile pentru interfata grafica
    JFrame frame;
    private JTextField firstPolynomialField = new JTextField();
    private JTextField secondPolynomialField = new JTextField();
    private JLabel firstPolynomialLabel = new JLabel("Polynomial 1:");
    private JLabel secondPolynomialLabel = new JLabel("Polynomial 2:");
    private JButton addButton = new JButton("Add");
    private JButton subtractButton = new JButton("Subtract");
    private JButton multiplyButton = new JButton("Multiply");
    private JButton divideButton = new JButton("Divide");
    private JButton deriveButton = new JButton("Derive");
    private JButton integrateButton = new JButton("Integrate");
    private JLabel resultLabel = new JLabel("Result:");
    private JLabel resultDisplayLabel = new JLabel("Result will be displayed here");
    private JButton resetButton = new JButton("Reset");

    // Constructorul apeleaza metoda initialize() care seteaza si configureazza interfata
    public GUI() {
        initialize();
    }

    // Metoda initialize() creaza un obiect JFrame care va fi fereastra aplicatiei
    // Seteaza titlul, dimensiunea si comportamentul la inchidre
    // De asemenea, configureaza un GridBagLayout pentru a pozitiona elem
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Polynomial Calculator");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 180, 180, 50};
        gridBagLayout.rowHeights = new int[]{32, 32, 32, 32, 32, 32, 32, 32};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        frame.getContentPane().setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 5, 5);
        constraints.gridx = 1;

        constraints.gridy = 1;
        frame.getContentPane().add(firstPolynomialLabel, constraints);
        constraints.gridy = 2;
        frame.getContentPane().add(secondPolynomialLabel, constraints);
        constraints.gridy = 6;
        frame.getContentPane().add(resultLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        frame.getContentPane().add(firstPolynomialField, constraints);
        constraints.gridy = 2;
        frame.getContentPane().add(secondPolynomialField, constraints);
        constraints.gridy = 6;
        frame.getContentPane().add(resultDisplayLabel, constraints);

        // Se adauga actiuni pe butoane
        addButton.addActionListener(e -> performOperation(Operation2.ADD));
        subtractButton.addActionListener(e -> performOperation(Operation2.SUBTRACT));
        multiplyButton.addActionListener(e -> performOperation(Operation2.MULTIPLY));
        divideButton.addActionListener(e -> performOperation(Operation2.DIVIDE));
        deriveButton.addActionListener(e -> performOperation(Operation2.DERIVE));
        integrateButton.addActionListener(e -> performOperation(Operation2.INTEGRATE));
        resetButton.addActionListener(e -> resetFields());

        constraints.gridx = 1;
        constraints.gridy = 3;
        frame.getContentPane().add(addButton, constraints);
        constraints.gridy = 4;
        frame.getContentPane().add(multiplyButton, constraints);
        constraints.gridy = 5;
        frame.getContentPane().add(deriveButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        frame.getContentPane().add(subtractButton, constraints);
        constraints.gridy = 4;
        frame.getContentPane().add(divideButton, constraints);
        constraints.gridy = 5;
        frame.getContentPane().add(integrateButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        // Se personalizeaza aspectul
        frame.getContentPane().add(resetButton, constraints);  // Set custom colors and font styles for the components
        firstPolynomialLabel.setForeground(new Color(47, 79, 79));
        secondPolynomialLabel.setForeground(new Color(47, 79, 79));
        resultLabel.setForeground(new Color(47, 79, 79));
        resultDisplayLabel.setForeground(new Color(25, 25, 112));

        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        addButton.setFont(buttonFont);
        subtractButton.setFont(buttonFont);
        multiplyButton.setFont(buttonFont);
        divideButton.setFont(buttonFont);
        deriveButton.setFont(buttonFont);
        integrateButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);

        addButton.setBackground(new Color(240, 230, 140));
        subtractButton.setBackground(new Color(240, 230, 140));
        multiplyButton.setBackground(new Color(240, 230, 140));
        divideButton.setBackground(new Color(240, 230, 140));
        deriveButton.setBackground(new Color(240, 230, 140));
        integrateButton.setBackground(new Color(240, 230, 140));
        resetButton.setBackground(new Color(255, 105, 180));
    }

    //Metoda performOperation() preia textul introdus in campurile firstPolynomialField si secondPolynomialField, construieste obiecte Polynomial pentru fiecare polinom
    // Efectueaza operatia corespunzatoare in functie de tipul de operatie primit ca argument
    private void performOperation(Operation2 operation) {
        String firstPolynomial = firstPolynomialField.getText();
        String secondPolynomial = secondPolynomialField.getText();

        try {
            Polynomial polynomial1 = new Polynomial();
            polynomial1.preiaRegex(firstPolynomial);
            Polynomial polynomial2 = new Polynomial();
            polynomial2.preiaRegex(secondPolynomial);

            switch (operation) {
                case ADD:
                    Polynomial result1 = Operation.add(polynomial1,polynomial2);
                    resultDisplayLabel.setText(result1.toString());
                    break;
                case SUBTRACT:
                    Polynomial result2 = Operation.subtract(polynomial1,polynomial2);
                    resultDisplayLabel.setText(result2.toString());
                    break;
                case MULTIPLY:
                    Polynomial  result3 = Operation.multiply(polynomial1,polynomial2);
                    resultDisplayLabel.setText(result3.toString());
                    break;
                case DIVIDE:
                    Pair result4 = Operation.divide(polynomial1,polynomial2);
                    resultDisplayLabel.setText(result4.toString());
                    break;
                case DERIVE:
                    Polynomial result5 = Operation.derivative(polynomial1);
                    resultDisplayLabel.setText(result5.toString());
                    break;
                case INTEGRATE:
                    Polynomial result6 = Operation.integrate(polynomial1);
                    resultDisplayLabel.setText(result6.toString()+" + C");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metoda resetFields() curata campurile de text si eticheta rezultatului, pregatind interfata pentru o noua operatie
    private void resetFields() {
        firstPolynomialField.setText("");
        secondPolynomialField.setText("");
        resultDisplayLabel.setText("Result will be displayed here");
    }

    // Clasa Operation2 este o enumerare care contine operatiile posibile
    private enum Operation2 {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        DERIVE,
        INTEGRATE
    }
}