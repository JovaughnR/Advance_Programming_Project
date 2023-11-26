package factory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.Person;

public class App {
    private static Socket socket;
    private static ObjectInputStream inFromServer = null;
    private static ObjectOutputStream outToServer = null;
    private static boolean isEmp = false;

    public static void connect(String host, int port) {
        try {
            App.socket = new Socket(host, port);
            configureStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void configureStreams() {
        try {
            App.outToServer = new ObjectOutputStream(App.socket.getOutputStream());
            App.outToServer.flush(); // Ensure data is sent immediately
            App.inFromServer = new ObjectInputStream(App.socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void signUpEntity(Person p) {
        System.out.println("Signing up entity");
        try {
            App.outToServer.writeObject("sign up");
            App.outToServer.flush();

            try {
                String serverSays = (String) inFromServer.readObject();
                System.out.println("Server says: " + serverSays);

                if (serverSays.equalsIgnoreCase("employee or customer")) {
                    String msg = (App.isEmp) ? "employee" : "customer";
                    outToServer.writeObject(msg);
                    outToServer.flush();

                    if (msg.equalsIgnoreCase("customer")) {

                        outToServer.writeObject(p);
                        outToServer.flush();
                    }

                    if (msg.equalsIgnoreCase("employee")) {

                        outToServer.writeObject(p);
                        outToServer.flush();
                    }

                    serverSays = (String) inFromServer.readObject();
                    System.out.println("Server says: " + serverSays);
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void initiate() {
        System.out.println(App.socket.getLocalPort());
        new SignUp();
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8000;
        App.connect(host, port);
        App.initiate();
    }
}

// package factory;

// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.net.Socket;

// /**
// * Hello world!
// *
// */
// public class App {
// private static Socket socket;
// private static ObjectInputStream inFromServer = null;
// private static ObjectOutputStream outToServer = null;
// private static boolean isEmp = false;

// public static void connect(String host, int port) {
// try {
// App.socket = new Socket(host, port);
// } catch (IOException e) {
// System.out.println(e.getMessage());
// }
// }

// private static void configureStreams() {
// try {

// App.inFromServer = new ObjectInputStream(App.socket.getInputStream());
// App.outToServer = new ObjectOutputStream(App.socket.getOutputStream());
// App.outToServer.flush(); // Ensure data is sent immediately

// // }
// // System.out.println("Socket: " + App.socket.isConnected());
// // System.out.println("Before: " + App.inFromServer);
// // System.out.println("Before: " + App.outToServer);
// // App.inFromServer = new ObjectInputStream(App.socket.getInputStream());
// // App.outToServer = new ObjectOutputStream(App.socket.getOutputStream());
// // System.out.println("After: " + App.inFromServer);
// // System.out.println("After: " + App.outToServer);
// } catch (IOException e) {
// System.out.println(e.getMessage());
// } finally {
// try {
// if (App.outToServer != null) {
// App.outToServer.close();
// }
// if (App.inFromServer != null) {
// App.inFromServer.close();
// }
// } catch (IOException e) {
// System.out.println("Error closing streams: " + e.getMessage());
// }
// }
// }

// public static void signUpEntity(Person p) {
// System.out.println("Signing up entity");
// App.configureStreams();
// System.out.println("Finish configuring Streams");
// System.out.println("Socket Status: " + App.socket.isConnected());
// try {
// System.out.println("Inside Try Block");
// System.out.println(App.outToServer);
// App.outToServer.writeObject("sign up");
// String serverSays = (String) inFromServer.readObject();
// System.out.println("Server says: " + serverSays);
// if (serverSays.equalsIgnoreCase("employee or customer")) {
// if (App.isEmp) {
// App.outToServer.writeObject("customer");
// App.outToServer.flush();
// App.outToServer.writeObject((Customer) p);
// } else if (!App.isEmp) {
// App.outToServer.writeObject("employee");
// App.outToServer.flush();
// App.outToServer.writeObject((Employee) p);
// }
// serverSays = (String) inFromServer.readObject();
// if (serverSays.equalsIgnoreCase("recieved")) {
// }

// }
// } catch (ClassNotFoundException | IOException e) {
// System.out.println(e.getMessage());
// }

// }

// public static void initiate() {
// System.out.println(App.socket.getLocalPort());
// new SignUp();
// }

// public static void main(String[] args) {
// String host = "127.0.0.1";
// int port = 8000;
// App.connect(host, port);
// App.initiate();
// }

// }
