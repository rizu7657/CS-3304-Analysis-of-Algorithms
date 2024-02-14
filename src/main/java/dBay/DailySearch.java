package dBay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DailySearch {
    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();
        String[] products = {
                "Smartphone",
                "Laptop",
                "Tablet",
                "Headphones",
                "Smartwatch",
                "Camera",
                "Bluetooth Speaker",
                "Gaming Console",
                "Fitness Tracker",
                "Wireless Earbuds",
                "Drone",
                "External Hard Drive",
                "Action Camera",
                "Virtual Reality Headset",
                "Portable Charger",
                "Smart Home Assistant",
                "E-reader",
                "Wireless Mouse",
                "Printer",
                "Monitor"
        };

        for (int i = 0; i < 10000; i++) {
            Order order = new Order();
            order.setId((long) (Math.random() * 10000));
            order.setProduct(products[(int) (Math.random() * products.length - 1)]);
            order.setPrice(Math.random() * 2500);
            order.setQuantity((int) (Math.random() * 5));
            order.setTotal(order.getPrice() * order.getQuantity());

            orders.add(order);
        }

        System.out.println("What is the ID of the order you are looking for:");
        Scanner in = new Scanner(System.in);
        int searchId = in.nextInt();

        Order order = SearchFacade.findById(orders, (long) searchId);

        if (order != null) {
            System.out.printf("Order found: #%s - %s for total R%.2f.\n", order.getId(), order.getProduct(), order.getTotal());
        } else {
            System.out.printf("Order with ID %s not found.\n", searchId);
        }
    }
}
