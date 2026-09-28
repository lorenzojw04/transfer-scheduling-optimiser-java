package transferscheduler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Python: snake_method(df, start_name)
 *
 * Traces a snake chain through the dataset starting from a missionary,
 * following destination areas to resident missionaries until the chain ends
 * (nobody lives in the next area, or we loop back to someone already visited).
 */
public class SnakeTracer {

    private final MissionaryDataset dataset;

    public SnakeTracer(MissionaryDataset dataset) {
        this.dataset = dataset;
    }

    public SnakeChain trace(String startName) {
        String currentName = startName;
        List<String> chain = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();

        // The main loop, keep going as long as there is a new name that has not been seen before
        while (currentName != null && !currentName.isEmpty() && !visited.contains(currentName)) {
            visited.add(currentName);
            chain.add(currentName);

            // Get the row for the current missionary
            Optional<Missionary> match = dataset.findByName(currentName);
            if (match.isEmpty()) {
                break;
            }
            Missionary missionary = match.get();

            // Find who is currently in the next_area
            List<Missionary> residents =
                    dataset.findResidents(missionary.getNextArea(), currentName);

            //if no one is there, the chain ends! Otherwise take the first resident as the next person in the snake
            if (residents.isEmpty()) {
                break;
            }
            currentName = residents.get(0).getName();
        }

        //done!
        return new SnakeChain(chain, visited);
    }
}
