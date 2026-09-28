package transferscheduler.output;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import transferscheduler.model.GroupResult;
import transferscheduler.model.SnakeChain;
import transferscheduler.model.TransferPlan;

/**
 * Prints a TransferPlan to a text stream (System.out by default in Main).
 * This holds all the "PROCESSING...", "Found...", "--- Group ---" and "Total..."
 * messages that used to live inside process_missionaries().
 */
public class ConsolePlanReporter implements PlanReporter {

    private final PrintStream out;
    private final ChainRenderer chainRenderer;

    public ConsolePlanReporter(PrintStream out, ChainRenderer chainRenderer) {
        this.out = out;
        this.chainRenderer = chainRenderer;
    }

    @Override
    public void report(TransferPlan plan) {
        out.println("PROCESSING: " + plan.getLabel());
        out.println("Found " + describeGroups(plan) + ".");

        for (GroupResult group : plan.getGroupResults()) {
            if (group.getMemberCount() == 0) {
                continue;
            }
            out.println("\n--- Group: " + group.getLabel() + " ---");

            for (SnakeChain chain : group.getChains()) {
                out.println("\n --- SNAKE CHAIN ---");
                out.println(chainRenderer.render(chain));
            }
        }

        out.println("\nTotal " + plan.getLabel().toLowerCase() + " accounted for: "
                + plan.getAssignedCount() + " / " + plan.getTotalMissionaries());
    }

    /** e.g. "2 new, 2 going home, and 6 remaining" */
    private String describeGroups(TransferPlan plan) {
        List<String> parts = new ArrayList<>();
        for (GroupResult g : plan.getGroupResults()) {
            parts.add(g.getMemberCount() + " " + g.getShortLabel());
        }
        int n = parts.size();
        if (n == 0) return "no groups";
        if (n == 1) return parts.get(0);
        if (n == 2) return parts.get(0) + " and " + parts.get(1);
        return String.join(", ", parts.subList(0, n - 1)) + ", and " + parts.get(n - 1);
    }
}
