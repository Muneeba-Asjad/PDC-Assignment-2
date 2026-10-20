import java.util.concurrent.*;

public class ParallelMaxSearch extends SensorSearchEngine {
    public ParallelMaxSearch(double[] data, int start, int end) {
        super(data, start, end);
    }

    @Override
    public double processGrid() {
        int size = endIdx - startIdx;
        if (size == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        int workers = Math.min(4, size);
        ExecutorService pool = Executors.newFixedThreadPool(workers);
        try {
            Future<Double>[] results = new Future[workers];
            int chunk = (size + workers - 1) / workers;
            for (int i = 0; i < workers; i++) {
                final int start = startIdx + i * chunk;
                final int end = Math.min(start + chunk, endIdx);
                results[i] = pool.submit(() -> {
                    double max = Double.NEGATIVE_INFINITY;
                    for (int j = start; j < end; j++) {
                        max = Math.max(max, sensorData[j]);
                    }
                    return max;
                });
            }
            double globalMax = Double.NEGATIVE_INFINITY;
            for (Future<Double> result : results) {
                globalMax = Math.max(globalMax, result.get());
            }
            return globalMax;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }

}
