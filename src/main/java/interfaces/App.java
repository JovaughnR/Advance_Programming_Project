package interfaces;

import java.awt.Color;

/**
   * @author Jovaughn Rose
   * @see https://github.com/jovaughnR
   */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

// import common.Date;
import common.Equipment;
import common.Message;
import common.Person;
import common.Rental;
import common.Transactions;

public class App {
    private static Socket socket;
    private static ObjectInputStream inFromServer = null;
    private static ObjectOutputStream outToServer = null;

    public static Thread appThread;
    public static Thread loopThread;
    public static Thread writeThread;
    public static Thread readThread;

    public static boolean isEmp = false;
    public static HashMap<String, Equipment> map = new HashMap<>();
    public static final int width = 1200, height = 1000;
    public static boolean sorted = false;
    public static final Color color = new Color(150, 90, 35);
    public static boolean messageCheck = false;
    // public static LinkedList<Message> messages = new LinkedList<>();

    // sets a variable to hold the id of the customer to recieve a message
    public static int messageRecieverId;

    // a flag to check if clients are currently sending messages
    public static boolean sendMessage = false;

    // set the current user to temporary value for testing remove when finish
    public static int currentUser = 111111;

    public static void connect(String host, int port) {
        try {
            App.socket = new Socket(host, port);
            configureStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void configureStreams() throws IOException {
        App.outToServer = new ObjectOutputStream(App.socket.getOutputStream());
        App.outToServer.flush();
        App.inFromServer = new ObjectInputStream(App.socket.getInputStream());
    }

    public static String signUpMessage(String name, int id) {
        return String.format(
                "Congratulations %s! You've been successfully signed up.%n%nYour Identification Number: [ %d ]%n%nWhen logging in, use this number along with the password you used during signup.%n%nWelcome to our system! Thank you for joining.",
                name, id);
    }

    public static int signUpEntity(Person p) {
        App.write("signUp", true);
        App.write((App.isEmp) ? "employee" : "customer", false);
        return (Integer) App.write(p, true);
    }

    public static Boolean signIn(int id, String password) {
        App.write("signIn", true);
        App.write(id, true);
        return (Boolean) App.write(password, true);
    }

    public static List<Equipment> getEquipments() {
        try {
            App.outToServer.writeObject("equipments");
            System.out.println("Requesting equipments from server");
            @SuppressWarnings("unchecked")
            List<Equipment> equipments = (List<Equipment>) inFromServer.readObject();
            return equipments;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void loadEquipment() {
        List<Equipment> equipments = App.getEquipments();
        for (Equipment equipment : equipments)
            App.map.put(equipment.getEquipmentName(), equipment);
    }

    public static void initiate(String host, int port) {

        App.connect(host, port);
        System.out.println("App connected on local port: " + App.socket.getLocalPort());
        App.loadEquipment();
        // App.loop();
        SwingUtilities.invokeLater(Login::new);

    }

    public static void sendType(String type) {
        try {
            App.outToServer.writeObject(type);
        } catch (IOException e) {
            System.out.println("Error sending type: " + e.getMessage());
        }
    }

    public static ArrayList<Rental> getRentalsById(int equipmentId) {
        if (App.loopThread.isAlive()) {
            loopThread.isInterrupted();
        }
        App.write("RentalsById", true);
        @SuppressWarnings("unchecked")
        ArrayList<Rental> rentals = (ArrayList<Rental>) App.write(equipmentId, true);
        return rentals;
    }

    public static boolean isEquipmentAvailable(int id, String date) {
        Equipment equipment = (Equipment) App.getObject(id, "requestEquipment");

        if ("Available".equalsIgnoreCase(equipment.getAvailabilityStatus()))
            return true;

        else {
            ArrayList<Rental> rental = getRentalsById(id);
            rental = RentalRequest.sortRentals(rental);
            String LastDate = rental.get(rental.size() - 1).getDate();
            int d1 = Date.dateToInt(date);
            int d2 = Date.dateToInt(LastDate);
            if (d2 < d1)
                return true;
        }
        return false;
    }

    public static boolean makeRentalRequest(Rental rental) {
        App.write("requestRental", true);
        int id = rental.getEquipmentId();
        if ((Boolean) App.write(id, true)) {
            App.write(rental, false);
            return true;
        } else
            App.write(false, false);
        return false;
    }

    public static ArrayList<Rental> getRentalRequests() {

        try {
            App.outToServer.writeObject("getRentalRequest");
            @SuppressWarnings("unchecked")
            ArrayList<Rental> rentals = (ArrayList<Rental>) inFromServer.readObject();
            System.out.println(rentals);
            if (App.sorted)
                return RentalRequest.sortRentals(rentals);
            return rentals;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static Object getObject(int id, String query) {
        // App.write(query, false);
        // return App.write(id, true);
        try {
            App.outToServer.writeObject(query);
            App.inFromServer.readObject();
            App.outToServer.writeObject(id);
            return inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static void removeRentalRequest(int equipId, int cusId) {
        App.write("removeRentalRequest", true);
        App.write(equipId, true);
        App.write(cusId, false);
    }

    public static void saveTransaction(Transactions transaction) {
        App.write("transaction", true);
        App.write(transaction, false);
    }

    // public static void loop() {
    // loopThread = new Thread(() -> {
    // Boolean status = (Boolean) App.write("isThereAmessageForMe", true);
    // if (status) {
    // Message message = (Message) App.write("sendMessage", true);
    // App.messages.add(message);
    // } else if (!status)
    // System.out.println("No message found for customer!");
    // });
    // }

    // the idea is to send the current user id to the server
    // and the server will hash the id alongside all other id's in the
    // database to find the message_id and then return all messages related to that
    // specific ID

    // public static LinkedList<Message> autoAppendMessages() {
    // try {
    // App.outToServer.writeObject("autoAppendMsg");
    // App.outToServer.flush();
    // App.inFromServer.readObject();

    // // sends the current user id to the server
    // App.outToServer.writeObject(App.currentUser);
    // App.outToServer.flush();

    // // check if the current user is an employee
    // if (App.isEmp)
    // App.outToServer.writeObject(true);
    // else
    // App.outToServer.writeObject(false);

    // App.outToServer.flush();
    // // get the messages form the server
    // Object ob = App.inFromServer.readObject();

    // @SuppressWarnings("unchecked")
    // LinkedList<Message> message = (LinkedList<Message>) ob;
    // return message;

    // } catch (IOException | ClassNotFoundException e) {
    // System.out.println("Error: " + e.getMessage());
    // }
    // return null;
    // }

    // expect an employee to chose which customer to send the message to

    // public static void sendMessage(String message) {
    // try {
    // App.inFromServer.readObject();
    // App.outToServer.writeObject(message);
    // } catch (IOException | ClassNotFoundException e) {
    // System.out.println("Error sending message: " + e.getMessage());
    // }
    // }

    public static Object write(Object obj, boolean auto) {
        try {
            App.outToServer.writeObject(obj);
            App.outToServer.flush();
            if (auto)
                return App.inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static Object getCustomerIds() {
        try {
            App.outToServer.writeObject("getCustomerIds");
            return App.inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static void sendMessageObj(Message message) {
        try {
            System.out.println("\t\tMessage Obj sending to server: " + message);
            App.outToServer.writeObject("messageObject");
            App.outToServer.flush();
            String serverSays = (String) App.inFromServer.readObject();

            System.out.println("Server Says + " + serverSays);

            System.out.println("The message object sending to server: " + message);

            // String serverSays = (String) App.inFromServer.readObject();
            // send the message object to the server
            App.outToServer.writeObject(message);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error sending Message object: " + e.getCause());
        }

    }

    public static LinkedList<Message> getMessage() {
        try {
            App.outToServer.writeObject("isThereAmessageForMe");
            if (!(boolean) App.inFromServer.readObject())
                return null;
            App.outToServer.writeObject("sendMessage");
            Object obj = App.inFromServer.readObject();
            @SuppressWarnings("unchecked")
            LinkedList<Message> messages = (LinkedList<Message>) obj;
            return messages;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error retrieving message:" + e.getCause());
        }
        return null;
    }

    // public static void activateMessaging() {
    // try {
    // System.out.println("\n\n\t\tSending operation activate messaging to server");
    // App.outToServer.writeObject("activateMessaging");
    // // App.outToServer.flush();

    // // String serverSays = (String) App.inFromServer.readObject();
    // // System.out.println("\t\tServer says: " + serverSays);

    // // if ("recieverId".equalsIgnoreCase(serverSays)) {
    // // System.out.println("Reciever id: " + App.messageRecieverId);
    // // App.getMessage();

    // // } else if ("noId".equals(serverSays)) {
    // // System.out.println("Current client Id: " + App.currentUser);
    // // // to do
    // // App.getMessage();

    // // }

    // App.getMessage();

    // } catch (IOException e) {
    // System.out.println("Error: " + e.getMessage());
    // }
    // }

    // public static void getMessage() {
    // try {
    // while (MessagingApp.isActive) {
    // App.outToServer.writeObject("isThereAmessageForMe");
    // Boolean status = (Boolean) App.inFromServer.readObject();

    // if (status == true) {
    // System.out.println("Message found for user");
    // App.outToServer.writeObject("SendMessageObject");
    // Message message = (Message) App.inFromServer.readObject();
    // MessagingApp.appendMessage(message.getMessage());
    // } else if (status == false) {
    // System.out.println("No messages found for user");
    // }
    // App.outToServer.flush();
    // }
    // } catch (IOException | ClassNotFoundException e) {
    // System.out.println("There was an error reading or writing: " + e.getCause());
    // }
    // }

    public static void main(String[] args) throws InterruptedException {

        String host = "127.0.0.1";
        int port = 8000;

        App.initiate(host, port);

        // Boolean status = (Boolean) App.write("isThereAmessageForMe", true);
        // if (status) {
        // Message message = (Message) App.write("sendMessage", true);
        // App.messages.add(message);
        // } else if (!status)
        // System.out.println("No message found for customer!");

        // App.loopThread.start();
        // App.loopThread.join();

        // // App.connect(host, port);
        // System.out.println(App.getEquipments());

        // Equipment[] equip = App.displayEquipments();
        // for (Equipment e : equip)
        // System.out.println(e);
        // App.initiate();
    }
}
