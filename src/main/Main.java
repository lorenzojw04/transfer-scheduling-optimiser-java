package transferscheduler.app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import transferscheduler.algorithm.GoingHomeGroup;
import transferscheduler.algorithm.NewMissionaryGroup;
import transferscheduler.algorithm.PriorityGroupPlanner;
import transferscheduler.algorithm.RemainingGroup;
import transferscheduler.algorithm.SnakeTracer;
import transferscheduler.algorithm.TransferPlanner;
import transferscheduler.data.CsvDataSource;
import transferscheduler.data.DataSource;
import transferscheduler.model.MissionaryDataset;
import transferscheduler.output.AlternatingHorizontalChainRenderer;
import transferscheduler.output.CompanionFormatter;
import transferscheduler.output.ConsolePlanReporter;
import transferscheduler.output.PlanReporter;

/**
 * Entry point and the ONLY place where the pieces are chosen and plugged together.
 * To change the program, change a line here:
 *   - different input?      -> new DataSource implementation
 *   - different algorithm?  -> new TransferPlanner / ChainTracer implementation
 *   - GUI or file output?   -> new PlanReporter implementation
 *
 * Usage: java transferscheduler.app.Main [path/to/test_environment.csv]
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Path csvPath = Path.of(args.length > 0 ? args[0] : "test_environment.csv");

        // Input
        DataSource dataSource = new CsvDataSource(csvPath);

        // Algorithm
        TransferPlanner planner = new PriorityGroupPlanner(
                new SnakeTracer(),
                List.of(new NewMissionaryGroup(), new GoingHomeGroup(), new RemainingGroup()));

        // Output
        PlanReporter reporter = new ConsolePlanReporter(
                System.out,
                new AlternatingHorizontalChainRenderer(new CompanionFormatter()));

        // Load dataset
        MissionaryDataset environment = new MissionaryDataset(dataSource.load());

        // Split into Anziani and Sorelle subsets, plan each, report each
        reporter.report(planner.plan(environment.filterByGender(true), "ANZIANI"));
        reporter.report(planner.plan(environment.filterByGender(false), "SORELLE"));
    }
}
