package com.bosch.coding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProducerTest {

    @Test
    public void testWarehouseRequestEvent() {
        Producer.WarehouseRequestEvent event = new Producer().new WarehouseRequestEvent("apples", 5, "add");
        assertEquals("apples", event.getFruit());
        assertEquals(5, event.getQuantity());
        assertEquals("add", event.getCommand());
    }

    @Test
    public void testWarehouseRequestEventFactory() {
        Producer.WarehouseRequestEventFactory factory = new Producer().new WarehouseRequestEventFactory();
        Producer.WarehouseRequestEvent event = factory.createEvent();
        assertNotNull(event);
        assertNotNull(event.getFruit());
        assertNotNull(event.getCommand());
        assertTrue(event.getQuantity() >= 0 && event.getQuantity() < 10);
    }
}