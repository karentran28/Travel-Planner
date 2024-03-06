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

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TravelPlanner tp = new TravelPlanner("Japan", 10, 2000.00);
            JsonWriter writer = new JsonWriter("./data/my\00fileName.json");
            writer.open();
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyTravelPlanner() {
        try {
            // save tp as JSON object
            TravelPlanner tp = new TravelPlanner("Japan", 10, 2000.00);
            tp.setDepartingFlight(LocalDate.of(2024, 05, 05));
            tp.setReturningFlight(LocalDate.of(2024, 05, 15));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTravelPlanner.json");
            writer.open();
            writer.write(tp);
            writer.close();

            // verify tp saved as JSON object by reading
            JsonReader reader = new JsonReader("./data/testWriterEmptyTravelPlanner.json");
            tp = reader.read();
            assertEquals("Japan", tp.getName());
            assertEquals(10, tp.getTotalDays());
            assertEquals(2000.00, tp.getBudget());
            assertEquals(0, tp.getDaysList().size());
            LocalDate testDepart = tp.getDepartingFlight();
            assertEquals(testDepart, tp.getDepartingFlight());
            LocalDate testReturn = tp.getReturningFlight();
            assertEquals(testReturn, tp.getReturningFlight());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTravelPlanner() {
        try {
            TravelPlanner tp = new TravelPlanner("Japan", 2, 1000.00);
            Activity activity1 = new Activity("sushi", 1, LocalTime.of(12, 00), 20.00);
            Activity activity2 = new Activity("Inari Temple", 2, LocalTime.of(14, 00), 0.00);
            tp.generateDaysList(tp.getTotalDays());
            Day day1 = tp.getDaysList().get(0);
            Day day2 = tp.getDaysList().get(1);
            day1.addActivity(activity1);
            day2.addActivity(activity2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTravelPlanner.json");
            writer.open();
            writer.write(tp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTravelPlanner.json");
            tp = reader.read();
            assertEquals("Japan", tp.getName());
            assertEquals(2, tp.getTotalDays());
            assertEquals(1000.00, tp.getBudget());

            List<Day> daysList = tp.getDaysList();
            assertEquals(2, daysList.size());

            assertEquals(1, day1.getActivitiesList().size());
            checkActivity("sushi", 1, LocalTime.of(12, 00),
                    20.00, day1.getActivity(0));

            assertEquals(1, day2.getActivitiesList().size());
            checkActivity("Inari Temple", 2, LocalTime.of(14, 00),
                    0.00, day2.getActivity(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
