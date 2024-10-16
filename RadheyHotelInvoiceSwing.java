import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class MenuItem {
    String name;
    double price;

    MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Order {
    MenuItem item;
    int quantity;

    Order(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double getTotalPrice() {
        return item.price * quantity;
    }
}

class RoomType {
    String type;
    double ratePerNight;

    RoomType(String type, double ratePerNight) {
        this.type = type;
        this.ratePerNight = ratePerNight;
    }
}

class RoomBooking {
    RoomType roomType;
    int numberOfNights;

    RoomBooking(RoomType roomType, int numberOfNights) {
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
    }

    double getTotalPrice() {
        return roomType.ratePerNight * numberOfNights;
    }
}

public class RadheyHotelInvoiceSwing extends JFrame {
    private ArrayList<MenuItem> menu = new ArrayList<>();
    private ArrayList<RoomType> roomTypes = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<RoomBooking> roomBookings = new ArrayList<>();

    private JTextArea invoiceArea;
    private JComboBox<String> menuComboBox;
    private JTextField quantityField;
    private JComboBox<String> roomComboBox;
    private JTextField nightsField;

    public RadheyHotelInvoiceSwing() {
        // Sample menu items
        menu.add(new MenuItem("Masala Papad", 20.0));
        menu.add(new MenuItem("Rosted Papad", 10.0));
        menu.add(new MenuItem("Paneer Butter Masala", 250.0));
        menu.add(new MenuItem("Rice & dal Talka", 180.0));
        menu.add(new MenuItem("Chapati", 20.0));
        menu.add(new MenuItem("Butter Nan", 40.0));
        menu.add(new MenuItem("Veg Biryani", 200.0));
        menu.add(new MenuItem("Gulab Jamun", 50.0));
        menu.add(new MenuItem("Paneer Chilli", 150.0));
        menu.add(new MenuItem("Manuchurian", 90.0));
        menu.add(new MenuItem("Manuchurian Rice", 150.0));

        // Sample room types
        roomTypes.add(new RoomType("Single Room", 1000.0));
        roomTypes.add(new RoomType("Double Room", 1800.0));
        roomTypes.add(new RoomType("Suite", 3000.0));

        // Setting up the frame
        setTitle("Radhey Hotel Invoice");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Invoice area
        invoiceArea = new JTextArea();
        invoiceArea.setEditable(false);
        add(new JScrollPane(invoiceArea), BorderLayout.CENTER);

        // Panel for ordering food
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new FlowLayout());

        menuComboBox = new JComboBox<>();
        for (MenuItem item : menu) {
            menuComboBox.addItem(item.name + " - Rs." + item.price);
        }
        quantityField = new JTextField(5);
        JButton orderButton = new JButton("Order Food");

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = menuComboBox.getSelectedIndex();
                int quantity = Integer.parseInt(quantityField.getText());
                orders.add(new Order(menu.get(selectedIndex), quantity));
                JOptionPane.showMessageDialog(null, "Food ordered successfully!");
                quantityField.setText("");
            }
        });

        orderPanel.add(menuComboBox);
        orderPanel.add(new JLabel("Quantity:"));
        orderPanel.add(quantityField);
        orderPanel.add(orderButton);

        // Panel for room booking
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new FlowLayout());

        roomComboBox = new JComboBox<>();
        for (RoomType type : roomTypes) {
            roomComboBox.addItem(type.type + " - Rs." + type.ratePerNight + " per night");
        }
        nightsField = new JTextField(5);
        JButton bookButton = new JButton("Book Room");

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = roomComboBox.getSelectedIndex();
                int nights = Integer.parseInt(nightsField.getText());
                roomBookings.add(new RoomBooking(roomTypes.get(selectedIndex), nights));
                JOptionPane.showMessageDialog(null, "Room booked successfully!");
                nightsField.setText("");
            }
        });

        roomPanel.add(roomComboBox);
        roomPanel.add(new JLabel("Nights:"));
        roomPanel.add(nightsField);
        roomPanel.add(bookButton);

        // Generate invoice button
        JButton invoiceButton = new JButton("Generate Invoice");
        invoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });

        // Adding panels to frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.add(orderPanel);
        mainPanel.add(roomPanel);
        mainPanel.add(invoiceButton);
        
        add(mainPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        double totalBill = 0;

        // Restaurant orders
        invoice.append("------------Invoice------------\n");
        for (Order order : orders) {
            invoice.append(order.item.name).append(" (x").append(order.quantity)
                   .append(") - Rs.").append(order.getTotalPrice()).append("\n");
            totalBill += order.getTotalPrice();
        }

        // Room bookings
        for (RoomBooking booking : roomBookings) {
            invoice.append(booking.roomType.type).append(" (x").append(booking.numberOfNights)
                   .append(" nights) - Rs.").append(booking.getTotalPrice()).append("\n");
            totalBill += booking.getTotalPrice();
        }

        // Display total bill
        invoice.append("\nTotal Bill: Rs.").append(totalBill).append("\n");
        invoice.append("Thank you for staying and dining at Radhey Restaurant!\n");

        invoiceArea.setText(invoice.toString());
    }

    public static void main(String[] args) {
        new RadheyHotelInvoiceSwing();
    }
}