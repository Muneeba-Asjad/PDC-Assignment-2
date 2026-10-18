import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        double[] assets = new double[10000];
        for (int i = 0; i < assets.length; i++) {
            assets[i] = 100 + (i % 100);
        }
        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(assets, 0, assets.length);
        double totalRisk = pool.invoke(riskTask);
        PortfolioThresholdTask stressTask = new PortfolioThresholdTask(
                assets, 0, assets.length, 150);
        pool.invoke(stressTask);
        long endTime = System.nanoTime();
        System.out.println("Total Risk: " + totalRisk);
        System.out.println("Risk Cap: 150");
        System.out.println("Stress Test: Completed");
        System.out.println("Processing Time: "
                + (endTime - startTime) / 1_000_000.0 + " ms");
        pool.shutdown();
    }
}
