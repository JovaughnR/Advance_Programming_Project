package interfaces;

import javax.swing.*;

import common.Message;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MessagingApp extends JFrame {
    public JTextArea messageArea;
    public JTextField inputField;
    public boolean isActive = false;
    public int senderId;

    public MessagingApp() {
        super("Message Area");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(350, 500);
        setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                App.messageCheck = isActive = false;
                // App.loopThread.interrupt();

            }
        });

        messageArea = new JTextArea();
        messageArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // autoAppendMessages();
                // wait
                System.out.println("Send button pressed");
                isActive = false;
                sendMessage();
                isActive = true;
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
        this.setResizable(false);
    }

    // private void autoAppendMessages() {
    // LinkedList<Message> messages = App.autoAppendMessages();
    // for (Message message : messages) {
    // if (App.isEmp) {
    // if (message.getEmployeeId() == App.currentUser) {
    // appendMessage("Employee: " + message.getMessage());
    // }
    // }
    // if (!App.isEmp) {
    // if (message.getCustomerId() == App.currentUser) {
    // appendMessage("Customer: " + message.getMessage());
    // }
    // }
    // }
    // }

    private void sendMessage() {
        Message message = new Message();
        if (App.isEmp) {
            message.setEmployeeId(App.currentUser);
            message.setCustomerId(App.messageRecieverId);
            message.setMessageType(1);
        } else {
            message.setCustomerId(App.currentUser);
            message.setMessageType(0);
            if (senderId != 0) {
                message.setEmployeeId(senderId);
            }
        }
        // set the message to what is on the message input field;
        message.setMessage(inputField.getText());

        System.out.println("Message from messaging App: " + message);
        App.sendMessageObj(message);
        // String message = inputField.getText();
        appendMessage("You: " + message.getMessage());
        inputField.setText("");
    }

    public void appendMessage(String message) {
        messageArea.append(message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    // MessagingApp example = new MessagingApp();
    // example.setVisible(true);
    // });
    // }
}