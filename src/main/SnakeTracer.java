package transferscheduler.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transferscheduler.model.Missionary;
import transferscheduler.model.MissionaryDataset;
import transferscheduler.model.SnakeChain;

/**
 * Python: snake_method(df, start_name)
 *
 * The original "snake method": follow each missionary's destination area to
 * whoever currently lives there, until nobody lives there or the chain loops
 * back to someone already visited.
 */
public class SnakeTracer implements ChainTracer {

    @Override
    public SnakeChain trace(MissionaryDataset dataset, Missionary start) {
        List<Missionary> chain = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Missionary current = start;

        while (current != null && !visited.contains(current.getName())) {
            visited.add(current.getName());
            chain.add(current);

            // Find who is currently in the next_area
            List<Missionary> residents =
                    dataset.findResidents(current.getNextArea(), current.getName());

            current = residents.isEmpty() ? null : residents.get(0);
        }

        return new SnakeChain(chain);
    }
}
