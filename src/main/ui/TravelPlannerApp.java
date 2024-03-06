package ui;

import model.TravelPlanner;
import model.Activity;
import model.Day;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;

// Represents the Travel Planner application
public class TravelPlannerApp {

    Scanner scan;
    TravelPlanner myPlanner;
    private String isFlying;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_FILE = "./data/travelplanner.json";

    // EFFECTS: runs the travel planner application
    public TravelPlannerApp() throws FileNotFoundException {
        scan = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);
        starter();
    }

    // EFFECTS: processes user input to create a new travel planner or load existing travel planner
    public void starter() {
        String command;
        createOrLoadMenu();
        command = scan.nextLine();

        if (command.equals("l")) {
            loadPlanner();
        } else if (command.equals("n")) {
            createPlanner();
            summarizePlanner();
        } else if (!command.equals("q")) {
            System.out.println("You have entered an input that is not an option.");
            starter();
        }
        mainMenu();
    }

    public void createOrLoadMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("\tl -> load existing travel planner from file");
        System.out.println("\tn -> create new travel planner");
        System.out.println("\tq -> quit");
    }

    // CITE: https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user input
    public void mainMenu() {
        boolean keepRunning = true;
        String command;

        while (keepRunning) {
            displayMenu();
            command = scan.nextLine();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Safe Travels! Goodbye!");
    }

    // EFFECTS: displays the options users have
    private void displayMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("\ta -> add new activity");
        System.out.println("\tb -> view existing activities");
        System.out.println("\tc -> edit existing activity");
        System.out.println("\td -> save planner to file");
        System.out.println("\tq -> quit");
    }

    // CITE: https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addActivity();
        } else if (command.equals("b")) {
            viewActivities();
        } else if (command.equals("c")) {
            editActivity();
        } else if (command.equals("d")) {
            savePlanner();
        } else {
            System.out.println("You have entered an input that is not an option.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes a TravelPlanner, generates a list of Days, sets departing
    //          and returning flights based on user input
    private void createPlanner() {
        System.out.println("Where are you travelling to?");
        String plannerName = scan.nextLine();

        System.out.println("How many days are you going for?");
        int totalDays = scan.nextInt();
        scan.nextLine();

        System.out.println("What is your estimated budget? Enter in format X.XX");
        double tripBudget = scan.nextDouble();
        scan.nextLine();

        myPlanner = new TravelPlanner(plannerName, totalDays, tripBudget);

        myPlanner.generateDaysList(myPlanner.getTotalDays());

        System.out.println("Are you flying to " + plannerName + "? Please type 'yes' or 'no'.");
        isFlying = scan.nextLine();
        addFlights();
    }

    // EFFECTS: provides a brief summary of Travel Planner created
    public void summarizePlanner() {
        System.out.println("Creating planner for " + myPlanner.getName());
        System.out.println("\tTotal Days on Travel: " + myPlanner.getTotalDays());
        System.out.println("\tEstimated Budget: $" + myPlanner.getBudget());
        if (myPlanner.getDepartingFlight() != null) {
            System.out.println("\tFlying on: " + myPlanner.getDepartingFlight());
            System.out.println("\tReturning on: " + myPlanner.getReturningFlight());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds date of departing flight and date of returning flight based on user input
    public void addFlights() {
        if (isFlying.equals("yes")) {
            System.out.println("What day are you flying? Enter date in YYYY-MM-DD format.");
            String departingFlightString = scan.nextLine();
            LocalDate departingFlightDate = LocalDate.parse(departingFlightString);
            myPlanner.setDepartingFlight(departingFlightDate);

            System.out.println("What day are you flying home? Enter in YYYY-MM-DD format.");
            String returningFlightString = scan.nextLine();
            LocalDate returningFlightDate = LocalDate.parse(returningFlightString);
            myPlanner.setReturningFlight(returningFlightDate);
        } else if (!isFlying.equals("no")) {
            System.out.println("Sorry " + isFlying + " is not an option. Please type 'yes' or 'no'.");
            isFlying = scan.nextLine();
            addFlights();
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes an Activity and adds it to given Day based on user input
    private void addActivity() {
        System.out.println("Please type the name of the activity you want to add.");
        String activityName = scan.nextLine();

        System.out.println("Which day is this activity planned for?");
        int activityDay = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter the time of this activity in HH:mm format.");
        String timeInString = scan.nextLine();
        LocalTime activityTime = LocalTime.parse(timeInString);

        System.out.println("What is the estimated cost of the activity?");
        double activityCost = scan.nextDouble();
        scan.nextLine();

        Activity newActivity = new Activity(activityName, activityDay, activityTime, activityCost);
        Day foundDay = myPlanner.findDay(activityDay);
        foundDay.addActivity(newActivity);

        System.out.println("Added " + activityName + " to Day " + activityDay + "!");
    }

    // displays the list of activities in given day prompted by user
    private void viewActivities() {
        System.out.println("Enter the day number you would like to view existing activities for.");
        int dayNumber = scan.nextInt();
        scan.nextLine();
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
    }

    // EFFECTS: Searches through list to find existing activity to edit based on user input, displays options
    //          for users to edit
    private void editActivity() {
        System.out.println("What is the name of the activity that would you like to edit?");
        String chosenActivityName = scan.nextLine();
        Activity foundActivity = myPlanner.searchForActivity(chosenActivityName);
        if (foundActivity != null) {
            System.out.println("What do you want to edit? Select from the following:");
            System.out.println("\ta -> edit name");
            System.out.println("\tb -> edit time");
            System.out.println("\tc -> edit cost");
            System.out.println("\te -> exit");
            String editCommand = scan.nextLine();
            chooseEditCommand(foundActivity, editCommand);
        } else {
            System.out.println("Sorry there is no activity named " + chosenActivityName);
        }
    }

    // MODIFIES: this
    // EFFECTS: executes the editing of existing activity based on user command
    private void chooseEditCommand(Activity chosenActivity, String editCommand) {
        if (editCommand.equals("a")) {
            System.out.println("Enter a new name for this activity.");
            String newName = scan.nextLine();
            chosenActivity.setName(newName);
            System.out.println("Changed to " + newName);
        } else if (editCommand.equals("b")) {
            System.out.println("Enter a new time for this activity in HH:mm format.");
            String newTimeString = scan.nextLine();
            LocalTime newTime = LocalTime.parse(newTimeString);
            chosenActivity.setTime(newTime);
            System.out.println("Changed to " + newTime);
        } else if (editCommand.equals("c")) {
            System.out.println("Enter the new cost for this activity in XX.XX format.");
            double newCost = scan.nextDouble();
            scan.nextLine();
            chosenActivity.setCost(newCost);
            System.out.println("Changed to " + newCost);
        } else if (!editCommand.equals("e")) {
            System.out.println("You have entered an input that is not an option.");
        }
    }

    // CITE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads travel planner from file
    private void loadPlanner() {
        try {
            myPlanner = jsonReader.read();
            System.out.println("Loaded " + myPlanner.getName() + " from " + JSON_FILE);
            summarizePlanner();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE);
        }
    }

    // CITE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: save travel planner to file
    private void savePlanner() {
        try {
            jsonWriter.open();
            jsonWriter.write(myPlanner);
            jsonWriter.close();
            System.out.println("Saved " + myPlanner.getName() + " to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println(JSON_FILE + " not found. Unable to write to file.");
        }
    }
}
