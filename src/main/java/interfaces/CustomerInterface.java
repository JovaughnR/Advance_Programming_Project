package interfaces;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import common.Equipment;
import common.Message;
import common.Person;
import common.Transactions;

public class CustomerInterface implements ActionListener {
    private JFrame frame;
    private JPanel dashboard, body, searchResultPanel;
    private JButton[] navigationButtons = new JButton[4];
    private JLabel logo;
    // private JTextField searchField;
    private JTextArea resultTextArea;
    private MessagingApp messagingApp;
    private EquipmentView equipments;

    public CustomerInterface() {
        configureInterface();
    }

    private void createFrame() {
        frame = new JFrame("Equipment Rental System");
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
        Object ob = App.getObject(App.currentUser, "entity");
        Person p = (Person) ob;

        JLabel title = new JLabel("Welcome " + p.getFirstName() + "!");
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(title);

        String[] headings = { "Approved Transactions", "New Messages" };

        Object obj = App.getObject(App.currentUser, "getTransactions");
        @SuppressWarnings("unchecked")
        LinkedList<Transactions> trans = (LinkedList<Transactions>) obj;

        // Message messages =

        // int data[][] = new int[1][2];
        // data[0][0] = trans.size();
        // data[0][1] =

        JTextField textField = new JTextField(placeholder);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new Dimension(50, 10));
        textField.setBackground(new Color(0, 0, 0, 0));
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setForeground(Color.BLACK);
        return textField;
    }

    private void createDashBoard() {
        dashboard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dashboard.setPreferredSize(new Dimension(1200, 90));
        dashboard.setBackground(Color.GRAY);
        dashboard.setBorder(new LineBorder(Color.BLACK, 2));
        logo = new JLabel(scaleImage("images/logo.jpeg", 80, 80));
        JLabel titleLabel = new JLabel("Equipment Rental System");
        dashboard.add(logo);
        dashboard.add(titleLabel);
    }

    // private void createResultTextArea() {
    // resultTextArea = new JTextArea();
    // resultTextArea.setEditable(false);
    // resultTextArea.setLineWrap(true);
    // resultTextArea.setBackground(new Color(255, 255, 255, 200));
    // JScrollPane scrollPane = new JScrollPane(resultTextArea);
    // scrollPane.setBounds(300, 150, 600, 450);
    // body.add(scrollPane);
    // }

    private void configureInterface() {
        createFrame();
        createDashBoard();
        createBody();
        createSearchResultPanel(); // Add this line to create the searchResultPanel

        messagingApp = new MessagingApp();
        messagingApp.setTitle("Customer Messaging");
        messagingApp.setSize(350, 500);
        // messagingApp.setLocationRelativeTo(null);

        equipments = new EquipmentView();
        equipments.setTitle("Search for a equipment");

        // searchField = createTextField("Search equipment...");
        // searchResultPanel.add(searchField);

        createButtons();

        for (JButton button : navigationButtons) {
            dashboard.add(button);
        }

        frame.add(dashboard, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void createBody() {
        body = new JPanel(null);
        body.setPreferredSize(new Dimension(1200, 800));
        body.setBackground(new Color(150, 90, 35));
        body.setBorder(new LineBorder(Color.BLACK, 2));
    }

    private void createSearchResultPanel() {
        searchResultPanel = new JPanel(new BorderLayout());
        searchResultPanel.setBounds(300, 150, 600, 450);
        searchResultPanel.setBackground(Color.LIGHT_GRAY);
        body.add(searchResultPanel);
    }

    private void createButtons() {
        final String[] t1 = {
                "SEARCH EQUIPMENT", "VIEW TRANSACTIONS",
                "VIEW TRANSACTION DETAILS", "LEAVE A MESSAGE",
        };

        int x = 50, y = 650, gap = 250;
        for (int i = 0; i < navigationButtons.length; i++) {
            navigationButtons[i] = new JButton(t1[i]);
            navigationButtons[i].addActionListener(this);
            navigationButtons[i].setContentAreaFilled(false);
            navigationButtons[i].setBorderPainted(false);
            navigationButtons[i].setForeground(Color.WHITE);
            navigationButtons[i].setFont(new Font("Monospaced", Font.BOLD, 12));
            navigationButtons[i].setBounds(x, y, 200, 50);
            x += gap;
            body.add(navigationButtons[i]);
        }
    }

    // public static void main(String[] args) {
    // App.connect("localhost", 8000);
    // App.loadEquipment();
    // SwingUtilities.invokeLater(CustomerInterface::new);
    // }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == navigationButtons[0])
            equipments.setVisible(true);

        if (event.getSource() == navigationButtons[3]) {
            // App.activateMessaging();
            this.messagingApp.isActive = true;
            messagingApp.setVisible(true);
            // do a message check to find messages
            App.messageCheck = true;

            while (this.messagingApp.isActive) {
                LinkedList<Message> messages = App.getMessage();
                while (messages != null && !messages.isEmpty()) {
                    Message message = messages.removeFirst();
                    int id = message.getEmployeeId();
                    messagingApp.senderId = id;

                    this.messagingApp.appendMessage(id + ": " + message.getMessage());
                }
            }

            // App.loopThread.start();
            // Thread messagThread = new Thread(() -> {
            // if (this.messagingApp.isActive) {
            // Boolean status = (Boolean) App.write("isThereAmessageForMe", true);
            // if (status) {
            // Message message = (Message) App.write("sendMessage", true);
            // this.messagingApp.appendMessage(message.getMessage());
            // } else if (!status)
            // System.out.println("No message found for customer!");
            // }
            // });
            // // App.getMessage();
            // messagThread.start();
            // }

            if (event.getSource() == navigationButtons[1]) {

                JFrame frame = new JFrame("Single Transaction view");
                frame.setSize(300, 400);
                frame.setLocationRelativeTo(null);

                Object obj = App.getObject(App.currentUser, "getTransactions");
                @SuppressWarnings("unchecked")
                LinkedList<Transactions> trans = (LinkedList<Transactions>) obj;

                if (trans.isEmpty())
                    JOptionPane.showMessageDialog(frame, "No Transactions Available");
                else {

                    System.out.println("\n\n\t\tNavigations[1] Button Pressed\n\n");
                    String[] headings = {
                            "equipment name", "date",
                            "category", "cost for rental",
                    };

                    JPanel panel = new JPanel(null);
                    panel.setBounds(20, 20, 350, 350);
                    panel.setBackground(App.color);

                    JButton[] buttons = new JButton[trans.size()];
                    JScrollPane scrollPane = new JScrollPane();

                    for (int i = 0; i < buttons.length; i++) {
                        System.out.println("\n\nInto for loop now !");
                        buttons[i] = new JButton("Transaction: " + (i + 1));
                        buttons[i].setBounds(30, 30, 200, 50);
                        buttons[i].addActionListener((e) -> {

                            Transactions transaction = trans.removeFirst();
                            System.out.println("Transactions: " + transaction);
                            int equipId = transaction.getEquipmentId();
                            Object ob = App.getObject(equipId, "singleEquipment");
                            Equipment equip = (Equipment) ob;

                            String data[][] = new String[1][4];
                            data[0][0] = equip.getEquipmentName();
                            data[0][1] = transaction.getDateOfTransaction();
                            data[0][2] = equip.getCategory();
                            data[0][3] = equip.getCost() + "";

                            JTable table = new JTable(data, headings);
                            scrollPane.add(table);
                            scrollPane.setViewportView(table);

                        });

                        panel.add(buttons[i]);
                    }

                    frame.add(panel);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setVisible(true);
                }

            }

            if (event.getSource() == navigationButtons[2]) {
                JFrame frame = new JFrame("All Transactions View");
                frame.setSize(700, 500);
                frame.setLayout(null);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                Object obj = App.getObject(App.currentUser, "getTransactions");
                @SuppressWarnings("unchecked")
                LinkedList<Transactions> list = (LinkedList<Transactions>) obj;

                if (list.isEmpty())
                    JOptionPane.showMessageDialog(frame, "No Transactions Available");
                else {
                    JPanel panel = new JPanel();
                    panel.setBounds(45, 30, 600, 400);
                    panel.setBackground(App.color);

                    String[] headings = {
                            "equipment name", "date",
                            "category", "cost for rental",
                    };

                    String[][] data = new String[list.size()][];

                    int counter = 0;
                    while (!list.isEmpty()) {

                        Transactions trans = list.removeFirst();
                        int equipId = trans.getEquipmentId();

                        Object ob = App.getObject(equipId, "singleEquipment");
                        Equipment equip = (Equipment) ob;

                        data[counter][0] = equip.getEquipmentName();
                        data[counter][1] = trans.getDateOfTransaction();
                        data[counter][2] = equip.getCategory();
                        data[counter][3] = equip.getCost() + "";
                        ++counter;

                    }

                    JTable table = new JTable(data, headings);
                    panel.add(table);
                    frame.add(panel);
                    frame.setVisible(true);
                }
            }

        }
    }
}
