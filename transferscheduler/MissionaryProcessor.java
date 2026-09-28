package transferscheduler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Python: process_missionaries(df, label)
 *
 * Runs the snake chain generation across the priority groups
 * (new -> going home -> remaining) for one dataset (anziani or sorelle).
 */
public class MissionaryProcessor {

    private final MissionaryDataset dataset;
    private final String label;

    private final NewMissionaryGroup newGroup = new NewMissionaryGroup();
    private final GoingHomeGroup goingHomeGroup = new GoingHomeGroup();
    private final RemainingGroup remainingGroup = new RemainingGroup();

    private final SnakeTracer tracer;
    private final ChainPrinter printer;

    public MissionaryProcessor(MissionaryDataset dataset, String label) {
        this.dataset = dataset;
        this.label = label;
        this.tracer = new SnakeTracer(dataset);
        this.printer = new AlternatingHorizontalChainPrinter(dataset, new CompanionFormatter());
    }

    public void process() {
        System.out.println("PROCESSING: " + label);

        System.out.println("Found " + newGroup.select(dataset).size() + " new, "
                + goingHomeGroup.select(dataset).size() + " going home, and "
                + remainingGroup.select(dataset).size() + " remaining.");

        Set<String> allAssigned = new HashSet<>();

        // Priority order matters: new first, then going home, then everyone else
        List<PriorityGroup> priorityGroups = List.of(newGroup, goingHomeGroup, remainingGroup);

        for (PriorityGroup group : priorityGroups) {
            List<String> names = group.selectNames(dataset);
            if (names.isEmpty()) {
                continue;
            }
            System.out.println("\n--- Group: " + group.getLabel() + " ---");

            for (String name : names) {
                if (!allAssigned.contains(name)) {
                    SnakeChain chain = tracer.trace(name);
                    allAssigned.addAll(chain.getVisited());
                    printer.print(chain);
                }
            }
        }

        System.out.println("\nTotal " + label.toLowerCase() + " accounted for: "
                + allAssigned.size() + " / " + dataset.size());
    }
}
