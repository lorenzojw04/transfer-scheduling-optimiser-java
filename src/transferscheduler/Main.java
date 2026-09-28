package transferscheduler;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Entry point. Snake method for both Anziani and Sorelle.
 *
 * Usage: java transferscheduler.Main [path/to/test_environment.csv]
 * (defaults to "test_environment.csv" in the current directory, like the Python version)
 */
public class Main {

    //if the user passes a filename use that, unless just use the default csv
    public static void main(String[] args) throws IOException {
        Path csvPath = Path.of(args.length > 0 ? args[0] : "test_environment.csv");

        // Load dataset
        MissionaryDataset environment = new MissionaryDataset(new CsvDataSource(csvPath).load());

        // Split into Anziani and Sorelle Subsets
        MissionaryDataset anzianiEnvironment = environment.filterByGender(true);
        MissionaryDataset sorelleEnvironment = environment.filterByGender(false);

        // This part runs it for both!
        new MissionaryProcessor(anzianiEnvironment, "ANZIANI").process();
        new MissionaryProcessor(sorelleEnvironment, "SORELLE").process();
    }
}
