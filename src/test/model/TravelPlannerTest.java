package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TravelPlannerTest {

    private TravelPlanner testTravelPlanner;

    @BeforeEach
    public void setup() {
        testTravelPlanner = new TravelPlanner("Japan", 4, 1000.00);
    }

    @Test
    public void testConstructor() {
        assertEquals("Japan", testTravelPlanner.getName());
        assertEquals(4, testTravelPlanner.getTotalDays());
        assertEquals(1000.00, testTravelPlanner.getBudget());
    }

    @Test
    public void testGetName() {
        assertEquals("Japan", testTravelPlanner.getName());
    }

    @Test
    public void testGetTotalDays() {
        assertEquals(4, testTravelPlanner.getTotalDays());
    }

    @Test
    public void testGetBudget() {
        assertEquals(1000.00, testTravelPlanner.getBudget());
    }

    @Test
    public void testGetDepartingFlight() {
        assertNull(testTravelPlanner.getDepartingFlight());
        LocalDate testDate = LocalDate.of(2024, 6, 20);
        testTravelPlanner.setDepartingFlight(testDate);
        assertEquals(testDate, testTravelPlanner.getDepartingFlight());
    }

    @Test
    public void testSetDepartingFlight() {
        LocalDate testDate = LocalDate.of(2024, 4, 28);
        testTravelPlanner.setDepartingFlight(testDate);
        assertEquals(testDate, testTravelPlanner.getDepartingFlight());
    }

    @Test
    public void testGetReturningFlight() {
        assertNull(testTravelPlanner.getReturningFlight());
        LocalDate testDate = LocalDate.of(2024, 05, 10);
        testTravelPlanner.setReturningFlight(testDate);
        assertEquals(testDate, testTravelPlanner.getReturningFlight());
    }

    @Test
    public void testSetReturningFlight() {
        LocalDate testDate = LocalDate.of(2024, 4, 28);
        testTravelPlanner.setReturningFlight(testDate);
        assertEquals(testDate, testTravelPlanner.getReturningFlight());
    }

    @Test
    public void testGetDaysList() {
        int totalDays = testTravelPlanner.getTotalDays();
        List<Day> days = testTravelPlanner.generateDaysList(totalDays);
        assertEquals(totalDays, days.size());
    }

    @Test
    public void testGenerateDaysList() {
        int total = testTravelPlanner.getTotalDays();
        testTravelPlanner.generateDaysList(total);

        assertEquals(total, testTravelPlanner.getDaysList().size());
        assertEquals(1, testTravelPlanner.getDaysList().get(0).getDayNumber());
        assertEquals(2, testTravelPlanner.getDaysList().get(1).getDayNumber());
        assertEquals(3, testTravelPlanner.getDaysList().get(2).getDayNumber());
        assertEquals(4, testTravelPlanner.getDaysList().get(3).getDayNumber());
    }

    @Test
    public void testFindDay() {
        assertNull(testTravelPlanner.findDay(3));
        testTravelPlanner.generateDaysList(testTravelPlanner.getTotalDays());

        int testNum = 3;
        Day testDay = testTravelPlanner.findDay(testNum);
        assertEquals(testNum, testDay.getDayNumber());
        assertNull(testTravelPlanner.findDay(100));
    }

    @Test
    public void testSearchForActivity() {
        Day d1 = new Day(1);
        Day d2 = new Day(2);
        LocalTime a1Time = LocalTime.of(7, 30);
        LocalTime a2Time = LocalTime.of(9, 00);
        LocalTime a3Time = LocalTime.of(13, 45);
        Activity a1 = new Activity("Fushimi Inari Shrine", 1, a1Time, 0.00);
        Activity a2 = new Activity("Bamboo Forest", 1, a2Time, 0.00);
        Activity a3 = new Activity("Disneyland", 2, a3Time, 100.00);
        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);

        testTravelPlanner.addDay(d1);
        testTravelPlanner.addDay(d2);

        assertEquals(a1, testTravelPlanner.searchForActivity("Fushimi Inari Shrine"));
        assertEquals(a2, testTravelPlanner.searchForActivity("Bamboo Forest"));
        assertEquals(a3, testTravelPlanner.searchForActivity("Disneyland"));
        assertNull(testTravelPlanner.searchForActivity("Osaka"));
    }

    @Test
    public void addDay() {
        Day d1 = new Day(1);
        Day d2 = new Day(2);

        testTravelPlanner.addDay(d1);
        testTravelPlanner.addDay(d2);

        List<Day> testList = testTravelPlanner.getDaysList();
        assertEquals(testList, testTravelPlanner.getDaysList());
        assertEquals(d1, testTravelPlanner.getDaysList().get(0));
        assertEquals(d2, testTravelPlanner.getDaysList().get(1));
    }
}
