package factory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

import common.*;

public class SignUp implements ActionListener {
    private JFrame frame;
    final private JTextField[] textFields = new JTextField[10];
    final private JPasswordField[] password = new JPasswordField[2];
    public boolean isCustomer = false;

    final private String[] labels = {
            "First Name", "Last Name", "Street Name",
            "Street Number", "City", "Parish or State",
            "Zip Code", "Country", "Email",
            "Phone", "Password", "Confirm Password",
    };

    private JButton submit;
    final Border border = new LineBorder(new Color(0, 0, 0, 50), 1);
    final Font font = new Font("Brush Script MT", Font.PLAIN, 17);

    public SignUp() {
        createFrame();
    }

    public Person getEntity() {
        final Person p = getPerson();
        p.setId(Generator.generateId());
        p.setAddress(getAddress());
        p.setPassword(retrievePassword());
        return p;
    }

    private Person getPerson() {
        Person p = new Person();
        p.setFirstName(textFields[0].getText());
        p.setLastName(textFields[1].getText());
        p.setEmail(textFields[8].getText());
        p.setPhone(textFields[9].getText());
        return p;
    }

    private Address getAddress() throws NumberFormatException {
        final Address address = new Address();
        address.setCity(textFields[4].getText());
        address.setStreetName(textFields[2].getText());
        address.setStreetNumber(Integer.parseInt(textFields[3].getText()));
        address.setZipCode(textFields[6].getText());
        address.set_parish_state(textFields[5].getText());
        address.setCountry(textFields[7].getText());
        return address;
    }

    private String retrievePassword() {
        char[] pass = password[0].getPassword();
        return new String(pass);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("Submit Button clicked");
        if (event.getSource() == submit) {
            if (isRequiredSigned()) {
                App.signUpEntity(getEntity());
                JOptionPane.showMessageDialog(frame, "Signed Up Succesfully!");
                frame.dispose();
            }
        }

    }

    private boolean isRequiredSigned() {
        return true; // uncomment out after finish testing
        // for (int i = 0; i < 10; i++) {
        // if (i == 3 || i == 6) {
        // } else if (textFields[i].getText().equals("")) {
        // textFields[i].setBorder(new LineBorder(Color.red, 1));
        // JOptionPane.showMessageDialog(frame, labels[i] + " is required!");
        // return false;
        // }
        // textFields[i].setBorder(new LineBorder(Color.green, 2));
        // }

        // String pass = new String(password[0].getPassword());
        // if (pass.equals("")) {
        // JOptionPane.showMessageDialog(frame, "Password required");
        // return false;
        // } else if (pass.equals(new String(password[1].getPassword()))) {
        // if (pass.length() < 6 || pass.length() > 14) {
        // JOptionPane.showMessageDialog(frame, "Password length must be between 6-14");
        // return false;
        // }
        // password[0].setBorder(new LineBorder(Color.green, 1));
        // password[1].setBorder(new LineBorder(Color.green, 1));
        // return true;
        // }
        // password[0].setBorder(new LineBorder(Color.red, 1));
        // JOptionPane.showMessageDialog(frame, "Passwords does not match.");
        // return false;
    }

    private JPanel innerPanels() {
        JPanel top, middle, bottom, outerPanel, centralPanel, submitPanel;

        top = new JPanel(new GridLayout(1, 2));
        for (int i = 0; i < 2; i++)
            top.add(createInsideTextFieldPanel(i));

        middle = new JPanel(new GridLayout(3, 2));
        ((GridLayout) middle.getLayout()).setHgap(20);
        middle.setBorder(border);
        for (int i = 2; i < 8; i++)
            middle.add(createInsideTextFieldPanel(i));

        bottom = new JPanel(new GridLayout(2, 2));
        ((GridLayout) bottom.getLayout()).setHgap(5);
        for (int i = 8; i < 10; i++)
            bottom.add(createInsideTextFieldPanel(i));

        for (int i = 0; i < 2; i++)
            bottom.add(createPasswordFields(i));

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(font);
        addressLabel.setHorizontalAlignment(JLabel.CENTER);

        centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(top, BorderLayout.NORTH);
        centralPanel.add(addressLabel, BorderLayout.CENTER);
        ((BorderLayout) centralPanel.getLayout()).setVgap(20);
        centralPanel.add(middle, BorderLayout.SOUTH);

        submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.BOLD, 18));
        submit.setForeground(new Color(10, 220, 53));
        submit.setPreferredSize(new Dimension(200, 50));
        submit.addActionListener(this);
        submitPanel.add(submit);

        outerPanel = new JPanel(new BorderLayout());
        ((BorderLayout) outerPanel.getLayout()).setVgap(30);
        outerPanel.setBounds(95, 10, 600, 860);
        outerPanel.add(centralPanel, BorderLayout.NORTH);
        outerPanel.add(bottom, BorderLayout.CENTER);
        outerPanel.add(submitPanel, BorderLayout.SOUTH);

        return outerPanel;
    }

    private JPanel createPasswordFields(int i) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel(labels[i + 10]);
        panel.setBackground(new Color(0, 20, 255, 50));
        password[i] = new JPasswordField();
        password[i].setHorizontalAlignment(JTextField.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        panel.add(label);
        panel.add(password[i]);
        password[i].setPreferredSize(new Dimension(50, 60));
        ((GridLayout) panel.getLayout()).setVgap(-20);
        return panel;
    }

    private JPanel createInsideTextFieldPanel(int i) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel(labels[i]);
        textFields[i] = new JTextField();
        textFields[i].setHorizontalAlignment(JTextField.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setFont(font);
        panel.add(label);
        panel.add(textFields[i]);
        if (i >= 2) {
            if (i < 8)
                panel.setBackground(new Color(80, 120, 53, 50));
            if (i >= 8)
                panel.setBackground(new Color(0, 20, 255, 50));
            textFields[i].setPreferredSize(new Dimension(100, 70));
            ((GridLayout) panel.getLayout()).setVgap(-22);
        } else {
            panel.setBackground(new Color(0, 20, 255, 50));
            textFields[i].setPreferredSize(new Dimension(50, 60));
            ((GridLayout) panel.getLayout()).setVgap(-6);
        }
        return panel;
    }

    private void createFrame() {
        frame = new JFrame("Sign Up Form");
        frame.setSize(800, 920);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(innerPanels());
        frame.setVisible(true);
    }
}
