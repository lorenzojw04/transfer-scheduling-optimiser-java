package transferscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A collection of missionaries with the queries the algorithm needs.
 * This is the Java stand-in for the pandas DataFrame: row order is preserved,
 * and every query returns rows in that original order.
 */
public class MissionaryDataset {

    private final List<Missionary> missionaries;

    public MissionaryDataset(List<Missionary> missionaries) {
        this.missionaries = Collections.unmodifiableList(new ArrayList<>(missionaries));
    }

    public int size() {
        return missionaries.size();
    }

    /** Python: environment[environment["is_male"] == True / False] */
    //splitting it into male and female here
    public MissionaryDataset filterByGender(boolean male) {
        return new MissionaryDataset(filter(m -> m.isMale() == male));
    }

    // Every missionary matching the condition, in original row order.
    //keeps original order
    public List<Missionary> filter(Predicate<Missionary> condition) {
        List<Missionary> result = new ArrayList<>();
        for (Missionary m : missionaries) {
            if (condition.test(m)) result.add(m);
        }
        return result;
    }

    /** Python: df[df["missionary_name"] == name] then .iloc[0] (first match only). */
    //finds the first missionary with given name (or empty)
    public Optional<Missionary> findByName(String name) {
        for (Missionary m : missionaries) {
            if (m.getName() != null && m.getName().equals(name)) return Optional.of(m);
        }
        return Optional.empty();
    }

    /**
     * Python: df[(df["current_area"] == area) & (df["missionary_name"] != excludedName)]
     * Finds everyone currently living in a given area, exluding one specific person. 
     */
    public List<Missionary> findResidents(String area, String excludedName) {
        return filter(m -> m.getCurrentArea() != null
                && m.getCurrentArea().equals(area)
                && !m.getName().equals(excludedName));
    }
}
