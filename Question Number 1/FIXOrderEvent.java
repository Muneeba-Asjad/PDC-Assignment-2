
public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp,
            double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;
        double total = basePrice + fee;

        System.out.println("\nFIX Stock Purchase");
        System.out.println("Order ID: " + clOrdId);
        System.out.println("Price: " + basePrice);
        System.out.println("Fee: " + fee);
        System.out.println("Total: " + total);
    }
}
