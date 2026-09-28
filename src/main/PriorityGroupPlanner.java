package transferscheduler.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transferscheduler.model.GroupResult;
import transferscheduler.model.Missionary;
import transferscheduler.model.MissionaryDataset;
import transferscheduler.model.SnakeChain;
import transferscheduler.model.TransferPlan;

/**
 * Python: the logic of process_missionaries(df, label), minus the printing.
 *
 * Goes through the priority groups in the order given. For every missionary
 * not yet in a chain, it asks the ChainTracer to build one. Both the tracer
 * and the groups are passed in, so either can be swapped without editing this class.
 */
public class PriorityGroupPlanner implements TransferPlanner {

    private final ChainTracer tracer;
    private final List<PriorityGroup> priorityGroups;

    public PriorityGroupPlanner(ChainTracer tracer, List<PriorityGroup> priorityGroups) {
        this.tracer = tracer;
        this.priorityGroups = List.copyOf(priorityGroups);
    }

    @Override
    public TransferPlan plan(MissionaryDataset dataset, String label) {
        Set<String> allAssigned = new HashSet<>();
        List<GroupResult> results = new ArrayList<>();

        for (PriorityGroup group : priorityGroups) {
            List<Missionary> members = group.select(dataset);
            List<SnakeChain> chains = new ArrayList<>();

            for (Missionary member : members) {
                if (!allAssigned.contains(member.getName())) {
                    SnakeChain chain = tracer.trace(dataset, member);
                    allAssigned.addAll(chain.getNames());
                    chains.add(chain);
                }
            }
            results.add(new GroupResult(group.getLabel(), group.getShortLabel(),
                    members.size(), chains));
        }

        return new TransferPlan(label, results, allAssigned.size(), dataset.size());
    }
}
