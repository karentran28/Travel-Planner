package ui;

import java.io.FileNotFoundException;

public class Main {
    // EFFECTS: runs Travel Planner application and Travel Planner graphical user interface,
    //          throws FileNotFoundException if file cannot be found.
    public static void main(String[] args)  {
        try {
            new TravelPlannerGUI();
            new TravelPlannerApp();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Unable to run application.");
        }
    }
}
