package transferscheduler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The complete result of planning one dataset (e.g. all the Anziani).
 * This is pure data with no printing, so a console reporter, a GUI,
 * a file exporter or a test can all consume it.
 */
public class TransferPlan {

    private final String label;
    private final List<GroupResult> groupResults;
    private final int assignedCount;
    private final int totalMissionaries;

    public TransferPlan(String label, List<GroupResult> groupResults,
                        int assignedCount, int totalMissionaries) {
        this.label = label;
        this.groupResults = Collections.unmodifiableList(new ArrayList<>(groupResults));
        this.assignedCount = assignedCount;
        this.totalMissionaries = totalMissionaries;
    }

    public String getLabel()                  { return label; }
    public List<GroupResult> getGroupResults() { return groupResults; }
    /** How many distinct missionaries ended up in some chain. */
    public int getAssignedCount()             { return assignedCount; }
    public int getTotalMissionaries()         { return totalMissionaries; }
}
