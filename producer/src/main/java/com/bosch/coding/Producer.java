package com.bosch.coding;

import java.util.Random;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Producer {

    /**
     * This class represents a warehouse request event
     */
    public final class WarehouseRequestEvent {
        private final String fruit;
        private final Integer quantity;
        private final String command;

        public WarehouseRequestEvent(String fruit, Integer quantity, String command) {
            this.fruit = fruit;
            this.quantity = quantity;
            this.command = command;
        }

        public String getFruit() {
            return fruit;
        }
        public Integer getQuantity() {
            return quantity;
        }
        public String getCommand() {
            return command;
        }
        @Override
        public String toString() {
            return "WarehouseRequestEvent{" +
                    "fruit='" + fruit + '\'' +
                    ", quantity=" + quantity +
                    ", command='" + command + '\'' +
                    '}';
        }
    }

    /**
     * This Factory class is responsible for generating random warehouse request events
     */
    public class WarehouseRequestEventFactory {
        private static final String[] fruits = {"apples", "bananas", "coconuts","dates","elderberries"};
        private static String[] commands = {"add","remove"};
        private static Random RANDOM = new Random();

        public WarehouseRequestEvent createEvent() {
            return new WarehouseRequestEvent(
                    fruits[RANDOM.nextInt(fruits.length)],
                    RANDOM.nextInt(10),
                    commands[RANDOM.nextInt(commands.length)]);
        }
    }


    public static void main(String[] args) {
        Producer producer = new Producer();
        WarehouseRequestEventFactory factory = producer.new WarehouseRequestEventFactory();
        //when all is done wait 100ms before next event

        ConnectionFactory rabbitMqFactory = new ConnectionFactory();
        rabbitMqFactory.setHost("localhost");

        try(Connection connection = rabbitMqFactory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare("warehouse_request", false, false, false, null);

            while (true) {
                WarehouseRequestEvent event = factory.createEvent();
                // Your code goes here
                //  System.out.println(event.toString());
                // Create a connection to the RabbitMQ server
                // Write event to a Rabbitmq topic

                String message = event.toString();
                channel.basicPublish("", "warehouse_requests", null, message.getBytes());
                System.out.println("Event Sent: " +message);
                        /**
                         * This is a blocking call that slows the event generation to 1 event per 100ms
                         */
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}