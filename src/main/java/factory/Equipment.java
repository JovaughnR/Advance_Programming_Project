package factory;

import java.io.Serializable;

public class Equipment implements Serializable {
    private String name, category, status;

    public Equipment(String name, String cat, boolean stat) {
        setName(name);
        setCategory(cat);
        setStatus(stat);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(Boolean available) {
        if (available)
            this.status = "Available";
        else
            this.status = "Booked";
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getStatus() {
        return this.status;
    }

}