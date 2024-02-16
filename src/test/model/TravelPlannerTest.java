package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TravelPlannerTest {

    private TravelPlanner testTravelPlanner;
    private Day d1;
    private Day d2;
    private Activity a1;
    private Activity a2;
    private Activity a3;

    @BeforeEach
    public void setup() {
        testTravelPlanner = new TravelPlanner("Japan", 10, 1000.00);

        d1 = new Day(1);
        d2 = new Day(2);

        a1 = new Activity("Fushimi Inari Shrine", 2, 7, 0.00);
        a2 = new Activity("Bamboo Forest", 2, 9, 0.00);
        a3 = new Activity("Disneyland", 3, 10, 100.00);

        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);

    }

    @Test
    public void testConstructor() {
        assertEquals("Japan", testTravelPlanner.getName());
        assertEquals(10, testTravelPlanner.getTotalDays());
        assertEquals(1000.00, testTravelPlanner.getBudget());
    }

    @Test
    public void testGetName() {
        assertEquals("Japan", testTravelPlanner.getName());
    }

    @Test
    public void testGetTotalDays() {
        assertEquals(10, testTravelPlanner.getTotalDays());
    }

    @Test
    public void testGetBudget() {
        assertEquals(1000.00, testTravelPlanner.getBudget());
    }

    @Test
    public void testGetDepartingFlight() {
        assertNull(testTravelPlanner.getDepartingFlight());
        testTravelPlanner.setDepartingFlight("06/20/2024");
        assertEquals("06/20/2024", testTravelPlanner.getDepartingFlight());
    }

    @Test
    public void testSetDepartingFlight() {
        testTravelPlanner.setDepartingFlight("04/28/2024");
        assertEquals("04/28/2024", testTravelPlanner.getDepartingFlight());
    }

    @Test
    public void testGetReturningFlight() {
        assertNull(testTravelPlanner.getReturningFlight());
        testTravelPlanner.setReturningFlight("05/10/2024");
        assertEquals("05/10/2024", testTravelPlanner.getReturningFlight());
    }

    @Test
    public void testSetReturningFlight() {
        testTravelPlanner.setReturningFlight("03/18/2024");
        assertEquals("03/18/2024", testTravelPlanner.getReturningFlight());
    }

    @Test
    public void testGetAccommodation() {
        assertEquals(0.00, testTravelPlanner.getAccommodation());
    }

    @Test
    public void testSetAccommodation() {
        testTravelPlanner.setAccommodation(200.00);
        assertEquals(200.00, testTravelPlanner.getAccommodation());
    }

    @Test
    public void testGetDaysList() {
        List<Day> days = testTravelPlanner.generateDaysList(testTravelPlanner.getTotalDays());
        int totalDays = testTravelPlanner.getTotalDays();

        assertEquals(totalDays, days.size());
    }

    @Test
    public void testGenerateDaysList() {
        int total = testTravelPlanner.getTotalDays();
        testTravelPlanner.generateDaysList(total);

        assertEquals(total, testTravelPlanner.getdaysList().size());
    }

    @Test
    public void testFindDay() {
        assertNull(testTravelPlanner.findDay(3));
        testTravelPlanner.generateDaysList(testTravelPlanner.getTotalDays());

        int testNum = 3;
        Day testDay = testTravelPlanner.findDay(testNum);

        assertEquals(testNum, testDay.getDayNumber());
    }

}
