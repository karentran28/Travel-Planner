package persistance;

import model.Activity;
import model.Day;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkDay(int number, List<Activity> activities, Day day) {
        assertEquals(number, day.getDayNumber());
        assertEquals(activities, day.getActivitiesList());
    }

    protected void checkActivity(String name, int day, LocalTime time, double cost, Activity activity) {
        assertEquals(name, activity.getName());
        assertEquals(day, activity.getDay());
        assertEquals(time, activity.getTime());
        assertEquals(cost, activity.getCost());
    }

}
