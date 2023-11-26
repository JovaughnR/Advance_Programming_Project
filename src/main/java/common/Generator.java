package common;

import java.io.Serializable;

class Generator implements Serializable {
    private static final long serialVersionUID = 12l;
    private static long customerCounter = 100001;

    public static long generateCustomerId() {
        return customerCounter++;
    }

    // public static void main(String[] args) throws RangeError {
    // Address address = new Address("TownHall", 233, "claremont", "St. Ann",
    // "Canada", "3562");
    // Person p = new Person(135, "John", "jones", "jon@mail.com", "473925",
    // address);
    // p.setPassword("helloworld");
    // System.out.println(p);
    // // Customer customer = new Customer(123, "James", "percival",
    // "james@mail.com",
    // // "52515", address);

    // }
}