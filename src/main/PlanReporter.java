package transferscheduler.output;

import transferscheduler.model.TransferPlan;

/**
 * Anything that can present a finished TransferPlan.
 * The console version is ConsolePlanReporter; a Swing/JavaFX window,
 * an HTML page or a spreadsheet exporter would just be more implementations.
 */
public interface PlanReporter {
    void report(TransferPlan plan);
}
