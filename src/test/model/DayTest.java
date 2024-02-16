package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        d1 = new Day(2);
        d2 = new Day(3);
        a1 = new Activity("Fushimi Inari Shrine", 2, 7, 0.00);
        a2 = new Activity("Bamboo Forest", 2, 9, 0.00);
        a3 = new Activity("Disneyland", 3, 10, 100.00);
    }

    @Test
    public void testAddActivity() {
        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);

    }

    @Test
    public void testRemoveActivity() {
        d1.removeActivity(a1);
        d1.removeActivity(a2);
        d2.removeActivity(a3);
    }

    @Test
    public void testGetActivitiesList() {
        d1.addActivity(a1);
        d1.addActivity(a2);
        d2.addActivity(a3);

        List<Activity> retList = d1.getActivitiesList();
        assertEquals(retList, d1.getActivitiesList());

        List<Activity> retList2 = d2.getActivitiesList();
        assertEquals(retList2, d2.getActivitiesList());
    }
}
