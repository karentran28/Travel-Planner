package ui;

import model.Activity;
import model.Day;
import model.TravelPlanner;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class GUI implements ActionListener {

    private TravelPlanner travelPlanner;
    public static final String JSON_FILE = "./data/travelplanner.json";

    JFrame mainFrame;
    JLabel imageLabel;

    JPanel buttonPanel;
    JPanel mainPanel;
    JPanel topPanel;
    JPanel summaryPanel;
    JPanel editingPanel;
    JPanel holdDayPanel;

    JButton loadPlannerButton;
    JButton savePlannerButton;
    JButton newActivityButton;
    JButton submitButton;

    JTextField nameField;
    JTextField dayField;
    JTextField budgetField;
    JTextField timeField;

    public GUI() {
        mainFrame = new JFrame(); //creates the frame
        mainFrame.setTitle("Travel Planner");
        mainFrame.setSize(1000, 800); // opens frame to size
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits program
        mainFrame.getContentPane().setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(231, 212, 231));
        mainFrame.add(mainPanel);

        setTopPanel();
        setButtonPanel();

        holdDayPanel = new JPanel(new FlowLayout());
        JScrollPane scrollPane = new JScrollPane(holdDayPanel); // Add scrolling capability
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainFrame.setVisible(true); // makes the frame visible by setting boolean to true
    }

    public void setTopPanel() {
        topPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        summaryPanel = new JPanel(new GridBagLayout());
        topPanel.add(summaryPanel);

        editingPanel = new JPanel();
        editingPanel.setLayout(new BorderLayout());
        editingPanel.setBackground(Color.yellow);

        ImageIcon image = new ImageIcon(getClass().getResource("airplanelogo.jpg"));
        Image adjustImage = image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon adjustedImage = new ImageIcon(adjustImage);
        imageLabel = new JLabel(adjustedImage);

        editingPanel.add(imageLabel, BorderLayout.CENTER);
        topPanel.add(editingPanel);
    }

    public void setButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(219, 168, 234));
        addLoadButton();
        addSaveButton();
        mainFrame.add(buttonPanel, BorderLayout.WEST);
    }

    public void addLoadButton() {
        loadPlannerButton = new JButton("Load Planner");
        loadPlannerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadPlannerButton.setPreferredSize(new Dimension(150, 50));
        loadPlannerButton.setFocusable(false);
        loadPlannerButton.setBackground(new Color(210, 218, 245));
        loadPlannerButton.addActionListener(this);

        buttonPanel.add(loadPlannerButton);
    }

    public void addSaveButton() {
        savePlannerButton = new JButton("Save Planner");
        savePlannerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        savePlannerButton.setPreferredSize(new Dimension(150, 50));
        savePlannerButton.setFocusable(false);
        savePlannerButton.setBackground(new Color(210, 218, 245));
        savePlannerButton.addActionListener(this);

        buttonPanel.add(savePlannerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (e.getSource() == loadPlannerButton) {
            handleLoadPlannerButton();
        } else if (actionCommand.startsWith("Day")) {
            choosePanel(actionCommand);
        } else if (e.getSource() == savePlannerButton) {
            handleSavePlannerButton();
        }
    }

    public void handleSavePlannerButton() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_FILE);
            jsonWriter.open();
            jsonWriter.write(travelPlanner);
            jsonWriter.close();
            System.out.println("Saved " + travelPlanner.getName() + " to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println(JSON_FILE + " not found. Unable to write to file.");
        }
    }

    public void createActivity() {
        int day = Integer.parseInt(dayField.getText());
        double cost = Double.parseDouble(budgetField.getText());
        LocalTime time = LocalTime.parse(timeField.getText());

        Activity activity = new Activity(nameField.getText(), day, time, cost);
        Day returnDay = travelPlanner.findDay(day);
        returnDay.addActivity(activity);
        addToPanel(activity);
    }

    public void addToPanel(Activity activity) {
        Component[] components = holdDayPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                System.out.println(panel.getName());
                if (panel.getName() != null && panel.getName().equals("Day " + dayField.getText())) {
                    JLabel label = new JLabel(activity.getName() + " - $" + activity.getCost());
                    addLabel(label, panel, activity);
                    panel.repaint();
                    panel.revalidate();
                }
            }
        }
    }

    public void choosePanel(String day) {
        boolean showPanel = false;
        Component[] components = holdDayPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                if (panel.getName() != null && panel.getName().equals("Day " + day.substring(4))) {
                    panel.setVisible(true);
                    showPanel = true;
                    holdDayPanel.revalidate();
                    holdDayPanel.repaint();
                } else {
                    panel.setVisible(false);
                }
            }
        }
    }

    public void handleLoadPlannerButton() {
        mainPanel.remove(imageLabel);
        loadExistingPlanner();
        createDayButtons();
        addNewActivityButton();
        loadPlannerButton.setEnabled(false); //this makes the button clickable once
    }

    public void addNewActivityButton() {
        newActivityButton = new JButton("Add Activity");
        buttonPanel.add(newActivityButton);
        newActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpActivityMaker();
            }
        });
    }

    public void popUpActivityMaker() {
        JDialog activityMaker = new JDialog();
        activityMaker.setLayout(new BoxLayout(activityMaker.getContentPane(), BoxLayout.Y_AXIS));
        activityMaker.setSize(300, 300);
        activityMaker.setResizable(false);

        nameField = new JTextField(20);
        timeField = new JTextField(20);
        dayField = new JTextField(20);
        budgetField = new JTextField(20);

        activityMaker.add(new JLabel("Name"));
        activityMaker.add(nameField);
        activityMaker.add(new JLabel("Day"));
        activityMaker.add(dayField);
        activityMaker.add(new JLabel("Time"));
        activityMaker.add(timeField);
        activityMaker.add(new JLabel("Cost"));
        activityMaker.add(budgetField);

        submitButton = new JButton("Submit");
        activityMaker.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createActivity();
            }
        });

        activityMaker.setVisible(true);
    }

    //EVERYTHING BELOW THIS IS CREATING THE LABELS FOR THE EXISTING PLANNER (NOT INC ACTIVITIES)
    public void loadExistingPlanner() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_FILE);
            travelPlanner = jsonReader.read();
            GridBagConstraints gbc = new GridBagConstraints();
            makeTitleLabel(gbc);
            makeTotalDaysLabel(gbc);
            makeBudgetLabel(gbc);
            makeFlightsLabel(gbc);

            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDayButtons() {
        int totalDays = travelPlanner.getTotalDays();
        for (int i = 1; i <= totalDays; i++) {
            JButton button = new JButton("Day " + i);
            button.setActionCommand("Day " + i);
            button.addActionListener(this);
            buttonPanel.add(button);

            JPanel dayPanel = createDayPanel();
            dayPanel.setName("Day " + i);
            createActivityPanel(i, dayPanel);
            holdDayPanel.add(dayPanel);
            dayPanel.setVisible(false);
        }
    }

    public void createActivityPanel(int day, JPanel dayPanel) {
        ArrayList<Activity> activities = travelPlanner.findDay(day).getActivitiesList();
        for (Activity a : activities) {
            JLabel label = new JLabel(a.getName() + " - $" + a.getCost());
            addLabel(label, dayPanel, a);
        }
    }

    public void addLabel(JLabel label, JPanel dayPanel, Activity activity) {
        Component[] components = dayPanel.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel) {
                JLabel existingLabel = (JLabel) c;
                String time = existingLabel.getText();
                if (activity.getTime().toString().equals(time)) {
                    existingLabel.setText(time + " - " + label.getText());
                }
            }
        }
    }

    public JPanel createDayPanel() {
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        int maxTime = 24;
        for (int i = 1; i <= maxTime; i++) {
            JLabel timeLabel = new JLabel();
            if (i <= 9) {
                timeLabel.setText("0" + i + ":00");
            } else {
                timeLabel.setText(i + ":00");
            }
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            dayPanel.add(timeLabel);
            dayPanel.add(Box.createVerticalStrut(5));
        }
        return dayPanel;
    }

    public void makeTitleLabel(GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("Travel Planner For: " + travelPlanner.getName());
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(136, 217, 130));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        summaryPanel.add(titleLabel, gbc);
    }

    public void makeTotalDaysLabel(GridBagConstraints gbc) {
        JLabel totalDaysLabel = new JLabel("Travelling For: " + travelPlanner.getTotalDays());
        totalDaysLabel.setOpaque(true);
        totalDaysLabel.setBackground(new Color(143, 175, 239));
        totalDaysLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        summaryPanel.add(totalDaysLabel, gbc);
    }

    public void makeBudgetLabel(GridBagConstraints gbc) {
        JLabel budgetLabel = new JLabel("Budget: " + travelPlanner.getBudget());
        budgetLabel.setOpaque(true);
        budgetLabel.setBackground(new Color(143, 175, 239));
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        summaryPanel.add(budgetLabel, gbc);
    }

    public void makeFlightsLabel(GridBagConstraints gbc) {
        if (travelPlanner.getDepartingFlight() != null && travelPlanner.getReturningFlight() != null) {
            JLabel departingFlightLabel = new JLabel("Departing on: " + travelPlanner.getDepartingFlight());
            departingFlightLabel.setOpaque(true);
            departingFlightLabel.setBackground(new Color(136, 217, 130));
            departingFlightLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 0;
            gbc.gridy = 2;
            summaryPanel.add(departingFlightLabel, gbc);

            JLabel returningFlightLabel = new JLabel("Returning on: " + travelPlanner.getReturningFlight());
            returningFlightLabel.setOpaque(true);
            returningFlightLabel.setBackground(new Color(136, 217, 130));
            returningFlightLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 1;
            summaryPanel.add(returningFlightLabel, gbc);
        }
    }
}
