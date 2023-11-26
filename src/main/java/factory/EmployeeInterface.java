package factory;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class EmployeeInterface extends MouseAdapter implements ActionListener {
    private JFrame frame;
    private JPanel dashboard, body, messagePanel, search_result_panel;
    private JButton[] buttons = new JButton[6], qir = new JButton[3];
    private JTextArea messageArea;
    private JLabel logo;
    private JTextField textField, dummy_text_field, searchField, dummy_search_field;
    Border border = new LineBorder(Color.black, 2);

    public EmployeeInterface() {
        configureInterface();
    }

    private void createMessageArea() {
        this.messageArea = new JTextArea();
        this.messageArea.setEditable(false);
        this.messageArea.setPreferredSize(new Dimension(330, 700));
        this.messageArea.setBackground(new Color(69, 30, 230));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        this.messagePanel.add(scrollPane, BorderLayout.NORTH);
    }

    private void createFrame() {
        frame = new JFrame("Grizzly's Entertainment Equipment Rentals");
        frame.setSize(1600, 1100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaleImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaleImage);
    }

    private void createMessagePanel() {
        this.messagePanel = new JPanel(new BorderLayout());
        this.messagePanel.setBounds(1225, 15, 350, 800);
        this.messagePanel.setBackground(new Color(69, 30, 230));
        this.messagePanel.setBorder(border);
        this.createMessageArea();
    }

    private JTextField createTextField(String t, int[] b, int c, int a) {
        JTextField f = new JTextField(t);
        f.setHorizontalAlignment(JTextField.CENTER);
        if (b[0] == 0) {
            f.setPreferredSize(new Dimension(350, 100));
            f.setBounds(b[0], b[1], b[2], b[3]);
        } else
            f.setBounds(b[0], b[1], b[2], b[3]);
        f.setBackground(new Color(0, 0, c, a));
        f.setFont(new Font("Arial", Font.PLAIN, 15));
        f.setForeground(Color.WHITE);
        return f;
    }

    private void createDashBoard() {
        dashboard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ((FlowLayout) dashboard.getLayout()).setHgap(40);
        dashboard.setPreferredSize(new Dimension(1600, 90));
        dashboard.setBackground(Color.GRAY);
        dashboard.setBorder(border);
        logo = new JLabel(scaleImage("images/logo.png", 100, 100));
        JLabel l = new JLabel("Grizzly's Entertainment Equipement Rental");
        l.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 20));
        dashboard.add(logo);
        dashboard.add(l);
    }

    private void createBody() {
        body = new JPanel(null);
        body.setPreferredSize(new Dimension(1600, 800));
        body.setBackground(new Color(150, 90, 35));

        body.add(messagePanel);
        body.add(searchField);
        body.add(dummy_search_field);
        body.add(search_result_panel);
        body.setBorder(border);
    }

    private void createSearchResultPanel() {
        search_result_panel = new JPanel(new BorderLayout());
        search_result_panel.setBounds(380, 150, 600, 450);
        search_result_panel.setBackground(Color.pink);
        search_result_panel.add(new JLabel(scaleImage("images/mic.jpeg", 600, 450)));
    }

    private void configureInterface() {
        final int[][] dim = { { 0, 700, 350, 100 }, { 510, 50, 350, 80 } };
        createFrame();
        createDashBoard();
        createMessagePanel();

        /* configure the search result panel */
        createSearchResultPanel();

        dummy_text_field = createTextField("Type Message", dim[0], 0, 0);
        textField = createTextField("", dim[0], 0, 0);

        messagePanel.add(textField, BorderLayout.SOUTH);
        messagePanel.add(dummy_text_field, BorderLayout.SOUTH);

        searchField = createTextField("", dim[1], 0, 0);
        dummy_search_field = createTextField("Search for Equipment Here", dim[1], 0, 70);

        createBody();
        createButtons();

        for (int i = 0; i < buttons.length; i++)
            dashboard.add(buttons[i]);

        for (int i = 0; i < qir.length; i++)
            body.add(qir[i]);

        frame.add(dashboard, BorderLayout.NORTH);
        frame.add(body, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int r = random.nextInt(0, 256);
        int g = random.nextInt(0, 256);
        int b = random.nextInt(0, 256);

        if (e.getSource() == buttons[5]) {
            messagePanel.setBorder(new LineBorder(new Color(r, g, b)));
        }

        if (e.getSource() == buttons[2]) {
            new InventoryInterface();
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        for (int i = 0; i < buttons.length; i++)
            if (event.getSource() == buttons[i]) {
                buttons[i].setForeground(Color.BLUE);
            }
    }

    @Override
    public void mouseExited(MouseEvent event) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setForeground(Color.WHITE);
        }
    }

    private void createButtons() {
        final String[] t1 = {
                "HOME", "RENTAL REQUESTS", "INVENTORY",
                "INVOICE", "CONTACT", "MESSAGE",
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(t1[i]);
            buttons[i].addMouseListener(this);
            buttons[i].addActionListener(this);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFont(new Font("Monospaced", Font.BOLD, 12));
        }

        final String[] t2 = {
                "GENERATE INVOICE", "GENERATE QUOTATION",
                "GENERATE RECEIPT",
        };

        int x = 250, y = 650, gap = 320;
        for (int i = 0; i < qir.length; i++) {
            qir[i] = new JButton(t2[i]);
            qir[i].setBounds(x, y, 200, 120);
            x += gap;
        }
    }

    public static void main(String[] args) {
        new EmployeeInterface();
    }
}
