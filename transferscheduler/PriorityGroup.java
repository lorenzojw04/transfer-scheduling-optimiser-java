package transferscheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * A priority group of missionaries (e.g. "New Missionaries").
 * Subclasses only say who belongs in the group via matches().
 */
public abstract class PriorityGroup {

    private final String label;

    protected PriorityGroup(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** Does this missionary belong in this group? */
    public abstract boolean matches(Missionary missionary);

    /** The missionaries of the dataset that belong in this group, in row order. */
    public List<Missionary> select(MissionaryDataset dataset) {
        return dataset.filter(this::matches);
    }

    /** Python: group["missionary_name"].tolist() */
    public List<String> selectNames(MissionaryDataset dataset) {
        List<String> names = new ArrayList<>();
        for (Missionary m : select(dataset)) {
            names.add(m.getName());
        }
        return names;
    }
}
