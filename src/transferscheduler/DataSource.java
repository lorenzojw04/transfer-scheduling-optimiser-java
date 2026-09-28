package transferscheduler;

import java.io.IOException;
import java.util.List;

/**
 * Anything that can supply missionaries to the program.
 * Right now there is only a CSV implementation, but a database or a
 * spreadsheet loader could implement this later without touching the algorithm!
 */
public interface DataSource {
    List<Missionary> load() throws IOException;
}
