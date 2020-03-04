package tracker.features.portfoliomgt;

import tracker.domain.Portfolio;
import tracker.domain.Stock;
import tracker.service.PricingService;
import tracker.storage.Persistence;

import java.util.List;

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
        load(ticker, units);
        save(ticker, units);
    }

    public void load(String ticker, int units) throws Exception {
        Stock stock = getStock(ticker, units);
        portfolio.add(stock);
    }

    public void delete(String ticker) {
        portfolio.delete(ticker);
        remove(ticker);
    }

    public double getValuation() {
        return portfolio.getValue();
    }

    public List<Stock> getListing() {
        return portfolio.getStocks();
    }

    private Stock getStock(String ticker, int units) throws Exception {
        int price = pricingService.getPrice(ticker);
        return new Stock(ticker, units, price);
    }

    private void save(String ticker, int units) {
        String sql = "INSERT INTO " + Persistence.STOCKS_TABLE_NAME
                + " VALUES('" + ticker + "', '" + units + "')";
        persistence.executeQuery(sql);
    }

    private void remove(String ticker) {
        String sql = "DELETE FROM " + Persistence.STOCKS_TABLE_NAME
                + " WHERE ticker='" + ticker + "'";
        persistence.executeQuery(sql);
    }
}
