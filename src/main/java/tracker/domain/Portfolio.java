package tracker.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    List<Stock> stocks;

    public Portfolio() {
        stocks = new ArrayList<Stock>();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void add(Stock stock) {
        stocks.add(stock);
    }

    public void delete(String ticker) {
        stocks.removeIf(obj -> obj.getTicker().equals(ticker));
    }

    public double getValue() {
        int totalInPence = 0;
        for (Stock stock : stocks) {
            totalInPence += stock.getUnits() * stock.getPrice();
        }

        int decimalPlaces = 2;
        BigDecimal penceInPound = new BigDecimal(100);
        BigDecimal bd = BigDecimal.valueOf(totalInPence);
        return bd.divide(penceInPound, decimalPlaces, RoundingMode.HALF_DOWN).doubleValue();
    }

}
