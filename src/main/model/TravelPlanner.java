package model;

import java.util.ArrayList;
import java.util.List;

// Represents a travel itinerary including the name of the trip and the dates
public class TravelPlanner {

    private String name;
    private int totalDays;
    private int budget;
    private String accommodation;
    private String flightInformation;
    private List<Day> days;

    public TravelPlanner(String name) {
        this.name = name;
        List<Day> days = new ArrayList<Day>();
        // list of days? where each day has a list of activities
        // or should i create a list of activities here with name as day 1, 2 etc.
    }


}
