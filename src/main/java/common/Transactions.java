package common;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transactions implements Serializable {
    private static final long serialVersionUID = 5l;
    private int customerId;
    private int equipmentId;
    private String dateOfTransaction;
    private BigDecimal moneyAmount;

    public Transactions() {
        // Default constructor
    }

    public Transactions(int customerId, int equipmentId, String dateOfTransaction, BigDecimal moneyAmount) {
        this.customerId = customerId;
        this.equipmentId = equipmentId;
        this.dateOfTransaction = dateOfTransaction;
        this.moneyAmount = moneyAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
