package model;

import java.util.ArrayList;
import model.Activity;

// Represents a day including a list of activities scheduled
public class Day {

    private ArrayList<Activity> activitiesList;
    private String day;

    public Day(String day) {
        this.day = day;
        this.activitiesList = new ArrayList<Activity>();
    }

    // REQUIRES: time of activity can't already be allotted to an activity existing in list
    // MODIFIES: this
    // EFFECTS: adds activity to list of activities
    public void addActivity(Activity activity) {
        activitiesList.add(activity);
    }

    // MODIFIES: this
    // EFFECTS: removes activity from list of activities
    public void removeActivity(Activity activity) {
        activitiesList.remove(activity);
    }

    public ArrayList<Activity> getActivitiesList() {
        return activitiesList;
    }
}
