package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

public class InventoryInterface extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel[] panel = new JPanel[4];
    private String[] categories = { "Sounding", "Lighting", "Powering", "Staging" };

    public InventoryInterface() {
        this.setTitle("Inventory");
        this.setSize(App.width, App.height);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        for (int i = 0; i < categories.length; i++)
            createEquipmentTab(categories[i], i);

        this.add(tabbedPane);
        constructInventory();
    }

    private JPanel constructPanel(String name) {
        JPanel equipmentPanel = new JPanel(new BorderLayout());
        JLabel equipmentName = new JLabel(name);
        equipmentName.setHorizontalAlignment(JLabel.CENTER);
        String imagePath = "images/" + name + ".jpeg";
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.add(new JLabel(scaleImage(imagePath, 200, 200)));

        JLabel quantityLabel = new JLabel("Quantity: " + App.map.get(name).getQuantity());
        quantityLabel.setHorizontalAlignment(JLabel.CENTER);

        equipmentPanel.add(equipmentName, BorderLayout.NORTH);
        equipmentPanel.add(imagePanel, BorderLayout.CENTER);
        equipmentPanel.add(quantityLabel, BorderLayout.SOUTH);
        equipmentPanel.setBorder(new LineBorder(Color.darkGray, 1));

        ((BorderLayout) equipmentPanel.getLayout()).setVgap(20);
        return equipmentPanel;
    }

    public void createEquipmentTab(String category, int i) {
        panel[i] = new JPanel(new GridLayout(3, 3));
        panel[i].setBackground(App.color);
        ((GridLayout) panel[i].getLayout()).setVgap(20);
        ((GridLayout) panel[i].getLayout()).setHgap(20);
        tabbedPane.add(category, panel[i]);
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void constructInventory() {

        Set<String> keys = App.map.keySet();
        for (String key : keys) {
            if ("Sounding".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[0].add(constructPanel(key));
            else if ("Lighting".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[1].add(constructPanel(key));
            else if ("Powering".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[2].add(constructPanel(key));
            else if ("Staging".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[3].add(constructPanel(key));
        }

    }

    // public static void main(String[] a) {
    // App.connect("localhost", 8000);
    // new InventoryInterface();
    // }
}
