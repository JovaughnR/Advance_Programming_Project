package interfaces;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;

import common.Rental;

public class Date extends JFrame implements ActionListener {
    private JButton submitDate;
    private JMenuItem[] days, years, months = new JMenuItem[12];
    private JMenuBar date;
    private JMenu year, month = new JMenu("Month"), day = new JMenu("Day");
    private String[] nameOfMonth = {
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    };
    private int dayAmount = 0;
    private final int DISPOSE_ON_CLOSE = 2;

    public Date() {
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new FlowLayout(1));
        this.createDateMenuBar();
        JLabel label = new JLabel("Select the date for the Request");
        submitDate = new JButton("Submit");
        submitDate.addActionListener(this);
        this.add(label);
        this.add(date);
        this.add(submitDate);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < years.length; i++)
            if (years[i] == e.getSource())
                year.setText(years[i].getText());

        for (int i = 1; i <= 12; i++)
            if (months[i - 1] == e.getSource()) {
                month.setText(months[i - 1].getText());
                this.day.removeAll();
                setDayAmount(i, getYear());
                setDays(this.dayAmount);
            }

        for (int i = 0; i < dayAmount; i++)
            if (days[i] == e.getSource())
                day.setText(days[i].getText());

        if (e.getSource() == submitDate) {
            // System.out.println("Submit button clicked!");
            if (isDateValid()) {
                // System.out.println("Checking if equipment available");
                final int equipId = EquipmentView.equipmentMap.get(EquipmentView.equipmentIdAdress);
                if (App.isEquipmentAvailable(equipId, this.toString())) {
                    // System.out.println("Equipment available");
                    // System.out.println("Current User: " + App.currentUser);
                    Rental rental = new Rental(toString(), App.currentUser, equipId);
                    // System.out.println("Rental: " + rental);
                    if (App.makeRentalRequest(rental))
                        JOptionPane.showMessageDialog(this, "Equipment Requested");
                    else
                        JOptionPane.showMessageDialog(this, "This equipment is unavailable at the moment");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "This equipment is unavailable at the moment");
                    this.dispose();
                }
            } else
                JOptionPane.showMessageDialog(this, "Date format not correct");
        }

    }

    private void setMonth() {
        for (int i = 0; i < 12; i++) {
            months[i] = new JMenuItem(nameOfMonth[i]);
            months[i].addActionListener(this);
            this.month.add(months[i]);
        }
    }

    private int getYear() {
        try {
            return Integer.parseInt(year.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    }

    private void setDayAmount(int i, int year) {
        if (i == 2)
            this.dayAmount = isLeapYear(year) ? 29 : 28;

        else if (i == 8 || i == 12)
            this.dayAmount = 31;
        else
            this.dayAmount = ((i % 2) == 0 || i == 11) ? 30 : 31;
    }

    private void createDateMenuBar() {
        date = new JMenuBar();

        // Initialize day, month, and year menus
        day = new JMenu("Day");
        month = new JMenu("Month");
        year = new JMenu("Year");

        day.addActionListener(this);
        year.addActionListener(this);
        month.addActionListener(this);

        // Add day, month, and year menus to the date menu bar

        setYear();
        setMonth();
        setDays(dayAmount);

        date.add(month);
        date.add(day);
        date.add(year);
    }

    private void setYear() {
        int year = Year.now().getValue();
        years = new JMenuItem[5]; // Initialize the years array

        for (int i = 0; i < 5; i++) {
            years[i] = new JMenuItem((year + i) + "");
            years[i].addActionListener(this);
            this.year.add(years[i]);
        }
    }

    private void setDays(int dayAmount) {
        days = new JMenuItem[dayAmount];

        for (int i = 0; i < dayAmount; i++) {
            days[i] = new JMenuItem("" + (i + 1));
            days[i].addActionListener(this);
            this.day.add(days[i]);
        }

        this.dayAmount = dayAmount;
    }

    public boolean isDateValid() {
        return null != this.toString();
    }

    @Override
    public String toString() {
        String y = "", m = "", d = "";
        try {
            y = Integer.parseInt(year.getText()) + "";
            d = Integer.parseInt(day.getText()) + "";
            if ("Month".equalsIgnoreCase(month.getText()))
                throw new NumberFormatException();
            m = month.getText();

        } catch (NumberFormatException e) {
            return null;
        }
        return formatDate(y, m, d);
    }

    public String formatDate(String y, String m, String d) {
        String date = y + "-";
        for (int i = 0; i < 12; i++)
            if (month.getText().equalsIgnoreCase(nameOfMonth[i]))
                date += (i + 1) + "-";

        return date + day.getText();
    }

    public static int dateToInt(String date) {
        String d = "";
        for (int i = 0; i < date.length(); i++)
            if (date.charAt(i) != '-')
                d += date.charAt(i);

        return Integer.parseInt(d);
    }

}
