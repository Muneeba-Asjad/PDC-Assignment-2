
import java.util.*;
import java.util.concurrent.*;

public class InventoryCatalogManager {
    static int THREADS = 10;
    static int OPERATIONS = 50000;

    static void benchmarkSynchronizedMap()
            throws Exception {
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 1000; i++) {
            map.put("P" + i, 100);
        }
        long start = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        for (int t = 0; t < THREADS; t++) {
            pool.execute(() -> {
                for (int i = 0; i < OPERATIONS / THREADS; i++) {
                    String id = "P" + (i % 1000);
                    Integer stock = map.get(id);
                    map.compute(id, (key, value) -> value + 1);
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.nanoTime();
        double time = (end - start) / 1e9;
        System.out.println("\nSynchronizedMap");
        System.out.println("Execution Time: " + time + " seconds");
        System.out.println("Throughput: "
                + (100000 / time) + " operations/second");
    }

    static void benchmarkConcurrentMap()
            throws Exception {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("P" + i, 100);
        }
        long start = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        for (int t = 0; t < THREADS; t++) {
            pool.execute(() -> {
                for (int i = 0; i < OPERATIONS / THREADS; i++) {
                    String id = "P" + (i % 1000);
                    Integer stock = map.get(id);
                    map.compute(id, (key, value) -> value + 1);
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.nanoTime();
        double time = (end - start) / 1e9;
        System.out.println("\nConcurrentHashMap");
        System.out.println("Execution Time: " + time + " seconds");
        System.out.println("Throughput: "
                + (100000 / time) + " operations/second");
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Inventory Catalog Benchmark");
        System.out.println("Threads: " + THREADS);
        System.out.println("Operations: " + OPERATIONS);

        benchmarkSynchronizedMap();
        benchmarkConcurrentMap();
    }
}