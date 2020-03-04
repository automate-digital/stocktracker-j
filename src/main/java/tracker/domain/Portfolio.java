package tracker.domain;

import java.util.List;

public class Portfolio {
    List<Stock> stocks;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void add(Stock stock) {
        stocks.add(stock);
    }
}
