package transferscheduler;

import java.util.List;

/**
 * Python: get_display_companion(row)
 *
 * If new missionary, their companion was OFFICE.
 * If going home, their companion will be OFFICE.
 * Otherwise the listed companions joined with ", " (or "None").
 */
public class CompanionFormatter {

    public String getDisplayCompanion(Missionary missionary) {
        if (missionary.isNewMissionary()) {
            return "OFFICE";
        } else if (missionary.isGoingHome()) {
            return "OFFICE";
        } else {
            List<String> companions = missionary.getCompanions();
            return companions.isEmpty() ? "None" : String.join(", ", companions);
        }
    }
}
