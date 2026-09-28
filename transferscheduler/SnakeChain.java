package transferscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The result of tracing one snake: the ordered chain of names and the set
 * of visited names. (Python returned these as a tuple: chain, visited.)
 */
public class SnakeChain {

    private final List<String> chain;
    private final Set<String> visited;

    public SnakeChain(List<String> chain, Set<String> visited) {
        this.chain = Collections.unmodifiableList(new ArrayList<>(chain));
        this.visited = Collections.unmodifiableSet(new LinkedHashSet<>(visited));
    }

    public List<String> getChain()  { return chain; }
    public Set<String> getVisited() { return visited; }
    public int length()             { return chain.size(); }
}
