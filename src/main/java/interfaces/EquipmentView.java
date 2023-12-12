package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class EquipmentView extends JFrame implements ActionListener {
    private JButton[] requests = new JButton[36];
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel[] panel = new JPanel[4];
    private String[] categories = { "Sounding", "Lighting", "Powering", "Staging" };
    public static HashMap<Integer, Integer> equipmentMap = new HashMap<>();
    public static int equipmentIdAdress;

    public EquipmentView() {
        this.setSize(App.width, App.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        for (int i = 0; i < categories.length; i++)
            createEquipmentTab(categories[i], i);

        int counter = 0;
        Set<String> keys = App.map.keySet();
        for (String key : keys) {
            System.out.println(App.map.get(key));
            BigDecimal cost = App.map.get(key).getCost();
            if ("Sounding".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[0].add(createEquipmentPanel(key, cost, counter));
            else if ("Lighting".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[1].add(createEquipmentPanel(key, cost, counter));
            else if ("Powering".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[2].add(createEquipmentPanel(key, cost, counter));
            else if ("Staging".equalsIgnoreCase(App.map.get(key).getCategory()))
                panel[3].add(createEquipmentPanel(key, cost, counter));

            equipmentMap.put(counter, App.map.get(key).getEquipmentId());
            counter++;
        }
        System.out.println(equipmentMap);
        this.add(tabbedPane);
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaleImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaleImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 36; i++)
            if (requests[i] == e.getSource()) {
                equipmentIdAdress = i;
                SwingUtilities.invokeLater(Date::new);
            }
    }

    private void createEquipmentTab(String category, int i) {
        panel[i] = new JPanel(new GridLayout(3, 3));
        panel[i].setBackground(App.color);
        ((GridLayout) panel[i].getLayout()).setVgap(20);
        ((GridLayout) panel[i].getLayout()).setHgap(20);
        tabbedPane.add(category, panel[i]);
    }

    private JPanel createEquipmentPanel(String name, BigDecimal cost, int i) {
        JPanel outerPanel = new JPanel(new BorderLayout());

        JLabel equipmentName = new JLabel(name);
        equipmentName.setHorizontalAlignment(JLabel.CENTER);

        String address = "images/" + name + ".jpeg";
        ImageIcon image = scaleImage(address, 300, 300);
        JLabel equipmentImage = new JLabel(image);

        JLabel costLabel = new JLabel("Cost Per Rental: " + cost);
        costLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel buttonArea = new JPanel(new FlowLayout(1));
        requests[i] = new JButton("Request");
        requests[i].addActionListener(this);

        buttonArea.add(costLabel);
        buttonArea.add(requests[i]);

        // ((BorderLayout) outerPanel.getLayout()).setVgap(30);
        outerPanel.setBackground(App.color);
        buttonArea.setBackground(App.color);

        outerPanel.add(equipmentName, BorderLayout.NORTH);
        outerPanel.add(equipmentImage, BorderLayout.CENTER); // Changed to CENTER
        outerPanel.add(buttonArea, BorderLayout.SOUTH); // Changed to SOUTH

        // Add outerPanel to the EquipmentView frame
        return outerPanel;
    }

}
