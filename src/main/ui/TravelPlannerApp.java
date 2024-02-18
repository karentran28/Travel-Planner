package ui;

import model.TravelPlanner;
import model.Activity;
import model.Day;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;

public class TravelPlannerApp {

    Scanner scan = new Scanner(System.in);
    TravelPlanner myPlanner;
    private String isFlying;
    private String haveAccommodations;

    public TravelPlannerApp() {
        runPlanner();
    }

    private void runPlanner() {
        createPlanner();
        summarizePlanner();
        mainMenu();
    }

    public void mainMenu() {
        displayMenu();
        String command = scan.next();
        processCommand(command);
    }

    private void displayMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("\ta -> add new activity");
        System.out.println("\tb -> view existing activities");
        System.out.println("\tc -> edit existing activity");
        System.out.println("\tq -> quit");
    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            addActivity();
        } else if (command.equals("b")) {
            viewActivities();
        } else if (command.equals("c")) {
            editActivity();
        }
    }

    private void createPlanner() {
        System.out.println("Where are you travelling to?");
        String plannerName = scan.next();

        System.out.println("How many days are you going for?");
        int totalDays = scan.nextInt();

        System.out.println("What is your estimated budget? Enter in format X.XX");
        double tripBudget = scan.nextDouble();

        myPlanner = new TravelPlanner(plannerName, totalDays, tripBudget);

        myPlanner.generateDaysList(myPlanner.getTotalDays());

        System.out.println("Are you flying to " + plannerName + "? Please type 'yes' or 'no'.");
        isFlying = scan.next();
        addFlights();

        System.out.println("Do you have accommodations booked? Please type 'yes' or 'no'.");
        haveAccommodations = scan.next();
        addAccommodations();
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
            System.out.println("What day are you flying? Enter date in YYYY-MM-DD format.");
            String departingFlightString = scan.next();
            LocalDate departingFlightDate = LocalDate.parse(departingFlightString);
            myPlanner.setDepartingFlight(departingFlightDate);

            System.out.println("What day are you flying home? Enter in YYYY-MM-DD format.");
            String returningFlightString = scan.next();
            LocalDate returningFlightDate = LocalDate.parse(returningFlightString);
            myPlanner.setReturningFlight(returningFlightDate);
        } else if (!isFlying.equals("no")) {
            System.out.println("Sorry " + isFlying + " is not an option. Please type 'yes' or 'no'.");
            isFlying = scan.next();
            addFlights();
        }
    }

    public void addAccommodations() {
        if (haveAccommodations.equals("yes")) {
            System.out.println("Enter the total cost of accommodations in X.XX format.");
            myPlanner.setAccommodation(scan.nextDouble());
        } else if (!haveAccommodations.equals("no")) {
            System.out.println("Sorry " + haveAccommodations + " is not an option. Please type 'yes' or 'no'.");
            haveAccommodations = scan.next();
            addAccommodations();
        }
    }

    private void addActivity() {
        System.out.println("Please type the name of the activity you want to add.");
        String activityName = scan.next();

        System.out.println("Which day is this activity planned for?");
        int activityDay = scan.nextInt();

        System.out.println("Enter the time of this activity in HH:mm format.");
        String timeInString = scan.next();
        LocalTime activityTime = LocalTime.parse(timeInString);

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
        List<Activity> activities = foundDay.getActivitiesList();

        System.out.println("Day " + foundDay.getDayNumber());
        if (activities.size() != 0) {
            for (Activity a : activities) {
                System.out.println(a.getTime() + " - " + a.getName() + " - $" + a.getCost());
            }
        } else {
            System.out.println("There are no activities planned for Day " + foundDay.getDayNumber());
        }
        mainMenu();
    }

    private void editActivity() {
        System.out.println("What is the name of the activity that would you like to edit?");
        String chosenActivityName = scan.next();
        Activity foundActivity = myPlanner.searchForActivity(chosenActivityName);
        if (foundActivity == null) {
            System.out.println("Sorry there is no activity named " + chosenActivityName);
            mainMenu();
        }

        System.out.println("What do you want to edit? Select from the following:");
        System.out.println("\ta -> edit name");
        System.out.println("\tb -> edit time");
        System.out.println("\tc -> edit cost");
        System.out.println("\te -> exit");
        String editCommand = scan.next();
        chooseEditCommand(foundActivity, editCommand);
    }

    private void chooseEditCommand(Activity chosenActivity, String editCommand) {
        if (editCommand.equals("a")) {
            System.out.println("Enter a new name for this activity.");
            String newName = scan.next();
            chosenActivity.setName(newName);
            System.out.println("Changed to " + newName);
            mainMenu();
        } else if (editCommand.equals("b")) {
            System.out.println("Enter a new time for this activity in HH:mm format.");
            String newTimeString = scan.next();
            LocalTime newTime = LocalTime.parse(newTimeString);
            chosenActivity.setTime(newTime);
            System.out.println("Changed to " + newTime);
            mainMenu();
        } else if (editCommand.equals("c")) {
            System.out.println("Enter the new cost for this activity in XX.XX format.");
            double newCost = scan.nextDouble();
            chosenActivity.setCost(newCost);
            System.out.println("Changed to " + newCost);
            mainMenu();
        } else if (editCommand.equals("e")) {
            mainMenu();
        }
    }
}