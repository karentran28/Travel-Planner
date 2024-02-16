package model;

//Represents an activity having a name, date, and time
public class Activity {

    private String name;
    private int day;
    private int time;
    private double cost;

    //similar to library project where they had an enum?
    //private Category category; // food, accommodations, activities, other

    public Activity(String name, int date, int time, double cost) {
        this.name = name;
        this.day = date;
        this.time = time;
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    public double getCost() {
        return cost;
    }

}


