package persistance;

import model.Activity;
import model.Day;
import model.TravelPlanner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fakefile.json");
        try {
            TravelPlanner tp = reader.read();
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyTravelPlannerForDay() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTravelPlanner.json");
        try {
            TravelPlanner tp = reader.read();
            assertEquals("japan", tp.getName());
            assertEquals(4, tp.getTotalDays());
            LocalDate testDepart = tp.getDepartingFlight();
            assertEquals(testDepart, tp.getDepartingFlight());
            LocalDate testReturn = tp.getReturningFlight();
            assertEquals(testReturn, tp.getReturningFlight());
            List<Day> dayList = tp.getDaysList();
            assertEquals(4, dayList.size());
            checkDay(1, dayList.get(0).getActivitiesList(), dayList.get(0));
            checkDay(2, dayList.get(1).getActivitiesList(), dayList.get(1));
            checkDay(3, dayList.get(2).getActivitiesList(), dayList.get(2));
            checkDay(4, dayList.get(3).getActivitiesList(), dayList.get(3));

        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    void testReaderEmptyTravelPlannerForActivity() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTravelPlanner.json");
        try {
            TravelPlanner tp = reader.read();
            List<Day> dayList = tp.getDaysList();
            List<Activity> day1 = dayList.get(0).getActivitiesList();
            assertEquals(0, day1.size());

            List<Activity> day2 = dayList.get(1).getActivitiesList();
            assertEquals(2, day2.size());
            LocalTime time1 = LocalTime.of(12, 00);
            checkActivity("sushi", 2, time1, 20.00, day2.get(0));
            LocalTime time2 = LocalTime.of(14, 00);
            checkActivity("Inari temple", 2, time2, 25.00, day2.get(1));

            List<Activity> day3 = dayList.get(2).getActivitiesList();
            assertEquals(1, day3.size());
            LocalTime time3 = LocalTime.of(14, 00);
            checkActivity("ski", 3, time3, 120.00, day3.get(0));

            List<Activity> day4 = dayList.get(3).getActivitiesList();
            assertEquals(0, day4.size());

        } catch (IOException e) {
            fail("exception should not have been thrown");
        }
    }
}
