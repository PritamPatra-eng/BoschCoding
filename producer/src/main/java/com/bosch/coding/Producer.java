package com.bosch.coding;

import java.util.Random;

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
        while (true) {
            WarehouseRequestEvent event = factory.createEvent();
            // Your code goes here
            //  System.out.println(event.toString());
            // Create a connection to the RabbitMQ server
            // Write event to a Rabbitmq topic

            /**
             * This is a blocking call that slows the event generation to 1 event per 100ms
             */
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}