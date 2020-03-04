package tracker.domain;

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
}
