package interfaces;

import javax.swing.*;
import javax.swing.border.LineBorder;

import common.Address;
import common.Person;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp implements ActionListener {
    private JFrame frame;
    private JButton submit;
    private final JTextField[] textFields = new JTextField[10];
    private final JPasswordField[] passwords = new JPasswordField[2];
    private final String[] labels = {
            "First Name", "Last Name", "Street Name",
            "Street Number", "City", "Parish or State",
            "Zip Code", "Country", "Email",
            "Phone", "Password", "Confirm Password",
    };

    public SignUp() {
        createFrame();
    }

    public Person getEntity() {
        Person person = getPerson();
        person.setAddress(getAddress());
        person.setPassword(retrievePassword());
        return person;
    }

    private Person getPerson() {
        Person person = new Person();
        person.setFirstName(textFields[0].getText());
        person.setLastName(textFields[1].getText());
        person.setEmail(textFields[8].getText());
        person.setPhone(textFields[9].getText());
        return person;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setCity(textFields[4].getText());
        address.setStreetName(textFields[2].getText());
        address.setStreetNumber(Integer.parseInt(textFields[3].getText()));
        address.setZipCode(textFields[6].getText());
        address.setParishState(textFields[5].getText());
        address.setCountry(textFields[7].getText());
        return address;
    }

    private String retrievePassword() {
        return new String(passwords[0].getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == submit) {
            if (isRequiredSigned()) {
                App.currentUser = App.signUpEntity(getEntity());
                JOptionPane.showMessageDialog(frame, App.signUpMessage(textFields[0].getText(), App.currentUser));
                if (!App.isEmp)
                    SwingUtilities.invokeLater(CustomerInterface::new);
                else
                    SwingUtilities.invokeLater(EmployeeInterface::new);
                this.frame.dispose();
            }
        }
    }

    private boolean isRequiredSigned() {
        for (int i = 0; i < 10; i++) {
            if (i == 3 || i == 6) {
                continue;
            }
            if (textFields[i].getText().isEmpty()) {
                textFields[i].setBorder(new LineBorder(Color.red, 1));
                JOptionPane.showMessageDialog(frame, labels[i] + " is required!");
                return false;
            }
            textFields[i].setBorder(new LineBorder(Color.green, 2));
        }

        String pass = new String(passwords[0].getPassword());
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Password required");
            return false;
        } else if (pass.equals(new String(passwords[1].getPassword()))) {
            if (pass.length() < 6 || pass.length() > 14) {
                JOptionPane.showMessageDialog(frame, "Password length must be between 6-14");
                return false;
            }
            passwords[0].setBorder(new LineBorder(Color.green, 1));
            passwords[1].setBorder(new LineBorder(Color.green, 1));
            return true;
        }
        passwords[0].setBorder(new LineBorder(Color.red, 1));
        JOptionPane.showMessageDialog(frame, "Passwords do not match.");
        return false;
    }

    private JPanel createPasswordFields(int i) {
        JPanel panel = createInnerPanel(labels[i + 10]);
        passwords[i] = new JPasswordField();
        addComponentToPanel(panel, passwords[i]);
        return panel;
    }

    private JPanel createInsideTextFieldPanel(int i) {
        JPanel panel = createInnerPanel(labels[i]);
        textFields[i] = new JTextField();
        addComponentToPanel(panel, textFields[i]);
        return panel;
    }

    private JPanel createInnerPanel(String labelText) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Brush Script MT", Font.PLAIN, 17));
        panel.setBackground(App.color);
        panel.add(label);
        return panel;
    }

    private void addComponentToPanel(JPanel panel, JComponent component) {
        ((JTextField) component).setHorizontalAlignment(JTextField.CENTER);
        component.setPreferredSize(new Dimension(100, 70));
        ((GridLayout) panel.getLayout()).setVgap(-22);
        panel.add(component);
    }

    private void createFrame() {
        frame = new JFrame("Sign Up Form");
        frame.setSize(800, 920);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(createInnerPanels());
        frame.setVisible(true);
    }

    private JPanel createInnerPanels() {
        JPanel top = createTopPanel();
        JPanel middle = createMiddlePanel();
        JPanel bottom = createBottomPanel();

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(top, BorderLayout.NORTH);
        centralPanel.add(middle, BorderLayout.CENTER);
        centralPanel.add(bottom, BorderLayout.SOUTH);
        ((BorderLayout) centralPanel.getLayout()).setVgap(20);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.BOLD, 18));
        submit.setForeground(new Color(10, 220, 53));
        submit.setPreferredSize(new Dimension(200, 50));
        submit.addActionListener(this);
        submitPanel.add(submit);

        JPanel outerPanel = new JPanel(new BorderLayout());
        ((BorderLayout) outerPanel.getLayout()).setVgap(30);
        outerPanel.setBounds(95, 10, 600, 860);
        outerPanel.add(centralPanel, BorderLayout.NORTH);
        outerPanel.add(submitPanel, BorderLayout.SOUTH);

        return outerPanel;
    }

    private JPanel createTopPanel() {
        JPanel top = new JPanel(new GridLayout(1, 2));
        for (int i = 0; i < 2; i++)
            top.add(createInsideTextFieldPanel(i));
        return top;
    }

    private JPanel createMiddlePanel() {
        JPanel middle = new JPanel(new GridLayout(3, 2));
        ((GridLayout) middle.getLayout()).setHgap(20);
        middle.setBorder(new LineBorder(new Color(0, 0, 0, 50), 1));
        for (int i = 2; i < 8; i++)
            middle.add(createInsideTextFieldPanel(i));
        return middle;
    }

    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel(new GridLayout(2, 2));
        ((GridLayout) bottom.getLayout()).setHgap(5);
        for (int i = 8; i < 10; i++)
            bottom.add(createInsideTextFieldPanel(i));
        for (int i = 0; i < 2; i++)
            bottom.add(createPasswordFields(i));
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Brush Script MT", Font.PLAIN, 17));
        addressLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(addressLabel, BorderLayout.NORTH);
        centralPanel.add(bottom, BorderLayout.CENTER);
        ((BorderLayout) centralPanel.getLayout()).setVgap(20);
        return centralPanel;
    }
}
