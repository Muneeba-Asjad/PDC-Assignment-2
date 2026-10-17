
public class TradingEngineApp {
    public static void main(String[] args) {

        TradeEvent event1 = new FIXOrderEvent(
                "FIX001", System.currentTimeMillis(),
                1000.0, "ORD101");

        TradeEvent event2 = new ReutersQuoteEvent(
                "Reuters001", System.currentTimeMillis(),
                100.0, 99.5, 100.5);

        event1.dispatch();
        event2.dispatch();
    }
}
