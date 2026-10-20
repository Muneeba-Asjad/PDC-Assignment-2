import java.util.concurrent.*;

public class ParallelMinSearch extends SensorSearchEngine {
    public ParallelMinSearch(double[] data, int start, int end) {

        super(data, start, end);
    }

    @Override
    public double processGrid() {
        int size = endIdx - startIdx;
        if (size == 0) {
            return Double.POSITIVE_INFINITY;
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
                    double min = Double.POSITIVE_INFINITY;
                    for (int j = start; j < end; j++) {
                        min = Math.min(min, sensorData[j]);
                    }
                    return min;
                });
            }
            double globalMin = Double.POSITIVE_INFINITY;
            for (Future<Double> result : results) {
                globalMin = Math.min(globalMin, result.get());
            }
            return globalMin;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }
}
