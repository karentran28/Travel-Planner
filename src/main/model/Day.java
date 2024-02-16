package model;

import java.util.ArrayList;
import model.Activity;

// Represents a day including a list of activities scheduled
public class Day {

    private ArrayList<Activity> activitiesList;
    private int dayNumber;

    public Day(int dayNumber) {
        this.dayNumber = dayNumber;
        this.activitiesList = new ArrayList<Activity>();
    }

    public int getDayNumber() {
        return dayNumber;
    }

    // REQUIRES: time of activity can't already be allotted to an activity existing in list
    // MODIFIES: this
    // EFFECTS: adds activity to list of activities (to list according to day)
    public void addActivity(Activity activity) {
        activitiesList.add(activity);
    }

    // EFFECTS: searches through the list for name of activity and returns it
    public Activity getActivity() {
        return null;
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
