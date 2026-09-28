package transferscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One row of the test environment (one missionary).
 * Replaces a single row of the pandas DataFrame in the Python version.
 *
 * Companion fields are null when the CSV value was NaN/blank.
 */
public class Missionary {

    private final String name;
    private final String companion1;
    private final String companion2;
    private final String currentArea;
    private final String nextArea;
    private final boolean goingHome;
    private final boolean newMissionary;
    private final boolean male;

    public Missionary(String name, String companion1, String companion2,
                      String currentArea, String nextArea,
                      boolean goingHome, boolean newMissionary, boolean male) {
        this.name = name;
        this.companion1 = companion1;
        this.companion2 = companion2;
        this.currentArea = currentArea;
        this.nextArea = nextArea;
        this.goingHome = goingHome;
        this.newMissionary = newMissionary;
        this.male = male;
    }

    public String getName()          { return name; }
    public String getCurrentArea()   { return currentArea; }
    public String getNextArea()      { return nextArea; }
    public boolean isGoingHome()     { return goingHome; }
    public boolean isNewMissionary() { return newMissionary; }
    public boolean isMale()          { return male; }

    /** The non-NaN companions, in order (companion_1 then companion_2). May be empty. */
    public List<String> getCompanions() {
        List<String> companions = new ArrayList<>();
        if (companion1 != null) companions.add(companion1);
        if (companion2 != null) companions.add(companion2);
        return Collections.unmodifiableList(companions);
    }

    @Override
    public String toString() {
        return "Missionary{" + name + ": " + currentArea + " -> " + nextArea + "}";
    }
}
