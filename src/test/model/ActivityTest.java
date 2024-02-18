package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

class ActivityTest {

    private Activity a1;
    private Activity a2;
    private Activity a3;
    private LocalTime a1Time = LocalTime.of(7, 30);
    private LocalTime a2Time = LocalTime.of(9, 00);
    private LocalTime a3Time = LocalTime.of(13, 45);

    @BeforeEach
    public void setup() {
        a1 = new Activity("Fushimi Inari Shrine", 2, a1Time, 0.00);
        a2 = new Activity("Bamboo Forest", 2, a2Time, 0.00);
        a3 = new Activity("Disneyland", 3, a3Time, 100.00);
    }

    @Test
    public void testConstructor() {
        assertEquals("Fushimi Inari Shrine", a1.getName());
        assertEquals(2, a1.getDay());
        assertEquals(a1Time, a1.getTime());
        assertEquals(0.00, a1.getCost());

        assertEquals("Bamboo Forest", a2.getName());
        assertEquals(2, a2.getDay());
        assertEquals(a2Time, a2.getTime());
        assertEquals(0.00, a2.getCost());

        assertEquals("Disneyland", a3.getName());
        assertEquals(3, a3.getDay());
        assertEquals(a3Time, a3.getTime());
        assertEquals(100.00, a3.getCost());
    }

    @Test
    public void testGetName() {
        assertEquals("Fushimi Inari Shrine", a1.getName());
        assertEquals("Bamboo Forest", a2.getName());
        assertEquals("Disneyland", a3.getName());
    }

    @Test
    public void testSetName() {
        a1.setName("Inari Temple");
        assertEquals("Inari Temple", a1.getName());
    }

    @Test
    public void testGetDay() {
        assertEquals(2, a1.getDay());
        assertEquals(2, a2.getDay());
        assertEquals(3, a3.getDay());
    }

    @Test
    public void testGetTime() {
        assertEquals(a1Time, a1.getTime());
        assertEquals(a2Time, a2.getTime());
        assertEquals(a3Time, a3.getTime());
    }

    @Test
    public void testSetTime() {
        LocalTime testTime = LocalTime.of(10, 00);
        a2.setTime(testTime);
        assertEquals(testTime, a2.getTime());
    }

    @Test
    public void testGetCost() {
        assertEquals(0.00, a1.getCost());
        assertEquals(0.00, a2.getCost());
        assertEquals(100.00, a3.getCost());
    }

    @Test
    public void testSetCost() {
        a3.setCost(300.00);
        assertEquals(300.00, a3.getCost());
    }
}