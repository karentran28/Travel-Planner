package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Represents a travel itinerary including the name of the trip, the total days of the trip,
// and the estimated budget of the trip
public class TravelPlanner {

    private String name;
    private int totalDays;
    private double budget;
    private double accommodation;       // the cost of accommodations
    private LocalDate departingFlight;     // the date of departing flight
    private LocalDate returningFlight;     // the date of the return flight
    private List<Day> totalTripDays;    // a list of days, each representing one Day of the trip

    public TravelPlanner(String name, int totalDays, double budget) {
        this.name = name;
        this.totalDays = totalDays;
        this.budget = budget;
        accommodation = 0.00;
        departingFlight = null;
        returningFlight = null;
        totalTripDays = new ArrayList<>();
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

    public LocalDate getDepartingFlight() {
        return departingFlight;
    }

    public void setDepartingFlight(LocalDate departingFlight) {
        this.departingFlight = departingFlight;
    }

    public LocalDate getReturningFlight() {
        return returningFlight;
    }

    public void setReturningFlight(LocalDate returningFlight) {
        this.returningFlight = returningFlight;
    }

    public double getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(double accommodation) {
        this.accommodation = accommodation;
    }

    public List<Day> getdaysList() {
        return totalTripDays;
    }

    // REQUIRES: totalDays must be >= 1
    // MODIFIES: this
    // EFFECTS: Creates and returns a list of Day with size equal to totalTripDays (one Day for each day of the trip)
    public List<Day> generateDaysList(int totalDays) {
        for (int i = 1; i <= totalDays; i++) {
            totalTripDays.add(new Day(i));
        }
        return totalTripDays;
    }

    // REQUIRES: dayNumber must be >= 1 and <= totalTripDays
    // EFFECTS: searches through the list to find a Day with dayNumber and returns it, otherwise return null
    public Day findDay(int dayNumber) {
        for (Day day : totalTripDays) {
            if (day.getDayNumber() == dayNumber) {
                return day;
            }
        }
        return null;
    }

    // EFFECTS: searches through the list of activities in each Day for an activity with given name and returns it,
    //          otherwise return null
    public Activity searchForActivity(String name) {
        for (Day d : totalTripDays) {
            for (Activity a : d.getActivitiesList()) {
                if (a.getName().equals(name)) {
                    return a;
                }
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds a Day into totalTripDays list (only used for testing)
    public void addDay(Day day) {
        totalTripDays.add(day);
    }
}
