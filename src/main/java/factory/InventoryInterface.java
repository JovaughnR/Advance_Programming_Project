package factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryInterface implements ActionListener {
    private JFrame frame;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel[] panel = new JPanel[4];
    private Color[] colors = { Color.blue, Color.green, Color.red, Color.magenta };
    private String[] categories = { "Sounding", "Lighting", "Powering", "Staging" };

    public InventoryInterface() {
        createFrame();

        for (int i = 0; i < categories.length; i++)
            createEquipmentTab(categories[i], i);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame("Inventory");
        frame.setSize(1600, 1100);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void createEquipmentTab(String category, int i) {
        panel[i] = new JPanel(new GridLayout());
        panel[i].setBackground(colors[i]);
        tabbedPane.setBounds(0, 0, 1600, 1000);
        tabbedPane.add(category, panel[i]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new InventoryInterface();
    }
}