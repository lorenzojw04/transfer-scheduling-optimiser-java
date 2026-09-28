package transferscheduler.algorithm;

import transferscheduler.model.Missionary;
import transferscheduler.model.MissionaryDataset;
import transferscheduler.model.SnakeChain;

/**
 * Strategy for building ONE chain from a starting missionary.
 * Implement this to try a different way of tracing chains
 * (e.g. one that also looks at train timetables).
 */
public interface ChainTracer {
    SnakeChain trace(MissionaryDataset dataset, Missionary start);
}
