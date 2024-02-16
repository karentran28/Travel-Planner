package model;

import java.util.ArrayList;
import java.util.List;

// Represents a travel itinerary including the name of the trip and the dates
public class TravelPlanner {

    private String name;
    private int totalDays;
    private double budget;
    private double accommodation;
    private String departingFlight;
    private String returningFlight;
    private List<Day> days;

    public TravelPlanner(String name, int totalDays, double budget) {
        this.name = name;
        this.totalDays = totalDays;
        this.budget = budget;
        accommodation = 0.00;
        departingFlight = null;
        returningFlight = null;
        days = new ArrayList<Day>();
    }

    public String getName() {
        return name;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public double getBudget() {
        return budget;
    }

    public String getDepartingFlight() {
        return departingFlight;
    }

    public void setDepartingFlight(String departingFlight) {
        this.departingFlight = departingFlight;
    }

    public String getReturningFlight() {
        return returningFlight;
    }

    public void setReturningFlight(String returningFlight) {
        this.returningFlight = returningFlight;
    }

    public double getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(double accommodation) {
        this.accommodation = accommodation;
    }

    public List<Day> getdaysList() {
        return days;
    }

    // MODIFIES: days
    // EFFECTS: Creates a list of days
    public List<Day> generateDaysList(int totalDays) {
        for (int i = 1; i <= totalDays; i++) {
            days.add(new Day(i));
        }
        return days;
    }

    public Day findDay(int dayNumber) {
        for (Day day : days) {
            if (day.getDayNumber() == dayNumber) {
                return day;
            }
        }
        return null;
    }


}
