package model;

import java.time.LocalTime;

//Represents an activity having a name, day, time, and cost (in dollars)
public class Activity {

    private String name;        // the name of the activity
    private int day;            // the day the activity is set for
    private LocalTime time;     // the time of the activity
    private double cost;        // the cost of the activity

    public Activity(String name, int day, LocalTime time, double cost) {
        this.name = name;
        this.day = day;
        this.time = time;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
