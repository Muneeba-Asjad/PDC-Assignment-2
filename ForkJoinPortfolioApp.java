import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double[] assets = new double[size];
        Random rand = new Random(42);

        for (int i = 0; i < size; i++) {
            assets[i] = 100 + rand.nextDouble() * 900;
        }

        ForkJoinPool pool = new ForkJoinPool();
        double riskCap = 500.0;
        long startTime = System.nanoTime();

        ParallelRiskCalculator initialRiskTask = new ParallelRiskCalculator(assets, 0, size);
        double initialTotalRisk = pool.invoke(initialRiskTask);

        PortfolioThresholdTask stressTask = new PortfolioThresholdTask(assets, 0, size, riskCap);
        pool.invoke(stressTask);

        ParallelRiskCalculator updatedRiskTask = new ParallelRiskCalculator(assets, 0, size);
        double updatedTotalRisk = pool.invoke(updatedRiskTask);

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("Initial Total Risk Score: $%.2f%n", initialTotalRisk);
        System.out.printf("Post Stress-Test Risk Score: $%.2f%n", updatedTotalRisk);
        System.out.printf("Parallel Execution Time: %.3f ms%n", durationMs);

        pool.close();
    }
}