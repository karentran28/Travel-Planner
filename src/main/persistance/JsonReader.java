package persistance;

import model.Activity;
import model.Day;
import model.TravelPlanner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

// CITE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads travel planner from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source JSON file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads travel planner from file and returns it; throws IOException if an error occurs reading data
    // from file
    public TravelPlanner read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTravelPlanner(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }

        return stringBuilder.toString();
    }

    // EFFECTS: parses travel planner from JSON object and returns it
    private TravelPlanner parseTravelPlanner(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int totalDays = jsonObject.getInt("totalDays");
        double budget = jsonObject.getDouble("budget");
        TravelPlanner tp = new TravelPlanner(name, totalDays, budget);

        LocalDate departingFlight;
        if (!jsonObject.isNull("departingFlight")) {
            departingFlight = LocalDate.parse(jsonObject.getString("departingFlight"));
            tp.setDepartingFlight(departingFlight);
        }

        LocalDate returnFlight;
        if (!jsonObject.isNull("returningFlight")) {
            returnFlight = LocalDate.parse(jsonObject.getString("returningFlight"));
            tp.setReturningFlight(returnFlight);
        }

        addDaysList(tp, jsonObject);
        return tp;
    }

    // MODIFIES: tp
    // EFFECTS: parses days from JSON object and adds them to travel planner
    private void addDaysList(TravelPlanner tr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("totalTripDays");
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json; //Object json being explicitly cast to a JSONObject
            addDay(tr, nextDay);
        }
    }

    // MODIFIES: tp
    // EFFECTS: parses day from JSON object and adds it to travel planner
    private void addDay(TravelPlanner tp, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        Day day = new Day(number);
        addActivities(day, jsonObject);
        tp.addDay(day);
    }

    // MODIFIES: day, tp
    // EFFECTS: parses activities from JSON object and adds them to day
    private void addActivities(Day day, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(day, nextActivity);
        }
    }

    // MODIFIES: day, tp
    // EFFECTS: parses activity from JSON object and adds it to day
    private void addActivity(Day day, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int dayNumber = jsonObject.getInt("day");
        LocalTime time = LocalTime.parse(jsonObject.getString("time"));
        double cost = jsonObject.getDouble("cost");

        Activity activity = new Activity(name, dayNumber, time, cost);
        day.addActivity(activity);
    }

}
