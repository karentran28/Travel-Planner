package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityTest {

    private Activity a1;
    private Activity a2;
    private Activity a3;

    @BeforeEach
    public void setup() {
        a1 = new Activity("Fushimi Inari Shrine", 2, 7, 0.00);
        a2 = new Activity("Bamboo Forest", 2, 9, 0.00);
        a3 = new Activity("Disneyland", 3, 10, 100.00);
    }

    @Test
    public void testGetName() {
        assertEquals("Fushimi Inari Shrine", a1.getName());
        assertEquals("Bamboo Forest", a2.getName());
        assertEquals("Disneyland", a3.getName());
    }

    @Test
    public void testGetDay() {
        assertEquals(2, a1.getDay());
        assertEquals(2, a2.getDay());
        assertEquals(3 , a3.getDay());
    }

    @Test
    public void testGetTime() {
        assertEquals(7, a1.getTime());
        assertEquals(9, a2.getTime());
        assertEquals(10, a3.getTime());
    }

    @Test
    public void testGetCost() {
        assertEquals(0.00, a1.getCost());
        assertEquals(0.00, a2.getCost());
        assertEquals(100.00, a3.getCost());
    }
}