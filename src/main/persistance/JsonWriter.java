package persistance;

import org.json.JSONObject;
import model.TravelPlanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// CITE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of travel planner to file
public class JsonWriter {

    private static final int TAB = 4;
    private String fileDestination; // The name of the file to store JSON representations
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileDestination));
    }

    // MODIFIES: this
    // EFFECTS: creates JSON representation of TravelPlanner to file
    public void write(TravelPlanner tr) {
        JSONObject json = tr.toJson(); // create a JSONObject that represents a TravelPlanner
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}
