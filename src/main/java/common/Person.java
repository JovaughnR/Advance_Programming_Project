package common;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phone;
    protected String password;
    public Address address;

    public Person() {
    }

    public Person(int id, String fn, String ln, String em, String ph, Address addr) {
        setId(id);
        setFirstName(fn);
        setLastName(ln);
        setEmail(em);
        setPhone(ph);
        setAddress(addr);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = Generator.hash(password);
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                // ", address=" + address.toString() +
                '}';
    }

    // public String[] personArray() {
    // String[] person = { firstName, lastName, email, phone };
    // return person;
    // }

}
