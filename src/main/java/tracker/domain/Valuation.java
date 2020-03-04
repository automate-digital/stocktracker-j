package tracker.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Valuation {
    PricingService pricing;
    int value;

    public Valuation(PricingService pricing) {
        this.pricing = pricing;
    }

    public int getPrice(Stock stock) {
        return pricing.getPrice(stock.getTicker());
    }

    public double getValue(Portfolio portfolio) {
        int totalInPence = 0;
        for (Stock stock : portfolio.getStocks()) {
            value = stock.getUnits() * stock.getPrice();
            totalInPence += value;
        }

        int decimalPlaces = 2;
        BigDecimal penceInPound = new BigDecimal(100);
        BigDecimal bd = BigDecimal.valueOf(totalInPence);
        return bd.divide(penceInPound, decimalPlaces,RoundingMode.HALF_DOWN).doubleValue();
    }
}
