package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest {

    private Day d1;
    private Day d2;
    private Activity a1;
    private Activity a2;
    private Activity a3;

    @BeforeEach
    public void setup() {
        d1 = new Day(1);
        d2 = new Day(2);

        LocalTime a1Time = LocalTime.of(7, 30);
        LocalTime a2Time = LocalTime.of(9, 00);
        LocalTime a3Time = LocalTime.of(13, 45);
        a1 = new Activity("Fushimi Inari Shrine", 1, a1Time, 0.00);
        a2 = new Activity("Bamboo Forest", 1, a2Time, 0.00);
        a3 = new Activity("Disneyland", 2, a3Time, 100.00);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, d1.getDayNumber());
        assertEquals(2, d2.getDayNumber());
    }

    @Test
    public void testAddActivity() {
        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);
        assertEquals(a1, d1.getActivity(0));
        assertEquals(a2, d1.getActivity(1));
        assertEquals(a3, d2.getActivity(0));
    }

    @Test
    public void testGetActivitiesList() {
        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);

        List<Activity> retList = d1.getActivitiesList();
        assertEquals(retList, d1.getActivitiesList());
        assertEquals(2, retList.size());

        List<Activity> retList2 = d2.getActivitiesList();
        assertEquals(retList2, d2.getActivitiesList());
        assertEquals(1, retList2.size());
    }
}
