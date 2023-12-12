package interfaces;

import javax.swing.*;
import javax.swing.border.*;

import common.Message;
import common.Rental;
import common.Person;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class EmployeeInterface extends MouseAdapter implements ActionListener {
    private JFrame frame;
    private JPanel dashboard, body, searchResultPanel;
    private JButton[] navigationButtons = new JButton[4];
    private JLabel logo;
    private JTextField searchField;
    private MessagingApp messagingApp;
    private InventoryInterface inventory;

    public EmployeeInterface() {
        this.inventory = new InventoryInterface();
        configureInterface();
    }

    private void createFrame() {
        frame = new JFrame("Grizzly's Entertainment Equipment Rentals");
        frame.setSize(App.width, App.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaleImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaleImage);
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new Dimension(350, 100));
        textField.setBackground(new Color(0, 0, 0, 0));
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setForeground(Color.WHITE);
        return textField;
    }

    private void createDashBoard() {
        dashboard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dashboard.setPreferredSize(new Dimension(1600, 90));
        dashboard.setBackground(Color.GRAY);
        dashboard.setBorder(new LineBorder(Color.BLACK, 2));
        logo = new JLabel(scaleImage("images/logo.jpeg", 100, 100));
        JLabel titleLabel = new JLabel("Grizzly's Entertainment Equipement Rental");
        dashboard.add(logo);
        dashboard.add(titleLabel);
    }

    private void createBody() {
        body = new JPanel(null);
        body.setPreferredSize(new Dimension(1600, 800));
        body.setBackground(new Color(150, 90, 35));
        body.setBorder(new LineBorder(Color.BLACK, 2));

        body.add(searchResultPanel);
    }

    private void createSearchResultPanel() {
        searchResultPanel = new JPanel(new BorderLayout());
        searchResultPanel.setBounds(300, 150, 600, 450);
        searchResultPanel.setBackground(Color.PINK);
        searchResultPanel.add(new JLabel(scaleImage("images/mic.jpeg", 600, 450)));
    }

    private void configureInterface() {
        createFrame();
        createDashBoard();
        createSearchResultPanel();

        messagingApp = new MessagingApp();
        messagingApp.setTitle("Employee Messaging");
        messagingApp.setSize(350, 500);
        messagingApp.setLocationRelativeTo(null);

        searchField = createTextField("");
        searchResultPanel.add(searchField);

        createBody();
        createButtons();

        for (JButton button : navigationButtons)
            dashboard.add(button);

        frame.add(dashboard, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == navigationButtons[0]) {
            System.out.println("Rental request button clicked");
            SwingUtilities.invokeLater(RentalRequest::new);
        }

        if (e.getSource() == navigationButtons[3]) {
            createEntityFrame("Send Message to: ", getMessage);
        }

        if (e.getSource() == navigationButtons[1]) {
            inventory.setVisible(true);
        }

        if (e.getSource() == navigationButtons[2]) {
            createEntityFrame("Create Invoice for: ", generateInvoice);
        }
    }

    @FunctionalInterface
    interface Function {
        void apply(int receiverId, JFrame frame);
    }

    Function getMessage = (id, frame) -> getMessage(id, frame);
    Function generateInvoice = (id, frame) -> generateInvoice(id, frame);

    private void createEntityFrame(String buttonText, Function func) {

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBackground(App.color);

        // get all the customers registered in the system
        @SuppressWarnings("unchecked")
        ArrayList<Integer> ids = (ArrayList<Integer>) App.getCustomerIds();

        JButton[] idButton = new JButton[ids.size()];

        int y = 20;
        for (int i = 0; i < ids.size(); i++) {
            final int receiverId = ids.get(i);

            idButton[i] = new JButton(buttonText + ": " + receiverId);
            idButton[i].setBounds(50, y, 200, 40);
            idButton[i].addActionListener((e) -> func.apply(receiverId, frame));

            panel.add(idButton[i]);
            y += 40 + 20;
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private void generateInvoice(int id, JFrame frame) {
        ArrayList<Rental> rentals = App.getRentalRequests();
        Person p = (Person) App.getObject(id, "entity");
        Rental rental = null;

        // get the last request in the list and process it
        for (int i = rentals.size() - 1; i >= 0; i--)
            if (rentals.get(i).getCustomerId() == id) {
                rental = rentals.get(i);
                break;
            }

        if (rental == null)
            JOptionPane.showMessageDialog(frame, "There is rental request for " + p.getFirstName());

        else {
            new Invoice(p, rental);
            frame.dispose();
        }
    }

    private void getMessage(int receiverId, JFrame frame) {
        App.messageRecieverId = receiverId;
        messagingApp.setVisible(true);
        messagingApp.isActive = true;

        App.messageCheck = true;

        LinkedList<Message> messages = App.getMessage();
        while (messages != null && !messages.isEmpty()) {
            Message message = messages.removeFirst();
            int id = message.getCustomerId();
            messagingApp.appendMessage(id + ": " + message.getMessage());
        }
        frame.dispose();
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        for (int i = 0; i < navigationButtons.length; i++)
            if (event.getSource() == navigationButtons[i]) {
                navigationButtons[i].setForeground(Color.BLUE);
            }
    }

    @Override
    public void mouseExited(MouseEvent event) {
        for (int i = 0; i < navigationButtons.length; i++) {
            navigationButtons[i].setForeground(Color.WHITE);
        }
    }

    private void createButtons() {
        final String[] t1 = {
                "RENTAL REQUESTS", "INVENTORY",
                "INVOICE", "MESSAGE",
        };

        for (int i = 0; i < navigationButtons.length; i++) {
            navigationButtons[i] = new JButton(t1[i]);
            navigationButtons[i].addMouseListener(this);
            navigationButtons[i].addActionListener(this);
            navigationButtons[i].setContentAreaFilled(false);
            navigationButtons[i].setBorderPainted(false);
            navigationButtons[i].setForeground(Color.WHITE);
            navigationButtons[i].setFont(new Font("Monospaced", Font.BOLD, 12));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeInterface::new);
    }
}
