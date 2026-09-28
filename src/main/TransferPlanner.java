package transferscheduler.algorithm;

import transferscheduler.model.MissionaryDataset;
import transferscheduler.model.TransferPlan;

/**
 * Strategy for planning a whole dataset. It only RETURNS a TransferPlan;
 * it never prints. Implement this to plug in a completely different algorithm.
 */
public interface TransferPlanner {
    TransferPlan plan(MissionaryDataset dataset, String label);
}
