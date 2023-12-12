package common;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 6l;
    private int messageType;
    private int customerId;
    private int employeeId;
    private String message;

    public Message() {
    }

    public Message(int messageType, int customerId, int employeeId, String message) {
        this.messageType = messageType;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", customerId=" + customerId +
                ", employeeId=" + employeeId +
                ", message='" + message + '\'' +
                '}';
    }
}
