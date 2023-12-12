package common;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipment")
public class Equipment implements Serializable {
    private static final long serialVersionUID = 3l;
    @Id
    @Column(name = "equipment_id")
    private int equipmentId;

    @Column(name = "equipment_name", nullable = false)
    private String equipmentName;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "availability_status", nullable = false)
    private String availabilityStatus;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal cost;

    public Equipment() {
    }

    public Equipment(String name, String cat, String status, int amount, BigDecimal cost) {
        this.equipmentName = name;
        this.category = cat;
        this.availabilityStatus = status;
        this.quantity = amount;
        this.cost = cost;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String toString() {
        return "Equipment{" +
                "equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", category='" + category + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}