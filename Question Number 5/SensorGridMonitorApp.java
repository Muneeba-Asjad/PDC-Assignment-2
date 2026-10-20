import java.util.Random;
import java.util.concurrent.*;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws Exception {
        int size = 50000;
        double[] sensorData = new double[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            sensorData[i] = 20 + random.nextDouble() * 80;
        }
        sensorData[100] = -10.5;
        sensorData[25000] = 150.75;
        ExecutorService pool = Executors.newFixedThreadPool(2);
        long startTime = System.nanoTime();
        try {
            SensorSearchEngine minSearch = new ParallelMinSearch(sensorData, 0, size);
            SensorSearchEngine maxSearch = new ParallelMaxSearch(sensorData, 0, size);
            Future<Double> minResult = pool.submit(() -> minSearch.processGrid());
            Future<Double> maxResult = pool.submit(() -> maxSearch.processGrid());
            double minTemperature = minResult.get();
            double maxVibration = maxResult.get();
            long endTime = System.nanoTime();
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("=== Sensor Grid Monitor ===");
            System.out.println("Total sensor readings: " + size);
            System.out.println("Minimum temperature: "
                    + minTemperature);
            System.out.println("Maximum vibration: "
                    + maxVibration);
            System.out.printf("Execution time: %.3f ms%n", timeMs);
        } finally {
            pool.shutdown();
        }
    }
}
