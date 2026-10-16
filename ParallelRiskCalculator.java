import java.util.concurrent.RecursiveTask;

public class ParallelRiskCalculator extends RecursiveTask<Double> {
    private static final int THRESHOLD = 1000;
    private final double[] assets;
    private final int start, end;

    public ParallelRiskCalculator(double[] assets, int start, int end) {
        this.assets = assets;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        if ((end - start) <= THRESHOLD) {
            double sum = 0;
            for (int i = start; i < end; i++) {
                sum += assets[i];
            }
            return sum;
        }

        int mid = start + (end - start) / 2;
        ParallelRiskCalculator left = new ParallelRiskCalculator(assets, start, mid);
        ParallelRiskCalculator right = new ParallelRiskCalculator(assets, mid, end);

        left.fork();
        return right.compute() + left.join();
    }
}