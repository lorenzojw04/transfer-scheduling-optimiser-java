package transferscheduler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Java equivalent of: environment = pd.read_csv("test_environment.csv") from the python project
 *
 * Mimics the parts of pandas behaviour the project relies on:
 *  - columns are found by header name (not by position)
 *  - "NaN" / blank cells become missing (null)
 *  - TRUE/FALSE (any capitalisation) become booleans
 * This was done just to make the carry over from python seamless
 */
public class CsvDataSource implements DataSource {

    /** The strings pandas treats as "missing" by default. */
    private static final Set<String> MISSING_VALUES = Set.of(
            "", "#N/A", "#N/A N/A", "#NA", "-1.#IND", "-1.#QNAN", "-NaN", "-nan",
            "1.#IND", "1.#QNAN", "<NA>", "N/A", "NA", "NULL", "NaN", "None",
            "n/a", "nan", "null");

            //store the path to the CSV file
    private final Path path;

    public CsvDataSource(Path path) {
        this.path = path;
    }


    @Override
    public List<Missionary> load() throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            throw new IOException("CSV file is empty: " + path);
        }

        // Map header names -> column index (admin stuff)
        List<String> header = splitLine(lines.get(0));
        Map<String, Integer> column = new HashMap<>();
        for (int i = 0; i < header.size(); i++) {
            column.put(header.get(i).trim(), i);
        }

        //loop over every data row here and split each row into cells
        List<Missionary> missionaries = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) continue;   // pandas skips blank lines too
            List<String> cells = splitLine(lines.get(i));

            //create a missionary object with necessary conversions
            missionaries.add(new Missionary(
                    text(cells, column, "missionary_name"),
                    text(cells, column, "companion_1_name"),
                    text(cells, column, "companion_2_name"),
                    text(cells, column, "current_area"),
                    text(cells, column, "next_area"),
                    bool(cells, column, "going_home"),
                    bool(cells, column, "new_missionary"),
                    bool(cells, column, "is_male")));
        }
        return missionaries;
    }

    /** Returns the cell as text, or null if it is a missing value. */
    private String text(List<String> cells, Map<String, Integer> column, String name) {
        Integer index = column.get(name);
        if (index == null) {
            throw new IllegalArgumentException("Missing column in CSV: " + name);
        }
        String value = index < cells.size() ? cells.get(index) : "";
        return MISSING_VALUES.contains(value) ? null : value;
    }

    //same as text, but also converts "true" and "false" into a real boolean
    private boolean bool(List<String> cells, Map<String, Integer> column, String name) {
        String value = text(cells, column, name);
        if (value != null) {
            if (value.equalsIgnoreCase("true"))  return true;
            if (value.equalsIgnoreCase("false")) return false;
        }
        throw new IllegalArgumentException(
                "Column '" + name + "' must be TRUE or FALSE but was: " + value);
    }

    /** Splits one CSV line on commas, respecting "quoted, fields". */
    private List<String> splitLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');   // escaped quote ""
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields;
    }
}
