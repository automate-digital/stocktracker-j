package tracker.features.portfoliomgt;

import tracker.domain.Portfolio;
import tracker.domain.Stock;
import tracker.service.PricingService;
import tracker.storage.Persistence;

import java.util.List;
import java.util.Map;

public class ManagePortfolio {
    PricingService pricingService;
    Persistence persistence;
    Portfolio portfolio;

    public ManagePortfolio(Persistence persistence, PricingService pricing) {
        this.persistence = persistence;
        this.pricingService = pricing;
        this.portfolio = new Portfolio();
    }

    public void add(String ticker, int units) throws Exception {
        if (find(ticker)) portfolio.delete(ticker); // duplicates not allowed
        load(ticker, units);
        save(ticker, units);
    }

    private boolean find(String ticker) {
        Map<String, Integer> stocks = persistence.getAllStocks();
        return stocks.containsKey(ticker);
    }

    public void load(String ticker, int units) throws Exception {
        Stock stock = getStock(ticker, units);
        portfolio.add(stock);
    }

    private Stock getStock(String ticker, int units) throws Exception {
        int price = pricingService.getPrice(ticker);
        return new Stock(ticker, units, price);
    }

    private void save(String ticker, int units) {
        String sql = null;
        if (!find(ticker)) {
            sql = "INSERT INTO " + Persistence.STOCKS_TABLE_NAME
                    + " VALUES('" + ticker + "', '" + units + "')";
        } else {
            sql = "UPDATE " + Persistence.STOCKS_TABLE_NAME
                    + " SET units=" + units + " WHERE ticker='" + ticker + "'";
        }
        persistence.executeQuery(sql);
    }

    public void delete(String ticker) {
        portfolio.delete(ticker);
        remove(ticker);
    }

    private void remove(String ticker) {
        String sql = "DELETE FROM " + Persistence.STOCKS_TABLE_NAME
                + " WHERE ticker='" + ticker + "'";
        persistence.executeQuery(sql);
    }

    public double getValuation() {
        return portfolio.getValue();
    }

    public List<Stock> getListing() {
        return portfolio.getStocks();
    }
}
