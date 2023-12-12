package interfaces;

/**
   * @author Jovaughn Rose
   * @see https://github.com/jovaughnR
   */

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {

    private JFrame frame;
    private JPanel outPanel, top, middle, bottom;
    private JButton signIn, customerSignUp, employeeSignUp;
    private JTextField idField;
    private JPasswordField passField;

    public Login() {
        createFrame();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == signIn) {
            final int id = Integer.parseInt(idField.getText());
            char[] password = passField.getPassword();
            if (App.signIn(id, new String(password))) {
                App.currentUser = id;
                JOptionPane.showMessageDialog(frame, "Signed Succesfully!");
                if (id < 111111) {
                    App.isEmp = false;
                    new CustomerInterface();
                } else {
                    App.isEmp = true;
                    new EmployeeInterface();
                }
            } else {
                idField.setBorder(new LineBorder(Color.red));
                passField.setBorder(new LineBorder(Color.red));
                JOptionPane.showMessageDialog(frame, "Incorrect identification or password");
            }
        } else if (event.getSource() == customerSignUp) {
            App.isEmp = false;
            new SignUp();
        }

        else if (event.getSource() == employeeSignUp) {
            App.isEmp = true;
            new SignUp();
        }

        this.frame.dispose();
    }

    private void createPanels() {
        outPanel = new JPanel(new BorderLayout());
        outPanel.setBounds(25, 20, 580, 580);
        outPanel.setBackground(new Color(150, 90, 35)); // Set background color

        // CREATE THE TOP SECTION
        JPanel idPanel = new JPanel(new GridLayout(2, 1));
        idPanel.setBackground(new Color(150, 90, 35)); // Set background color
        JLabel idLabel = new JLabel("Your Identification Number");
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 80));
        idField.setHorizontalAlignment(JTextField.CENTER);
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel passPanel = new JPanel(new GridLayout(2, 1));
        passPanel.setBackground(new Color(150, 90, 35)); // Set background color
        JLabel passLabel = new JLabel("Your Password");
        passLabel.setHorizontalAlignment(JLabel.CENTER);
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(200, 80));
        passField.setHorizontalAlignment(JPasswordField.CENTER);
        passPanel.add(passLabel);
        passPanel.add(passField);

        top = new JPanel(new BorderLayout());
        top.add(idPanel, BorderLayout.NORTH);
        top.add(passPanel, BorderLayout.CENTER);

        // CREATE THE MIDDLE SECTION
        middle = new JPanel();
        middle.setBackground(new Color(150, 90, 35)); // Set background color
        JPanel signInPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signIn = new JButton("Sign In");
        signIn.setPreferredSize(new Dimension(300, 100)); // Set larger size
        signIn.setBackground(new Color(60, 179, 113)); // Set a different color
        signIn.addActionListener(this);
        signInPanel.add(signIn);

        middle.add(signInPanel);

        // CREATE THE BOTTOM SECTION
        bottom = new JPanel(new GridLayout(1, 2));
        bottom.setBackground(new Color(150, 90, 35)); // Set background color
        customerSignUp = new JButton("New Customer Sign Up");
        customerSignUp.setBackground(new Color(135, 206, 250)); // Set a different color
        customerSignUp.addActionListener(this);
        employeeSignUp = new JButton("New Employee Sign Up");
        employeeSignUp.setBackground(new Color(255, 69, 0)); // Set a different color
        employeeSignUp.addActionListener(this);

        bottom.add(customerSignUp);
        bottom.add(employeeSignUp);

        outPanel.add(top, BorderLayout.NORTH);
        outPanel.add(middle, BorderLayout.CENTER);
        outPanel.add(bottom, BorderLayout.SOUTH);
        ((BorderLayout) outPanel.getLayout()).setVgap(50);

        frame.add(outPanel);
    }

    private void createFrame() {
        frame = new JFrame("Grizzly's Entertainment Equipment Rental Login");
        frame.setSize(640, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);

        createPanels();

        frame.setVisible(true);
    }

}
