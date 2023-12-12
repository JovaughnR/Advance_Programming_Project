package common;

import java.io.Serializable;

public class Rental implements Serializable {
    public static final long serialVersionUID = 4l;
    private String date;
    private int customerId;
    public int equipmentId;

    public Rental(String date, int customerId) {
        setDate(date);
        setCustomerId(customerId);
    }

    public Rental(String date, int customerId, int equipmentId) {
        setDate(date);
        setCustomerId(customerId);
        setEquipmentId(equipmentId);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setEquipmentId(int id) {
        this.equipmentId = id;
    }

    public String getDate() {
        return date.toString();
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "date=" + date +
                ", customer id='" + customerId + '\'' +
                ", equipment id='" + equipmentId + '\'' +
                '}';

    }
}
