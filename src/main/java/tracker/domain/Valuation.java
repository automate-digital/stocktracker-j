package tracker.domain;

public class Valuation {
    PricingService pricing;
    int value;

    public Valuation(PricingService pricing) {
        this.pricing = pricing;
    }

    public int getValue(Portfolio portfolio) {
        int total = 0;
        for (Stock stock : portfolio.getStocks()) {
            value = pricing.getPrice(stock.getTicker());
            total += value;
        }
        return total;
    }
}
