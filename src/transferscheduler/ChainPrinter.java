package transferscheduler;

/**
 * Base class for anything that can display a snake chain.
 * Subclasses decide the layout; this class holds the shared tools
 * (the dataset to look missionaries up in, the companion formatter,
 * and string padding).
 */
public abstract class ChainPrinter {

    protected final MissionaryDataset dataset;
    protected final CompanionFormatter companionFormatter;

    protected ChainPrinter(MissionaryDataset dataset, CompanionFormatter companionFormatter) {
        this.dataset = dataset;
        this.companionFormatter = companionFormatter;
    }

    public abstract void print(SnakeChain chain);

    /** Python: str.ljust(width) */
    protected static String padRight(String text, int width) {
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < width) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
