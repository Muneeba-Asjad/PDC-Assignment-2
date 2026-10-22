public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Default Master Thread: " + Thread.currentThread().getName());

        TelemetryThread telemetryWorker = new TelemetryThread("Telemetry-Worker");
        Thread logWorker = new Thread(new LogProcessorTask(), "Log-Worker");

        System.out.println("Starting asynchronous background tasks...");
        telemetryWorker.start();
        logWorker.start();

        System.out.println("Active Threads Spawned. Master thread continues non-blocking execution.");
    }
}