package ui;

import model.TravelPlanner;
import model.Activity;
import model.Day;

import java.util.Scanner;
import java.util.List;

public class UserInterface {

    Scanner scan = new Scanner(System.in);
    TravelPlanner myPlanner;
    private String isFlying;
    private String haveAccommodations;
    private String confirm;

    public UserInterface() {
        runPlanner();
    }

    private void runPlanner() {
        createPlanner();
        summarizePlanner();
        mainMenu();
    }

    public void mainMenu() {
        String command = null;

        displayMenu();
        command = scan.next();
        processCommand(command);
    }

    private void createPlanner() {
        System.out.println("Where are you travelling to?");
        String plannerName = scan.next();

        System.out.println("How many days are you going for?");
        int totalDays = scan.nextInt();

        System.out.println("What is your estimated budget? Enter in format XX.XX");
        double tripBudget = scan.nextDouble();

        System.out.println("Are you flying to " + plannerName + "? Please type 'yes' or 'no'.");
        isFlying = scan.next();
        addFlights();

        System.out.println("Do you have accommodations booked? Please type 'yes' or 'no'.");
        haveAccommodations = scan.next();
        addAccommodations();

        myPlanner = new TravelPlanner(plannerName, totalDays, tripBudget);

        myPlanner.generateDaysList(myPlanner.getTotalDays());
    }

    public void summarizePlanner() {
        System.out.println("Creating planner for " + myPlanner.getName());
        System.out.println("\tTotal Days on Travel: " + myPlanner.getTotalDays());
        System.out.println("\tEstimated Budget: $" + myPlanner.getBudget());
        if (isFlying.equals("yes")) {
            System.out.println("\tFlying on: " + myPlanner.getDepartingFlight());
            System.out.println("\tReturning on: " + myPlanner.getReturningFlight());
        }
        if (haveAccommodations.equals("yes")) {
            System.out.println("\tCost of Accommodations: $" + myPlanner.getAccommodation());
        }
    }

    public void addFlights() {
        if (isFlying.equals("yes")) {
            System.out.println("What day are you flying? Enter in mm/dd/yyyy format.");
            myPlanner.setDepartingFlight(scan.next());

            System.out.println("What day are you flying home? Enter in mm/dd/yyyy format.");
            myPlanner.setReturningFlight(scan.next());
        }
    }

    public void addAccommodations() {
        if (haveAccommodations.equals("yes")) {
            System.out.println("Enter the total cost of accommodations in XX.XX format.");
            myPlanner.setAccommodation(scan.nextDouble());
        }
    }

    private void displayMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("\ta -> add new activity");
        System.out.println("\tb -> view existing activities");
    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            addActivity();
        } else {
            viewActivities();
        }
    }

    private void addActivity() {
        System.out.println("Please type the name of the activity you want to add.");
        String activityName = scan.next();

        System.out.println("Which day is this activity planned for?");
        int activityDay = scan.nextInt();

        System.out.println("Enter the time of this activity");
        int activityTime = scan.nextInt();

        System.out.println("What is the estimated cost of the activity?");
        double activityCost = scan.nextDouble();

        Activity newActivity = new Activity(activityName, activityDay, activityTime, activityCost);
        Day foundDay = myPlanner.findDay(activityDay);
        foundDay.addActivity(newActivity);

        System.out.println("Added " + activityName + " to Day " + activityDay + "!");
        mainMenu();
    }

    private void viewActivities() {
        System.out.println("Enter the day number you would like to view existing activities for.");
        int dayNumber = scan.nextInt();
        Day foundDay = myPlanner.findDay(dayNumber);

        if (foundDay != null) {
            List<Activity> activities = foundDay.getActivitiesList();

            if (activities.size() != 0) {
                System.out.println("Activities: ");
                for (Activity a : activities) {
                    System.out.println("Time: " + a.getTime() + ":" + a.getName());
                }
            }
        }
    }
    // return day x activities
    // display like a schedule of the activities
    // should include time:name of activity
}

