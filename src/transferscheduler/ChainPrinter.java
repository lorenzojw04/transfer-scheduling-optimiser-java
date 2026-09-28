package transferscheduler;

/**
 * Base class for anything that can display a snake chain.
 * Subclasses decide the layout; this class holds the shared tools
 * (the dataset to look missionaries up in, the companion formatter,
 * and string padding).
 */

//holds the two tools every printer needs
public abstract class ChainPrinter {

    protected final MissionaryDataset dataset;
    protected final CompanionFormatter companionFormatter;

    protected ChainPrinter(MissionaryDataset dataset, CompanionFormatter companionFormatter) {
        this.dataset = dataset;
        this.companionFormatter = companionFormatter;
    }

    //subclasses must implement how to actually print
    public abstract void print(SnakeChain chain);

    //Pads a string on the right with spaces so the columns line up
    protected static String padRight(String text, int width) {
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < width) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
