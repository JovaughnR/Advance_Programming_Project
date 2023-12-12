package interfaces;

import java.time.LocalDate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import common.*;

public class Invoice extends JFrame implements ActionListener {
    JButton confirm;
    JTextArea displayArea;
    Equipment equipment;
    Person p;
    Rental rental;

    public Invoice(Person p, Rental rental) {
        this.p = p;
        this.rental = rental;

        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        confirm = new JButton("Generate Invoice");

        JPanel buttonPanel = new JPanel(new FlowLayout(1));
        buttonPanel.add(confirm);

        // display the information
        displayArea = new JTextArea();
        displayArea.setPreferredSize(new Dimension(350, 200));
        displayArea.setText(displayInformation());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(displayArea, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm)
            generatePDF();

    }

    private void generatePDF() {
        final String filePath = "output.pdf";
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Adding content to the PDF
            String title = "Grizzly's Entertainment Equipment Rental";
            String name = p.getFirstName() + " " + p.getLastName();
            String email = p.getEmail();
            String phoneNumber = p.getPhone();
            String requestDate = rental.getDate();

            LocalDate currentDate = LocalDate.now();

            String equipmentName = equipment.getEquipmentName();
            BigDecimal cost = equipment.getCost();

            // Adding content to the PDF
            document.add(new Paragraph(title));
            document.add(new Paragraph("Invoice for: " + name));
            document.add(new Paragraph("Email: " + email));
            document.add(new Paragraph("Phone Number: " + phoneNumber));
            document.add(new Paragraph("Equipment Rented: " + equipmentName));
            document.add(new Paragraph("Requested Date: " + requestDate));
            document.add(new Paragraph("Today's Date: " + currentDate));
            document.add(new Paragraph("Cost: $" + cost));
            document.add(new Paragraph(""));

            // Additional content can be added as needed...

            // Closing the document
            document.close();

            System.out.println("PDF generated successfully!");

        } catch (IOException e) {
            System.out.println("Error generating PDF: " + e.getMessage());
        }
    }

    private String displayInformation() {
        // Extracting values from objects
        String title = "\tGrizzly's Entertainment Equipment Rental";
        String name = p.getFirstName() + " " + p.getLastName();
        String email = p.getEmail();
        String phoneNumber = p.getPhone();
        int equipmentId = rental.getEquipmentId();

        equipment = (Equipment) App.getObject(equipmentId, "singleEquipment");
        String equipmentName = equipment.getEquipmentName();
        BigDecimal cost = equipment.getCost();

        // Displaying information in the interface
        String information = String.format(
                "%s\n\n  Invoice for: %s\n\n  Email: %s\n\n  Phone Number: %s\n\n  Equipment Rented: %s\n\n  Cost: $%s\n\n",
                title, name, email, phoneNumber, equipmentName, cost);

        return information;
    }

    public static void main(String[] args) {
        // Create a sample Person and Rental for testing
        App.connect("localhost", 8000);
        Person person = new Person(100000, "John", "Doe", "john.doe@example.com",
                "123-456-7890", null);
        Rental rental = new Rental("21-10-2023", 100000, 200001); // Assuming you have a Rental class

        // Create an Invoice and generate the PDF
        Invoice invoice = new Invoice(person, rental);
        // invoice.generatePDF();
    }
}
