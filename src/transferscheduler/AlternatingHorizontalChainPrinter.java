package transferscheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Prints the chain horizontally with alternating arrows between the top row
 * (missionaries) and bottom row (companions/Office) to reflect travel paths.
 * This here is a direct translation from the python implementation!
 */
public class AlternatingHorizontalChainPrinter extends ChainPrinter {

    private static final String DASH_ARROW = "  --->  ";
    private static final String SPACE_GAP = " ".repeat(DASH_ARROW.length());

    public AlternatingHorizontalChainPrinter(MissionaryDataset dataset,
                                             CompanionFormatter companionFormatter) {
        super(dataset, companionFormatter);
    }

    @Override
    public void print(SnakeChain snake) {
        List<String> chain = snake.getChain();
        List<String> tops = new ArrayList<>();
        List<String> bots = new ArrayList<>();

        for (String name : chain) {
            Missionary row = dataset.findByName(name).orElseThrow(
                    () -> new IllegalStateException("Unknown missionary in chain: " + name));
            tops.add(name);
            bots.add(companionFormatter.getDisplayCompanion(row));
        }

        // Each column is as wide as the longer of its two strings
        List<Integer> widths = new ArrayList<>();
        for (int i = 0; i < chain.size(); i++) {
            widths.add(Math.max(tops.get(i).length(), bots.get(i).length()));
        }

        StringBuilder topLine = new StringBuilder();
        StringBuilder botLine = new StringBuilder();

        System.out.println("\n --- SNAKE CHAIN ---");

        for (int i = 0; i < chain.size(); i++) {
            topLine.append(padRight(tops.get(i), widths.get(i)));
            botLine.append(padRight(bots.get(i), widths.get(i)));

            if (i < chain.size() - 1) {
                if (i % 2 == 0) {
                    topLine.append(DASH_ARROW);
                    botLine.append(SPACE_GAP);
                } else {
                    topLine.append(SPACE_GAP);
                    botLine.append(DASH_ARROW);
                }
            }
        }

        System.out.println(topLine);
        System.out.println(botLine);
    }
}
