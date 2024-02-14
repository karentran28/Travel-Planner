package model;

//Represents an activity having a name, date, and time
public class Activity {

    private String name;
    private String date;
    private String time;
    private double cost;

    //similar to library project where they had an enum?
    //private Category category; // food, accommodations, activities, other

    public Activity(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Double getCost() {
        return cost;
    }

}


