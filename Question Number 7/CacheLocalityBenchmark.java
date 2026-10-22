public class CacheLocalityBenchmark {
    static int N = 2000;

    static double[][] matrix = new double[N][N];

    static void fillMatrix() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i + j;
            }
        }
    }

    static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {

                sum += matrix[i][j];
            }
        }
        return sum;
    }

    static double traverseColumnMajor() {
        double sum = 0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        fillMatrix();
        long rowTime = 0;
        long colTime = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            double rowSum = traverseRowMajor();
            long end = System.nanoTime();
            rowTime += end - start;
            start = System.nanoTime();
            double colSum = traverseColumnMajor();
            end = System.nanoTime();
            colTime += end - start;
        }
        double rowMs = rowTime / 1_000_000.0;
        double colMs = colTime / 1_000_000.0;
        System.out.println("Matrix Size: " + N + " x " + N);
        System.out.println("Iterations: 10");
        System.out.printf(
                "Row-Major Total Time: %.2f ms%n", rowMs);
        System.out.printf(
                "Column-Major Total Time: %.2f ms%n", colMs);
        System.out.printf(
                "Row-Major Average Time: %.2f ms%n", rowMs / 10);
        System.out.printf(
                "Column-Major Average Time: %.2f ms%n", colMs / 10);
        System.out.printf(
                "Speedup Ratio (Column / Row): %.2f%n",
                colMs / rowMs);
    }
}