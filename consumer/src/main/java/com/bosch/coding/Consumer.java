package com.bosch.coding;

public class Consumer {
    private static final String QUEUE_NAME = "warehouse_requests";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String DB_USER = "bosch";
    private static final String DB_PASSWORD = "very_secret";

    public static void main(String[] args) {
        ConnectionFactory rabbitMQFactory = new ConnectionFactory();
        rabbitMQFactory.setHost("localhost");

        try (com.rabbitmq.client.Connection connection = rabbitMQFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Event Received: " + message);
                updateInventory(message);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processMessage(String message) {
        String[] parts = message.replace("WarehouseRequestEvent{", "").replace("}", "").split(",");
        String fruit = parts[0].split("=")[1].trim();
        int quantity = Integer.parseInt(parts[1].split("=")[1].trim());
        String command = parts[2].split("=")[1].trim();

        if ("add".equals(command)) {
            addInventory(fruit, quantity);
        } else if ("remove".equals(command)) {
            removeInventory(fruit);
        } else {
            System.out.println("Unknown command: " + command);
        }
    }

    private static void addInventory(String fruit, int quantity) {
        String sql = "UPDATE inventory SET quantity = quantity + ? WHERE fruit = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);
            pstmt.setString(2, fruit);
            pstmt.executeUpdate();
            System.out.println("Added " + quantity + " to " + fruit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeInventory(String fruit) {
        String sql = "DELETE FROM inventory WHERE fruit = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fruit);
            pstmt.executeUpdate();
            System.out.println("Removed " + fruit + " from inventory");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}