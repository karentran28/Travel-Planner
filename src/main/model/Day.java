package model;

import org.json.JSONArray;
import persistance.Writable;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a day of the trip including a day number and a list of activities scheduled
public class Day implements Writable {

    private ArrayList<Activity> activitiesList;
    private int dayNumber;

    public Day(int dayNumber) {
        this.dayNumber = dayNumber;
        this.activitiesList = new ArrayList<>();
    }

    public int getDayNumber() {
        return dayNumber;
    }

    // REQUIRES: time of activity can't already be allotted to an existing activity in list
    // MODIFIES: this
    // EFFECTS: adds activity to list of activities for the day
    public void addActivity(Activity activity) {
        activitiesList.add(activity);
    }

    // REQUIRES: must have existing activity at given index
    // EFFECTS: returns activity at index
    public Activity getActivity(int index) {
        return activitiesList.get(index);
    }

    // EFFECTS: returns list of activities for the day
    public ArrayList<Activity> getActivitiesList() {
        return activitiesList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("number", dayNumber);
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: return JSON array of activities in this Day
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity a : activitiesList) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
