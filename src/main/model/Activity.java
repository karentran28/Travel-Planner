package model;

import persistance.Writable;
import org.json.JSONObject;

import java.time.LocalTime;

//Represents an activity having a name, day, time, and cost (in dollars)
public class Activity implements Writable {

    private String name;        // the name of the activity
    private int day;            // the day the activity is set for
    private LocalTime time;     // the time of the activity
    private double cost;        // the cost of the activity

    public Activity(String name, int day, LocalTime time, double cost) {
        this.name = name;
        this.day = day;
        this.time = time;
        this.cost = cost;
        EventLog.getInstance().logEvent(new Event("New activity created."));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Name of activity set."));
    }

    public int getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
        EventLog.getInstance().logEvent(new Event("Time of activity set."));
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
        EventLog.getInstance().logEvent(new Event("Cost of activity set."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("day", day);
        json.put("time", time);
        json.put("cost", cost);
        return json;
    }
}
