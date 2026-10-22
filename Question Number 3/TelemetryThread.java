public class TelemetryThread extends Thread {
    public TelemetryThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] CPU & Memory Metric Sample: " + i);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}