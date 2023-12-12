package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;

import common.Equipment;
import common.Person;
import common.Rental;
import common.Transactions;

import java.util.ArrayList;
import java.util.LinkedList;

public class RentalRequest extends JFrame implements ActionListener {
    JButton scheduleButton, sortByDateButton, sortByOrderButton;
    JScrollPane scrollPane;
    // JDesktopPane desktopPane = new JDesktopPane();
    String[] columnNames = {
            "Customer ID", "Customer Name", "Customer Email",
            "Customer Cell #", "Equipment Name", "Equipment ID",
            "Date for Request", "Cost",
    };
    String scheduleMsg = "";
    JPanel body = new JPanel(new GridBagLayout());
    Person p = null;
    ArrayList<Rental> rentals = null;
    LinkedList<String> list = new LinkedList<>();

    public RentalRequest() {
        this.setSize(App.width, App.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        configureBody();
        this.add(body);
        this.setVisible(true);
    }

    private void configureBody() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.8; // 80% height for the table
        gbc.fill = GridBagConstraints.BOTH;
        this.body.add(rentalDataTable(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.2; // 20% height for the scheduling section
        this.body.add(schedulingSection(), gbc);
    }

    private JScrollPane rentalDataTable() {
        rentals = App.getRentalRequests();
        String[][] data = new String[rentals.size()][8];
        final String q1 = "entity", q2 = "singleEquipment";

        for (int i = 0; i < rentals.size(); i++) {
            System.out.println("Current Rental: " + rentals.get(i));
            final int cusId = rentals.get(i).getCustomerId();
            final int equipId = rentals.get(i).getEquipmentId();
            p = (Person) App.getObject(cusId, q1);
            System.out.println("Person Received: " + p);
            Equipment e = (Equipment) App.getObject(equipId, q2);
            System.out.println("Equipment Received: " + e);
            list.add(e.getEquipmentName());

            data[i][0] = cusId + "";
            data[i][1] = p.getFirstName() + " " + p.getLastName();
            data[i][2] = p.getEmail();
            data[i][3] = p.getPhone();
            data[i][4] = e.getEquipmentName();
            data[i][5] = equipId + "";
            data[i][6] = rentals.get(i).getDate();
            data[i][7] = e.getCost() + "";
        }

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(120);
        table.setPreferredSize(new Dimension(App.width, 800));
        centerAlignText(table);
        table.setBackground(new Color(150, 90, 35));
        scrollPane = new JScrollPane(table);

        return new JScrollPane(table);
    }

    /**
     * @param rentals is expect a list of rentals
     * @return returns a sorted list of rental equipments
     */
    public static ArrayList<Rental> sortRentals(ArrayList<Rental> rentals) {
        ArrayList<Rental> sortedRentalList = new ArrayList<>();

        for (int i = 0; i < rentals.size(); i++) {
            Rental currentRental = rentals.get(i);
            int d1 = Date.dateToInt(currentRental.getDate());
            int min = i;

            for (int j = i + 1; j < rentals.size(); j++) {
                int d2 = Date.dateToInt(rentals.get(j).getDate());
                if (d2 < d1) {
                    d1 = Date.dateToInt(rentals.get(j).getDate());
                    min = j;
                }
            }
            sortedRentalList.add(rentals.get(min));
            rentals.remove(min);
            --i;
        }
        // Return the sorted list of rentals according to the a-z
        return sortedRentalList;
    }

    private void centerAlignText(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(centerRenderer);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        header.setReorderingAllowed(false);
    }

    private JPanel schedulingSection() {
        JPanel basePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        basePanel.setBackground(new Color(150, 90, 35));
        basePanel.setPreferredSize(new Dimension(App.width, 200));

        // Create an empty label to add space vertically
        JLabel spaceLabel = new JLabel();
        spaceLabel.setPreferredSize(new Dimension(1, 140)); // Adjust the height to create space

        scheduleButton = new JButton("Schedule Equipment");
        scheduleButton.setPreferredSize(new Dimension(200, 80)); // Set preferred size
        scheduleButton.addActionListener(this);

        sortByDateButton = new JButton("Sort by date");
        sortByDateButton.setPreferredSize(new Dimension(200, 80)); // Set preferred size
        sortByDateButton.addActionListener(this);

        sortByOrderButton = new JButton("Sort by order");
        sortByOrderButton.setPreferredSize(new Dimension(200, 80)); // Set preferred size
        sortByOrderButton.addActionListener(this);

        basePanel.add(spaceLabel);
        basePanel.add(scheduleButton);
        basePanel.add(sortByDateButton);
        basePanel.add(sortByOrderButton);
        return basePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sortByDateButton) {
            if (App.sorted) {
                scheduleMsg = "Scheduling equipment for earliest date";
                String msg = "Already sorted!";
                JOptionPane.showMessageDialog(this, msg);
            } else {
                this.dispose();
                App.sorted = true;
                SwingUtilities.invokeLater(RentalRequest::new);
            }
        }

        if (e.getSource() == sortByOrderButton) {
            if (!App.sorted) {
                scheduleMsg = "Scheduling equipment in the order they came";
                String msg = "Already in order!";
                JOptionPane.showMessageDialog(this, msg);

            } else {
                this.dispose();
                App.sorted = false;
                SwingUtilities.invokeLater(RentalRequest::new);
            }
        }

        if (e.getSource() == scheduleButton) {
            JFrame frame = new JFrame(scheduleMsg);
            frame.setSize(800, 200);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new BorderLayout());
            JScrollPane pane = new JScrollPane();

            String[] headings = {
                    "Equipment Name",
                    "Equipment Id",
                    "Customer Id",
            };

            String name = list.removeFirst();
            int equipId = rentals.get(0).getEquipmentId();
            int cusId = rentals.get(0).getCustomerId();

            String[][] data = {
                    { name, equipId + "", cusId + "" }
            };

            JButton confirm = new JButton("Confirm");
            confirm.addActionListener((event) -> {
                App.removeRentalRequest(equipId, cusId);
                String msg = "Equipment Scheduled Successfully!";
                JOptionPane.showMessageDialog(frame, msg);
                // To be tested
                Equipment equip = (Equipment) App.getObject(equipId, "singleEquipment");

                Transactions trans = new Transactions();
                trans.setCustomerId(cusId);
                trans.setEquipmentId(equipId);
                trans.setMoneyAmount(equip.getCost());
                trans.setDateOfTransaction(rentals.get(0).getDate());

                App.saveTransaction(trans);

                frame.dispose();
                this.dispose();
                SwingUtilities.invokeLater(RentalRequest::new);
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener((event) -> {
                frame.dispose();
                list.addFirst(name);
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            JTable table = new JTable(data, headings);
            table.setPreferredSize(new Dimension(800, 120));
            table.setBackground(new Color(150, 90, 35, 200));
            table.setRowHeight(30);
            centerAlignText(table);

            pane.setViewportView(table);

            panel.add(pane, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    }

}
